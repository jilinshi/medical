package com.medical.action;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import com.medical.common.Base64Image;
import com.medical.common.FileUpload;
import com.medical.common.Idcard;
import com.medical.dto.DisasterafterDTO;
import com.medical.dto.OrganDTO;
import com.medical.dto.UserInfoDTO;
import com.medical.model.JzDisasterafterExample;
import com.medical.service.BusinessService;
import com.medical.service.DisasterAfterService;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class DisasterAction extends ActionSupport {
	
	private static final long serialVersionUID = -5126976226423666862L;
	private String result;
	private DisasterafterDTO disasterafterDTO;
	private List<DisasterafterDTO> disasterafterDTOs;
	private DisasterAfterService disasterAfterService;
	private String[] filebase64;
	private HashMap<String, String> map;
	private String paperid;
	private BusinessService businessService;
	private List<OrganDTO> orgs;
	private String cur_page;
	private String term;
	private String oid;
	private String value;
	private String opertime1;
	private String opertime2;
	private String approveresult;
	private String toolsmenu;
	@SuppressWarnings("rawtypes")
	public String disasterappinit() {
		Map session = ActionContext.getContext().getSession();
		UserInfoDTO user = (UserInfoDTO) session.get("user");
		if (user.getOrganizationId().length() == 4) {

		} else {
			result = "您所在的机构，没有审批权限！";
			return "result";
		}
		return SUCCESS;
	}
	
	public String disasterapp() {
		String idcard = disasterafterDTO.getPaperid();
		if(idcard.length()==15){
			//15位转18位
			idcard = Idcard.uptoeighteen(idcard);
		}
		boolean flag = Idcard.Verify(idcard);
		if(flag){
			try {
				String birthday = Idcard.getBirthday(idcard);
				SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
				Date birthdate = sdf.parse(birthday);
				disasterafterDTO.setBirthday(birthdate);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}  
			disasterafterDTO = this.disasterAfterService.saveDisasterApp(disasterafterDTO);
			FileUpload fu = new FileUpload("/file/disasterafter");
			String dir = fu.filepath + "\\" + disasterafterDTO.getDaId() + "\\";
			fu.MakeDir(dir);
			if (null != filebase64) {
				for (int i = 0; i < filebase64.length; i++) {
					Base64Image.GenerateImage(filebase64[i], dir + (i + 1) + ".jpg");
				}
			}
		}else{
			result ="身份证号码错误！,请输入正确的身份证";
			return "result";
		}
		return SUCCESS;
	}
	
	public String getinforbypaperid(){
		String idcard=paperid;
		JSONObject json = new JSONObject();
		if(paperid.length()==15){
			//15位转18位
			idcard = Idcard.uptoeighteen(paperid);
		}
		boolean flag = Idcard.Verify(idcard);
		if(flag){
			disasterafterDTO = this.disasterAfterService.countAllAssitpay(paperid);
			json.put("in_num", disasterafterDTO.getIn_num());
			json.put("out_num", disasterafterDTO.getOut_num());
			json.put("in_sumpay", disasterafterDTO.getIn_sumpay());
			json.put("out_sumpay", disasterafterDTO.getOut_sumpay());
			json.put("mt1", disasterafterDTO.getMt1());
			json.put("mt2", disasterafterDTO.getMt2());
			json.put("message", "身份证号码正确！");
			json.put("flag", "1");
		}else{
			json.put("message", "身份证号码错误！");
			json.put("flag", "0");
		}
		result = json.toString();
		return SUCCESS;
	}
	
	public String printinhospital() {
		disasterafterDTO = this.disasterAfterService
				.findMemberInfoPrint(disasterafterDTO);
		map = new HashMap<String, String>();
		String medicaltype = "";
		String sickencontent = "";
		if ("1".equals(disasterafterDTO.getMedicaltype())) {
			medicaltype = "住院";
			sickencontent = disasterafterDTO.getSickencontent();
		} else if ("2".equals(disasterafterDTO.getMedicaltype())) {
			medicaltype = "门诊";
			if ("-1".equals(disasterafterDTO.getDiagnose())) {
				sickencontent = "其他";
			} else if ("0001".equals(disasterafterDTO.getDiagnose())) {
				sickencontent = "尿毒症";
			} else if ("0002".equals(disasterafterDTO.getDiagnose())) {
				sickencontent = "肝、肾脏移植（抗排异治疗）";
			} else if ("0004".equals(disasterafterDTO.getDiagnose())) {
				sickencontent = "肿瘤（仅限于放疗、化疗）";
			} else if ("0005".equals(disasterafterDTO.getDiagnose())) {
				sickencontent = "骨髓移植（抗排异治疗）";
			} else if ("0006".equals(disasterafterDTO.getDiagnose())) {
				sickencontent = "心脏移植（抗排异治疗）";
			}
		}
		String persontype = "";
		if ("111".equals(disasterafterDTO.getPersontype())) {
			persontype = "再保障";
		} else if ("100".equals(disasterafterDTO.getPersontype())) {
			persontype = "城市低保";
		} else if ("110".equals(disasterafterDTO.getPersontype())) {
			persontype = "农村低保";
		} else {
			persontype = "普通居民";
		}
		String insuretype = "";
		if ("1".equals(disasterafterDTO.getInsuretype())) {
			insuretype = "医保";
		} else if ("2".equals(disasterafterDTO.getInsuretype())) {
			insuretype = "农合";
		} else {
			insuretype = "其他";
		}

		map.put("MEDICALTYPE", medicaltype);
		map.put("HOSPITAL", disasterafterDTO.getHospital());
		map.put("MEMBERNAME", disasterafterDTO.getMembername());
		map.put("SSN", disasterafterDTO.getSsn());
		map.put("PERSONTYPE", persontype);
		map.put("PAPERID", disasterafterDTO.getPaperid());
		map.put("SEX", disasterafterDTO.getSex());
		map.put("ADDRESS", disasterafterDTO.getFamaddr());
		map.put("INSURETYPE", insuretype);
		map.put("SICKENCONTENT", sickencontent);
		map.put("TIKETNO", disasterafterDTO.getTiketno());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		String printtime = sdf.format(cal.getTime());
		map.put("BEGINTIME", disasterafterDTO.getBegintimeval());
		map.put("ENDTIME", disasterafterDTO.getEndtimeval());
		map.put("PRINTTIME", printtime);
		map.put("INDATE", disasterafterDTO.getIndate().toString());
		map.put("NUM", disasterafterDTO.getNum().toString());
		map.put("ASISSTPAY", disasterafterDTO.getAsisstpay().toString());
		map.put("TOTALCOST", disasterafterDTO.getTotalcost().toString());
		map.put("INSUREPAY", disasterafterDTO.getInsurepay().toString());
		map.put("OUTPAY", disasterafterDTO.getOutpay().toString());
		map.put("CAPAY", disasterafterDTO.getCapay().toString());
		map.put("BUSINESSPAY", disasterafterDTO.getBusinesspay().toString());
		map.put("PAYLINE", disasterafterDTO.getPayLine().toString());
		map.put("HOSIPITALPAY", disasterafterDTO.getHospitalpay().toString());
		map.put("SUMPAY", disasterafterDTO.getSumpay().toString());

		/*// 记录打印报销凭证次数
		int pzprinum = Integer.valueOf(disasterafterDTO.getPzPrinum()) + 1;
		disasterafterDTO.setPzPrinum(pzprinum + "");
		int u = this.baseinfoService.updateMedicalpzPinSum(disasterafterDTO,
				"pz");
		System.out.println(u);*/
		return SUCCESS;
	}
	
	public String printoutpatient() {
		disasterafterDTO = this.disasterAfterService
				.findMemberInfoPrint(disasterafterDTO);
		map = new HashMap<String, String>();
		String medicaltype = "";
		String sickencontent = "";
		if ("1".equals(disasterafterDTO.getMedicaltype())) {
			medicaltype = "住院";
			sickencontent = disasterafterDTO.getSickencontent();
		} else if ("2".equals(disasterafterDTO.getMedicaltype())) {
			medicaltype = "门诊";
			if ("-1".equals(disasterafterDTO.getDiagnose())) {
				sickencontent = "其他";
			} else if ("0001".equals(disasterafterDTO.getDiagnose())) {
				sickencontent = "尿毒症";
			} else if ("0002".equals(disasterafterDTO.getDiagnose())) {
				sickencontent = "肝、肾脏移植（抗排异治疗）";
			} else if ("0004".equals(disasterafterDTO.getDiagnose())) {
				sickencontent = "肿瘤（仅限于放疗、化疗）";
			} else if ("0005".equals(disasterafterDTO.getDiagnose())) {
				sickencontent = "骨髓移植（抗排异治疗）";
			} else if ("0006".equals(disasterafterDTO.getDiagnose())) {
				sickencontent = "心脏移植（抗排异治疗）";
			}
		}
		String persontype = "";
		if ("111".equals(disasterafterDTO.getPersontype())) {
			persontype = "再保障";
		} else if ("100".equals(disasterafterDTO.getPersontype())) {
			persontype = "城市低保";
		} else if ("110".equals(disasterafterDTO.getPersontype())) {
			persontype = "农村低保";
		} else {
			persontype = "普通居民";
		}
		String insuretype = "";
		if ("1".equals(disasterafterDTO.getInsuretype())) {
			insuretype = "医保";
		} else if ("2".equals(disasterafterDTO.getInsuretype())) {
			insuretype = "农合";
		} else {
			insuretype = "其他";
		}

		map.put("MEDICALTYPE", medicaltype);
		map.put("HOSPITAL", disasterafterDTO.getHospital());
		map.put("MEMBERNAME", disasterafterDTO.getMembername());
		map.put("SSN", disasterafterDTO.getSsn());
		map.put("PERSONTYPE", persontype);
		map.put("PAPERID", disasterafterDTO.getPaperid());
		map.put("SEX", disasterafterDTO.getSex());
		map.put("ADDRESS", disasterafterDTO.getFamaddr());
		map.put("INSURETYPE", insuretype);
		map.put("SICKENCONTENT", sickencontent);
		map.put("TIKETNO", disasterafterDTO.getTiketno());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		String printtime = sdf.format(cal.getTime());
		map.put("BEGINTIME", disasterafterDTO.getBegintimeval());
		map.put("ENDTIME", disasterafterDTO.getEndtimeval());
		map.put("PRINTTIME", printtime);
		map.put("INDATE", disasterafterDTO.getIndate().toString());
		map.put("NUM", disasterafterDTO.getNum().toString());
		map.put("ASISSTPAY", disasterafterDTO.getAsisstpay().toString());
		map.put("TOTALCOST", disasterafterDTO.getTotalcost().toString());
		map.put("INSUREPAY", disasterafterDTO.getInsurepay().toString());
		map.put("OUTPAY", disasterafterDTO.getOutpay().toString());
		map.put("CAPAY", disasterafterDTO.getCapay().toString());
		map.put("BUSINESSPAY", disasterafterDTO.getBusinesspay().toString());
		map.put("PAYLINE", disasterafterDTO.getPayLine().toString());
		map.put("HOSIPITALPAY", disasterafterDTO.getHospitalpay().toString());
		map.put("SUMPAY", disasterafterDTO.getSumpay().toString());

		/*// 记录打印报销凭证次数
		int pzprinum = Integer.valueOf(disasterafterDTO.getPzPrinum()) + 1;
		disasterafterDTO.setPzPrinum(pzprinum + "");
		int u = this.baseinfoService.updateMedicalpzPinSum(disasterafterDTO,
				"pz");
		System.out.println(u);*/
		return SUCCESS;
	}
	
	public String printapp() {
		disasterafterDTO = this.disasterAfterService
				.findMemberInfoPrint(disasterafterDTO);
		map = new HashMap<String, String>();
		String medicaltype = "";
		String sickencontent = "";
		if ("1".equals(disasterafterDTO.getMedicaltype())) {
			medicaltype = "住院";
			sickencontent = disasterafterDTO.getSickencontent();
		} else if ("2".equals(disasterafterDTO.getMedicaltype())) {
			medicaltype = "门诊";
			if ("-1".equals(disasterafterDTO.getDiagnose())) {
				sickencontent = "其他";
			} else if ("0001".equals(disasterafterDTO.getDiagnose())) {
				sickencontent = "尿毒症";
			} else if ("0002".equals(disasterafterDTO.getDiagnose())) {
				sickencontent = "肝、肾脏移植（抗排异治疗）";
			} else if ("0004".equals(disasterafterDTO.getDiagnose())) {
				sickencontent = "肿瘤（仅限于放疗、化疗）";
			} else if ("0005".equals(disasterafterDTO.getDiagnose())) {
				sickencontent = "骨髓移植（抗排异治疗）";
			} else if ("0006".equals(disasterafterDTO.getDiagnose())) {
				sickencontent = "心脏移植（抗排异治疗）";
			}
		}
		String persontype = "";
		if ("111".equals(disasterafterDTO.getPersontype())) {
			persontype = "再保障";
		} else if ("100".equals(disasterafterDTO.getPersontype())) {
			persontype = "城市低保";
		} else if ("110".equals(disasterafterDTO.getPersontype())) {
			persontype = "农村低保";
		} else {
			persontype = "普通居民";
		}
		String insuretype = "";
		if ("1".equals(disasterafterDTO.getInsuretype())) {
			insuretype = "医保";
		} else if ("2".equals(disasterafterDTO.getInsuretype())) {
			insuretype = "农合";
		} else {
			insuretype = "其他";
		}

		map.put("MEDICALTYPE", medicaltype);
		map.put("HOSPITAL", disasterafterDTO.getHospital());
		map.put("MEMBERNAME", disasterafterDTO.getMembername());
		map.put("SSN", disasterafterDTO.getSsn());
		map.put("PERSONTYPE", persontype);
		map.put("PAPERID", disasterafterDTO.getPaperid());
		map.put("SEX", disasterafterDTO.getSex());
		map.put("ADDRESS", disasterafterDTO.getFamaddr());
		map.put("INSURETYPE", insuretype);
		map.put("SICKENCONTENT", sickencontent);
		map.put("TIKETNO", disasterafterDTO.getTiketno());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		String printtime = sdf.format(cal.getTime());
		map.put("BEGINTIME", disasterafterDTO.getBegintimeval());
		map.put("ENDTIME", disasterafterDTO.getEndtimeval());
		map.put("PRINTTIME", printtime);
		map.put("INDATE", disasterafterDTO.getIndate().toString());
		map.put("NUM", disasterafterDTO.getNum().toString());
		map.put("ASISSTPAY", disasterafterDTO.getAsisstpay().toString());
		map.put("TOTALCOST", disasterafterDTO.getTotalcost().toString());
		map.put("INSUREPAY", disasterafterDTO.getInsurepay().toString());
		map.put("OUTPAY", disasterafterDTO.getOutpay().toString());
		map.put("CAPAY", disasterafterDTO.getCapay().toString());
		map.put("BUSINESSPAY", disasterafterDTO.getBusinesspay().toString());
		map.put("PAYLINE", disasterafterDTO.getPayLine().toString());
		map.put("HOSIPITALPAY", disasterafterDTO.getHospitalpay().toString());
		map.put("SUMPAY", disasterafterDTO.getSumpay().toString());
		map.put("TELEPHONE", disasterafterDTO.getTelephone());
		map.put("FAMCOUNT", disasterafterDTO.getFamcountval());
		map.put("BIRTHDAY", disasterafterDTO.getBirthdayval());

		/*// 记录打印报销凭证次数
		int appprinum = Integer.valueOf(disasterafterDTO.getAppPrinum()) + 1;
		disasterafterDTO.setAppPrinum(appprinum + "");
		int u = this.baseinfoService.updateMedicalpzPinSum(disasterafterDTO,
				"app");
		System.out.println(u);*/
		return SUCCESS;
	}
	
	@SuppressWarnings("rawtypes")
	public String querydisasterinit() {
		Map session = ActionContext.getContext().getSession();
		UserInfoDTO userinfo = (UserInfoDTO) session.get("user");
		String orgid = userinfo.getOrganizationId();
		if (4 == orgid.length()) {
			this.setOrgs(this.businessService.getOrganList(userinfo
					.getOrganizationId()));
		} else {
			this.result = "您所在的机构，没有查询权限！";
			return "result";
		}
		return SUCCESS;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String querydisaster() {
		Map session = ActionContext.getContext().getSession();
		UserInfoDTO userinfo = (UserInfoDTO) session.get("user");
		String orgid = userinfo.getOrganizationId();
		JzDisasterafterExample example = new JzDisasterafterExample();
		String jwhere = "";
		if (null == cur_page || "".equals(cur_page)) {
			com.medical.model.JzDisasterafterExample.Criteria criteria = example
					.createCriteria();
			if ("MEMBERNAME".equals(term)) {
				criteria.andMembernameLike(value + "%");
				jwhere = jwhere + "and da.membername like '" + value + "%'";
			} else if ("PAPERID".equals(term)) {
				criteria.andPaperidLike(value + "%");
				jwhere = jwhere + "and da.paperid like '" + value + "%'";
			} else {
			}
			if (!"".equals(approveresult)) {
				criteria.andApproveresultEqualTo(approveresult);
				jwhere = jwhere + " and da.approveresult='" + approveresult
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
						+ "and to_char(da.updatetime,'yyyy-MM-dd') >= '"
						+ opertime2 + "'";
			} else if (opertime2.equals("") || null == opertime2) {
				criteria.andUpdatetimeLessThan(opertimefrom);
				jwhere = jwhere + "and to_char(da.updatetime,'yyyy-MM-dd') < '"
						+ opertime1 + "'";
			} else {
				criteria.andUpdatetimeBetween(opertimefrom, opertimeto);
				jwhere = jwhere + "and to_char(da.updatetime,'yyyy-MM-dd') >='"
						+ opertime1
						+ "' and to_char(da.updatetime,'yyyy-MM-dd') < '"
						+ opertime2 + "'";
			}
			session.put("sql", example);
			session.put("jwhere", jwhere);
			cur_page = "1";
		} else {
			example = (JzDisasterafterExample) session.get("sql");
			jwhere = (String) session.get("jwhere");
		}

		this.setDisasterafterDTOs(disasterAfterService.queryDisasterafters(example,
				new Integer(cur_page), "querydisaster.action"));
		this.setToolsmenu(disasterAfterService.getPager().getToolsmenu());
		//this.setOrgs(this.disasterAfterService.getOrganList(orgid));
		return SUCCESS;
	}
	
	public String cancel() {
		JSONObject json = new JSONObject();
		int u = this.disasterAfterService.deleteDisasterafter(disasterafterDTO);
		json.put("u", u);
		result = json.toString();
		return SUCCESS;
	}
	
	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public DisasterafterDTO getDisasterafterDTO() {
		return disasterafterDTO;
	}

	public void setDisasterafterDTO(DisasterafterDTO disasterafterDTO) {
		this.disasterafterDTO = disasterafterDTO;
	}

	public List<DisasterafterDTO> getDisasterafterDTOs() {
		return disasterafterDTOs;
	}

	public void setDisasterafterDTOs(List<DisasterafterDTO> disasterafterDTOs) {
		this.disasterafterDTOs = disasterafterDTOs;
	}

	public DisasterAfterService getDisasterAfterService() {
		return disasterAfterService;
	}

	public void setDisasterAfterService(DisasterAfterService disasterAfterService) {
		this.disasterAfterService = disasterAfterService;
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

	public String getPaperid() {
		return paperid;
	}

	public void setPaperid(String paperid) {
		this.paperid = paperid;
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

	public String getOid() {
		return oid;
	}

	public void setOid(String oid) {
		this.oid = oid;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
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

	public String getApproveresult() {
		return approveresult;
	}

	public void setApproveresult(String approveresult) {
		this.approveresult = approveresult;
	}

	public String getToolsmenu() {
		return toolsmenu;
	}

	public void setToolsmenu(String toolsmenu) {
		this.toolsmenu = toolsmenu;
	}

}
