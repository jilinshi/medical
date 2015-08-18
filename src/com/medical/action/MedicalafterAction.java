package com.medical.action;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.tempuri.IService1;
import org.tempuri.IService1Proxy;

import com.medical.common.Base64Image;
import com.medical.common.FileUpload;
import com.medical.dto.ActDTO;
import com.medical.dto.BaseInfoDTO;
import com.medical.dto.CheckDTO;
import com.medical.dto.DeptDTO;
import com.medical.dto.MedicalafterDTO;
import com.medical.dto.OrganDTO;
import com.medical.dto.UserInfoDTO;
import com.medical.dto.YBHospitalDTO;
import com.medical.model.JzMabills;
import com.medical.model.JzMabillsExample;
import com.medical.model.JzMedicalafterExample;
import com.medical.service.BaseinfoService;
import com.medical.service.BusinessService;
import com.medical.service.SearchService;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

import dbbx.IServiceYljzJlc;
import dbbx.IServiceYljzJlcProxy;


public class MedicalafterAction extends ActionSupport {
	private static final long serialVersionUID = -5126976226423666862L;
	private MedicalafterDTO medicalafterDTO;
	private List<MedicalafterDTO> medicalafters;
	private BaseinfoService baseinfoService;
	private SearchService searchService;
	private BaseInfoDTO baseInfoDTO;
	private List<BaseInfoDTO> baseinfos;
	private String result;
	private BusinessService businessService;
	private List<OrganDTO> orgs;
	private List<CheckDTO> months;
	private List<JzMabills> mabills;
	private String m;
	private String[] filebase64;
	private String cur_page;
	private String term;
	private String oid;
	private String operational;
	private String toolsmenu;
	private String value;
	private String opertime1;
	private String opertime2;
	private ActDTO actDTO;
	private String aasql;
	private String ds;
	private String fileName;
	private String content;
	private String type;
	private HashMap<String, String> map;
	private String approveresult;
	private List<YBHospitalDTO> ybhospitals;
	private YBHospitalDTO yBHospitalDTO;
	private String mtype;
	private String ssn;
	private String hid;
	private String serialno;
	private List<DeptDTO> hs;

	public String countassist() {
		medicalafterDTO = baseinfoService.findCountAssist(medicalafterDTO);
		JSONObject json = new JSONObject();
		json.put("r", medicalafterDTO.getR());
		json.put("assitpay", medicalafterDTO.getAsisstpay());
		json.put("actId", medicalafterDTO.getActId());
		result = json.toString();
		return SUCCESS;
	}

	public String countdbbx() {
		JSONObject json = new JSONObject();
		IServiceYljzJlc sms = new IServiceYljzJlcProxy();
		BigDecimal sumPreScope = BigDecimal.ZERO;
		medicalafterDTO = baseinfoService.findSumPayDbbx(medicalafterDTO);
		if (medicalafterDTO.getSumtotalcost() == null) {
			medicalafterDTO.setSumtotalcost(BigDecimal.ZERO);
			medicalafterDTO.setSuminsurepay(BigDecimal.ZERO);
			medicalafterDTO.setSumoutpay(BigDecimal.ZERO);
		}
		sumPreScope = medicalafterDTO.getSumtotalcost()
				.subtract(medicalafterDTO.getSuminsurepay())
				.subtract(medicalafterDTO.getSumoutpay());
		try {
			String s = sms.getDbbxValue(sumPreScope,
					medicalafterDTO.getTotalcost(),
					medicalafterDTO.getInsurepay(),
					medicalafterDTO.getOutpay(),
					Integer.valueOf(medicalafterDTO.getWsflag()),
					Integer.valueOf(medicalafterDTO.getMemberType()));
			json.put("dbbx", s);
			result = json.toString();
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}

	@SuppressWarnings("rawtypes")
	public String afterquerymemberinit() {
		Map session = ActionContext.getContext().getSession();
		UserInfoDTO user = (UserInfoDTO) session.get("user");
		if (user.getOrganizationId().length() == 4) {

		} else {
			result = "�����ڵĻ�����û������Ȩ�ޣ�";
			return "result";
		}
		return SUCCESS;
	}

	public String afterquerymember() {
		// ��ѯ��Ա������Ϣ
		baseinfos = this.baseinfoService.findMemberByPaperId(baseInfoDTO);
		medicalafters = this.baseinfoService
				.findMedicalaftersByPaperId(baseInfoDTO);
		if (baseinfos.size() == 0) {
			result = "û�д�����Ϣ�����ʵ��";
			return "result";
		}
		// ��ѯ��Աҽ������Ϣ

		return SUCCESS;
	}

	public String afterapplyinit() {
		// ��ѯ��Ա������Ϣ
		medicalafterDTO = this.baseinfoService.findMemberByID(baseInfoDTO);
		// ��ѯ��Ա�ĵͱ�����ʱ�䡢�ٱ��Ͼ���ʱ��
		baseInfoDTO = this.baseinfoService.findSalvationStatus(baseInfoDTO);
		// ��ѯ����ҽԺ
		this.setHs(this.searchService.getHospitals());
		return SUCCESS;
	}

	public String afterapply() {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		int year = calendar.get(Calendar.YEAR);
		Calendar calendar_end = Calendar.getInstance();
		calendar_end.setTime(medicalafterDTO.getEndtime());
		int business_year = calendar_end.get(Calendar.YEAR);
		if(year-business_year>1){
			result = "�����������ޣ����豨����";
			return "result";
		}
		// ��ѯ���������Ϣ��jz_act
		actDTO = this.baseinfoService.findActByID(medicalafterDTO);
		medicalafterDTO.setActId(actDTO.getActId());
		medicalafterDTO = this.baseinfoService.saveAfterApply(medicalafterDTO);
		FileUpload fu = new FileUpload("/file/medicalafter");
		String dir = fu.filepath + "\\" + medicalafterDTO.getMaId() + "\\";
		fu.MakeDir(dir);
		if (null != filebase64) {
			for (int i = 0; i < filebase64.length; i++) {
				Base64Image
						.GenerateImage(filebase64[i], dir + (i + 1) + ".jpg");
			}
		}
		return SUCCESS;
	}

	@SuppressWarnings("rawtypes")
	public String queryafterinit() {
		Map session = ActionContext.getContext().getSession();
		UserInfoDTO userinfo = (UserInfoDTO) session.get("user");
		String orgid = userinfo.getOrganizationId();
		if (4 == orgid.length()) {
			this.setOrgs(this.businessService.getOrganList(userinfo
					.getOrganizationId()));
			return SUCCESS;
		} else {
			this.result = "�����ڵĻ�����û�в�ѯȨ�ޣ�";
			return "result";
		}

	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String queryafter() {
		Map session = ActionContext.getContext().getSession();
		UserInfoDTO userinfo = (UserInfoDTO) session.get("user");
		String orgid = userinfo.getOrganizationId();
		JzMedicalafterExample example = new JzMedicalafterExample();
		String jwhere = "";
		if (null == cur_page || "".equals(cur_page)) {
			com.medical.model.JzMedicalafterExample.Criteria criteria = example
					.createCriteria();
			criteria.andOnNoLike(oid + "%");
			jwhere = jwhere + "and ma.on_no like '" + oid + "%'";
			if ("SSN".equals(term)) {
				criteria.andSsnLike(value + "%");
				jwhere = jwhere + "and ma.ssn like '" + value + "%'";
			} else if ("FAMILYNO".equals(term)) {
				criteria.andFamilynoLike(value + "%");
				jwhere = jwhere + "and ma.familyno like '" + value + "%'";
			} else if ("MEMBERNAME".equals(term)) {
				criteria.andMembernameLike(value + "%");
				jwhere = jwhere + "and ma.membername like '" + value + "%'";
			} else if ("PAPERID".equals(term)) {
				criteria.andPaperidLike(value + "%");
				jwhere = jwhere + "and ma.paperid like '" + value + "%'";
			} else {
			}
			if (!"".equals(ds)) {
				criteria.andMemberTypeEqualTo(ds);
				jwhere = jwhere + " and ma.member_type='" + ds + "'";
			}
			if (!"".equals(approveresult)) {
				criteria.andApproveresultEqualTo(approveresult);
				jwhere = jwhere + " and ma.approveresult='" + approveresult
						+ "'";
			}
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date opertimefrom = new Date();
			Date opertimeto = new Date();
			try {
				if (!opertime1.equals("")) {
					opertimefrom = sdf.parse(opertime1.substring(0, 10));
				}
				if (!opertime2.equals("")) {
					opertimeto = sdf.parse(opertime2.substring(0, 10));
				}
			} catch (ParseException e) {
				e.printStackTrace();
			}
			if ((opertime1.equals("") || null == opertime1)
					&& (opertime2.equals("") || null == opertime2)) {
			} else if (opertime1.equals("") || null == opertime1) {
				criteria.andUpdatetimeGreaterThan(opertimeto);
				jwhere = jwhere
						+ "and to_char(ma.updatetime,'yyyy-MM-dd') >= '"
						+ opertime2 + "'";
			} else if (opertime2.equals("") || null == opertime2) {
				criteria.andUpdatetimeLessThan(opertimefrom);
				jwhere = jwhere + "and to_char(ma.updatetime,'yyyy-MM-dd') < '"
						+ opertime1 + "'";
			} else {
				criteria.andUpdatetimeBetween(opertimefrom, opertimeto);
				jwhere = jwhere + "and to_char(ma.updatetime,'yyyy-MM-dd') >='"
						+ opertime1
						+ "' and to_char(ma.updatetime,'yyyy-MM-dd') < '"
						+ opertime2 + "'";
			}
			session.put("sql", example);
			session.put("jwhere", jwhere);
			cur_page = "1";
		} else {
			example = (JzMedicalafterExample) session.get("sql");
			jwhere = (String) session.get("jwhere");
		}

		this.setMedicalafters(this.baseinfoService.queryMedicalafters(example,
				new Integer(cur_page), "queryafter.action"));
		this.setToolsmenu(this.businessService.getPager().getToolsmenu());
		this.setOrgs(this.businessService.getOrganList(orgid));
		return SUCCESS;
	}

	@SuppressWarnings("rawtypes")
	public String queryafterffinit() {
		Map session = ActionContext.getContext().getSession();
		UserInfoDTO userinfo = (UserInfoDTO) session.get("user");
		String orgid = userinfo.getOrganizationId();
		if (4 == orgid.length()) {
			this.setOrgs(this.businessService.getOrganList(userinfo
					.getOrganizationId()));
			return SUCCESS;
		} else {
			this.result = "�����ڵĻ�����û�в�ѯȨ�ޣ�";
			return "result";
		}

	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String queryafterff() {
		Map session = ActionContext.getContext().getSession();
		UserInfoDTO userinfo = (UserInfoDTO) session.get("user");
		String orgid = userinfo.getOrganizationId();
		JzMedicalafterExample example = new JzMedicalafterExample();
		String jwhere = "";
		if (null == cur_page || "".equals(cur_page)) {
			com.medical.model.JzMedicalafterExample.Criteria criteria = example
					.createCriteria();
			criteria.andOnNoLike(oid + "%");
			jwhere = jwhere + "and ma.on_no like '" + oid + "%'";
			if ("SSN".equals(term)) {
				criteria.andSsnLike(value + "%");
				jwhere = jwhere + "and ma.ssn like '" + value + "%'";
			} else if ("FAMILYNO".equals(term)) {
				criteria.andFamilynoLike(value + "%");
				jwhere = jwhere + "and ma.familyno like '" + value + "%'";
			} else if ("MEMBERNAME".equals(term)) {
				criteria.andMembernameLike(value + "%");
				jwhere = jwhere + "and ma.membername like '" + value + "%'";
			} else if ("PAPERID".equals(term)) {
				criteria.andPaperidLike(value + "%");
				jwhere = jwhere + "and ma.paperid like '" + value + "%'";
			} else {
			}
			if (!"".equals(ds)) {
				criteria.andMemberTypeEqualTo(ds);
				jwhere = jwhere + " and ma.member_type='" + ds + "'";
			}

			criteria.andImplstsEqualTo("0");
			jwhere = jwhere + " and ma.implsts = 0 ";
			criteria.andApproveresultEqualTo("1");
			jwhere = jwhere + " and ma.approveresult='1'";
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date opertimefrom = new Date();
			Date opertimeto = new Date();
			try {
				if (!opertime1.equals("")) {
					opertimefrom = sdf.parse(opertime1.substring(0, 10));
				}
				if (!opertime2.equals("")) {
					opertimeto = sdf.parse(opertime2.substring(0, 10));
				}
			} catch (ParseException e) {
				e.printStackTrace();
			}
			if ((opertime1.equals("") || null == opertime1)
					&& (opertime2.equals("") || null == opertime2)) {
			} else if (opertime1.equals("") || null == opertime1) {
				criteria.andUpdatetimeGreaterThan(opertimeto);
				jwhere = jwhere
						+ "and to_char(ma.updatetime,'yyyy-MM-dd') >= '"
						+ opertime2 + "'";
			} else if (opertime2.equals("") || null == opertime2) {
				criteria.andUpdatetimeLessThan(opertimefrom);
				jwhere = jwhere + "and to_char(ma.updatetime,'yyyy-MM-dd') < '"
						+ opertime1 + "'";
			} else {
				criteria.andUpdatetimeBetween(opertimefrom, opertimeto);
				jwhere = jwhere + "and to_char(ma.updatetime,'yyyy-MM-dd') >='"
						+ opertime1
						+ "' and to_char(ma.updatetime,'yyyy-MM-dd') < '"
						+ opertime2 + "'";
			}
			session.put("sql", example);
			session.put("jwhere", jwhere);
			cur_page = "1";
		} else {
			example = (JzMedicalafterExample) session.get("sql");
			jwhere = (String) session.get("jwhere");
		}
		String aasql = " SELECT count(*) as rc,    sum(ma.totalcost) as totalcost, "
				+ " sum(ma.insurepay) as insurepay,        sum(ma.asisstpay) as asisstpay "
				+ " FROM JZ_MEDICALAFTER MA  WHERE 1 = 1    "
				+ jwhere
				+ " "
				+ " and ma.implsts = 0    and ma.approveresult = '1'  and 1=1 ";
		System.out.println(aasql);
		String u = baseinfoService.queryMaStat(aasql);
		this.setMedicalafters(this.baseinfoService.queryMedicalafters(example,
				new Integer(cur_page), "queryafterff.action"));
		this.setToolsmenu(this.businessService.getPager().getToolsmenu());
		this.setOrgs(this.businessService.getOrganList(orgid));
		this.setResult(u);
		session.put("aasql", aasql);
		return SUCCESS;
	}

	public String genmabillinit() {
		String sql = "select (select org.organization_id||'-'||org.asorgname from "
				+ "sys_t_organization org where org.organization_id=sb.on_no) as orgname , "
				+ " decode(sb.ds,'1','����' ,'2','ũ��',null) as ds,  "
				+ "sb.on_no, max(sb.sb_batchname) as batchname, "
				+ " count(*) rc, sum(ma.asisstpay) as asisstpay "
				+ " from jz_medicalafter ma, (select sb.sb_batchname,  sb.sb_id, "
				+ " sb.sb_disposests,  '1' as ds,  sb.on_no "
				+ " from salvationbatch@cs sb, salvationoperation@cs so "
				+ " where so.so_id = sb.so_id  and so.st_id = '4' "
				+ " and sb.sb_disposests = '������' union all "
				+ " select sb.sb_batchname, sb.sb_id,  sb.sb_disposests, "
				+ " '2' as ds, sb.on_no "
				+ " from salvationbatch@nc sb, salvationoperation@nc so "
				+ " where so.so_id = sb.so_id and so.st_id = '4' "
				+ " and sb.sb_disposests = '������') sb "
				+ " where sb.on_no = substr(ma.on_no, 1, 6) and sb.ds = ma.member_type "
				+ " and ma.implsts = '0' and ma.approveresult = '1' "
				+ " group by sb.on_no, sb.ds  order by sb.ds, sb.on_no";
		medicalafters = baseinfoService.queryMaBillStat(sql);
		sql = "update JZ_MABILLS t   set t.bank_account =       (select max(mem.f_accounts)          from member_baseinfo mem "
				+ " where mem.familyno = t.familyno) where t.bank_account is null   and t.bank_account1 is null";
	 baseinfoService.updateMedicalafterAcc(sql);
		return SUCCESS;
	}

	public String genmabill() {

		baseinfoService.saveMaBatchDone();
		String sql = "select (select org.organization_id||'-'||org.asorgname from "
				+ "sys_t_organization org where org.organization_id=sb.on_no) as orgname , "
				+ " decode(sb.ds,'1','����' ,'2','ũ��',null) as ds,  "
				+ "sb.on_no, max(sb.sb_batchname) as batchname, "
				+ " count(*) rc, sum(ma.asisstpay) as asisstpay "
				+ " from jz_medicalafter ma, (select sb.sb_batchname,  sb.sb_id, "
				+ " sb.sb_disposests,  '1' as ds,  sb.on_no "
				+ " from salvationbatch@cs sb, salvationoperation@cs so "
				+ " where so.so_id = sb.so_id  and so.st_id = '4' "
				+ " and sb.sb_disposests = '������' union all "
				+ " select sb.sb_batchname, sb.sb_id,  sb.sb_disposests, "
				+ " '2' as ds, sb.on_no "
				+ " from salvationbatch@nc sb, salvationoperation@nc so "
				+ " where so.so_id = sb.so_id and so.st_id = '4' "
				+ " and sb.sb_disposests = '������') sb ,jz_mabills mb "
				+ " where sb.on_no = substr(ma.on_no, 1, 6) and sb.ds = ma.member_type "
				+ " and ma.implsts = '1' and ma.approveresult = '1' and mb.sb_id=sb.sb_id "
				+ " group by sb.on_no, sb.ds  order by sb.ds, sb.on_no";
		System.out.println(sql);
		medicalafters = baseinfoService.queryMaBillStat(sql);
		this.setResult("�������");
		return SUCCESS;
	}

	@SuppressWarnings("rawtypes")
	public String queryafterffdoneinit() {
		Map session = ActionContext.getContext().getSession();
		UserInfoDTO userinfo = (UserInfoDTO) session.get("user");
		String orgid = userinfo.getOrganizationId();
		if (4 == orgid.length()) {
			this.setOrgs(this.businessService.getOrganList(userinfo
					.getOrganizationId()));
			this.setMonths(this.baseinfoService.getMonths());
			return SUCCESS;
		} else {
			this.result = "�����ڵĻ�����û�в�ѯȨ�ޣ�";
			return "result";
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String queryafterffdone() {
		Map session = ActionContext.getContext().getSession();
		UserInfoDTO userinfo = (UserInfoDTO) session.get("user");
		String orgid = userinfo.getOrganizationId();
		JzMabillsExample example = new JzMabillsExample();
		String jwhere = "";
		if (null == cur_page || "".equals(cur_page)) {
			com.medical.model.JzMabillsExample.Criteria criteria = example
					.createCriteria();
			if (!"".equals(ds)) {
				criteria.andDsEqualTo(ds);
			}
			if (!"".equals(m)) {
				criteria.andBatchnameLike(m + "%");
			}

			jwhere = "select  t.familyno, t.membername,  t.paperid, t.assispay, "
					+ " t.batchname,  t.mastername,  t.masteridcard, t.bank_account, t.bank_account1 "
					+ " from JZ_MABILLS t where t.batchname like '"
					+ m
					+ "%' and t.ds like '" + ds + "%'";

			System.out.println(jwhere);

			session.put("sql", example);
			session.put("jwhere", jwhere);
			session.put("m1", m);
			cur_page = "1";
		} else {
			example = (JzMabillsExample) session.get("sql");
			jwhere = (String) session.get("jwhere");
			this.m = (String) session.get("m1");
		}

		this.setMabills(this.baseinfoService.queryMedicalafters01(example,
				new Integer(cur_page), "queryafterffdone.action"));
		this.setToolsmenu(this.businessService.getPager().getToolsmenu());
		this.setOrgs(this.businessService.getOrganList(orgid));
		this.setMonths(this.baseinfoService.getMonths());
		return SUCCESS;
	}

	public String printinhospital() {
		medicalafterDTO = this.baseinfoService
				.findMemberInfoPrint(medicalafterDTO);
		map = new HashMap<String, String>();
		String medicaltype = "";
		String sickencontent = "";
		if ("1".equals(medicalafterDTO.getMedicaltype())) {
			medicaltype = "סԺ";
			sickencontent = medicalafterDTO.getSickencontent();
		} else if ("2".equals(medicalafterDTO.getMedicaltype())) {
			medicaltype = "����";
			if ("-1".equals(medicalafterDTO.getDiagnose())) {
				sickencontent = "����";
			} else if ("0001".equals(medicalafterDTO.getDiagnose())) {
				sickencontent = "��֢";
			} else if ("0002".equals(medicalafterDTO.getDiagnose())) {
				sickencontent = "�Ρ�������ֲ�����������ƣ�";
			} else if ("0004".equals(medicalafterDTO.getDiagnose())) {
				sickencontent = "�����������ڷ��ơ����ƣ�";
			} else if ("0005".equals(medicalafterDTO.getDiagnose())) {
				sickencontent = "������ֲ�����������ƣ�";
			} else if ("0006".equals(medicalafterDTO.getDiagnose())) {
				sickencontent = "������ֲ�����������ƣ�";
			}
		}
		String persontype = "";
		if ("111".equals(medicalafterDTO.getPersontype())) {
			persontype = "�ٱ���";
		} else if ("100".equals(medicalafterDTO.getPersontype())) {
			persontype = "���еͱ�";
		} else if ("110".equals(medicalafterDTO.getPersontype())) {
			persontype = "ũ��ͱ�";
		} else {
			persontype = "��ͨ����";
		}
		String insuretype = "";
		if ("1".equals(medicalafterDTO.getInsuretype())) {
			insuretype = "ҽ��";
		} else if ("2".equals(medicalafterDTO.getInsuretype())) {
			insuretype = "ũ��";
		} else {
			insuretype = "����";
		}

		map.put("MEDICALTYPE", medicaltype);
		map.put("HOSPITAL", medicalafterDTO.getHospital());
		map.put("MEMBERNAME", medicalafterDTO.getMembername());
		map.put("FAMILYNO", medicalafterDTO.getFamilyno());
		map.put("SSN", medicalafterDTO.getSsn());
		map.put("PERSONTYPE", persontype);
		map.put("PAPERID", medicalafterDTO.getPaperid());
		map.put("SEX", medicalafterDTO.getSex());
		map.put("ADDRESS", medicalafterDTO.getFamaddr());
		map.put("INSURETYPE", insuretype);
		map.put("SICKENCONTENT", sickencontent);
		map.put("TIKETNO", medicalafterDTO.getTiketno());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		String printtime = sdf.format(cal.getTime());
		map.put("BEGINTIME", medicalafterDTO.getBegintimeval());
		map.put("ENDTIME", medicalafterDTO.getEndtimeval());
		map.put("PRINTTIME", printtime);
		map.put("INDATE", medicalafterDTO.getIndate().toString());
		map.put("NUM", medicalafterDTO.getNum().toString());
		map.put("ASISSTPAY", medicalafterDTO.getAsisstpay().toString());
		map.put("TOTALCOST", medicalafterDTO.getTotalcost().toString());
		map.put("INSUREPAY", medicalafterDTO.getInsurepay().toString());
		map.put("OUTPAY", medicalafterDTO.getOutpay().toString());
		map.put("CAPAY", medicalafterDTO.getCapay().toString());
		map.put("BUSINESSPAY", medicalafterDTO.getBusinesspay().toString());
		map.put("PAYLINE", medicalafterDTO.getPayLine().toString());
		map.put("HOSIPITALPAY", medicalafterDTO.getHospitalpay().toString());
		map.put("SUMPAY", medicalafterDTO.getSumpay().toString());

		// ��¼��ӡ����ƾ֤����
		int pzprinum = Integer.valueOf(medicalafterDTO.getPzPrinum()) + 1;
		medicalafterDTO.setPzPrinum(pzprinum + "");
		int u = this.baseinfoService.updateMedicalpzPinSum(medicalafterDTO,
				"pz");
		System.out.println(u);
		return SUCCESS;
	}

	public String printoutpatient() {
		medicalafterDTO = this.baseinfoService
				.findMemberInfoPrint(medicalafterDTO);
		map = new HashMap<String, String>();
		String medicaltype = "";
		String sickencontent = "";
		if ("1".equals(medicalafterDTO.getMedicaltype())) {
			medicaltype = "סԺ";
			sickencontent = medicalafterDTO.getSickencontent();
		} else if ("2".equals(medicalafterDTO.getMedicaltype())) {
			medicaltype = "����";
			if ("-1".equals(medicalafterDTO.getDiagnose())) {
				sickencontent = "����";
			} else if ("0001".equals(medicalafterDTO.getDiagnose())) {
				sickencontent = "��֢";
			} else if ("0002".equals(medicalafterDTO.getDiagnose())) {
				sickencontent = "�Ρ�������ֲ�����������ƣ�";
			} else if ("0004".equals(medicalafterDTO.getDiagnose())) {
				sickencontent = "�����������ڷ��ơ����ƣ�";
			} else if ("0005".equals(medicalafterDTO.getDiagnose())) {
				sickencontent = "������ֲ�����������ƣ�";
			} else if ("0006".equals(medicalafterDTO.getDiagnose())) {
				sickencontent = "������ֲ�����������ƣ�";
			}
		}
		String persontype = "";
		if ("111".equals(medicalafterDTO.getPersontype())) {
			persontype = "�ٱ���";
		} else if ("100".equals(medicalafterDTO.getPersontype())) {
			persontype = "���еͱ�";
		} else if ("110".equals(medicalafterDTO.getPersontype())) {
			persontype = "ũ��ͱ�";
		} else {
			persontype = "��ͨ����";
		}
		String insuretype = "";
		if ("1".equals(medicalafterDTO.getInsuretype())) {
			insuretype = "ҽ��";
		} else if ("2".equals(medicalafterDTO.getInsuretype())) {
			insuretype = "ũ��";
		} else {
			insuretype = "����";
		}

		map.put("MEDICALTYPE", medicaltype);
		map.put("HOSPITAL", medicalafterDTO.getHospital());
		map.put("MEMBERNAME", medicalafterDTO.getMembername());
		map.put("FAMILYNO", medicalafterDTO.getFamilyno());
		map.put("SSN", medicalafterDTO.getSsn());
		map.put("PERSONTYPE", persontype);
		map.put("PAPERID", medicalafterDTO.getPaperid());
		map.put("SEX", medicalafterDTO.getSex());
		map.put("ADDRESS", medicalafterDTO.getFamaddr());
		map.put("INSURETYPE", insuretype);
		map.put("SICKENCONTENT", sickencontent);
		map.put("TIKETNO", medicalafterDTO.getTiketno());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		String printtime = sdf.format(cal.getTime());
		map.put("BEGINTIME", medicalafterDTO.getBegintimeval());
		map.put("ENDTIME", medicalafterDTO.getEndtimeval());
		map.put("PRINTTIME", printtime);
		map.put("INDATE", medicalafterDTO.getIndate().toString());
		map.put("NUM", medicalafterDTO.getNum().toString());
		map.put("ASISSTPAY", medicalafterDTO.getAsisstpay().toString());
		map.put("TOTALCOST", medicalafterDTO.getTotalcost().toString());
		map.put("INSUREPAY", medicalafterDTO.getInsurepay().toString());
		map.put("OUTPAY", medicalafterDTO.getOutpay().toString());
		map.put("CAPAY", medicalafterDTO.getCapay().toString());
		map.put("BUSINESSPAY", medicalafterDTO.getBusinesspay().toString());
		map.put("PAYLINE", medicalafterDTO.getPayLine().toString());
		map.put("HOSIPITALPAY", medicalafterDTO.getHospitalpay().toString());
		map.put("SUMPAY", medicalafterDTO.getSumpay().toString());

		// ��¼��ӡ����ƾ֤����
		int pzprinum = Integer.valueOf(medicalafterDTO.getPzPrinum()) + 1;
		medicalafterDTO.setPzPrinum(pzprinum + "");
		int u = this.baseinfoService.updateMedicalpzPinSum(medicalafterDTO,
				"pz");
		System.out.println(u);
		return SUCCESS;
	}

	public String printapp() {
		medicalafterDTO = this.baseinfoService
				.findMemberInfoPrint(medicalafterDTO);
		map = new HashMap<String, String>();
		String medicaltype = "";
		String sickencontent = "";
		if ("1".equals(medicalafterDTO.getMedicaltype())) {
			medicaltype = "סԺ";
			sickencontent = medicalafterDTO.getSickencontent();
		} else if ("2".equals(medicalafterDTO.getMedicaltype())) {
			medicaltype = "����";
			if ("-1".equals(medicalafterDTO.getDiagnose())) {
				sickencontent = "����";
			} else if ("0001".equals(medicalafterDTO.getDiagnose())) {
				sickencontent = "��֢";
			} else if ("0002".equals(medicalafterDTO.getDiagnose())) {
				sickencontent = "�Ρ�������ֲ�����������ƣ�";
			} else if ("0004".equals(medicalafterDTO.getDiagnose())) {
				sickencontent = "�����������ڷ��ơ����ƣ�";
			} else if ("0005".equals(medicalafterDTO.getDiagnose())) {
				sickencontent = "������ֲ�����������ƣ�";
			} else if ("0006".equals(medicalafterDTO.getDiagnose())) {
				sickencontent = "������ֲ�����������ƣ�";
			}
		}
		String persontype = "";
		if ("111".equals(medicalafterDTO.getPersontype())) {
			persontype = "�ٱ���";
		} else if ("100".equals(medicalafterDTO.getPersontype())) {
			persontype = "���еͱ�";
		} else if ("110".equals(medicalafterDTO.getPersontype())) {
			persontype = "ũ��ͱ�";
		} else {
			persontype = "��ͨ����";
		}
		String insuretype = "";
		if ("1".equals(medicalafterDTO.getInsuretype())) {
			insuretype = "ҽ��";
		} else if ("2".equals(medicalafterDTO.getInsuretype())) {
			insuretype = "ũ��";
		} else {
			insuretype = "����";
		}

		map.put("MEDICALTYPE", medicaltype);
		map.put("HOSPITAL", medicalafterDTO.getHospital());
		map.put("MEMBERNAME", medicalafterDTO.getMembername());
		map.put("FAMILYNO", medicalafterDTO.getFamilyno());
		map.put("SSN", medicalafterDTO.getSsn());
		map.put("PERSONTYPE", persontype);
		map.put("PAPERID", medicalafterDTO.getPaperid());
		map.put("SEX", medicalafterDTO.getSex());
		map.put("ADDRESS", medicalafterDTO.getFamaddr());
		map.put("INSURETYPE", insuretype);
		map.put("SICKENCONTENT", sickencontent);
		map.put("TIKETNO", medicalafterDTO.getTiketno());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		String printtime = sdf.format(cal.getTime());
		map.put("BEGINTIME", medicalafterDTO.getBegintimeval());
		map.put("ENDTIME", medicalafterDTO.getEndtimeval());
		map.put("PRINTTIME", printtime);
		map.put("INDATE", medicalafterDTO.getIndate().toString());
		map.put("NUM", medicalafterDTO.getNum().toString());
		map.put("ASISSTPAY", medicalafterDTO.getAsisstpay().toString());
		map.put("TOTALCOST", medicalafterDTO.getTotalcost().toString());
		map.put("INSUREPAY", medicalafterDTO.getInsurepay().toString());
		map.put("OUTPAY", medicalafterDTO.getOutpay().toString());
		map.put("CAPAY", medicalafterDTO.getCapay().toString());
		map.put("BUSINESSPAY", medicalafterDTO.getBusinesspay().toString());
		map.put("PAYLINE", medicalafterDTO.getPayLine().toString());
		map.put("HOSIPITALPAY", medicalafterDTO.getHospitalpay().toString());
		map.put("SUMPAY", medicalafterDTO.getSumpay().toString());
		map.put("TELEPHONE", medicalafterDTO.getTelephone());
		map.put("FAMCOUNT", medicalafterDTO.getFamcountval());
		map.put("BIRTHDAY", medicalafterDTO.getBirthdayval());

		// ��¼��ӡ����ƾ֤����
		int appprinum = Integer.valueOf(medicalafterDTO.getAppPrinum()) + 1;
		medicalafterDTO.setAppPrinum(appprinum + "");
		int u = this.baseinfoService.updateMedicalpzPinSum(medicalafterDTO,
				"app");
		System.out.println(u);
		return SUCCESS;
	}

	public String viewafter() {
		medicalafterDTO = this.baseinfoService.findMemberByKey(medicalafterDTO);
		return SUCCESS;
	}

	public String cancelafter() {
		JSONObject json = new JSONObject();
		int u = this.baseinfoService.updateMedicalafter(medicalafterDTO);
		json.put("u", u);
		result = json.toString();
		return SUCCESS;
	}

	public String viewafterfile() {
		medicalafters = new ArrayList<MedicalafterDTO>();
		File dir = new File("Z:\\pic\\medicalafter\\"
				+ medicalafterDTO.getMaId());
		File[] fs = dir.listFiles();
		for (int i = 0; i < fs.length; i++) {
			MedicalafterDTO e = new MedicalafterDTO();
			String path = fs[i].getAbsolutePath();
			String name = fs[i].getName();
			e.setFilepath(path);
			e.setFilename(name);
			System.out.println(name);
			medicalafters.add(e);
		}
		return SUCCESS;
	}

	@SuppressWarnings("rawtypes")
	public String fileDownload() {
		Map session = ActionContext.getContext().getSession();
		String m = (String) session.get("m1");
		this.m = m;
		content = businessService.findMaBillContent(m, ds, type);
		System.out.println(content);
		return SUCCESS;
	}

	@SuppressWarnings("rawtypes")
	public InputStream getDownload() throws Exception {
		Map session = ActionContext.getContext().getSession();
		String m = (String) session.get("m1");
		System.out.println(m + "-" + ds + "-" + type + ".txt");
		this.fileName = m + "-" + ds + "-" + type + ".txt";
		ByteArrayInputStream is = null;
		if (null == content || "".equals(content)) {
			is = new ByteArrayInputStream("��".getBytes("UTF-8"));
		} else {
			is = new ByteArrayInputStream(this.content.getBytes("UTF-8"));
		}
		System.out.println(2);
		return is;
	}

	public String medicalusebatchinit() {
		String sql = "select (select org.organization_id||'-'||org.asorgname from "
				+ "sys_t_organization org where org.organization_id=sb.on_no) as orgname , "
				+ " decode(sb.ds,'1','����' ,'2','ũ��',null) as ds,  "
				+ "sb.on_no, max(sb.sb_batchname) as batchname, "
				+ " count(*) rc, sum(ma.asisstpay) as asisstpay "
				+ " from jz_medicalafter ma, (select sb.sb_batchname,  sb.sb_id, "
				+ " sb.sb_disposests,  '1' as ds,  sb.on_no "
				+ " from salvationbatch@cs sb, salvationoperation@cs so "
				+ " where so.so_id = sb.so_id  and so.st_id = '4' "
				+ " and sb.sb_disposests = '������' union all "
				+ " select sb.sb_batchname, sb.sb_id,  sb.sb_disposests, "
				+ " '2' as ds, sb.on_no "
				+ " from salvationbatch@nc sb, salvationoperation@nc so "
				+ " where so.so_id = sb.so_id and so.st_id = '4' "
				+ " and sb.sb_disposests = '������') sb ,jz_mabills mb "
				+ " where sb.on_no = substr(ma.on_no, 1, 6) and sb.ds = ma.member_type "
				+ " and ma.implsts = '1' and ma.approveresult = '1' and mb.sb_id=sb.sb_id "
				+ " group by sb.on_no, sb.ds  order by sb.ds, sb.on_no";
		System.out.println(sql);
		medicalafters = baseinfoService.queryMaBillStat(sql);
		// this.setResult("�������");
		this.setMonths(this.baseinfoService.getMonths());
		return SUCCESS;
	}

	public String medicalusebatch() {
		this.setMonths(this.baseinfoService.getMonths());
		// ����
		if (type.equals("1")) {
			baseinfoService.saveCommitMaBatch();
			String sql = "select (select org.organization_id||'-'||org.asorgname from "
					+ "sys_t_organization org where org.organization_id=sb.on_no) as orgname , "
					+ " decode(sb.ds,'1','����' ,'2','ũ��',null) as ds,  "
					+ "sb.on_no, max(sb.sb_batchname) as batchname, "
					+ " count(*) rc, sum(ma.asisstpay) as asisstpay "
					+ " from jz_medicalafter ma, (select sb.sb_batchname,  sb.sb_id, "
					+ " sb.sb_disposests,  '1' as ds,  sb.on_no "
					+ " from salvationbatch@cs sb, salvationoperation@cs so "
					+ " where so.so_id = sb.so_id  and so.st_id = '4' "
					+ " and sb.sb_disposests = '��ͳ��' union all "
					+ " select sb.sb_batchname, sb.sb_id,  sb.sb_disposests, "
					+ " '2' as ds, sb.on_no "
					+ " from salvationbatch@nc sb, salvationoperation@nc so "
					+ " where so.so_id = sb.so_id and so.st_id = '4' "
					+ " and sb.sb_disposests = '��ͳ��') sb ,jz_mabills mb "
					+ " where sb.on_no = substr(ma.on_no, 1, 6) and sb.ds = ma.member_type "
					+ " and ma.implsts = '1' and ma.approveresult = '1' and mb.sb_id=sb.sb_id "
					+ " group by sb.on_no, sb.ds  order by sb.ds, sb.on_no";
			System.out.println(sql);
			medicalafters = baseinfoService.queryMaBillStat(sql);
		}
		// ��������
		if (type.equals("2")) {
			baseinfoService.saveCancelMaBatch(m);
		}
		return SUCCESS;
	}

	public String querystatus(){
		JSONObject json = new JSONObject();
		String ds = medicalafterDTO.getMemberType();
		SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd");
		String enddate = sdf.format(medicalafterDTO.getEndtime());
		String strs[]=enddate.split("-");
		String year = strs[0];
		String month = strs[1];
		String endtime = year + "��" + month + "��";
		String datatype = "";
		if("1".equals(ds)){
			datatype = "@CS.REGRESS.RDBMS.DEV.US.ORACLE.COM";
		}else if("2".equals(ds)){
			datatype = "@NC.REGRESS.RDBMS.DEV.US.ORACLE.COM";
			if("01".equals(month)||"02".equals(month)||"03".equals(month)){
				month = "03";
			}else if("04".equals(month)||"05".equals(month)||"06".equals(month)){
				month = "06";
			}else if("07".equals(month)||"08".equals(month)||"09".equals(month)){
				month = "09";
			}else if("10".equals(month)||"11".equals(month)||"12".equals(month)){
				month = "12";
			}
			endtime = year + "��" + month + "��";
		}
		String sql = " select ba.st_id,substr(ba.bar_subject,0,8) as maxdate ,ty.st_name as stname"
				   + " from familymember"+datatype+" fa , "
				   + " batch_almsreckoning"+datatype+" ba ," 
				   + " salvationtype"+datatype+" ty "
		           + " where fa.f_familyid = ba.bar_familyid "
		           + " and ba.st_id = ty.st_id "
		           + " and fa.fm_paperid = '"+medicalafterDTO.getPaperid()+"' "
		           + " and ba.st_id in ('1','31') "
		           + " and substr(ba.bar_subject,0,8) = '" +endtime+ "' ";
		medicalafters = baseinfoService.findstatus(sql);
		String message="";
		String flag = "";
		if(medicalafters.size()>0){
			for (int i=0; i<medicalafters.size(); i++){
				String stname = medicalafters.get(i).getStname();
				//String maxdate = medicalafters.get(i).getMaxdate();
				flag = "�ڱ�";
				message = message + stname +":"+ flag + "; ";
			}
		}else{
			flag = "���ڱ�";
			message = message + flag + ";";
		}
		json.put("message", message);
		result = json.toString();
		return SUCCESS;
	}
	
	@SuppressWarnings("rawtypes")
	public String queryyibaoinit(){
		BaseInfoDTO baseInfoDTO = new BaseInfoDTO();
		baseInfoDTO.setMemberId(medicalafterDTO.getMemberId());
		baseInfoDTO.setDs(medicalafterDTO.getMemberType());
		medicalafterDTO = baseinfoService.findMemberByID(baseInfoDTO);
		medicalafterDTO.setBegintimeval(opertime1);
		medicalafterDTO.setEndtimeval(opertime2);
		medicalafterDTO.setMedicaltype(mtype);
		IService1 iService1 = new IService1Proxy();
		try {
			String  xml= iService1.getHospitalList();
			//System.out.println(xml);
			Document document = DocumentHelper.parseText(xml);
			String resultFlag = document.selectSingleNode(
					"//GetHospitalList/Result/ResultFlag").getText();
			String message = document.selectSingleNode(
					"//GetHospitalList/Result/Message").getText();
			if ("1".equals(resultFlag)) {
				List list = document
						.selectNodes("//GetHospitalList/NewDataSet/jljzj");
				Iterator iter = list.iterator();
				ybhospitals = new ArrayList<YBHospitalDTO>();
				while (iter.hasNext()) {
					YBHospitalDTO ybhdto= new YBHospitalDTO();
					Element ele = (Element) iter.next();
					String hid = ele.element("������").getText();
					String hname= ele.element("��������").getText();
					String hlevel= ele.element("ҽԺ�ȼ�").getText();
					ybhdto.setHospitalid(hid);
					ybhdto.setHospitalname(hname);
					ybhdto.setHospitallevel(hlevel);
					ybhospitals.add(ybhdto);
				}
			}else{
				result=message;
			}
		} catch (RemoteException | DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	@SuppressWarnings("rawtypes")
	public String queryyibao(){
		//System.out.println(medicalafterDTO);
		String medicaltype =medicalafterDTO.getMedicaltype();
		String ybNumber = medicalafterDTO.getSsn();
		String beginTime = "";
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		if(medicalafterDTO.getBegintime()==null){
		}else{
			beginTime = sdf.format(medicalafterDTO.getBegintime()).replace("-", "");
		}
		if(medicalafterDTO.getBegintimeval()==null || "".equals(medicalafterDTO.getBegintimeval())){
		}else{
			beginTime = medicalafterDTO.getBegintimeval().replace("-", "");
		}
		String endTime = "";
		if(medicalafterDTO.getEndtime()==null){
		}else{
			endTime = sdf.format(medicalafterDTO.getEndtime()).replace("-", "");
		}
		if(medicalafterDTO.getEndtimeval()==null || "".equals(medicalafterDTO.getEndtimeval())){
		}else{
			endTime = medicalafterDTO.getEndtimeval().replace("-", "");
		}
		String hospitalID = medicalafterDTO.getHospitalid();
		JSONObject json = new JSONObject();
		IService1 iService1 = new IService1Proxy();
		//סԺ
		if("1".equals(medicaltype)){
			try {
				String  xml= iService1.getInpatientRegStr(ybNumber, beginTime, endTime, hospitalID);
				//System.out.println(xml);
				Document document = DocumentHelper.parseText(xml);
				List list = document
						.selectNodes("//NewDataSet/jljzj");
				if(list.size()>0){
					Iterator iter = list.iterator();
					while (iter.hasNext()) {
						Element ele = (Element) iter.next();
						String ybnumber = "";
						if(ele.element("ҽ�����") != null){
							ybnumber = ele.element("ҽ�����").getText();
						}
						String hospitalid = "";
						if(ele.element("ҽԺ���") != null){
							hospitalid = ele.element("ҽԺ���").getText();
						}
						String serialno = "";
						if(ele.element("סԺ��ˮ��") != null){
							serialno = ele.element("סԺ��ˮ��").getText();
						}
						String medtype = ""; 
						if(ele.element("ҽ�����") != null){
							medtype = ele.element("ҽ�����").getText();
						}
						String diagnoseno_i = "";
						if(ele.element("��Ժ��ϼ�������") != null){
							diagnoseno_i = ele.element("��Ժ��ϼ�������").getText();
						}
						String diagnosename_i = "";
						if(ele.element("��Ժ��ϼ�������") != null){
							diagnosename_i = ele.element("��Ժ��ϼ�������").getText();
						}
						String indeptname = "";
						if(ele.element("��������") != null){
							indeptname = ele.element("��������").getText();
						}
						String begintime = "";
						if(ele.element("��Ժʱ��") != null){
							begintime = ele.element("��Ժʱ��").getText();
						}
						String outflag = "";
						if(ele.element("��Ժ״̬") != null){
							outflag = ele.element("��Ժ״̬").getText();
						}
						String status = "";
						if(ele.element("��Ч��־") != null){
							status = ele.element("��Ч��־").getText();
						}
						json.put("ybnumber", ybnumber);
						json.put("hospitalid", hospitalid);
						json.put("serialno", serialno);
						json.put("medtype", medtype);
						json.put("diagnoseno_i", diagnoseno_i);
						json.put("diagnosename_i", diagnosename_i);
						json.put("indeptname", indeptname);
						json.put("begintime", yibaoformattime(begintime));
						json.put("outflag", outflag);
						json.put("status", status);
						json.put("msg","1");
					}
				}else{
					json.put("msg","0");
				}
				
			} catch (RemoteException | DocumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		//����
		}else if("2".equals(medicaltype)){
		}
		result = json.toString();
		return SUCCESS;
	}
	
	@SuppressWarnings("rawtypes")
	public String queryjiesuan(){
		//System.out.println(ssn+"::"+hid+"::"+serialno);
		JSONObject json = new JSONObject();
		IService1 iService1 = new IService1Proxy();
		try {
			String  xml= iService1.getInpatientBillStr(ssn, hid, serialno);
			//System.out.println(xml);
			Document document = DocumentHelper.parseText(xml);
			List list = document
					.selectNodes("//NewDataSet/jljzj");
			if(list.size()>0){
				Iterator iter = list.iterator();
				while (iter.hasNext()) {
					Element ele = (Element) iter.next();
					String ybnumber_js = "";
					if(ele.element("ҽ�����") != null){
						ybnumber_js = ele.element("ҽ�����").getText();
					}
					String hospitalid_js = "";
					if(ele.element("ҽԺ���") != null){
						hospitalid_js = ele.element("ҽԺ���").getText();
					}
					String serialno_js = "";
					if(ele.element("������ˮ��") != null){
						serialno_js = ele.element("������ˮ��").getText();
					}
					String shoujuno_js = "";
					if(ele.element("�վݺ�") != null){
						shoujuno_js = ele.element("�վݺ�").getText();
					}
					String medtype_js = "";
					if(ele.element("ҽ�����") != null){
						medtype_js = ele.element("ҽ�����").getText();
					}
					String businesstype_js = "";
					if(ele.element("��������") != null){
						businesstype_js = ele.element("��������").getText();
					}
					String opertime_js = "";
					if(ele.element("��������") != null){
						opertime_js = ele.element("��������").getText();
					}
					String diagnoseno_o = "";
					if(ele.element("��Ժ��ϼ�������") != null){
						diagnoseno_o = ele.element("��Ժ��ϼ�������").getText();
					}
					String diagnosename_o = "";
					if(ele.element("��Ժ��ϼ�������") != null){
						diagnosename_o = ele.element("��Ժ��ϼ�������").getText();
					}
					String indeptname_js = "";
					if(ele.element("��������") != null){
						indeptname_js = ele.element("��������").getText();
					}
					String begintime_js = "";
					if(ele.element("��Ժʱ��") != null){
						begintime_js = ele.element("��Ժʱ��").getText();
					}
					String endtime_js = "";
					if(ele.element("��Ժʱ��") != null){
						endtime_js = ele.element("��Ժʱ��").getText();
					}
					String pay_total = "";
					if(ele.element("���úϼ�") != null){
						pay_total = ele.element("���úϼ�").getText();
					}
					String pay_account = "";
					if(ele.element("�ʻ�֧��") != null){
						pay_account = ele.element("�ʻ�֧��").getText();
					}
					String pay_insurance = "";
					if(ele.element("ͳ��֧��") != null){
						pay_insurance = ele.element("ͳ��֧��").getText();
					}
					String pay_person = "";
					if(ele.element("����֧��") != null){
						pay_person = ele.element("����֧��").getText();
					}
					String pay_gwy = "";
					if(ele.element("����Աͳ��֧��") != null){
						pay_gwy = ele.element("����Աͳ��֧��").getText();
					}
					String pay_lx = "";
					if(ele.element("����ͳ��֧��") != null){
						pay_lx = ele.element("����ͳ��֧��").getText();
					}
					String pay_gs = "";
					if(ele.element("����ͳ��֧��") != null){
						pay_gs = ele.element("����ͳ��֧��").getText();
					}
					String pay_sy = "";
					if(ele.element("����ͳ��֧��") != null){
						pay_sy = ele.element("����ͳ��֧��").getText();
					}
					String pay_sygs = "";
					if(ele.element("������ҵ����ͳ��֧��") != null){
						pay_sygs = ele.element("������ҵ����ͳ��֧��").getText();
					}
					String pay_zgf = "";
					if(ele.element("�����չ˷�ͳ��֧��") != null){
						pay_zgf = ele.element("�����չ˷�ͳ��֧��").getText();
					}
					String pay_out = "";
					if(ele.element("Ŀ¼�ڷ��úϼ�") != null){
						pay_out = ele.element("Ŀ¼�ڷ��úϼ�").getText();
					}
					String pay_self = "";
					if(ele.element("��������") != null){
						pay_self = ele.element("��������").getText();
					}
					String pay_line = "";
					if(ele.element("����") != null){
						pay_line = ele.element("����").getText();
					}
					String pay_fengding = "";
					if(ele.element("����ҽ���Ƿ����") != null){
						pay_fengding = ele.element("����ҽ���Ƿ����").getText();
					}
					String pay_dbz = "";
					if(ele.element("�����ֱ�־") != null){
						pay_dbz = ele.element("�����ֱ�־").getText();
					}
					String pay_jsrc = "";
					if(ele.element("�����˴�") != null){
						pay_jsrc = ele.element("�����˴�").getText();
					}
					String pay_ddyljzfd = "";
					if(ele.element("���ζ���ҽ�ƻ����ֵ�") != null){
						pay_ddyljzfd = ele.element("���ζ���ҽ�ƻ����ֵ�").getText();
					}
					String pay_sybxzfje = "";
					if(ele.element("������ҵ����֧�����") != null){
						pay_sybxzfje = ele.element("������ҵ����֧�����").getText();
					}
					String status = "";
					if(ele.element("��Ч��־") != null){
						status = ele.element("��Ч��־").getText();
					}
					json.put("ybnumber_js", ybnumber_js);
					json.put("hospitalid_js", hospitalid_js);
					json.put("serialno_js", serialno_js);
					json.put("shoujuno_js", shoujuno_js);
					json.put("medtype_js", medtype_js);
					json.put("businesstype_js", businesstype_js);
					json.put("opertime_js", yibaoformattime(opertime_js));
					json.put("diagnoseno_o", diagnoseno_o);
					json.put("diagnosename_o", diagnosename_o);
					json.put("indeptname_js", indeptname_js);
					json.put("begintime_js", yibaoformattime(begintime_js));
					json.put("endtime_js", yibaoformattime(endtime_js));
					json.put("pay_total", pay_total);
					json.put("pay_account", pay_account);
					json.put("pay_insurance", pay_insurance);
					json.put("pay_person", pay_person);
					json.put("pay_gwy", pay_gwy);
					json.put("pay_lx", pay_lx);
					json.put("pay_gs", pay_gs);
					json.put("pay_sy", pay_sy);
					json.put("pay_sygs", pay_sygs);
					json.put("pay_zgf", pay_zgf);
					json.put("pay_out", pay_out);
					json.put("pay_self", pay_self);
					json.put("pay_line", pay_line);
					json.put("pay_fengding", pay_fengding);
					json.put("pay_dbz", pay_dbz);
					json.put("pay_jsrc", pay_jsrc);
					json.put("pay_ddyljzfd", pay_ddyljzfd);
					json.put("pay_sybxzfje", pay_sybxzfje);
					json.put("status", status);
					json.put("msg","1");
				}
			}else{
				json.put("msg","0");
			}
		} catch (RemoteException | DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		result = json.toString();
		return SUCCESS;
	}
	
	private String yibaoformattime(String time){
		String newtime = "";
		if(time.length()>=14){
			String year = time.substring(0, 4);
			String month = time.substring(4, 6);
			String date = time.substring(6, 8);
			String hour = time.substring(8, 10);
			String minute = time.substring(10, 12);
			String second = time.substring(12, 14);
			newtime = year+"-"+month+"-"+date+" "+hour+":"+minute+":"+second;
		}
		return newtime;
	}
	
	@SuppressWarnings("rawtypes")
	public String queryybbxinit(){
		IService1 iService1 = new IService1Proxy();
		try {
			String  xml= iService1.getHospitalList();
			//System.out.println(xml);
			Document document = DocumentHelper.parseText(xml);
			String resultFlag = document.selectSingleNode(
					"//GetHospitalList/Result/ResultFlag").getText();
			String message = document.selectSingleNode(
					"//GetHospitalList/Result/Message").getText();
			if ("1".equals(resultFlag)) {
				List list = document
						.selectNodes("//GetHospitalList/NewDataSet/jljzj");
				Iterator iter = list.iterator();
				ybhospitals = new ArrayList<YBHospitalDTO>();
				while (iter.hasNext()) {
					YBHospitalDTO ybhdto= new YBHospitalDTO();
					Element ele = (Element) iter.next();
					String hid = ele.element("������").getText();
					String hname= ele.element("��������").getText();
					String hlevel= ele.element("ҽԺ�ȼ�").getText();
					ybhdto.setHospitalid(hid);
					ybhdto.setHospitalname(hname);
					ybhdto.setHospitallevel(hlevel);
					ybhospitals.add(ybhdto);
				}
			}else{
				result=message;
			}
		} catch (RemoteException | DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	public BaseinfoService getBaseinfoService() {
		return baseinfoService;
	}

	public void setBaseinfoService(BaseinfoService baseinfoService) {
		this.baseinfoService = baseinfoService;
	}

	public SearchService getSearchService() {
		return searchService;
	}

	public void setSearchService(SearchService searchService) {
		this.searchService = searchService;
	}

	public List<MedicalafterDTO> getMedicalafters() {
		return medicalafters;
	}

	public void setMedicalafters(List<MedicalafterDTO> medicalafters) {
		this.medicalafters = medicalafters;
	}

	public MedicalafterDTO getMedicalafterDTO() {
		return medicalafterDTO;
	}

	public void setMedicalafterDTO(MedicalafterDTO medicalafterDTO) {
		this.medicalafterDTO = medicalafterDTO;
	}

	public BaseInfoDTO getBaseInfoDTO() {
		return baseInfoDTO;
	}

	public void setBaseInfoDTO(BaseInfoDTO baseInfoDTO) {
		this.baseInfoDTO = baseInfoDTO;
	}

	public List<BaseInfoDTO> getBaseinfos() {
		return baseinfos;
	}

	public void setBaseinfos(List<BaseInfoDTO> baseinfos) {
		this.baseinfos = baseinfos;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public BusinessService getBusinessService() {
		return businessService;
	}

	public void setBusinessService(BusinessService businessService) {
		this.businessService = businessService;
	}

	public List<OrganDTO> getOrgs() {
		return orgs;
	}

	public void setOrgs(List<OrganDTO> orgs) {
		this.orgs = orgs;
	}

	public String getCur_page() {
		return cur_page;
	}

	public void setCur_page(String cur_page) {
		this.cur_page = cur_page;
	}

	public String getTerm() {
		return term;
	}

	public void setTerm(String term) {
		this.term = term;
	}

	public String getToolsmenu() {
		return toolsmenu;
	}

	public void setToolsmenu(String toolsmenu) {
		this.toolsmenu = toolsmenu;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getOid() {
		return oid;
	}

	public void setOid(String oid) {
		this.oid = oid;
	}

	public String getOperational() {
		return operational;
	}

	public void setOperational(String operational) {
		this.operational = operational;
	}

	public String[] getFilebase64() {
		return filebase64;
	}

	public void setFilebase64(String[] filebase64) {
		this.filebase64 = filebase64;
	}

	public HashMap<String, String> getMap() {
		return map;
	}

	public void setMap(HashMap<String, String> map) {
		this.map = map;
	}

	public String getOpertime1() {
		return opertime1;
	}

	public void setOpertime1(String opertime1) {
		this.opertime1 = opertime1;
	}

	public String getOpertime2() {
		return opertime2;
	}

	public void setOpertime2(String opertime2) {
		this.opertime2 = opertime2;
	}

	public ActDTO getActDTO() {
		return actDTO;
	}

	public void setActDTO(ActDTO actDTO) {
		this.actDTO = actDTO;
	}

	public String getAasql() {
		return aasql;
	}

	public void setAasql(String aasql) {
		this.aasql = aasql;
	}

	public String getDs() {
		return ds;
	}

	public void setDs(String ds) {
		this.ds = ds;
	}

	public List<CheckDTO> getMonths() {
		return months;
	}

	public void setMonths(List<CheckDTO> months) {
		this.months = months;
	}

	public String getM() {
		return m;
	}

	public void setM(String m) {
		this.m = m;
	}

	public List<JzMabills> getMabills() {
		return mabills;
	}

	public void setMabills(List<JzMabills> mabills) {
		this.mabills = mabills;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getApproveresult() {
		return approveresult;
	}

	public void setApproveresult(String approveresult) {
		this.approveresult = approveresult;
	}

	public List<YBHospitalDTO> getYbhospitals() {
		return ybhospitals;
	}

	public void setYbhospitals(List<YBHospitalDTO> ybhospitals) {
		this.ybhospitals = ybhospitals;
	}

	public YBHospitalDTO getyBHospitalDTO() {
		return yBHospitalDTO;
	}

	public void setyBHospitalDTO(YBHospitalDTO yBHospitalDTO) {
		this.yBHospitalDTO = yBHospitalDTO;
	}

	public String getMtype() {
		return mtype;
	}

	public void setMtype(String mtype) {
		this.mtype = mtype;
	}

	public String getSsn() {
		return ssn;
	}

	public void setSsn(String ssn) {
		this.ssn = ssn;
	}

	public String getHid() {
		return hid;
	}

	public void setHid(String hid) {
		this.hid = hid;
	}

	public String getSerialno() {
		return serialno;
	}

	public void setSerialno(String serialno) {
		this.serialno = serialno;
	}

	public List<DeptDTO> getHs() {
		return hs;
	}

	public void setHs(List<DeptDTO> hs) {
		this.hs = hs;
	}

}
