package com.medical.service;

import java.math.BigDecimal;
import java.util.List;

import com.medical.common.Pager;
import com.medical.dto.BizCheckDTO;
import com.medical.dto.BusinessDTO;

public interface BizCheckService {
	/**
	 * ���ݲ�ѯ������ѯ�������б�
	 * 
	 * @param bizCheckDTO
	 * @return
	 */
	public List<BizCheckDTO> findByTerm(BizCheckDTO bizCheckDTO,String type);

	/**
	 * ����������ID��ѯ������
	 * 
	 * @param bizcheckId
	 * @return
	 */
	public BizCheckDTO findByBizIdAndBizcheckId(Integer bizId,
			BigDecimal bizcheckId);

	/**
	 * ���������������ݿ�
	 * 
	 * @param bizCheckDTO
	 */
	public Boolean saveBizCheck(BizCheckDTO bizCheckDTO);

	/**
	 * ȡ�÷�ҳ�� toolsmenu
	 * 
	 * @return
	 */
	public Pager getPager();
	/***
	 * ���������ѯ
	 * @return
	 */
	public List<BusinessDTO> findSalvationQuery(BizCheckDTO bizCheckDTO);
}