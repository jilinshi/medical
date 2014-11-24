package com.medical.action;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

import com.medical.common.FileHandle;
import com.medical.common.FilesFilter;
import com.medical.dto.AspApproveDTO;
import com.medical.dto.ChronicApproveDTO;
import com.medical.dto.ChronicBillDTO;
import com.medical.dto.ChronicStatusDTO;
import com.medical.dto.CurrectChronicDTO;
import com.medical.dto.ImgDTO;
import com.medical.dto.OrganDTO;
import com.medical.dto.PersonDTO;
import com.medical.dto.UserInfoDTO;
import com.medical.model.JzAspapproveExample;
import com.medical.model.JzChronicapproveExample;
import com.medical.model.JzChronicapproveExample.Criteria;
import com.medical.model.MemberBaseinfoExample;
import com.medical.service.ChronicApproveService;
import com.medical.service.ChronicBillService;
import com.medical.system.DictionaryHandle;
import com.medical.system.impl.DictionaryHandleImpl;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class ChronicAction extends ActionSupport {
	static Logger log = Logger.getLogger(ChronicAction.class);
	private static final long serialVersionUID = 1L;
	private ChronicApproveDTO chronicApproveDTO;
	private AspApproveDTO aspApproveDTO;
	private ChronicApproveService chronicApproveService;
	private ChronicBillService chronicBillService;
	private Integer cur_page;
	private String toolsmenu;
	private String result;
	private List<PersonDTO> persons;
	private List<ChronicApproveDTO> cas;
	private List<CurrectChronicDTO> ccs;
	private List<AspApproveDTO> aas;
	private HashMap<String, String> chronics;
	private String term;
	private String value;
	private List<ImgDTO> healthrecordimgs;
	private PersonDTO personDTO;
	private HashMap<String, String> hrs;
	private ChronicStatusDTO chronicStatusDTO;
	private List<ChronicBillDTO> cbs;
	private HashMap<String, String> asps;
	private File chronic;
	private String chronicContentType;
	private String chronicFileName;
	private String ds;
	private String app1;
	private String app2;
	private String app3;
	private List<OrganDTO> orgs;
	private String orgid;
	private String icdid;
	private String flag;
	private String memberId;
	private String apds;
	private String state;

	@SuppressWarnings({ "rawtypes" })
	public String checkChronicMemberInit() {
		Map session = ActionContext.getContext().getSession();
		UserInfoDTO user = (UserInfoDTO) session.get("user");
		String orgno = user.getOrganizationId();
		if (8 == orgno.length()) {
			chronicApproveDTO = new ChronicApproveDTO();
			chronicApproveDTO.setFamilyno("");
			return "1";
		} else if (6 == orgno.length()) {
			return "2";
		} else if (4 == orgno.length()) {
			return "2";
		} else {
			return "4";
		}
	}

	public String checkChronicMember() {
		persons = chronicApproveService
				.findChronicMembersByFamilyno(chronicApproveDTO.getFamilyno());
		return SUCCESS;
	}

	@SuppressWarnings("rawtypes")
	public String approveChronicMemberInit() {
		Map session = ActionContext.getContext().getSession();
		UserInfoDTO user = (UserInfoDTO) session.get("user");
		String orgno = user.getOrganizationId();
		if (null != chronicApproveDTO.getChronicapproveId()
				&& "q".equals(chronicApproveDTO.getSsn())) {
			chronicApproveDTO = chronicApproveService
					.findChronicApproveDTOByID(chronicApproveDTO);
			if (orgno.length() == 8) {
			} else if (orgno.length() == 6) {
				chronicApproveDTO.setApridea(chronicApproveDTO.getApridea2());
				chronicApproveDTO.setAprresult(chronicApproveDTO
						.getAprresult2());
			} else if (orgno.length() == 4) {
				chronicApproveDTO.setApridea(chronicApproveDTO.getApridea3());
				chronicApproveDTO.setAprresult(chronicApproveDTO
						.getAprresult3());
			}
		} else if ("d".equals(chronicApproveDTO.getSsn())) {
			chronicApproveService.removeChronicApprove(chronicApproveDTO);
			result = "作废成功";
			return "dresult";
		} else if ("m".equals(chronicApproveDTO.getSsn())) {
			chronicApproveDTO = chronicApproveService
					.findChronicApproveDTOByID(chronicApproveDTO);
			if (orgno.length() == 8) {
				chronicApproveDTO.setAprresult(chronicApproveDTO
						.getAprresult1());
				chronicApproveDTO.setApridea(chronicApproveDTO.getApridea1());
				chronicApproveDTO.setAprtime(chronicApproveDTO.getAprtime1());
				chronicApproveDTO.setAprperson(chronicApproveDTO
						.getAprperson1());
			} else if (orgno.length() == 6) {
				chronicApproveDTO.setAprresult(chronicApproveDTO
						.getAprresult2());
				chronicApproveDTO.setApridea(chronicApproveDTO.getApridea2());
				chronicApproveDTO.setAprtime(chronicApproveDTO.getAprtime2());
				chronicApproveDTO.setAprperson(chronicApproveDTO
						.getAprperson2());
			} else if (orgno.length() == 4) {
				chronicApproveDTO.setAprresult(chronicApproveDTO
						.getAprresult3());
				chronicApproveDTO.setApridea(chronicApproveDTO.getApridea3());
				chronicApproveDTO.setAprtime(chronicApproveDTO.getAprtime3());
				chronicApproveDTO.setAprperson(chronicApproveDTO
						.getAprperson3());
			}
		} else {
			List<ChronicApproveDTO> list = chronicApproveService
					.findCheckApproves(chronicApproveDTO);
			if (null != list && list.size() > 0) {
				chronicApproveDTO = list.get(0);
				System.out.println(">>" + chronicApproveDTO.getMemberId());
				chronicApproveDTO = chronicApproveService
						.findChronicApproveDTOByID(chronicApproveDTO);
				System.out.println(">>" + chronicApproveDTO.getMemberId());
				cbs = chronicBillService.findChronicBillBySSN(
						chronicApproveDTO.getMemberId(),
						chronicApproveDTO.getMemberType());
				System.out.println(">>" + chronicApproveDTO.getMemberId());
				result = "此人员正在审批中或者已经审批完成";
				return "result";
			} else {
				chronicApproveDTO = chronicApproveService
						.findChronicMembersBySSN(chronicApproveDTO);
				chronicApproveDTO.setChronicapproveId(null);
			}
		}
		hrs = chronicApproveService.findHrsBySsn(
				chronicApproveDTO.getMemberId(), (short) 1);
		DictionaryHandle dictionaryHandle = new DictionaryHandleImpl();
		chronics = dictionaryHandle.getDsMap("007");
		return SUCCESS;
	}

	@SuppressWarnings("rawtypes")
	public String approveChronicMember() {
		Map session = ActionContext.getContext().getSession();
		UserInfoDTO user = (UserInfoDTO) session.get("user");
		String orgno = user.getOrganizationId();
		if (8 == orgno.length()) {
			chronicApproveDTO.setAprtime1(new Date());
			chronicApproveDTO.setAprperson1(user.getUsername());
			chronicApproveDTO.setApridea1(chronicApproveDTO.getApridea());
			chronicApproveDTO.setAprresult1(chronicApproveDTO.getAprresult());
		} else if (6 == orgno.length()) {
			chronicApproveDTO.setApridea2(chronicApproveDTO.getApridea());
			chronicApproveDTO.setAprresult2(chronicApproveDTO.getAprresult());
			chronicApproveDTO.setAprtime2(new Date());
			chronicApproveDTO.setAprperson2(user.getUsername());
		} else if (4 == orgno.length()) {
			chronicApproveDTO.setApridea3(chronicApproveDTO.getApridea());
			chronicApproveDTO.setAprresult3(chronicApproveDTO.getAprresult());
			chronicApproveDTO.setAprtime3(new Date());
			chronicApproveDTO.setAprperson3(user.getUsername());
		}
		chronicApproveDTO = chronicApproveService.saveApprove(
				chronicApproveDTO, orgno);
		hrs = chronicApproveService.findHrsBySsn(
				chronicApproveDTO.getMemberId(), (short) 1);
		result = "保存成功";
		return SUCCESS;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public String queryChronicMembers() {
		Map session = ActionContext.getContext().getSession();
		UserInfoDTO user = (UserInfoDTO) session.get("user");
		String orgno = user.getOrganizationId();
		JzChronicapproveExample e = new JzChronicapproveExample();
		if (null == cur_page) {
			cur_page = 1;
			Criteria c1 = e.createCriteria();
			c1.andAreaLike(orgno + "%").andFlagEqualTo(new Short("1"))
					.andAprlevelEqualTo((short) (orgno.length() / 2));
			if ("ssn".equals(term)) {
				c1.andSsnEqualTo(value);
			} else if ("name".equals(term)) {
				c1.andNameLike("%" + value + "%");
			} else if ("familyno".equals(term)) {
				c1.andFamilyIdEqualTo(value);
			}
			if ("医保接口核对".equals(icdid)) {
				c1.andApridea1EqualTo("医保接口核对").andApridea2EqualTo("医保接口核对");
			} else if ("民政审批".equals(icdid)) {
				c1.andApridea1NotEqualTo("医保接口核对").andApridea1NotEqualTo(
						"医保接口核对");
			} else {
			}
			if ("1".equals(ds)) {
				c1.andFamilyIdLike("2202%");
			} else if ("2".equals(ds)) {
				c1.andFamilyIdLike("22002%");
			} else {
			}
			session.put("sql", e);
		} else {
			e = (JzChronicapproveExample) session.get("sql");
		}
		e.setOrderByClause(" chronicapprove_id desc");
		cas = chronicApproveService
				.findApprove(
						"page/business/chronic/querychronicmembers.action",
						cur_page, e);
		toolsmenu = chronicApproveService.getPager().getToolsmenu();
		return SUCCESS;
	}

	@SuppressWarnings({ "rawtypes" })
	public String queryChronicMembersDoneInit() {
		Map session = ActionContext.getContext().getSession();
		UserInfoDTO userinfo = (UserInfoDTO) session.get("user");
		String orgid = userinfo.getOrganizationId();
		DictionaryHandle dictionaryHandle = new DictionaryHandleImpl();
		chronics = dictionaryHandle.getDsMap("007");
		orgs = chronicApproveService.getOrganList(orgid);
		return SUCCESS;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String queryChronicMembersDone() {
		Map session = ActionContext.getContext().getSession();
		UserInfoDTO user = (UserInfoDTO) session.get("user");
		String orgno = orgid;
		String sql = "";
		if (null == cur_page) {
			sql = "select * from v_chronic t where 1=1 ";
			cur_page = 1;
			if ("ssn".equals(term)) {
				sql = sql + " and t.ssn='" + value + "'";
			} else if ("name".equals(term)) {
				sql = sql + " and t.name='" + value + "'";
			} else if ("familyno".equals(term)) {
				sql = sql + " and t.FAMILY_ID='" + value + "'";
			} else if ("paperid".equals(term)) {
				sql = sql + " and t.paperid='" + value + "'";

			}

			if ("1".equals(ds)) {
				sql = sql + " and t.member_type='1'";
			} else if ("2".equals(ds)) {
				sql = sql + " and t.member_type='2'";
			} else {
			}
			
			if ("医保接口核对".equals(apds)) {
				sql = sql + " and t.APRIDEA1='医保接口核对' and t.APRIDEA2='医保接口核对'";
			} else if ("民政审批".equals(apds)) {
				sql = sql + " and (t.APRIDEA1 <> '医保接口核对' or t.APRIDEA2 <> '医保接口核对'" 
						+ " or t.APRIDEA1 is null or t.APRIDEA2 is null) ";
			} else {
			}

			if("".equals(state)){
				
			}else if("0".equals(state)){
				sql = sql + " and (t.state='0' or t.state is null) ";
			}else if("1".equals(state)){
				sql = sql + " and t.state='1' ";
			}
			
			if ("".equals(app1)) {
			} else if ("1".equals(app1)) {
				sql = sql + " and t.APRRESULT1='1'";
			} else if ("2".equals(app1)) {
				sql = sql + " and t.APRRESULT1='2'";
			}
			if ("".equals(app2)) {
			} else if ("1".equals(app2)) {
				sql = sql + " and t.APRRESULT2='1'";
			} else if ("2".equals(app2)) {
				sql = sql + " and t.APRRESULT2='2'";
			}
			if ("".equals(app3)) {
			} else if ("1".equals(app3)) {
				sql = sql + " and t.APRRESULT3='1'";
			} else if ("2".equals(app2)) {
				sql = sql + " and t.APRRESULT3='2'";
			}
			if (!"".equals(icdid)) {
				sql = sql + " and   REGEXP_INSTR   (t.entitys,'" + icdid
						+ "')>0";
			}
			if (!"".equals(flag)) {
				sql = sql + " and t.FLAG='" + flag + "'";
			}
			if (!"".equals(orgno)) {
				sql = sql + " and t.area like '" + orgno + "%'";
			}
			sql = sql + "  order by  chronicapprove_id desc ";
			session.put("sql", sql);
		} else {
			sql = (String) session.get("sql");
		}

		cas = chronicApproveService.findApprove1(
				"page/business/chronic/querychronicmembersdone.action?orgid="
						+ orgid, cur_page, sql);
		DictionaryHandle dictionaryHandle = new DictionaryHandleImpl();
		chronics = dictionaryHandle.getDsMap("007");
		orgs = chronicApproveService.getOrganList(user.getOrganizationId());
		toolsmenu = chronicApproveService.getPager().getToolsmenu();
		return SUCCESS;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String queryChronicMembersCard() {
		Map session = ActionContext.getContext().getSession();
		UserInfoDTO user = (UserInfoDTO) session.get("user");
		String orgno = user.getOrganizationId();
		String sql = "";
		if (null == cur_page) {
			sql = "select t.FAMILY_ID,"
					+ " t. NAME,"
					+ " t. SSN,"
					+ "   icd.name as  ENTITY,"
					+ " t. STATE,"
					+ " t. APPTIME,"
					+ " t. FLAG,"
					+ " t. PAPERID,"
					+ " t. MEMBER_ID,"
					+ " t. MEMBER_TYPE,"
					+ " d. OPTTIME,"
					+ " d. STS,"
					+ " d. REMARK"
					+ "  ,t.on_no , t.an_filename ,ap.chronicapprove_id from (select ss.family_id,"
					+ " ss.name,"
					+ " ss.ssn,"
					+ " ss.entity,"
					+ " ss.state,"
					+ " ss.apptime,"
					+ " ss.flag,"
					+ " mem.paperid,"
					+ " ss.member_id,"
					+ " ss.member_type ,mem.on_no , mem.an_filename "
					+ " from jz_chronicstatus ss, member_baseinfo mem"
					+ " where ss.member_id = mem.member_id"
					+ " and ss.member_type = mem.ds) t"
					+ " left join iccard d on d.member_type = t.MEMBER_TYPE"
					+ " and d.member_id = t.MEMBER_ID"
					+ " and d.data_flag = 1  left join icd10 icd on icd.icd_id=t.entity  "
					+ "left join (select max(ap.chronicapprove_id) as chronicapprove_id,ap.member_id, "
					+ " ap.member_type from jz_chronicapprove ap group by ap.member_id, ap.member_type) ap "
					+ " on ap.member_type = t.MEMBER_TYPE   and ap.member_id = t.MEMBER_ID"
					+ " where t.state = '1'" + " and t.flag = '1'";

			cur_page = 1;
			if ("ssn".equals(term)) {
				sql = sql + " and t.ssn='" + value + "'";
			} else if ("name".equals(term)) {
				sql = sql + " and t.name like '%" + value + "%'";
			} else if ("familyno".equals(term)) {
				sql = sql + " and t.FAMILY_ID='" + value + "'";
			} else if ("paperid".equals(term)) {
				sql = sql + " and t.paperid='" + value + "'";

			}

			if ("1".equals(ds)) {
				sql = sql + " and t.member_type='1'";
			} else if ("2".equals(ds)) {
				sql = sql + " and t.member_type='2'";
			} else {
			}

			/*
			 * if ("".equals(app1)) { } else if ("1".equals(app1)) { sql = sql +
			 * " and t.APRRESULT1='1'"; } else if ("2".equals(app1)) { sql = sql
			 * + " and t.APRRESULT1='2'"; } if ("".equals(app2)) { } else if
			 * ("1".equals(app2)) { sql = sql + " and t.APRRESULT2='1'"; } else
			 * if ("2".equals(app2)) { sql = sql + " and t.APRRESULT2='2'"; } if
			 * (!"".equals(icdid)) { sql = sql + " and t.ENTITY='" + icdid +
			 * "'"; }
			 */
			if (!"".equals(orgno)) {
				sql = sql + " and t.on_no like '" + orgno + "%'";
			}
			sql = sql + " order by t.family_id";
			session.put("sql", sql);
		} else {
			sql = (String) session.get("sql");
		}

		cas = chronicApproveService.findApprove2(
				"page/business/chronic/querychronicmemberscard.action",
				cur_page, sql);
		toolsmenu = chronicApproveService.getPager().getToolsmenu();
		return SUCCESS;
	}

	public String viewcard() {
		chronicApproveDTO = chronicApproveService
				.findCardView(chronicApproveDTO);
		this.setMemberId(chronicApproveDTO.getMemberId());
		this.setDs(chronicApproveDTO.getMemberType());
		return SUCCESS;
	}

	public String viewApproveList() {
		chronicApproveDTO = chronicApproveService
				.findChronicApproveDTOByID(chronicApproveDTO);
		cbs = chronicBillService.findChronicBillBySSN(
				chronicApproveDTO.getMemberId(),
				chronicApproveDTO.getMemberType());
		hrs = chronicApproveService.findHrsBySsn(
				chronicApproveDTO.getMemberId(),
				new Short(chronicApproveDTO.getMemberType()));
		return SUCCESS;
	}

	// 核对健康档案
	public String checkHealthrecordInit() {
		ArrayList<ImgDTO> list = new ArrayList<ImgDTO>();
		File dir = new File(FileHandle.getInstance().picpath1);
		FilesFilter filter = new FilesFilter("jpg");
		File[] fs = dir.listFiles(filter);
		for (File s : fs) {
			BufferedImage image = null;
			try {
				image = ImageIO.read(s);
			} catch (IOException ex) {
				ex.printStackTrace();
			}
			Long width = new Long(image.getWidth());// 图片宽度
			Long height = new Long(image.getHeight());// 图片高度
			ImgDTO e = new ImgDTO(width, height);
			e.setWidth(width);
			e.setHeight(height);
			e.setFilename(s.getName());
			e.setSfilepath(s.getPath());
			list.add(e);
		}
		healthrecordimgs = list;
		return SUCCESS;
	}

	public String queryPersonBySsn() {
		personDTO = chronicApproveService.findPersonBySsn(personDTO);
		JSONObject json = new JSONObject();
		json.put("membername", personDTO.getMembername());
		json.put("memberid", personDTO.getMemberId());
		json.put("paperid", personDTO.getPaperid());
		json.put("ssn", personDTO.getSsn());
		result = json.toString();
		return SUCCESS;
	}

	public String checkHealthrecord() {
		JSONObject json = new JSONObject();
		// 文件移动
		if (null == personDTO.getMasterName()
				|| "".equals(personDTO.getMasterName())) {
			json.put("str", "请重新选择健康卡片");
			json.put("flag", "0");
			json.put("filename", personDTO.getMasterName());
		} else {
			File oldfile = new File(
					(FileHandle.getInstance().picpath1 + "/" + personDTO
							.getMasterName()).trim());
			File dir = new File((FileHandle.getInstance().uploadpath1.trim()
					+ "/" + personDTO.getSsn())
					+ "/");
			if (!dir.exists()) {
				dir.mkdirs();
			}
			FileHandle.getInstance().copy(oldfile,
					new File(dir.getPath() + "/" + oldfile.getName()));
			oldfile.delete();

			chronicApproveService.saveHealthrecord(personDTO.getRprkind(),
					personDTO.getSsn(), personDTO.getMemberId(), (short) 1,
					new File(dir.getPath() + "/" + oldfile.getName()));
			json.put("str", "核对成功");
			json.put("flag", "1");
			json.put("filename", personDTO.getMasterName());
		}
		result = json.toString();
		return SUCCESS;
	}

	public String printhr() {
		cas = chronicApproveService.findPrinthr(chronicApproveDTO);
		if (null != cas) {
			chronicApproveDTO = cas.get(0);
			chronics = chronicApproveService.findPrinthrMap(chronicApproveDTO
					.getFamilyno(),
					chronicApproveDTO.getFamilyId().substring(0, 10));
		}
		return SUCCESS;
	}

	@SuppressWarnings("rawtypes")
	public String printasp() {
		Map session = ActionContext.getContext().getSession();
		UserInfoDTO user = (UserInfoDTO) session.get("user");
		String orgno = user.getOrganizationId();
		aspApproveDTO.setOnno(orgno);
		aas = chronicApproveService.findPrintasp(aspApproveDTO);
		if (null != aas && aas.size() == 1) {
			aspApproveDTO = aas.get(0);
			setAsps(chronicApproveService.findPrintAspMap(aspApproveDTO
					.getFamilyId(), aspApproveDTO.getFamilyId()
					.substring(0, 10), aspApproveDTO.getFamilyno()));
			return SUCCESS;
		} else {
			result = "审批信息出错，请作废重新审批！";
			return ERROR;
		}

	}

	// 因病申请低保
	@SuppressWarnings("rawtypes")
	public String applySalInit() {
		// 进入街道
		// 进入区县
		// 进入市级
		Map session = ActionContext.getContext().getSession();
		UserInfoDTO user = (UserInfoDTO) session.get("user");
		String orgno = user.getOrganizationId();
		if (8 == orgno.length()) {
			return "1";
		} else if (6 == orgno.length()) {
			return "2";
		} else if (4 == orgno.length()) {
			result = "没有操作这个功能权限！";
			return SUCCESS;
		} else {
			result = "没有操作这个功能权限！";
			return SUCCESS;
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String queryApplySalPerson() {

		Map session = ActionContext.getContext().getSession();
		UserInfoDTO user = (UserInfoDTO) session.get("user");
		String orgno = user.getOrganizationId();
		MemberBaseinfoExample e = new MemberBaseinfoExample();
		if (null == cur_page || "".equals(cur_page)) {
			cur_page = 1;
			com.medical.model.MemberBaseinfoExample.Criteria c = e
					.createCriteria();
			c.andFamilynoLike(orgno + "%");
			if ("ssn".equals(term)) {
				c.andSsnEqualTo(value);
			} else if ("name".equals(term)) {
				c.andMembernameLike("%" + value + "%");
			}
			c.andIsybsqdbEqualTo(new BigDecimal(2)).andPersonstateEqualTo("正常");
			session.put("sql", e);
		} else {
			e = (MemberBaseinfoExample) session.get("sql");
		}
		e.setOrderByClause("familyno , relmaster");
		persons = chronicApproveService.findAspMembersByFamilyno(
				"page/business/applysalvation/queryapplysalperson.action",
				cur_page, e);
		toolsmenu = chronicApproveService.getPager().getToolsmenu();
		return SUCCESS;
	}

	public String approveAspInit() {
		String memberId = aspApproveDTO.getMemberId();
		String memberType = aspApproveDTO.getMemberType();
		BigDecimal aspapproveId = aspApproveDTO.getAspapproveId();
		aspApproveDTO = new AspApproveDTO();
		if (aspapproveId.compareTo(new BigDecimal("-1")) == 0) {
			List<AspApproveDTO> asps = chronicApproveService
					.findAspApprovesBySsn(memberId, memberType);
			if (null != asps && asps.size() > 0) {
				result = "此人员正在审批中或者已经审批完成";
				return "result";
			} else {
				aspApproveDTO = chronicApproveService.findAspApproveBySsn(
						memberId, memberType);
			}
		} else {
			if ("d".equals(memberId)) {
				chronicApproveService.removeAsp(aspapproveId);
				result = "作废成功";
				return "result";
			} else {
				aspApproveDTO = chronicApproveService
						.findAspApproveById(aspapproveId);
			}
		}
		hrs = chronicApproveService.findHrsBySsn(aspApproveDTO.getMemberId(),
				(short) 2);
		return SUCCESS;
	}

	@SuppressWarnings("rawtypes")
	public String approveAsp() {
		Map session = ActionContext.getContext().getSession();
		UserInfoDTO user = (UserInfoDTO) session.get("user");
		String orgno = user.getOrganizationId();
		if (8 == orgno.length()) {
			aspApproveDTO.setAprtime1(new Date());
			aspApproveDTO.setAprperson1(user.getUsername());
			aspApproveDTO.setApridea1(aspApproveDTO.getApridea());
			aspApproveDTO.setAprresult1(aspApproveDTO.getAprresult());
		} else if (6 == orgno.length()) {
			aspApproveDTO.setApridea2(aspApproveDTO.getApridea());
			aspApproveDTO.setAprresult2(aspApproveDTO.getAprresult());
			aspApproveDTO.setAprtime2(new Date());
			aspApproveDTO.setAprperson2(user.getUsername());
		} else if (4 == orgno.length()) {
			aspApproveDTO.setApridea3(aspApproveDTO.getApridea());
			aspApproveDTO.setAprresult3(aspApproveDTO.getAprresult());
			aspApproveDTO.setAprtime3(new Date());
			aspApproveDTO.setAprperson3(user.getUsername());
		}
		aspApproveDTO = chronicApproveService.saveAspApprove(aspApproveDTO,
				orgno);
		aspApproveDTO = chronicApproveService.findAspApproveById(aspApproveDTO
				.getAspapproveId());
		hrs = chronicApproveService.findHrsBySsn(aspApproveDTO.getMemberId(),
				(short) 2);
		result = "保存成功";
		return SUCCESS;

	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String queryAsp() {
		Map session = ActionContext.getContext().getSession();
		UserInfoDTO user = (UserInfoDTO) session.get("user");
		String orgno = user.getOrganizationId();
		JzAspapproveExample e = new JzAspapproveExample();
		if (null == cur_page) {
			cur_page = 1;
			com.medical.model.JzAspapproveExample.Criteria c = e
					.createCriteria();
			c.andFamilyIdLike(orgno + "%").andFlagEqualTo(new Short("1"))
					.andAprlevelEqualTo((short) (orgno.length() / 2));
			if ("ssn".equals(term)) {
				c.andSsnEqualTo(value);
			} else if ("name".equals(term)) {
				c.andNameLike("%" + value + "%");
			}
			session.put("sql", e);
		} else {
			e = (JzAspapproveExample) session.get("sql");
		}
		e.setOrderByClause("family_id, aprtime1 desc , aspapprove_id desc");
		aas = chronicApproveService.findAspApprove(
				"page/business/applysalvation/queryasp.action", cur_page, e);
		toolsmenu = chronicApproveService.getPager().getToolsmenu();
		return SUCCESS;
	}

	@SuppressWarnings({ "unchecked", "rawtypes", "unused" })
	public String queryAspDone() {
		Map session = ActionContext.getContext().getSession();
		UserInfoDTO user = (UserInfoDTO) session.get("user");
		String orgno = user.getOrganizationId();
		JzAspapproveExample e = new JzAspapproveExample();
		if (null == cur_page) {
			cur_page = 1;
			com.medical.model.JzAspapproveExample.Criteria c = e
					.createCriteria();
			c.andFamilyIdLike(orgid + "%").andFlagEqualTo(new Short("1"));
			if ("ssn".equals(term)) {
				c.andSsnEqualTo(value);
			} else if ("name".equals(term)) {
				c.andNameLike("%" + value + "%");
			}
			if ("1".equals(app1)) {
				c.andAprresult1EqualTo(new Short(app1));
			}
			if ("2".equals(app1)) {
				c.andAprresult1EqualTo(new Short(app1));
			}
			if ("1".equals(app2)) {
				c.andAprresult2EqualTo(new Short(app2));
			}
			if ("2".equals(app2)) {
				c.andAprresult2EqualTo(new Short(app2));
			}
			c.andFlagEqualTo((short) 1);
			session.put("sql", e);
		} else {
			e = (JzAspapproveExample) session.get("sql");
		}
		e.setOrderByClause("aprtime2 desc ,  printcount");
		aas = chronicApproveService
				.findAspApprove(
						"page/business/applysalvation/queryaspdone.action",
						cur_page, e);
		toolsmenu = chronicApproveService.getPager().getToolsmenu();
		return SUCCESS;
	}

	public String viewAsp() {
		aspApproveDTO = chronicApproveService.findAspApproveById(aspApproveDTO
				.getAspapproveId());
		hrs = chronicApproveService.findHrsBySsn(aspApproveDTO.getMemberId(),
				(short) 2);
		return SUCCESS;
	}

	public String printAsp() {
		return SUCCESS;
	}

	public String checkAspInit() {
		ArrayList<ImgDTO> list = new ArrayList<ImgDTO>();
		File dir = new File(FileHandle.getInstance().picpath2);
		FilesFilter filter = new FilesFilter("jpg");
		File[] fs = dir.listFiles(filter);
		if (null != fs) {
			for (File s : fs) {
				ImgDTO e = new ImgDTO();
				e.setFilename(s.getName());
				e.setSfilepath(s.getPath());
				list.add(e);
			}
		}
		healthrecordimgs = list;
		return SUCCESS;
	}

	public String checkAsp() {
		JSONObject json = new JSONObject();
		// 文件移动
		if (null == personDTO.getMasterName()
				|| "".equals(personDTO.getMasterName())) {

			if ("0".equals(personDTO.getRprtype())) {
				chronicApproveService.saveYBSQ(personDTO.getRprtype(),
						personDTO.getMemberId());
				json.put("str", "改变因病申请低保状态为不同意!");
				json.put("flag", "0");
				json.put("filename", personDTO.getMasterName());
			} else {

				json.put("str", "请重新选择健康卡片");
				json.put("flag", "0");
				json.put("filename", personDTO.getMasterName());
			}
		} else {
			File oldfile = new File(
					(FileHandle.getInstance().picpath2 + "/" + personDTO
							.getMasterName()).trim());
			File dir = new File((FileHandle.getInstance().uploadpath2.trim()
					+ "/" + personDTO.getMemberId())
					+ "/");
			if (!dir.exists()) {
				dir.mkdirs();
			}
			FileHandle.getInstance().copy(oldfile,
					new File(dir.getPath() + "/" + oldfile.getName()));
			oldfile.delete();
			chronicApproveService.saveHealthrecord(personDTO.getRprkind(),
					personDTO.getSsn(), personDTO.getMemberId(), (short) 2,
					new File(dir.getPath() + "/" + oldfile.getName()));
			chronicApproveService.saveYBSQ(personDTO.getRprtype(),
					personDTO.getMemberId());
			json.put("str", "核对成功");
			json.put("flag", "1");
			json.put("filename", personDTO.getMasterName());
		}
		result = json.toString();
		return SUCCESS;
	}

	public String queryAspBySsn() {
		try {
			personDTO = chronicApproveService.findAspBySsn(personDTO);
			aas = chronicApproveService.findAspsBySsn(personDTO);
			JSONArray arr = new JSONArray();
			for (AspApproveDTO a : aas) {
				JSONObject d = JSONObject.fromObject(a);
				arr.add(d);
			}
			JSONObject json = new JSONObject();
			json.put("membername", personDTO.getMembername());
			json.put("memberid", personDTO.getMemberId());
			json.put("paperid", personDTO.getPaperid());
			String ssn = personDTO.getSsn();
			if (null == personDTO.getSsn() || "".equals(personDTO.getSsn())) {
				ssn = "";
			}
			json.put("ssn", ssn);
			json.put("aas", arr);
			result = json.toString();
		} catch (RuntimeException re) {
			re.printStackTrace();
		}
		return SUCCESS;
	}

	public String delfile() {
		FileHandle fh = new FileHandle();
		String path = "";
		if ("1".equals(term)) {
			path = fh.picpath1;
		} else if ("2".equals(term)) {
			path = fh.picpath2;
		}
		log.error("file>>>>>>>>>>>" + path + "" + value);
		File file = new File(path + "\\" + value);
		JSONObject json = new JSONObject();
		json.put("flag", file.delete());
		result = json.toString();
		return SUCCESS;
	}

	@SuppressWarnings("rawtypes")
	public String chronicmemberstat() {
		Map session = ActionContext.getContext().getSession();
		UserInfoDTO user = (UserInfoDTO) session.get("user");
		String orgno = user.getOrganizationId();
		cas = chronicApproveService.findChronicMemberStat(orgno);
		return SUCCESS;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public String querychronicstatinit() {
		Map session = ActionContext.getContext().getSession();
		UserInfoDTO user = (UserInfoDTO) session.get("user");
		String orgno = user.getOrganizationId();
		String sql = "select count(*) as cs, "
				+ " sum(decode(ss.member_type, 1, 1, 0)) as cs1, "
				+ " sum(decode(ss.member_type, 2, 1, 0)) as cs2, "
				+ " (select t.icdname from chronic_icd t where t.id = ss.entity) as ORGNAME"
				+ " from jz_chronicstatus ss where ss.flag = 1 and ss.state = 1 "
				+ " and exists (select * from member_baseinfo mem "
				+ " where mem.member_id = ss.member_id and mem.ds = ss.member_type "
				+ " and mem.on_no like '" + orgno + "%') group by ss.entity";

		cas = chronicApproveService.findChronicstat(sql);
		session.put("sql", sql);
		return SUCCESS;
	}

	public String querychronicstat() {
		return SUCCESS;
	}

	/**
	 * 当前慢性在保户查询
	 * 
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public String querychroniccinit() {
		Map session = ActionContext.getContext().getSession();
		UserInfoDTO userinfo = (UserInfoDTO) session.get("user");
		String orgid = userinfo.getOrganizationId();
		DictionaryHandle dictionaryHandle = new DictionaryHandleImpl();
		chronics = dictionaryHandle.getDsMap("007");
		orgs = chronicApproveService.getOrganList(orgid);
		return SUCCESS;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String querychronicc() {
		Map session = ActionContext.getContext().getSession();
		UserInfoDTO userinfo = (UserInfoDTO) session.get("user");
		String orgid = userinfo.getOrganizationId();
		DictionaryHandle dictionaryHandle = new DictionaryHandleImpl();
		chronics = dictionaryHandle.getDsMap("007");
		orgs = chronicApproveService.getOrganList(orgid);
		String orgno = orgid;
		String sql = "";
		if (null == cur_page) {
			sql = "select * from CURRECT_CHRONIC t where 1=1 and t.STATE = '1' and t.FLAG1 = '1' ";
			cur_page = 1;
			if ("ssn".equals(term)) {
				sql = sql + " and t.ssn='" + value + "'";
			} else if ("name".equals(term)) {
				sql = sql + " and t.name like '%" + value + "%'";
			} else if ("familyno".equals(term)) {
				sql = sql + " and t.FAMILY_ID='" + value + "'";
			} else if ("paperid".equals(term)) {
				sql = sql + " and t.paperid='" + value + "'";
			}
			if ("1".equals(ds)) {
				sql = sql + " and t.member_type='1'";
			} else if ("2".equals(ds)) {
				sql = sql + " and t.member_type='2'";
			} else {
			}

			if (!"".equals(icdid)) {
				sql = sql + " and   REGEXP_INSTR   (t.entitys,'" + icdid
						+ "')>0";
			}
			if ("医保接口核对".equals(apds)) {
				sql = sql + " t.APRIDEA1='医保接口核对' and  t.APRIDEA2='医保接口核对' ";

			} else if ("民政审批".equals(apds)) {
				sql = sql + " t.APRIDEA1<>'医保接口核对' and  t.APRIDEA2<>'医保接口核对' ";
			} else {
			}

			if (!"".equals(orgno)) {
				sql = sql + " and t.AREA like '" + orgno + "%'";
			}
			sql = sql + "  order by  t.FAMILY_ID desc ";
			session.put("sql", sql);
		} else {
			sql = (String) session.get("sql");
		}
		ccs = chronicApproveService.findApprove3(
				"page/business/chronic/querychronicc.action?orgid="
						+ orgid, cur_page, sql);
		 
		toolsmenu = chronicApproveService.getPager().getToolsmenu();
		return SUCCESS;
	}

	// 因病申请低保
	public void setChronicApproveDTO(ChronicApproveDTO chronicApproveDTO) {
		this.chronicApproveDTO = chronicApproveDTO;
	}

	public ChronicApproveDTO getChronicApproveDTO() {
		return chronicApproveDTO;
	}

	public void setChronicApproveService(
			ChronicApproveService chronicApproveService) {
		this.chronicApproveService = chronicApproveService;
	}

	public ChronicApproveService getChronicApproveService() {
		return chronicApproveService;
	}

	public void setPersons(List<PersonDTO> persons) {
		this.persons = persons;
	}

	public List<PersonDTO> getPersons() {
		return persons;
	}

	public int getCur_page() {
		return cur_page;
	}

	public void setCur_page(int curPage) {
		cur_page = curPage;
	}

	public void setCas(List<ChronicApproveDTO> cas) {
		this.cas = cas;
	}

	public List<ChronicApproveDTO> getCas() {
		return cas;
	}

	public void setToolsmenu(String toolsmenu) {
		this.toolsmenu = toolsmenu;
	}

	public String getToolsmenu() {
		return toolsmenu;
	}

	public void setChronics(HashMap<String, String> chronics) {
		this.chronics = chronics;
	}

	public HashMap<String, String> getChronics() {
		return chronics;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getResult() {
		return result;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public String getTerm() {
		return term;
	}

	public void setTerm(String term) {
		this.term = term;
	}

	public void setCur_page(Integer curPage) {
		cur_page = curPage;
	}

	public void setHealthrecordimgs(List<ImgDTO> healthrecordimgs) {
		this.healthrecordimgs = healthrecordimgs;
	}

	public List<ImgDTO> getHealthrecordimgs() {
		return healthrecordimgs;
	}

	public void setPersonDTO(PersonDTO personDTO) {
		this.personDTO = personDTO;
	}

	public PersonDTO getPersonDTO() {
		return personDTO;
	}

	public void setHrs(HashMap<String, String> hrs) {
		this.hrs = hrs;
	}

	public HashMap<String, String> getHrs() {
		return hrs;
	}

	public void setChronicStatusDTO(ChronicStatusDTO chronicStatusDTO) {
		this.chronicStatusDTO = chronicStatusDTO;
	}

	public ChronicStatusDTO getChronicStatusDTO() {
		return chronicStatusDTO;
	}

	public void setChronicBillService(ChronicBillService chronicBillService) {
		this.chronicBillService = chronicBillService;
	}

	public ChronicBillService getChronicBillService() {
		return chronicBillService;
	}

	public void setCbs(List<ChronicBillDTO> cbs) {
		this.cbs = cbs;
	}

	public List<ChronicBillDTO> getCbs() {
		return cbs;
	}

	public AspApproveDTO getAspApproveDTO() {
		return aspApproveDTO;
	}

	public void setAspApproveDTO(AspApproveDTO aspApproveDTO) {
		this.aspApproveDTO = aspApproveDTO;
	}

	public void setAas(List<AspApproveDTO> aas) {
		this.aas = aas;
	}

	public List<AspApproveDTO> getAas() {
		return aas;
	}

	public void setAsps(HashMap<String, String> asps) {
		this.asps = asps;
	}

	public HashMap<String, String> getAsps() {
		return asps;
	}

	public File getChronic() {
		return chronic;
	}

	public void setChronic(File chronic) {
		this.chronic = chronic;
	}

	public String getChronicContentType() {
		return chronicContentType;
	}

	public void setChronicContentType(String chronicContentType) {
		this.chronicContentType = chronicContentType;
	}

	public String getChronicFileName() {
		return chronicFileName;
	}

	public void setChronicFileName(String chronicFileName) {
		this.chronicFileName = chronicFileName;
	}

	public String getDs() {
		return ds;
	}

	public void setDs(String ds) {
		this.ds = ds;
	}

	public String getApp1() {
		return app1;
	}

	public void setApp1(String app1) {
		this.app1 = app1;
	}

	public String getApp2() {
		return app2;
	}

	public void setApp2(String app2) {
		this.app2 = app2;
	}

	public void setOrgs(List<OrganDTO> orgs) {
		this.orgs = orgs;
	}

	public List<OrganDTO> getOrgs() {
		return orgs;
	}

	public String getOrgid() {
		return orgid;
	}

	public void setOrgid(String orgid) {
		this.orgid = orgid;
	}

	public String getIcdid() {
		return icdid;
	}

	public void setIcdid(String icdid) {
		this.icdid = icdid;
	}

	/**
	 * @return the app3
	 */
	public String getApp3() {
		return app3;
	}

	/**
	 * @param app3
	 *            the app3 to set
	 */
	public void setApp3(String app3) {
		this.app3 = app3;
	}

	/**
	 * @return the flag
	 */
	public String getFlag() {
		return flag;
	}

	/**
	 * @param flag
	 *            the flag to set
	 */
	public void setFlag(String flag) {
		this.flag = flag;
	}

	/**
	 * @return the memberId
	 */
	public String getMemberId() {
		return memberId;
	}

	/**
	 * @param memberId
	 *            the memberId to set
	 */
	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public String getApds() {
		return apds;
	}

	public void setApds(String apds) {
		this.apds = apds;
	}

	public List<CurrectChronicDTO> getCcs() {
		return ccs;
	}

	public void setCcs(List<CurrectChronicDTO> ccs) {
		this.ccs = ccs;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

}
