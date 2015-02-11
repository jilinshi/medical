package com.medical.service;

import java.io.File;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

import com.medical.common.Pager;
import com.medical.dto.AspApproveDTO;
import com.medical.dto.ChronicApproveDTO;
import com.medical.dto.CurrectChronicDTO;
import com.medical.dto.OrganDTO;
import com.medical.dto.PersonDTO;
import com.medical.model.JzAspapproveExample;
import com.medical.model.JzChronicapproveExample;
import com.medical.model.MemberBaseinfoExample;

public interface ChronicApproveService {

	public List<ChronicApproveDTO> findApprove(String url, int curpage,
			JzChronicapproveExample e);

	/**
	 * 
	 * @param chronicApproveDTO
	 * @param orgno
	 */
	public ChronicApproveDTO saveApprove(ChronicApproveDTO chronicApproveDTO,
			String orgno);

	/**
	 * 
	 * @param chronicApproveDTO
	 * @return
	 */
	public List<ChronicApproveDTO> findApprovesBySSN(
			ChronicApproveDTO chronicApproveDTO);

	/**
	 * ��֤�Ƿ�������Բ���������
	 * 
	 * @param familyno
	 * @return
	 */
	public List<PersonDTO> findChronicMembersByFamilyno(String familyno);

	/**
	 * ���Բ�����������
	 * 
	 * @param ssn
	 * @return
	 */
	public ChronicApproveDTO findChronicMembersBySSN(
			ChronicApproveDTO chronicApproveDTO);

	/**
	 * ��ѯ������ͨ��ID
	 * 
	 * @param chronicApproveDTO
	 * @return
	 */
	public ChronicApproveDTO findChronicApproveDTOByID(
			ChronicApproveDTO chronicApproveDTO);

	/**
	 * �Ƿ���Լ�������
	 * 
	 * @param chronicApproveDTO
	 * @return
	 */
	public List<ChronicApproveDTO> findCheckApproves(
			ChronicApproveDTO chronicApproveDTO);

	/**
	 * ɾ������
	 * 
	 * @param chronicApproveDTO
	 */
	public void removeChronicApprove(ChronicApproveDTO chronicApproveDTO);

	/**
	 * 
	 * @param personDTO
	 * @return
	 */
	public PersonDTO findPersonBySsn(PersonDTO personDTO);

	/**
	 * 
	 * @param ssn
	 * @param memberid
	 * @param file
	 */
	public void saveHealthrecord(String detail, String ssn, String memberid, Short annextype,
			File file);

	/**
	 * 
	 * @param ssn
	 * @return
	 */
	public HashMap<String, String> findHrsBySsn(String ssn, Short type);

	/**
	 * ��ӡ����Դ
	 * 
	 * @param ssn
	 * @return
	 */
	public List<ChronicApproveDTO> findPrinthr(
			ChronicApproveDTO chronicApproveDTO);

	/**
	 * 
	 * @param familyno
	 * @return
	 */
	public HashMap<String, String> findPrinthrMap(String familyno,String fid);

	/**
	 * 
	 * @param curPage 
	 * @param string 
	 * @param familyno
	 * @return
	 */
	public List<PersonDTO> findAspMembersByFamilyno(String string, Integer curPage, MemberBaseinfoExample example);

	/**
	 * 
	 * @return
	 */
	public Pager getPager();

	/**
	 * 
	 * @param ssn
	 * @return
	 */
	public AspApproveDTO findAspApproveBySsn(String ssn,String memberType);

	/**
	 * 
	 * @param aspapproveId
	 * @return
	 */
	public AspApproveDTO findAspApproveById(BigDecimal aspapproveId);

	/**
	 * 
	 * @param aspApproveDTO
	 * @param orgno
	 */
	public AspApproveDTO saveAspApprove(AspApproveDTO aspApproveDTO,
			String orgno);

	/**
	 * 
	 * @param string
	 * @param curPage
	 * @param e
	 * @return
	 */
	public List<AspApproveDTO> findAspApprove(String string, Integer curPage,
			JzAspapproveExample e);

	/**
	 * 
	 * @param ssn
	 * @param memberType 
	 * @return
	 */
	public List<AspApproveDTO> findAspApprovesBySsn(String ssn, String memberType);

	/**
	 * 
	 * @param aspapproveId
	 */
	public void removeAsp(BigDecimal aspapproveId);

	/**
	 * 
	 * @param familyno
	 * @param familyid 
	 * @param memberId 
	 * @return
	 */
	public HashMap<String, String> findPrintAspMap(String familyno, String familyid, String memberId);
	/**
	 * 
	 * @param aspApproveDTO
	 * @return
	 */
	public List<AspApproveDTO> findPrintasp(AspApproveDTO aspApproveDTO);

	/**
	 * 
	 * @param personDTO
	 * @return
	 */
	public PersonDTO findAspBySsn(PersonDTO personDTO);

	/**
	 * �޸�cs���ݿ�
	 */
	public void saveYBSQ(String type, String memberId);

	public List<AspApproveDTO> findAspsBySsn(PersonDTO personDTO);

	public List<ChronicApproveDTO> findChronicMemberStat(String orgno);

	public ChronicApproveDTO findCardView(ChronicApproveDTO chronicApproveDTO);

	public List<OrganDTO> getOrganList(String orgid);

	public List<ChronicApproveDTO> findChronicstat(String sql);

	public List<ChronicApproveDTO> findApprove1(String string,
			Integer cur_page, String sql);

	public List<ChronicApproveDTO> findApprove2(String string,
			Integer cur_page, String sql);

	public List<CurrectChronicDTO> findApprove3(String string,
			Integer cur_page, String sql);
	
	public BigDecimal queryChronicBill(ChronicApproveDTO chronicApproveDTO);
}