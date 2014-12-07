package com.medical.service;

import java.util.HashMap;
import java.util.List;

import com.medical.common.Pager;
import com.medical.dto.BusinessDTO;
import com.medical.dto.ManualDTO;
import com.medical.dto.MedicalafterDTO;
import com.medical.dto.OrganDTO;
import com.medical.dto.PersonDTO;
import com.medical.dto.PrintBillDTO;
import com.medical.dto.VbizDTO;
import com.medical.model.DmBizdept;
import com.medical.model.Icd10;
import com.medical.model.JzBizExample;
import com.medical.model.JzManualExample;

public interface BusinessService {

	/**
	 * ���еͱ� �����ش󼲲�������Ϣ �½� ����
	 * 
	 * @param businessDTO
	 */
	public void saveOutpatientForTown(BusinessDTO businessDTO);

	/**
	 * ���еͱ� סԺ������Ϣ¼�� �½� ����
	 * 
	 * @param businessDTO
	 */
	public void saveOutpatientForCountry(BusinessDTO businessDTO);

	/**
	 * ũ��ͱ� �����ش󼲲�������Ϣ �½� ����
	 * 
	 * @param businessDTO
	 */
	public void saveHospitalForTown(BusinessDTO businessDTO);

	/**
	 * ũ��ͱ� סԺ������Ϣ¼�� �½� ����
	 * 
	 * @param businessDTO
	 */
	public void saveHospitalForCountry(BusinessDTO businessDTO);

	/**
	 * ��ѯҵ��
	 * 
	 * @param businessDTO
	 * @return
	 */
	public BusinessDTO findBusinessDTO(BusinessDTO businessDTO);

	/**
	 * �Ƿ���ҽ��
	 * 
	 * ����ҽ����ţ��ھ��������в�ѯ�������ڸñ��϶�������������
	 * 
	 * @return personDTO
	 */
	public PersonDTO findMedicalPerson(PersonDTO personDTO);

	/**
	 * ��ѯ��ͥ��Ϣ
	 * 
	 * @param personDTO
	 * @return
	 */
	public List<PersonDTO> findMedicalFamily(PersonDTO personDTO);

	/**
	 * ����ó�ҽ�ƾ������,����webservice(�����ݿ�) type 1:���㱣�� 0:���㲻����
	 * 
	 * @param businessDTO
	 * @return
	 */
	public HashMap<String, String> findMedicalMoney(BusinessDTO businessDTO,
			String type);

	/**
	 * ����ҽ�ƾ����� ������webservice(�������ݿ�)
	 * 
	 * @param businessDTO
	 * @return
	 */
	public String findAccMedicalMoney(BusinessDTO businessDTO);

	/**
	 * ��ȡҽԺ�б�
	 * 
	 * @return
	 */
	public List<DmBizdept> findHospitalList();

	/**
	 * ��ȡ���б�
	 * 
	 * @return
	 */
	public List<Icd10> findSickList();

	/**
	 * ���ݲ����� ��ѯ������
	 * 
	 * @param code
	 * @return
	 */
	public List<Icd10> findSickListByCode(String code, String type);

	/**
	 * ͨ��ssn����ҵ��
	 * 
	 * @param ssn
	 * @return
	 */
	public List<VbizDTO> findBizBySsn(PersonDTO personDTO, String biztype);

	/**
	 * ����ҵ��
	 */
	public void removeBiz(String bizId);

	/**
	 * ��ѯҵ����Ϣ
	 * 
	 * @param bizId
	 * @return
	 */
	public BusinessDTO findBiz(String bizId);

	/**
	 * ҳ��ȥ�ֵ�
	 * 
	 * @param dvid
	 * @return
	 */
	public String getDictValue(String dvid);

	/***
	 * �м�������ѯ
	 * 
	 * @param personDTO
	 * @return
	 */
	public List<VbizDTO> queryBusiByCity(JzBizExample example, Integer curpage);

	/**
	 * ͨ��ssn���Ҳ�¼ҵ��
	 * 
	 * @param ssn
	 * @return
	 */
	public List<VbizDTO> findBizRecordBySsn(PersonDTO personDTO);

	/**
	 * ���油¼����
	 * 
	 * @param businessDTO
	 */
	public void saveBizrecord(BusinessDTO businessDTO);

	/**
	 * ��ѯ��¼��Ϣ
	 * 
	 * @return
	 */
	public List<VbizDTO> queryBizRecordBySsn(JzBizExample example,
			Integer curpage);

	public List<OrganDTO> getOrganList(String organid);

	public Pager getPager();

	/**
	 * ҽǰ����
	 * 
	 * @param businessDTO
	 */
	public void savePreMedical(BusinessDTO businessDTO);

	/**
	 * ҽǰ������ѯ
	 * 
	 * @param example
	 * @param curpage
	 * @return
	 */
	public List<PersonDTO> queryPreMedicalMember(String familyno,
			String organizationId);

	/**
	 * ҽǰ������ѯ
	 * 
	 * @param example
	 * @param curpage
	 * @return
	 */
	public List<VbizDTO> queryPreMedicalBiz(JzBizExample example,
			Integer curpage);

	/**
	 * ҽǰ������ѯ
	 * 
	 * @param ssn
	 * @return
	 */
	public List<VbizDTO> queryPreMedicalBizBySsn(String ssn);

	/**
	 * 
	 * @param businessDTO
	 */
	public void saveDailyMedical(BusinessDTO businessDTO);

	/**
	 * 
	 * @param familyno
	 * @param organizationId
	 * @return
	 */
	public List<PersonDTO> queryDailyMedicalMember(String familyno,
			String organizationId);

	/**
	 * �ճ�����
	 * 
	 * @param example
	 * @param curpage
	 * @return
	 */
	public List<VbizDTO> queryDailyMedicalBiz(JzBizExample example,
			Integer curpage);

	/**
	 * �ճ�����
	 * 
	 * @param ssn
	 * @return
	 */
	public List<VbizDTO> queryDailyMedicalBizBySsn(String ssn);

	/**
	 * ��ѯ�ֹ�������
	 * 
	 * @param ssn
	 * @return
	 */
	public List<ManualDTO> queryManualApproves(String ssn);

	/**
	 * �����ֹ�����
	 * 
	 * @param manual
	 * @return
	 */
	public ManualDTO saveManualApprove(ManualDTO manual);

	/**
	 * ��ѯ������Ϣ
	 * 
	 * @param manual
	 * @return
	 */
	public ManualDTO findManualApprove(ManualDTO manual);

	/**
	 * ɾ��������Ϣ
	 * 
	 * @param manual
	 */
	public void removeManualApprove(ManualDTO manual);

	/**
	 * ��ѯ�ֹ������б�
	 * 
	 * @param example
	 * @param curpage
	 * @return
	 */
	public List<ManualDTO> queryManualApprove(JzManualExample example,
			Integer curpage);

	/**
	 * �ۼƶ��˵�
	 * 
	 * @param printBillDTO
	 * @return
	 */
	public List<PrintBillDTO> findBillTotalByDept(PrintBillDTO printBillDTO);

	/**
	 * ���������
	 * @param medicalafterDTO 
	 */
	public void findCountAssist(MedicalafterDTO medicalafterDTO);

	public String findMaBillContent(String m, String ds, String type);

}
