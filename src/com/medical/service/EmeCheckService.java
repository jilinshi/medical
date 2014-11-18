package com.medical.service;

import java.util.List;

import com.medical.common.Pager;
import com.medical.dto.EmeCheckDTO;

public interface EmeCheckService {
	/**
	 * ��ѯ�߷��б�
	 * 
	 * @param emeCheckDTO
	 * @return
	 */
	public List<EmeCheckDTO> queryEmeCheckViews(String jwhere, int cur_page);

	/**
	 * ��ѯ�����б�
	 * 
	 * @param emeCheckDTO
	 * @return
	 */
	public List<EmeCheckDTO> queryEmeCheckApproves(String jwhere, int cur_page);

	/**
	 * 
	 * @param emeCheckDTO
	 */
	public void saveInterview(EmeCheckDTO emeCheckDTO);

	/**
	 * 
	 * @param emeCheckDTO
	 */
	public void saveStreetApproves(EmeCheckDTO emeCheckDTO);

	/**
	 * 
	 * @param emeCheckDTO
	 */
	public void saveAregApproves(EmeCheckDTO emeCheckDTO);

	public EmeCheckDTO getMemberInfo(String memberid);

	public EmeCheckDTO getEmeCheckInfo(String emecheckId,String memberId);
	public Pager getPager();
}
