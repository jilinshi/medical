package com.medical.action;

import java.util.List;
import java.util.Map;

import com.medical.common.Base64Image;
import com.medical.common.FileUpload;
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
		disasterafterDTO = this.disasterAfterService.saveDisasterApp(disasterafterDTO);
		/*FileUpload fu = new FileUpload("/file/medicalafter");
		String dir = fu.filepath + "\\" + disasterafterDTO.getDaId() + "\\";
		fu.MakeDir(dir);
		if (null != filebase64) {
			for (int i = 0; i < filebase64.length; i++) {
				Base64Image
						.GenerateImage(filebase64[i], dir + (i + 1) + ".jpg");
			}
		}*/
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
}
