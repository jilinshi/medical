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
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import com.medical.common.Base64Image;
import com.medical.common.FileUpload;
import com.medical.dto.ActDTO;
import com.medical.dto.BaseInfoDTO;
import com.medical.dto.CheckDTO;
import com.medical.dto.MedicalafterDTO;
import com.medical.dto.OrganDTO;
import com.medical.dto.UserInfoDTO;
import com.medical.model.JzMabills;
import com.medical.model.JzMabillsExample;
import com.medical.model.JzMedicalafterExample;
import com.medical.service.BaseinfoService;
import com.medical.service.BusinessService;
import com.medical.service.SearchService;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

import dbbx.ServiceMainSoap;
import dbbx.ServiceMainSoapProxy;

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
		ServiceMainSoap sms = new ServiceMainSoapProxy();
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
			result = "您所在的机构，没有审批权限！";
			return "result";
		}
		return SUCCESS;
	}

	public String afterquerymember() {
		// 查询人员基本信息
		baseinfos = this.baseinfoService.findMemberByPaperId(baseInfoDTO);
		medicalafters = this.baseinfoService
				.findMedicalaftersByPaperId(baseInfoDTO);
		if (baseinfos.size() == 0) {
			result = "没有此人信息，请核实！";
			return "result";
		}
		// 查询人员医后报销信息

		return SUCCESS;
	}

	public String afterapplyinit() {
		// 查询人员基本信息
		medicalafterDTO = this.baseinfoService.findMemberByID(baseInfoDTO);
		// 查询本年救助信息：jz_act
		actDTO = this.baseinfoService.findActByID(baseInfoDTO);
		// 查询人员的低保救助时间、再保障救助时间
		baseInfoDTO = this.baseinfoService.findSalvationStatus(baseInfoDTO);
		return SUCCESS;
	}

	public String afterapply() {
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
			this.result = "您所在的机构，没有查询权限！";
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
			this.result = "您所在的机构，没有查询权限！";
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
				+ " decode(sb.ds,'1','城市' ,'2','农村',null) as ds,  "
				+ "sb.on_no, max(sb.sb_batchname) as batchname, "
				+ " count(*) rc, sum(ma.asisstpay) as asisstpay "
				+ " from jz_medicalafter ma, (select sb.sb_batchname,  sb.sb_id, "
				+ " sb.sb_disposests,  '1' as ds,  sb.on_no "
				+ " from salvationbatch@cs sb, salvationoperation@cs so "
				+ " where so.so_id = sb.so_id  and so.st_id = '4' "
				+ " and sb.sb_disposests = '处理中' union all "
				+ " select sb.sb_batchname, sb.sb_id,  sb.sb_disposests, "
				+ " '2' as ds, sb.on_no "
				+ " from salvationbatch@nc sb, salvationoperation@nc so "
				+ " where so.so_id = sb.so_id and so.st_id = '4' "
				+ " and sb.sb_disposests = '处理中') sb "
				+ " where sb.on_no = substr(ma.on_no, 1, 6) and sb.ds = ma.member_type "
				+ " and ma.implsts = '0' and ma.approveresult = '1' "
				+ " group by sb.on_no, sb.ds  order by sb.ds, sb.on_no";
		medicalafters = baseinfoService.queryMaBillStat(sql);

		return SUCCESS;
	}

	public String genmabill() {

		baseinfoService.saveMaBatchDone();
		String sql = "select (select org.organization_id||'-'||org.asorgname from "
				+ "sys_t_organization org where org.organization_id=sb.on_no) as orgname , "
				+ " decode(sb.ds,'1','城市' ,'2','农村',null) as ds,  "
				+ "sb.on_no, max(sb.sb_batchname) as batchname, "
				+ " count(*) rc, sum(ma.asisstpay) as asisstpay "
				+ " from jz_medicalafter ma, (select sb.sb_batchname,  sb.sb_id, "
				+ " sb.sb_disposests,  '1' as ds,  sb.on_no "
				+ " from salvationbatch@cs sb, salvationoperation@cs so "
				+ " where so.so_id = sb.so_id  and so.st_id = '4' "
				+ " and sb.sb_disposests = '处理中' union all "
				+ " select sb.sb_batchname, sb.sb_id,  sb.sb_disposests, "
				+ " '2' as ds, sb.on_no "
				+ " from salvationbatch@nc sb, salvationoperation@nc so "
				+ " where so.so_id = sb.so_id and so.st_id = '4' "
				+ " and sb.sb_disposests = '处理中') sb ,jz_mabills mb "
				+ " where sb.on_no = substr(ma.on_no, 1, 6) and sb.ds = ma.member_type "
				+ " and ma.implsts = '1' and ma.approveresult = '1' and mb.sb_id=sb.sb_id "
				+ " group by sb.on_no, sb.ds  order by sb.ds, sb.on_no";
		System.out.println(sql);
		medicalafters = baseinfoService.queryMaBillStat(sql);
		this.setResult("结算完毕");
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
			this.result = "您所在的机构，没有查询权限！";
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
			/*
			 * if ("SSN".equals(term)) { } else if ("FAMILYNO".equals(term)) {
			 * criteria.andFamilynoLike(value + "%"); jwhere = jwhere +
			 * "and ma.familyno like '" + value + "%'"; } else if
			 * ("MEMBERNAME".equals(term)) { criteria.andMembernameLike(value +
			 * "%"); jwhere = jwhere + "and ma.membername like '" + value +
			 * "%'"; } else if ("PAPERID".equals(term)) {
			 * criteria.andPaperidLike(value + "%"); jwhere = jwhere +
			 * "and ma.paperid like '" + value + "%'"; } else { }
			 */
			if (!"".equals(ds)) {
				criteria.andDsEqualTo(ds);
			}
			if (!"".equals(m)) {
				criteria.andBatchnameLike(m + "%");
			}

			/*
			 * SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); Date
			 * opertimefrom = new Date(); Date opertimeto = new Date(); try { if
			 * (!opertime1.equals("")) { opertimefrom =
			 * sdf.parse(opertime1.substring(0, 10)); } if
			 * (!opertime2.equals("")) { opertimeto =
			 * sdf.parse(opertime2.substring(0, 10)); } } catch (ParseException
			 * e) { e.printStackTrace(); } if ((opertime1.equals("") || null ==
			 * opertime1) && (opertime2.equals("") || null == opertime2)) { }
			 * else if (opertime1.equals("") || null == opertime1) {
			 * criteria.andUpdatetimeGreaterThan(opertimeto); jwhere = jwhere +
			 * "and to_char(ma.updatetime,'yyyy-MM-dd') >= '" + opertime2 + "'";
			 * } else if (opertime2.equals("") || null == opertime2) {
			 * criteria.andUpdatetimeLessThan(opertimefrom); jwhere = jwhere +
			 * "and to_char(ma.updatetime,'yyyy-MM-dd') < '" + opertime1 + "'";
			 * } else { criteria.andUpdatetimeBetween(opertimefrom, opertimeto);
			 * jwhere = jwhere + "and to_char(ma.updatetime,'yyyy-MM-dd') >='" +
			 * opertime1 + "' and to_char(ma.updatetime,'yyyy-MM-dd') < '" +
			 * opertime2 + "'"; }
			 */
			session.put("sql", example);
			session.put("jwhere", jwhere);
			session.put("m1", m);
			cur_page = "1";
		} else {
			example = (JzMabillsExample) session.get("sql");
			jwhere = (String) session.get("jwhere");
		}
		/*
		 * String aasql =
		 * " SELECT count(*) as rc,    sum(ma.totalcost) as totalcost, " +
		 * " sum(ma.insurepay) as insurepay,        sum(ma.asisstpay) as asisstpay "
		 * + " FROM JZ_MEDICALAFTER MA  WHERE 1 = 1    " + jwhere + " " +
		 * " and ma.implsts = 0    and ma.approveresult = '1'  and 1=1 ";
		 */
		// System.out.println(aasql);
		// String u = baseinfoService.queryMaStat(aasql);
		this.setMabills(this.baseinfoService.queryMedicalafters01(example,
				new Integer(cur_page), "queryafterffdone.action?m=" + this.m));
		this.setToolsmenu(this.businessService.getPager().getToolsmenu());
		this.setOrgs(this.businessService.getOrganList(orgid));
		this.setMonths(this.baseinfoService.getMonths());
		// this.setResult(u);
		session.put("aasql", aasql);
		return SUCCESS;
	}

	public String printinhospital() {
		medicalafterDTO = this.baseinfoService
				.findMemberInfoPrint(medicalafterDTO);
		map = new HashMap<String, String>();
		String medicaltype = "";
		String sickencontent = "";
		if ("1".equals(medicalafterDTO.getMedicaltype())) {
			medicaltype = "住院";
			sickencontent = medicalafterDTO.getSickencontent();
		} else if ("2".equals(medicalafterDTO.getMedicaltype())) {
			medicaltype = "门诊";
			if ("-1".equals(medicalafterDTO.getDiagnose())) {
				sickencontent = "其他";
			} else if ("0001".equals(medicalafterDTO.getDiagnose())) {
				sickencontent = "尿毒症";
			} else if ("0002".equals(medicalafterDTO.getDiagnose())) {
				sickencontent = "肝、肾脏移植（抗排异治疗）";
			} else if ("0004".equals(medicalafterDTO.getDiagnose())) {
				sickencontent = "肿瘤（仅限于放疗、化疗）";
			} else if ("0005".equals(medicalafterDTO.getDiagnose())) {
				sickencontent = "骨髓移植（抗排异治疗）";
			} else if ("0006".equals(medicalafterDTO.getDiagnose())) {
				sickencontent = "心脏移植（抗排异治疗）";
			}
		}
		String persontype = "";
		if ("111".equals(medicalafterDTO.getPersontype())) {
			persontype = "再保障";
		} else if ("100".equals(medicalafterDTO.getPersontype())) {
			persontype = "城市低保";
		} else if ("110".equals(medicalafterDTO.getPersontype())) {
			persontype = "农村低保";
		} else {
			persontype = "普通居民";
		}
		String insuretype = "";
		if ("1".equals(medicalafterDTO.getInsuretype())) {
			insuretype = "医保";
		} else if ("2".equals(medicalafterDTO.getInsuretype())) {
			insuretype = "农合";
		} else {
			insuretype = "其他";
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

		// 记录打印报销凭证次数
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
			medicaltype = "住院";
			sickencontent = medicalafterDTO.getSickencontent();
		} else if ("2".equals(medicalafterDTO.getMedicaltype())) {
			medicaltype = "门诊";
			if ("-1".equals(medicalafterDTO.getDiagnose())) {
				sickencontent = "其他";
			} else if ("0001".equals(medicalafterDTO.getDiagnose())) {
				sickencontent = "尿毒症";
			} else if ("0002".equals(medicalafterDTO.getDiagnose())) {
				sickencontent = "肝、肾脏移植（抗排异治疗）";
			} else if ("0004".equals(medicalafterDTO.getDiagnose())) {
				sickencontent = "肿瘤（仅限于放疗、化疗）";
			} else if ("0005".equals(medicalafterDTO.getDiagnose())) {
				sickencontent = "骨髓移植（抗排异治疗）";
			} else if ("0006".equals(medicalafterDTO.getDiagnose())) {
				sickencontent = "心脏移植（抗排异治疗）";
			}
		}
		String persontype = "";
		if ("111".equals(medicalafterDTO.getPersontype())) {
			persontype = "再保障";
		} else if ("100".equals(medicalafterDTO.getPersontype())) {
			persontype = "城市低保";
		} else if ("110".equals(medicalafterDTO.getPersontype())) {
			persontype = "农村低保";
		} else {
			persontype = "普通居民";
		}
		String insuretype = "";
		if ("1".equals(medicalafterDTO.getInsuretype())) {
			insuretype = "医保";
		} else if ("2".equals(medicalafterDTO.getInsuretype())) {
			insuretype = "农合";
		} else {
			insuretype = "其他";
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

		// 记录打印报销凭证次数
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
			medicaltype = "住院";
			sickencontent = medicalafterDTO.getSickencontent();
		} else if ("2".equals(medicalafterDTO.getMedicaltype())) {
			medicaltype = "门诊";
			if ("-1".equals(medicalafterDTO.getDiagnose())) {
				sickencontent = "其他";
			} else if ("0001".equals(medicalafterDTO.getDiagnose())) {
				sickencontent = "尿毒症";
			} else if ("0002".equals(medicalafterDTO.getDiagnose())) {
				sickencontent = "肝、肾脏移植（抗排异治疗）";
			} else if ("0004".equals(medicalafterDTO.getDiagnose())) {
				sickencontent = "肿瘤（仅限于放疗、化疗）";
			} else if ("0005".equals(medicalafterDTO.getDiagnose())) {
				sickencontent = "骨髓移植（抗排异治疗）";
			} else if ("0006".equals(medicalafterDTO.getDiagnose())) {
				sickencontent = "心脏移植（抗排异治疗）";
			}
		}
		String persontype = "";
		if ("111".equals(medicalafterDTO.getPersontype())) {
			persontype = "再保障";
		} else if ("100".equals(medicalafterDTO.getPersontype())) {
			persontype = "城市低保";
		} else if ("110".equals(medicalafterDTO.getPersontype())) {
			persontype = "农村低保";
		} else {
			persontype = "普通居民";
		}
		String insuretype = "";
		if ("1".equals(medicalafterDTO.getInsuretype())) {
			insuretype = "医保";
		} else if ("2".equals(medicalafterDTO.getInsuretype())) {
			insuretype = "农合";
		} else {
			insuretype = "其他";
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

		// 记录打印报销凭证次数
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
			is = new ByteArrayInputStream("无".getBytes("UTF-8"));
		} else {
			is = new ByteArrayInputStream(this.content.getBytes("UTF-8"));
		}
		System.out.println(2);
		return is;
	}

	public String medicalusebatchinit() {
		String sql = "select (select org.organization_id||'-'||org.asorgname from "
				+ "sys_t_organization org where org.organization_id=sb.on_no) as orgname , "
				+ " decode(sb.ds,'1','城市' ,'2','农村',null) as ds,  "
				+ "sb.on_no, max(sb.sb_batchname) as batchname, "
				+ " count(*) rc, sum(ma.asisstpay) as asisstpay "
				+ " from jz_medicalafter ma, (select sb.sb_batchname,  sb.sb_id, "
				+ " sb.sb_disposests,  '1' as ds,  sb.on_no "
				+ " from salvationbatch@cs sb, salvationoperation@cs so "
				+ " where so.so_id = sb.so_id  and so.st_id = '4' "
				+ " and sb.sb_disposests = '处理中' union all "
				+ " select sb.sb_batchname, sb.sb_id,  sb.sb_disposests, "
				+ " '2' as ds, sb.on_no "
				+ " from salvationbatch@nc sb, salvationoperation@nc so "
				+ " where so.so_id = sb.so_id and so.st_id = '4' "
				+ " and sb.sb_disposests = '处理中') sb ,jz_mabills mb "
				+ " where sb.on_no = substr(ma.on_no, 1, 6) and sb.ds = ma.member_type "
				+ " and ma.implsts = '1' and ma.approveresult = '1' and mb.sb_id=sb.sb_id "
				+ " group by sb.on_no, sb.ds  order by sb.ds, sb.on_no";
		System.out.println(sql);
		medicalafters = baseinfoService.queryMaBillStat(sql);
		// this.setResult("结算完毕");
		this.setMonths(this.baseinfoService.getMonths());
		return SUCCESS;
	}

	public String medicalusebatch() {
		this.setMonths(this.baseinfoService.getMonths());
		// 结算
		if (type.equals("1")) {
			baseinfoService.saveCommitMaBatch();
			String sql = "select (select org.organization_id||'-'||org.asorgname from "
					+ "sys_t_organization org where org.organization_id=sb.on_no) as orgname , "
					+ " decode(sb.ds,'1','城市' ,'2','农村',null) as ds,  "
					+ "sb.on_no, max(sb.sb_batchname) as batchname, "
					+ " count(*) rc, sum(ma.asisstpay) as asisstpay "
					+ " from jz_medicalafter ma, (select sb.sb_batchname,  sb.sb_id, "
					+ " sb.sb_disposests,  '1' as ds,  sb.on_no "
					+ " from salvationbatch@cs sb, salvationoperation@cs so "
					+ " where so.so_id = sb.so_id  and so.st_id = '4' "
					+ " and sb.sb_disposests = '待统计' union all "
					+ " select sb.sb_batchname, sb.sb_id,  sb.sb_disposests, "
					+ " '2' as ds, sb.on_no "
					+ " from salvationbatch@nc sb, salvationoperation@nc so "
					+ " where so.so_id = sb.so_id and so.st_id = '4' "
					+ " and sb.sb_disposests = '待统计') sb ,jz_mabills mb "
					+ " where sb.on_no = substr(ma.on_no, 1, 6) and sb.ds = ma.member_type "
					+ " and ma.implsts = '1' and ma.approveresult = '1' and mb.sb_id=sb.sb_id "
					+ " group by sb.on_no, sb.ds  order by sb.ds, sb.on_no";
			System.out.println(sql);
			medicalafters = baseinfoService.queryMaBillStat(sql);
		}
		// 撤销结算
		if (type.equals("2")) {
			baseinfoService.saveCancelMaBatch(m);
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
}
