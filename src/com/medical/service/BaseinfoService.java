package com.medical.service;

import java.util.List;

import com.medical.common.Pager;
import com.medical.dto.BaseInfoDTO;
import com.medical.dto.CheckDTO;
import com.medical.dto.HealthDTO;
import com.medical.dto.MedicalafterDTO;
import com.medical.model.JzMedicalafterExample;

public interface BaseinfoService {
	// �ͱ�����Ϣ�б��ѯ
	public List<HealthDTO> findForperson(HealthDTO healthDTO, String orgno);

	// ������ԱId��ѯ����Ա��Ϣ
	public HealthDTO findByMemberId(String memberId,String ds);

	public String getToolsmenu();
	
	// ��Ϣ�˶���Ϣ�б��ѯ
	public List<CheckDTO> findAllMemberInfo(String url, Integer curpage,
			String sql);
	
	public Pager getPager();
	
	public CheckDTO findMemberInfo(CheckDTO checkDTO);
	
	public int updateTestSsn(CheckDTO checkDTO);
	
	public List<BaseInfoDTO> findMemberByPaperId(BaseInfoDTO baseInfoDTO);
	
	public MedicalafterDTO findMemberByID(BaseInfoDTO baseInfoDTO);
	
	public MedicalafterDTO saveAfterApply(MedicalafterDTO medicalafterDTO);
	
	public List<MedicalafterDTO> findMedicalaftersByPaperId(BaseInfoDTO baseInfoDTO);
	
	public List<MedicalafterDTO> queryMedicalafters(JzMedicalafterExample example,Integer curpage);

	public MedicalafterDTO findCountAssist(MedicalafterDTO medicalafterDTO);
	
	public MedicalafterDTO findMemberInfoPrint(MedicalafterDTO m);
	
	public MedicalafterDTO findMemberByKey(MedicalafterDTO m);
}
