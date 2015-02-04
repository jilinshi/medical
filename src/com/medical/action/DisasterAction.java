package com.medical.action;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.medical.common.Base64Image;
import com.medical.common.FileUpload;
import com.medical.common.Idcard;
import com.medical.dto.DisasterafterDTO;
import com.medical.dto.UserInfoDTO;
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

}
