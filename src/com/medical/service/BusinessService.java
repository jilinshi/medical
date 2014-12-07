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
	 * 城市低保 门诊重大疾病救助信息 新建 作废
	 * 
	 * @param businessDTO
	 */
	public void saveOutpatientForTown(BusinessDTO businessDTO);

	/**
	 * 城市低保 住院救助信息录入 新建 作废
	 * 
	 * @param businessDTO
	 */
	public void saveOutpatientForCountry(BusinessDTO businessDTO);

	/**
	 * 农村低保 门诊重大疾病救助信息 新建 作废
	 * 
	 * @param businessDTO
	 */
	public void saveHospitalForTown(BusinessDTO businessDTO);

	/**
	 * 农村低保 住院救助信息录入 新建 作废
	 * 
	 * @param businessDTO
	 */
	public void saveHospitalForCountry(BusinessDTO businessDTO);

	/**
	 * 查询业务单
	 * 
	 * @param businessDTO
	 * @return
	 */
	public BusinessDTO findBusinessDTO(BusinessDTO businessDTO);

	/**
	 * 是否有医保
	 * 
	 * 输入医保编号，在居民数据中查询，若存在该保障对象，则给予救助。
	 * 
	 * @return personDTO
	 */
	public PersonDTO findMedicalPerson(PersonDTO personDTO);

	/**
	 * 查询家庭信息
	 * 
	 * @param personDTO
	 * @return
	 */
	public List<PersonDTO> findMedicalFamily(PersonDTO personDTO);

	/**
	 * 计算得出医疗救助金额,调用webservice(存数据库) type 1:计算保存 0:计算不保存
	 * 
	 * @param businessDTO
	 * @return
	 */
	public HashMap<String, String> findMedicalMoney(BusinessDTO businessDTO,
			String type);

	/**
	 * 测算医疗救助金 ，调用webservice(不存数据库)
	 * 
	 * @param businessDTO
	 * @return
	 */
	public String findAccMedicalMoney(BusinessDTO businessDTO);

	/**
	 * 读取医院列表
	 * 
	 * @return
	 */
	public List<DmBizdept> findHospitalList();

	/**
	 * 读取病列表
	 * 
	 * @return
	 */
	public List<Icd10> findSickList();

	/**
	 * 根据病代码 查询病名称
	 * 
	 * @param code
	 * @return
	 */
	public List<Icd10> findSickListByCode(String code, String type);

	/**
	 * 通过ssn查找业务
	 * 
	 * @param ssn
	 * @return
	 */
	public List<VbizDTO> findBizBySsn(PersonDTO personDTO, String biztype);

	/**
	 * 作废业务
	 */
	public void removeBiz(String bizId);

	/**
	 * 查询业务信息
	 * 
	 * @param bizId
	 * @return
	 */
	public BusinessDTO findBiz(String bizId);

	/**
	 * 页面去字典
	 * 
	 * @param dvid
	 * @return
	 */
	public String getDictValue(String dvid);

	/***
	 * 市级审批查询
	 * 
	 * @param personDTO
	 * @return
	 */
	public List<VbizDTO> queryBusiByCity(JzBizExample example, Integer curpage);

	/**
	 * 通过ssn查找补录业务单
	 * 
	 * @param ssn
	 * @return
	 */
	public List<VbizDTO> findBizRecordBySsn(PersonDTO personDTO);

	/**
	 * 保存补录单据
	 * 
	 * @param businessDTO
	 */
	public void saveBizrecord(BusinessDTO businessDTO);

	/**
	 * 查询补录信息
	 * 
	 * @return
	 */
	public List<VbizDTO> queryBizRecordBySsn(JzBizExample example,
			Integer curpage);

	public List<OrganDTO> getOrganList(String organid);

	public Pager getPager();

	/**
	 * 医前审批
	 * 
	 * @param businessDTO
	 */
	public void savePreMedical(BusinessDTO businessDTO);

	/**
	 * 医前审批查询
	 * 
	 * @param example
	 * @param curpage
	 * @return
	 */
	public List<PersonDTO> queryPreMedicalMember(String familyno,
			String organizationId);

	/**
	 * 医前审批查询
	 * 
	 * @param example
	 * @param curpage
	 * @return
	 */
	public List<VbizDTO> queryPreMedicalBiz(JzBizExample example,
			Integer curpage);

	/**
	 * 医前救助查询
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
	 * 日常救助
	 * 
	 * @param example
	 * @param curpage
	 * @return
	 */
	public List<VbizDTO> queryDailyMedicalBiz(JzBizExample example,
			Integer curpage);

	/**
	 * 日常救助
	 * 
	 * @param ssn
	 * @return
	 */
	public List<VbizDTO> queryDailyMedicalBizBySsn(String ssn);

	/**
	 * 查询手工审批单
	 * 
	 * @param ssn
	 * @return
	 */
	public List<ManualDTO> queryManualApproves(String ssn);

	/**
	 * 保存手工审批
	 * 
	 * @param manual
	 * @return
	 */
	public ManualDTO saveManualApprove(ManualDTO manual);

	/**
	 * 查询审批信息
	 * 
	 * @param manual
	 * @return
	 */
	public ManualDTO findManualApprove(ManualDTO manual);

	/**
	 * 删除审批信息
	 * 
	 * @param manual
	 */
	public void removeManualApprove(ManualDTO manual);

	/**
	 * 查询手工审批列表
	 * 
	 * @param example
	 * @param curpage
	 * @return
	 */
	public List<ManualDTO> queryManualApprove(JzManualExample example,
			Integer curpage);

	/**
	 * 累计对账单
	 * 
	 * @param printBillDTO
	 * @return
	 */
	public List<PrintBillDTO> findBillTotalByDept(PrintBillDTO printBillDTO);

	/**
	 * 计算救助金
	 * @param medicalafterDTO 
	 */
	public void findCountAssist(MedicalafterDTO medicalafterDTO);

	public String findMaBillContent(String m, String ds, String type);

}
