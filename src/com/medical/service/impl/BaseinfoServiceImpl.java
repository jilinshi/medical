package com.medical.service.impl;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.medical.common.Pager;
import com.medical.dao.ExecutSQLDAO;
import com.medical.dao.JzActDAO;
import com.medical.dao.JzBizDAO;
import com.medical.dao.JzMabillsDAO;
import com.medical.dao.JzMedicalafterDAO;
import com.medical.dao.MemberBaseinfoDAO;
import com.medical.dao.TestSsnDAO;
import com.medical.dto.ActDTO;
import com.medical.dto.BaseInfoDTO;
import com.medical.dto.BizDTO;
import com.medical.dto.CheckDTO;
import com.medical.dto.HealthDTO;
import com.medical.dto.MedicalafterDTO;
import com.medical.dto.YBCheckDTO;
import com.medical.model.ExecutSQL;
import com.medical.model.JzAct;
import com.medical.model.JzActExample;
import com.medical.model.JzBiz;
import com.medical.model.JzBizExample;
import com.medical.model.JzMabills;
import com.medical.model.JzMabillsExample;
import com.medical.model.JzMedicalafter;
import com.medical.model.JzMedicalafterExample;
import com.medical.model.MemberBaseinfo;
import com.medical.model.MemberBaseinfoExample;
import com.medical.model.MemberBaseinfoExample.Criteria;
import com.medical.model.TestSsn;
import com.medical.model.TestSsnExample;
import com.medical.service.BaseinfoService;

public class BaseinfoServiceImpl implements BaseinfoService {
	static Logger log = Logger.getLogger(BaseinfoServiceImpl.class);
	private MemberBaseinfoDAO memberBaseinfoDAO;
	private ExecutSQLDAO executSQLDAO;
	private JzBizDAO jzBizDAO;
	private Pager pager;
	private String toolsmenu;
	private TestSsnDAO testSsnDAO;
	private JzMedicalafterDAO jzMedicalafterDAO;
	private JzMabillsDAO jzMabillsDAO;
	private JzActDAO jzActDAO;

	@Override
	public HealthDTO findByMemberId(String memberId, String ds) {
		HealthDTO dto = new HealthDTO();
		/*
		 * MemberBaseinfoExample memberexample = new MemberBaseinfoExample();
		 * com.medical.model.MemberBaseinfoExample.Criteria membercriteria =
		 * memberexample .createCriteria();
		 * membercriteria.andMemberIdEqualTo(memberId).andDsEqualTo(ds);
		 * memberBaseinfoDAO.selectByExample(memberexample);
		 * Iterator<MemberBaseinfo> it = memberBaseinfoDAO.selectByExample(
		 * memberexample).iterator(); if (it.hasNext()) { MemberBaseinfo
		 * memberBaseinfo = it.next();
		 * dto.setMemberId(memberBaseinfo.getMemberId());
		 * dto.setFamilyno(memberBaseinfo.getFamilyno());
		 * dto.setMasterName(memberBaseinfo.getMasterName());
		 * dto.setRelmaster(memberBaseinfo.getRelmaster());
		 * dto.setMembername(memberBaseinfo.getMembername());
		 * dto.setPaperid(memberBaseinfo.getPaperid());
		 * dto.setSsn(memberBaseinfo.getSsn());
		 * dto.setSex(memberBaseinfo.getSex());
		 * dto.setBirthday(memberBaseinfo.getBirthday());
		 * dto.setRprkind(memberBaseinfo.getRprkind());
		 * dto.setRprtype(memberBaseinfo.getRprtype());
		 * dto.setRpraddress(memberBaseinfo.getRpraddress());
		 * dto.setLinkmode(memberBaseinfo.getLinkmode());
		 * dto.setHealth(memberBaseinfo.getHealth());
		 * dto.setSickentype(memberBaseinfo.getSickentype());
		 * dto.setSickenname(memberBaseinfo.getSickenname());
		 * dto.setDeformity(memberBaseinfo.getDeformity());
		 * dto.setDefgrade(memberBaseinfo.getDefgrade()); }
		 */
		String ssn = memberId;
		if (ssn != null && !"".equals(ssn)) {
			JzBizExample bizexample = new JzBizExample();
			com.medical.model.JzBizExample.Criteria bizCriteria = bizexample
					.createCriteria();
			bizCriteria.andSsnEqualTo(ssn);
			Iterator<JzBiz> itbiz = jzBizDAO.selectByExample(bizexample)
					.iterator();
			List<BizDTO> bizlist = new ArrayList<BizDTO>();
			while (itbiz.hasNext()) {
				BizDTO bizDTO = new BizDTO();
				JzBiz biz = itbiz.next();
				bizDTO.setBizId(biz.getBizId());
				// bizDTO.setHospitalId(biz.getHospitalId());
				if ("H015".equals(biz.getHospitalId())) {
					bizDTO.setHospitalId("医药学院附属医院");
				} else if ("H005".equals(biz.getHospitalId())) {
					bizDTO.setHospitalId("吉林省中西医结合医院");
				} else if ("H007".equals(biz.getHospitalId())) {
					bizDTO.setHospitalId("市医院");
				} else if ("H001".equals(biz.getHospitalId())) {
					bizDTO.setHospitalId("中心医院");
				}
				bizDTO.setCenterId(biz.getCenterId());
				if ("10".equals(biz.getBizType())) {
					bizDTO.setBizType("购药");
				} else if ("11".equals(biz.getBizType())) {
					bizDTO.setBizType("门诊");
				} else if ("12".equals(biz.getBizType())) {
					bizDTO.setBizType("住院");
				} else if ("13".equals(biz.getBizType())) {
					bizDTO.setBizType("门诊慢性病、重大疾病");
				} else if ("14".equals(biz.getBizType())) {
					bizDTO.setBizType("家庭病床");
				} else if ("16".equals(biz.getBizType())) {
					bizDTO.setBizType("门诊特殊病(特治特检)");
				} else if ("41".equals(biz.getBizType())) {
					bizDTO.setBizType("工伤门诊");
				} else if ("42".equals(biz.getBizType())) {
					bizDTO.setBizType("工伤住院");
				} else if ("51".equals(biz.getBizType())) {
					bizDTO.setBizType("生育门诊");
				} else if ("52".equals(biz.getBizType())) {
					bizDTO.setBizType("生育住院");
				}
				bizDTO.setBizName(biz.getBizName());
				bizDTO.setSerialNo(biz.getSerialNo());
				bizDTO.setSsn(biz.getSsn());
				bizDTO.setFamilyId(biz.getFamilyId());
				bizDTO.setName(biz.getName());
				bizDTO.setSex(biz.getSex());
				bizDTO.setBirthday(biz.getBirthday());
				bizDTO.setIdcard(biz.getIdcard());
				bizDTO.setTreatmentType(biz.getTreatmentType());
				bizDTO.setTreatmentName(biz.getTreatmentName());
				bizDTO.setBizTimes(biz.getBizTimes());
				bizDTO.setRegDate(biz.getRegDate());
				bizDTO.setBeginDate(biz.getBeginDate());
				bizDTO.setInDeptName(biz.getInDeptName());
				bizDTO.setInAreaName(biz.getInAreaName());
				bizDTO.setInBed(biz.getInBed());
				bizDTO.setInDisease(biz.getInDisease());
				bizDTO.setInDiseaseName(biz.getInDiseaseName());
				bizDTO.setDiagnose(biz.getDiagnose());
				bizDTO.setDiagnoseName(biz.getDiagnoseName());
				bizDTO.setDiagnoseDate(biz.getDiagnoseDate());
				bizDTO.setFinDisease(biz.getFinDisease());
				bizDTO.setFinDiseaseName(biz.getFinDiseaseName());
				bizDTO.setEndDate(biz.getEndDate());
				bizDTO.setOutDeptName(biz.getOutDeptName());
				bizDTO.setComplications(biz.getComplications());
				bizDTO.setInDays(biz.getInDays());
				bizDTO.setAssistType(biz.getAssistType());
				bizDTO.setStaus(biz.getStaus());
				bizDTO.setPatientId(biz.getPatientId());
				bizDTO.setFinDate(biz.getFinDate());
				bizDTO.setInmoney(biz.getInmoney());
				bizDTO.setOperuid(biz.getOperuid());
				bizDTO.setOpertime(biz.getOpertime());
				bizDTO.setAssistFlag(biz.getAssistFlag());
				bizDTO.setPhotopath(biz.getPhotopath());
				bizDTO.setAllmoney(biz.getAllmoney());
				bizDTO.setPersonpay(biz.getPersonpay());
				bizDTO.setInsurancepay(biz.getInsurancepay());
				bizDTO.setAssistpay(biz.getAssistpay());
				bizDTO.setAssistpaymsg(biz.getAssistpaymsg());
				bizDTO.setPhotopath2(biz.getPhotopath2());
				bizDTO.setConfirmFlag(biz.getConfirmFlag());
				bizDTO.setInMoney(biz.getInMoney());
				bizDTO.setPersonType(biz.getPersonType());
				bizDTO.setFeeBatch(biz.getFeeBatch());
				bizDTO.setOutFlag(biz.getOutFlag());
				bizDTO.setAlreadyGet(biz.getAlreadyGet());
				bizDTO.setGatherFlag(biz.getGatherFlag());
				bizDTO.setSmsState(biz.getSmsState());
				bizDTO.setCheckState(biz.getCheckState());
				bizlist.add(bizDTO);
			}
			dto.setBizDTOs(bizlist);
		}
		return dto;
	}

	@Override
	public List<HealthDTO> findForperson(HealthDTO healthDTO, String onno) {

		String term = healthDTO.getTerm();
		String operational = healthDTO.getOperational();
		String value = healthDTO.getValue();
		List<HealthDTO> result = new ArrayList<HealthDTO>();
		MemberBaseinfoExample example = new MemberBaseinfoExample();
		Criteria criteria = example.createCriteria();
		criteria.andOnNoLike(onno + "%");
		if (null != value & !"".equals(value)) {
			if ("SSN".equals(term)) {
				// 社会保险号
				if ("=".equals(operational)) {
					criteria.andSsnEqualTo(value);
				} else if ("like".equals(operational)) {
					criteria.andSsnLike("%" + value + "%");
				}
			} else if ("FAMILYNO".equals(term)) {
				// 家庭编号
				if ("=".equals(operational)) {
					criteria.andFamilynoEqualTo(value);
				} else if ("like".equals(operational)) {
					criteria.andFamilynoLike("%" + value + "%");
				}
			} else if ("MEMBERNAME".equals(term)) {
				// 姓名
				if ("=".equals(operational)) {
					criteria.andMembernameEqualTo(value);

				} else if ("like".equals(operational)) {
					criteria.andMembernameLike("%" + value + "%");
				}
			} else if ("PAPERID".equals(term)) {
				// 身份证号
				if ("=".equals(operational)) {
					criteria.andPaperidEqualTo(value);
				} else if ("like".equals(operational)) {
					criteria.andPaperidLike("%" + value + "%");
				}
			}
		}
		this.pager.setCurrentpage(healthDTO.getCurpage());
		this.pager.setAll(memberBaseinfoDAO.countByExample(example));
		this.pager.setUrl(healthDTO.getUrl());
		this.pager.setPagesize(healthDTO.getPageSize());
		setToolsmenu(this.pager.genToolsmenu());

		Iterator<MemberBaseinfo> it = memberBaseinfoDAO
				.selectByExampleWithoutBLOBs(example, pager.getStart(),
						pager.getPagesize()).iterator();

		while (it.hasNext()) {
			MemberBaseinfo memberBaseinfo = it.next();
			HealthDTO dto = new HealthDTO();
			dto.setMemberId(memberBaseinfo.getMemberId());
			dto.setFamilyno(memberBaseinfo.getFamilyno());
			dto.setMasterName(memberBaseinfo.getMasterName());
			dto.setRelmaster(memberBaseinfo.getRelmaster());
			dto.setMembername(memberBaseinfo.getMembername());
			dto.setPaperid(memberBaseinfo.getPaperid());
			dto.setSsn(memberBaseinfo.getSsn());
			dto.setSex(memberBaseinfo.getSex());
			dto.setBirthday(memberBaseinfo.getBirthday());
			dto.setRprkind(memberBaseinfo.getRprkind());
			dto.setRprtype(memberBaseinfo.getRprtype());
			dto.setRpraddress(memberBaseinfo.getRpraddress());
			dto.setLinkmode(memberBaseinfo.getLinkmode());
			result.add(dto);
		}

		return result;
	}

	@SuppressWarnings({ "rawtypes" })
	public List<CheckDTO> findAllMemberInfo(String url, Integer curpage,
			String sql) {
		List<CheckDTO> list = new ArrayList<CheckDTO>();
		ExecutSQL executSQL = new ExecutSQL();
		executSQL.setExecutsql(sql);
		pager.setCurrentpage(curpage);
		pager.setUrl(url);
		pager.setPagesize(16);
		try {
			pager.setAll(executSQLDAO.queryCnt(executSQL));
			pager.setUrl(url);
			pager.getToolsmenu();
			executSQL.setEnd(pager.getEnd());
			executSQL.setStart(pager.getStart());
			List<HashMap> rs = executSQLDAO.queryRow(executSQL);
			for (HashMap s : rs) {
				CheckDTO e = new CheckDTO();
				e.setFamilyno((String) s.get("FAMILYNO"));
				e.setMembername((String) s.get("MEMBERNAME"));
				e.setPaperid((String) s.get("PAPERID"));
				e.setMemberId((String) s.get("MEMBER_ID"));
				e.setDs((String) s.get("DS"));
				e.setSsn((String) s.get("SSN"));
				e.setSsn1((String) s.get("SSN1"));
				e.setSsn2((String) s.get("SSN2"));
				e.setSsn3((String) s.get("SSN3"));
				e.setPersonstate((String) s.get("PERSONSTATE"));
				e.setAssistType((String) s.get("ASSIST_TYPE"));
				e.setAsort((BigDecimal) s.get("ASORT"));
				list.add(e);
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return list;
	}

	public CheckDTO findMemberInfo(CheckDTO checkDTO) {
		CheckDTO cdto = new CheckDTO();
		MemberBaseinfoExample example = new MemberBaseinfoExample();
		Criteria criteria = example.createCriteria();
		criteria.andMemberIdEqualTo(checkDTO.getMemberId());
		criteria.andDsEqualTo(checkDTO.getDs());
		MemberBaseinfo info = memberBaseinfoDAO.selectByExampleWithoutBLOBs(
				example).get(0);
		cdto.setFamilyno(info.getFamilyno());
		cdto.setMembername(info.getMembername());
		cdto.setPaperid(info.getPaperid());
		cdto.setSsn(info.getSsn());
		cdto.setDs(info.getDs());
		cdto.setMemberId(info.getMemberId());
		cdto.setAssistType(info.getAssistType());
		cdto.setAsort(info.getAsort());
		cdto.setPersonstate(info.getPersonstate());
		cdto.setMasterName(info.getMasterName());
		return cdto;
	}

	public int updateTestSsn(CheckDTO checkDTO) {
		int u = 0;
		int m = 0;
		int return_flag = 0;
		TestSsn record = new TestSsn();
		TestSsnExample example = new TestSsnExample();
		com.medical.model.TestSsnExample.Criteria criteria = example
				.createCriteria();
		criteria.andMemberIdEqualTo(checkDTO.getMemberId());
		criteria.andDsEqualTo(checkDTO.getDs());
		if (null == checkDTO.getSsn1() || "".equals(checkDTO.getSsn1())) {

		} else {
			record.setSsn1(checkDTO.getSsn1());
		}
		if (null == checkDTO.getSsn2() || "".equals(checkDTO.getSsn2())) {

		} else {
			record.setSsn2(checkDTO.getSsn2());
		}
		if (null == checkDTO.getSsn3() || "".equals(checkDTO.getSsn3())) {

		} else {
			record.setSsn3(checkDTO.getSsn3());
		}
		record.setUtime(new Date());
		// 更新test_ssn表
		u = testSsnDAO.updateByExampleSelective(record, example);
		// 更新member_baseinfo视图
		MemberBaseinfo record1 = new MemberBaseinfo();
		MemberBaseinfoExample example1 = new MemberBaseinfoExample();
		Criteria criteria1 = example1.createCriteria();
		criteria1.andMemberIdEqualTo(checkDTO.getMemberId());
		criteria1.andDsEqualTo(checkDTO.getDs());
		record1.setSsn(checkDTO.getSsncheck());
		m = memberBaseinfoDAO.updateByExampleSelective(record1, example1);
		// 更新城市人员信息表
		if ("1".equals(checkDTO.getDs())) {
			String sql = " update familymember@cs.regress.rdbms.dev.us.oracle.com fcs "
					+ " set fcs.indi_id='"
					+ checkDTO.getSsncheck()
					+ "'"
					+ " where fcs.fm_id='" + checkDTO.getMemberId() + "'";
			ExecutSQL executSQL = new ExecutSQL();
			executSQL.setExecutsql(sql);
			try {
				executSQLDAO.updateSQL(executSQL);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else if ("2".equals(checkDTO.getDs())) {
			u = -1;
		}
		if (u == 1 && m == 1) {
			return_flag = 1;
		} else {
			return_flag = 0;
		}
		return return_flag;
	}

	public List<BaseInfoDTO> findMemberByPaperId(BaseInfoDTO baseInfoDTO) {
		List<BaseInfoDTO> mbdtos = new ArrayList<BaseInfoDTO>();
		MemberBaseinfoExample example = new MemberBaseinfoExample();
		Criteria criteria = example.createCriteria();
		criteria.andPaperidEqualTo(baseInfoDTO.getPaperid());
		criteria.andPersonstateEqualTo("正常");
		List<String> values = new ArrayList<String>();
		values.add("10");
		values.add("11");
		criteria.andAssistTypeIn(values);
		List<MemberBaseinfo> info = memberBaseinfoDAO
				.selectByExampleWithoutBLOBs(example);
		for (MemberBaseinfo s : info) {
			BaseInfoDTO e = new BaseInfoDTO();
			e.setFamilyno(s.getFamilyno());
			e.setMembername(s.getMembername());
			e.setMasterName(s.getMasterName());
			e.setSex(s.getSex());
			e.setAddress(s.getAddress());
			e.setPaperid(s.getPaperid());
			e.setSsn(s.getSsn());
			e.setDs(s.getDs());
			e.setMemberId(s.getMemberId());
			e.setAssistType(s.getAssistType());
			e.setAsort(s.getAsort());
			e.setPersonstate(s.getPersonstate());
			e.setMasterName(s.getMasterName());
			e.setRelmaster(s.getRelmaster());
			e.setOnNo(s.getOnNo());
			e.setBirthday(s.getBirthday());
			mbdtos.add(e);
		}
		return mbdtos;
	}

	public List<MedicalafterDTO> findMedicalaftersByPaperId(
			BaseInfoDTO baseInfoDTO) {
		List<MedicalafterDTO> madtos = new ArrayList<MedicalafterDTO>();
		JzMedicalafterExample example = new JzMedicalafterExample();
		com.medical.model.JzMedicalafterExample.Criteria criteria = example
				.createCriteria();
		criteria.andPaperidEqualTo(baseInfoDTO.getPaperid());
		List<JzMedicalafter> info = jzMedicalafterDAO.selectByExample(example);
		for (JzMedicalafter s : info) {
			MedicalafterDTO e = new MedicalafterDTO();
			e.setMaId(s.getMaId());
			e.setFamilyno(s.getFamilyno());
			e.setMembername(s.getMembername());
			e.setPaperid(s.getPaperid());
			e.setSsn(s.getSsn());
			e.setHospital(s.getHospital());
			e.setHospitallevel(s.getHospitallevel());
			e.setSickencontent(s.getSickencontent());
			e.setBegintime(s.getBegintime());
			e.setEndtime(s.getEndtime());
			e.setApproveresult(s.getApproveresult());
			e.setApprovecontent(s.getApprovecontent());
			e.setTotalcost(s.getTotalcost());
			e.setInsurepay(s.getInsurepay());
			e.setOutpay(s.getOutpay());
			e.setCapay(s.getCapay());
			e.setBusinesspay(s.getBusinesspay());
			e.setAsisstpay(s.getAsisstpay());
			e.setCreatetime(s.getCreatetime());
			e.setUpdatetime(s.getUpdatetime());
			e.setMemberId(s.getMemberId());
			e.setMemberType(s.getMemberType());
			e.setImplsts(s.getImplsts());
			e.setTiketno(s.getTiketno());
			e.setMedicaltype(s.getMedicaltype());
			e.setInsuretype(s.getInsuretype());
			e.setPersontype(s.getPersontype());
			e.setOnNo(s.getOnNo());
			madtos.add(e);
		}
		return madtos;
	}

	public List<MedicalafterDTO> queryMedicalafters(
			JzMedicalafterExample example, Integer curpage, String url) {
		List<JzMedicalafter> info = jzMedicalafterDAO.selectByExample(example);
		List<MedicalafterDTO> madtos = new ArrayList<MedicalafterDTO>();

		pager.setAll(info.size());
		pager.setUrl(url);
		pager.setCurrentpage(curpage.intValue());
		pager.setPagesize(14);
		pager.getToolsmenu();

		for (int i = 0; i < pager.getPagesize(); i++) {
			if (pager.getStart() + i < pager.getAll()) {
				JzMedicalafter s = info.get(pager.getStart() + i);
				MedicalafterDTO e = new MedicalafterDTO();
				e.setMaId(s.getMaId());
				e.setFamilyno(s.getFamilyno());
				e.setMembername(s.getMembername());
				e.setPaperid(s.getPaperid());
				e.setSsn(s.getSsn());
				e.setHospital(s.getHospital());
				e.setHospitallevel(s.getHospitallevel());
				e.setSickencontent(s.getSickencontent());
				e.setBegintime(s.getBegintime());
				e.setEndtime(s.getEndtime());
				e.setApproveresult(s.getApproveresult());
				e.setApprovecontent(s.getApprovecontent());
				e.setTotalcost(s.getTotalcost());
				e.setInsurepay(s.getInsurepay());
				e.setOutpay(s.getOutpay());
				e.setCapay(s.getCapay());
				e.setBusinesspay(s.getBusinesspay());
				e.setAsisstpay(s.getAsisstpay());
				e.setCreatetime(s.getCreatetime());
				e.setUpdatetime(s.getUpdatetime());
				e.setMemberId(s.getMemberId());
				e.setMemberType(s.getMemberType());
				e.setImplsts(s.getImplsts());
				e.setTiketno(s.getTiketno());
				e.setMedicaltype(s.getMedicaltype());
				e.setInsuretype(s.getInsuretype());
				e.setPersontype(s.getPersontype());
				madtos.add(e);
			}
		}
		return madtos;
	}

	public List<JzMabills> queryMedicalafters01(JzMabillsExample example,
			Integer curpage, String url) {
		List<JzMabills> info = jzMabillsDAO.selectByExample(example);
		List<JzMabills> madtos = new ArrayList<JzMabills>();

		pager.setAll(info.size());
		pager.setUrl(url);
		pager.setCurrentpage(curpage.intValue());
		pager.setPagesize(14);
		pager.getToolsmenu();

		for (int i = 0; i < pager.getPagesize(); i++) {
			if (pager.getStart() + i < pager.getAll()) {
				JzMabills s = info.get(pager.getStart() + i);
				madtos.add(s);
			}
		}
		return madtos;
	}

	public MedicalafterDTO findMemberByID(BaseInfoDTO baseInfoDTO) {
		MedicalafterDTO e = new MedicalafterDTO();
		MemberBaseinfoExample example = new MemberBaseinfoExample();
		Criteria criteria = example.createCriteria();
		criteria.andMemberIdEqualTo(baseInfoDTO.getMemberId());
		criteria.andDsEqualTo(baseInfoDTO.getDs());
		MemberBaseinfo s = memberBaseinfoDAO.selectByExampleWithoutBLOBs(
				example).get(0);
		e.setFamilyno(s.getFamilyno());
		e.setMembername(s.getMembername());
		e.setMasterName(s.getMasterName());
		e.setSex(s.getSex());
		e.setPaperid(s.getPaperid());
		e.setSsn(s.getSsn());
		e.setMemberType(s.getDs());
		e.setMemberId(s.getMemberId());
		e.setAssistType(s.getAssistType());
		e.setAsort(s.getAsort());
		e.setPersonstate(s.getPersonstate());
		e.setRelmaster(s.getRelmaster());
		e.setOnNo(s.getOnNo());
		e.setBirthday(s.getBirthday());
		e.setFamaddr(s.getAddress());
		e.setTelephone(s.getLinkmode());
		e.setFamcount(Short.parseShort(s.getfPersoncount()));
		e.setBankaccounts(s.getfAccounts());
		return e;
	}

	public MedicalafterDTO saveAfterApply(MedicalafterDTO medicalafterDTO) {

		JzMedicalafter record = new JzMedicalafter();
		if (null == medicalafterDTO.getMaId()) {
			record.setBankAccount(medicalafterDTO.getBankaccounts());
			record.setBankAccount1(medicalafterDTO.getBankaccounts1());
			record.setMastername(medicalafterDTO.getMasterName());
			record.setFamilyno(medicalafterDTO.getFamilyno());
			record.setMembername(medicalafterDTO.getMembername());
			record.setPaperid(medicalafterDTO.getPaperid());
			record.setSsn(medicalafterDTO.getSsn());
			record.setHospital(medicalafterDTO.getHospital());
			record.setHospitallevel(medicalafterDTO.getHospitallevel());
			record.setSickencontent(medicalafterDTO.getSickencontent());
			record.setBegintime(medicalafterDTO.getBegintime());
			record.setEndtime(medicalafterDTO.getEndtime());
			record.setApproveresult(medicalafterDTO.getApproveresult());
			record.setTotalcost(medicalafterDTO.getTotalcost());
			record.setInsurepay(medicalafterDTO.getInsurepay());
			record.setOutpay(medicalafterDTO.getOutpay());
			record.setCapay(medicalafterDTO.getCapay());
			record.setBusinesspay(medicalafterDTO.getBusinesspay());
			record.setAsisstpay(medicalafterDTO.getAsisstpay());
			record.setCreatetime(new Date());
			record.setUpdatetime(record.getCreatetime());
			record.setMemberId(medicalafterDTO.getMemberId());
			record.setMemberType(medicalafterDTO.getMemberType());
			record.setImplsts("0");
			record.setTiketno(medicalafterDTO.getTiketno());
			record.setMedicaltype(medicalafterDTO.getMedicaltype());
			record.setInsuretype(medicalafterDTO.getInsuretype());
			record.setPersontype(medicalafterDTO.getPersontype());
			record.setOnNo(medicalafterDTO.getOnNo());
			record.setPayLine(medicalafterDTO.getPayLine());
			record.setHospitalpay(medicalafterDTO.getHospitalpay());
			record.setDiagnose(medicalafterDTO.getDiagnose());
			record.setSex(medicalafterDTO.getSex());
			record.setTelephone(medicalafterDTO.getTelephone());
			record.setFamaddr(medicalafterDTO.getFamaddr());
			record.setFamcount(medicalafterDTO.getFamcount());
			record.setBirthday(medicalafterDTO.getBirthday());
			record.setWsflag(medicalafterDTO.getWsflag());
			record.setWzzflag(medicalafterDTO.getWzzflag());
			record.setPzPrinum(Long.valueOf("0"));
			record.setAppPrinum(Long.valueOf("0"));
			record.setSelfpay(medicalafterDTO.getSelfpay());
			record.setHospitalid(medicalafterDTO.getHospitalid());
			record.setHospitaltype(medicalafterDTO.getHospitaltype());
			BigDecimal id = jzMedicalafterDAO.insertSelective(record);
			medicalafterDTO.setMaId(id);
			JzAct jzAct = jzActDAO.selectByPrimaryKey(medicalafterDTO
					.getActId());
			if (medicalafterDTO.getMedicaltype().equals("1")) {
				jzAct.setActBizInhospitalTimes((short) (jzAct
						.getActBizInhospitalTimes() + 1));
				jzAct.setActBizMoney(jzAct.getActBizMoney().add(
						medicalafterDTO.getAsisstpay()));
				jzAct.setActBizTimes((short) (jzAct.getActBizTimes() + 1));
			} else if (medicalafterDTO.getMedicaltype().equals("2")) {
				jzAct.setActBizMoney2(jzAct.getActBizMoney2().add(
						medicalafterDTO.getAsisstpay()));
				jzAct.setActBizTimes((short) (jzAct.getActBizTimes() + 1));
			} else {
			}
			/*
			 * Calendar calendar = Calendar.getInstance(); calendar.setTime(new
			 * Date()); int year = calendar.get(Calendar.YEAR);
			 * jzAct.setActYear((short) year);
			 */
			jzActDAO.updateByPrimaryKeySelective(jzAct);
		} else {
		}

		return medicalafterDTO;
	}

	@SuppressWarnings({ "rawtypes", "static-access" })
	@Override
	public MedicalafterDTO findCountAssist(MedicalafterDTO medicalafterDTO) {
		try {
			Calendar cal = Calendar.getInstance();
			cal.setTime(medicalafterDTO.getEndtime());
			int year = cal.get(cal.YEAR);
			JzActExample example = new JzActExample();
			example.createCriteria()
					.andMemberIdEqualTo(medicalafterDTO.getMemberId())
					.andMemberTypeEqualTo(medicalafterDTO.getMemberType())
					.andActYearEqualTo((short) year);
			List<JzAct> acts = jzActDAO.selectByExample(example);
			JzAct currentact = new JzAct();
			if (null != acts && acts.size() > 0) {
				currentact = acts.get(0);
			} else {
				currentact.setMemberId(medicalafterDTO.getMemberId());
				currentact.setMemberType(medicalafterDTO.getMemberType());
				currentact.setActYear((short) year);
				currentact.setActBizInhospitalTimes((short) 0);
				currentact.setActBizMoney(new BigDecimal(0));
				currentact.setActBizMoney2(new BigDecimal(0));
				currentact.setActId(jzActDAO.insertSelective(currentact));
			}
			String idcard = medicalafterDTO.getPaperid();
			//定点医院住院次数 1:住院 2:门诊
			ActDTO actDTO_DDZY = this.getdingdiancount(year,idcard,"1");
			String ZBZ_FLAG = "";
			if ("111".equals(medicalafterDTO.getPersontype())) {
				ZBZ_FLAG = "2";
			} else if ("101".equals(medicalafterDTO.getPersontype())) {
				ZBZ_FLAG = "2";
			} else {
				ZBZ_FLAG = "1";
			}
			String sql = "select func_calcassist("
					+ medicalafterDTO.getMemberType() + ","
					+ medicalafterDTO.getInsuretype() + "," + ZBZ_FLAG + ","
					+ medicalafterDTO.getHospitallevel() + ","
					+ medicalafterDTO.getMedicaltype() + ",to_char('"
					+ medicalafterDTO.getDiagnose() + "'),"
					+ currentact.getActBizInhospitalTimes()
					+ ",0,0," + currentact.getActBizMoney2().doubleValue()
					+ "," + currentact.getActBizMoney().doubleValue() + ","
					+ medicalafterDTO.getTotalcost().doubleValue() + ","
					+ medicalafterDTO.getPayLine().doubleValue() + ","
					+ medicalafterDTO.getInsurepay().doubleValue() + ","
					+ medicalafterDTO.getHospitalpay().doubleValue() + ","
					+ medicalafterDTO.getOutpay().doubleValue() + ","
					+ medicalafterDTO.getCapay().doubleValue() + ","
					+ medicalafterDTO.getBusinesspay().doubleValue() + ","
					+ medicalafterDTO.getWsflag() + ","
					+ medicalafterDTO.getWzzflag() + ","
					+ medicalafterDTO.getSelfpay() + ","
					+ medicalafterDTO.getHospitaltype() + ","
					+ actDTO_DDZY.getActBizDDInhospitalTimes()
					+ ") as r from dual";

			/*
			 * sql="select func_calcassist(ds => v_ds, medicare_type =>
			 * v_medicare_type, zbz_flag => v_zbz_flag, hospital_level =>
			 * v_hospital_level, biz_type => v_biz_type, diagnose => v_diagnose,
			 * zycount => v_zycount, single_flag => v_single_flag, single_count
			 * => v_single_count, pay_summz => v_pay_summz, pay_sumzy =>
			 * v_pay_sumzy, pay_total => v_pay_total, pay_beginline =>
			 * v_pay_beginline, pay_medicare => v_pay_medicare, pay_hosadd =>
			 * v_pay_hosadd, pay_out => v_pay_out, pay_dbbx => v_pay_dbbx,
			 * pay_comm => v_pay_comm) as r from dual";
			 */
			/*
			 * PAY_TOTAL in NUMBER, --总费用 PAY_BEGINLINE in NUMBER, --起付线
			 * PAY_MEDICARE in NUMBER, --统筹 PAY_HOSADD in NUMBER, --医院补助 PAY_OUT
			 * in NUMBER, --自费（目录外） PAY_DBBX in NUMBER, --大病保险 PAY_COMM in
			 * NUMBER, --商业保险
			 */
			System.out.println(sql);
			ExecutSQL executSQL = new ExecutSQL();
			executSQL.setExecutsql(sql);
			List<HashMap> maps = executSQLDAO.queryAll(executSQL);
			String result = (String) maps.get(0).get("R");
			System.out.println(result);
			medicalafterDTO.setR(result);
			String[] i = result.split("-");
			BigDecimal asisstpay = BigDecimal.ZERO;
			if (i.length == 2) {
				asisstpay = new BigDecimal(i[0]);
			} else if (i.length == 3) {
				asisstpay = new BigDecimal("-" + i[1]);
			}
			medicalafterDTO.setAsisstpay(asisstpay);
			medicalafterDTO.setActId(currentact.getActId());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return medicalafterDTO;
	}
	
	@SuppressWarnings("rawtypes")
	private ActDTO getdingdiancount(int year, String idcard, String medicaltype){
		ActDTO actDTO = new ActDTO();
		try {
			String sql = " select nvl(sum(tt.pay_assist),0) as sumpay,count(1) as cishu from v_pay_act tt " 
					+ " where tt.idcard='"+idcard+"' " 
					+ " and to_char(tt.end_date,'yyyy') = '"+year+"' " 
					+ " and tt.medicaltype='"+medicaltype+"' ";
			ExecutSQL executSQL = new ExecutSQL();
			executSQL.setExecutsql(sql);
			HashMap map = executSQLDAO.queryAll(executSQL).get(0);
			BigDecimal s = (BigDecimal) map.get("CISHU");
			actDTO.setActBizDDInhospitalTimes(s.shortValue());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return actDTO;
	}

	@SuppressWarnings("rawtypes")
	public MedicalafterDTO findMemberInfoPrint(MedicalafterDTO m) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(m.getEndtime());
		int year = cal.get(cal.YEAR);
		MedicalafterDTO medicalafterDTO = new MedicalafterDTO();
		try {
			String sql = " select ma_id, jma.familyno, jma.membername, jma.paperid, jma.ssn, jma.hospital, "
					+ " jma.hospitallevel, jma.sickencontent, jma.begintime, jma.endtime, jma.approveresult,"
					+ " jma.approvecontent, jma.totalcost, jma.insurepay, jma.outpay, jma.capay, jma.businesspay, "
					+ " jma.asisstpay, jma.createtime, jma.updatetime, jma.member_id, jma.member_type, jma.implsts,"
					+ " jma.tiketno, jma.medicaltype, jma.insuretype, jma.persontype, jma.on_no, jma.pay_line, "
					+ " jma.hospitalpay, jma.diagnose, jma.famcount, jma.famaddr, jma.telephone, jma.sex, jma.birthday,"
					+ " c.act_biz_inhospital_times as num, c.act_biz_money as sumpay, "
					+ " (trunc(jma.endtime, 'dd') - trunc(jma.begintime, 'dd')) as indate, "
					+ " jma.pz_prinum, jma.app_prinum "
					+ " from jz_medicalafter jma, "
					+ " jz_act c "
					+ " where 1=1 "
					+ " and jma.member_id = c.member_id "
					+ " and jma.member_type = c.member_type "
					+ " and c.act_year = '"
					+ year
					+ "' "
					+ " and jma.ma_id = "
					+ m.getMaId();
			ExecutSQL executSQL = new ExecutSQL();
			executSQL.setExecutsql(sql);
			HashMap map = executSQLDAO.queryAll(executSQL).get(0);
			medicalafterDTO.setMaId((BigDecimal) map.get("MA_ID"));
			medicalafterDTO.setMembername((String) map.get("MEMBERNAME"));
			medicalafterDTO.setFamilyno((String) map.get("FAMILYNO"));
			medicalafterDTO.setPaperid((String) map.get("PAPERID"));
			medicalafterDTO.setSsn((String) map.get("SSN"));
			medicalafterDTO.setSex((String) map.get("SEX"));
			medicalafterDTO.setFamaddr((String) map.get("FAMADDR"));
			medicalafterDTO.setPersontype((String) map.get("PERSONTYPE"));
			medicalafterDTO.setMedicaltype((String) map.get("MEDICALTYPE"));
			medicalafterDTO.setSickencontent((String) map.get("SICKENCONTENT"));
			medicalafterDTO.setDiagnose((String) map.get("DIAGNOSE"));
			medicalafterDTO.setTiketno((String) map.get("TIKETNO"));
			medicalafterDTO.setHospital((String) map.get("HOSPITAL"));
			medicalafterDTO.setHospitallevel((String) map.get("HOSPITALLEVEL"));
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			String begintime = sdf.format((Date) map.get("BEGINTIME"));
			String endtime = sdf.format((Date) map.get("ENDTIME"));
			Date birth = (Date) map.get("BIRTHDAY");
			String birthday = "";
			if (birth == null || "".equals(birth)) {
			} else {
				birthday = sdf.format((Date) map.get("BIRTHDAY"));
			}
			medicalafterDTO.setBegintimeval(begintime);
			medicalafterDTO.setEndtimeval(endtime);
			medicalafterDTO.setBirthdayval(birthday);
			medicalafterDTO.setNum((BigDecimal) map.get("NUM"));
			medicalafterDTO.setIndate((BigDecimal) map.get("INDATE"));
			medicalafterDTO.setTotalcost((BigDecimal) map.get("TOTALCOST"));
			medicalafterDTO.setInsurepay((BigDecimal) map.get("INSUREPAY"));
			medicalafterDTO.setOutpay((BigDecimal) map.get("OUTPAY"));
			medicalafterDTO.setCapay((BigDecimal) map.get("CAPAY"));
			medicalafterDTO.setBusinesspay((BigDecimal) map.get("BUSINESSPAY"));
			medicalafterDTO.setAsisstpay((BigDecimal) map.get("ASISSTPAY"));
			medicalafterDTO.setPayLine((BigDecimal) map.get("PAY_LINE"));
			medicalafterDTO.setHospitalpay((BigDecimal) map.get("HOSPITALPAY"));
			medicalafterDTO.setInsuretype((String) map.get("INSURETYPE"));
			medicalafterDTO.setSumpay((BigDecimal) map.get("SUMPAY"));
			medicalafterDTO.setTelephone((String) map.get("TELEPHONE"));
			BigDecimal famcount = (BigDecimal) map.get("FAMCOUNT");
			if (famcount == null) {

			} else {
				medicalafterDTO.setFamcountval(famcount.toString());
				medicalafterDTO.setFamcount(famcount.shortValue());
			}

			BigDecimal pz = (BigDecimal) map.get("PZ_PRINUM");
			medicalafterDTO.setPzPrinum(pz.toString());
			BigDecimal app = (BigDecimal) map.get("APP_PRINUM");
			medicalafterDTO.setAppPrinum(app.toString());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return medicalafterDTO;
	}

	public MedicalafterDTO findMemberByKey(MedicalafterDTO m) {
		MedicalafterDTO e = new MedicalafterDTO();
		JzMedicalafterExample example = new JzMedicalafterExample();
		com.medical.model.JzMedicalafterExample.Criteria criteria = example
				.createCriteria();
		criteria.andMaIdEqualTo(m.getMaId());
		JzMedicalafter s = jzMedicalafterDAO.selectByExample(example).get(0);
		MemberBaseinfoExample example1 = new MemberBaseinfoExample();
		com.medical.model.MemberBaseinfoExample.Criteria criteria1 = example1
				.createCriteria();
		criteria1.andMemberIdEqualTo(m.getMemberId());
		criteria1.andDsEqualTo(m.getMemberType());
		MemberBaseinfo mb = memberBaseinfoDAO.selectByExampleWithoutBLOBs(
				example1).get(0);
		e.setMaId(s.getMaId());
		e.setFamilyno(s.getFamilyno());
		e.setMembername(s.getMembername());
		e.setPaperid(s.getPaperid());
		e.setSsn(s.getSsn());
		e.setHospital(s.getHospital());
		e.setHospitallevel(s.getHospitallevel());
		e.setSickencontent(s.getSickencontent());
		e.setBegintime(s.getBegintime());
		e.setEndtime(s.getEndtime());
		e.setApproveresult(s.getApproveresult());
		e.setApprovecontent(s.getApprovecontent());
		e.setTotalcost(s.getTotalcost());
		e.setInsurepay(s.getInsurepay());
		e.setOutpay(s.getOutpay());
		e.setCapay(s.getCapay());
		e.setBusinesspay(s.getBusinesspay());
		e.setAsisstpay(s.getAsisstpay());
		e.setCreatetime(s.getCreatetime());
		e.setUpdatetime(s.getUpdatetime());
		e.setMemberId(s.getMemberId());
		e.setMemberType(s.getMemberType());
		e.setImplsts(s.getImplsts());
		e.setTiketno(s.getTiketno());
		e.setMedicaltype(s.getMedicaltype());
		e.setInsuretype(s.getInsuretype());
		e.setPersontype(s.getPersontype());
		e.setPayLine(s.getPayLine());
		e.setHospitalpay(s.getHospitalpay());
		e.setOnNo(s.getOnNo());
		e.setFamaddr(s.getFamaddr());
		e.setSex(s.getSex());
		e.setMasterName(mb.getMasterName());
		e.setRelmaster(mb.getRelmaster());
		e.setWsflag(s.getWsflag());
		e.setPzPrinum(s.getPzPrinum().toString());
		e.setAppPrinum(s.getAppPrinum().toString());
		e.setWzzflag(s.getWzzflag());
		e.setSelfpay(s.getSelfpay());
		return e;
	}

	@SuppressWarnings("static-access")
	public int updateMedicalafter(MedicalafterDTO m) {
		int u = 0;
		JzMedicalafter record = new JzMedicalafter();
		record.setMaId(m.getMaId());
		record.setApproveresult("-1");
		record.setUpdatetime(new Date());
		int jmd = jzMedicalafterDAO.updateByPrimaryKeySelective(record);
		// 处理jz_act
		// (1)查询jz_act
		Calendar cal = Calendar.getInstance();
		cal.setTime(m.getEndtime());
		int year = cal.get(cal.YEAR);
		JzActExample example = new JzActExample();
		example.createCriteria().andMemberIdEqualTo(m.getMemberId())
				.andMemberTypeEqualTo(m.getMemberType())
				.andActYearEqualTo((short) year);
		JzAct ja = jzActDAO.selectByExample(example).get(0);
		// (2)修改jz_act
		JzAct record1 = new JzAct();
		if ("1".equals(m.getMedicaltype())) {
			record1.setActBizMoney(ja.getActBizMoney().subtract(
					m.getAsisstpay()));
			record1.setActBizInhospitalTimes((short) (ja
					.getActBizInhospitalTimes() - 1));
		} else if ("2".equals(m.getMedicaltype())) {
			record1.setActBizMoney2(ja.getActBizMoney2().subtract(
					m.getAsisstpay()));
		}
		record1.setActBizTimes((short) (ja.getActBizTimes() - 1));
		record1.setActId(ja.getActId());
		int jad = jzActDAO.updateByPrimaryKeySelective(record1);
		if (jmd > 0 && jad > 0) {
			u = 1;
		}
		return u;
	}

	public ActDTO findActByID(MedicalafterDTO medicalafterDTO) {
		Calendar calendar_end = Calendar.getInstance();
		calendar_end.setTime(medicalafterDTO.getEndtime());
		int business_year = calendar_end.get(Calendar.YEAR);
		ActDTO actDTO = new ActDTO();
		JzActExample example = new JzActExample();
		com.medical.model.JzActExample.Criteria criteria = example
				.createCriteria();
		criteria.andMemberIdEqualTo(medicalafterDTO.getMemberId())
				.andMemberTypeEqualTo(medicalafterDTO.getMemberType())
				.andActYearEqualTo((short) business_year);
		List<JzAct> acts = jzActDAO.selectByExample(example);
		if (acts.size() > 0) {
			JzAct act = acts.get(0);
			actDTO.setActId(act.getActId());
			actDTO.setMemberId(act.getMemberId());
			actDTO.setHospitalId(act.getHospitalId());
			actDTO.setCenterId(act.getCenterId());
			actDTO.setActYear(act.getActYear());
			actDTO.setActBizType(act.getActBizType());
			actDTO.setActBizTimes(act.getActBizTimes());
			actDTO.setActBizMoney(act.getActBizMoney());
			actDTO.setActBizInhospitalTimes(act.getActBizInhospitalTimes());
			actDTO.setActBizMoney2(act.getActBizMoney2());
			actDTO.setActBizMoneyOld(act.getActBizMoneyOld());
			actDTO.setAddMz(act.getAddMz());
			actDTO.setAddZy(act.getAddZy());
			actDTO.setMemberType(act.getMemberType());
		}
		return actDTO;
	}

	@SuppressWarnings("rawtypes")
	public BaseInfoDTO findSalvationStatus(BaseInfoDTO baseInfoDTO) {
		BaseInfoDTO b = new BaseInfoDTO();
		try {
			String from = "";
			if ("1".equals(baseInfoDTO.getDs())) {
				from = "from salvationstatus@cs.regress.rdbms.dev.us.oracle.com ss,familyinfo@cs.regress.rdbms.dev.us.oracle.com info ";
			} else if ("2".equals(baseInfoDTO.getDs())) {
				from = "from salvationstatus@nc.regress.rdbms.dev.us.oracle.com ss,familyinfo@nc.regress.rdbms.dev.us.oracle.com info ";
			}
			String sql = " select ss.ss_ot_id, "
					+ " max(case ss.st_id when '1' then ss.ss_state end) as dbstate, "
					+ " max(case ss.st_id when '1' then to_char(ss.ss_begintime,'yyyy-MM-dd') end) as dbtime, "
					+ " max(case ss.st_id when '31' then ss.ss_state end) as zbzstate, "
					+ " max(case ss.st_id when '31' then to_char(ss.ss_begintime,'yyyy-MM-dd') end) as zbztime "
					+ from + " where ss.ss_ot_id = info.familyid "
					+ " and info.familyno = '" + baseInfoDTO.getFamilyno()
					+ "' " + " group by ss.ss_ot_id ";
			ExecutSQL executSQL = new ExecutSQL();
			executSQL.setExecutsql(sql);
			List<HashMap> map = executSQLDAO.queryAll(executSQL);
			if (map.size() > 0) {
				HashMap m = map.get(0);
				b.setDbstate((String) m.get("DBSTATE"));
				b.setDbtime((String) m.get("DBTIME"));
				b.setZbzstate((String) m.get("ZBZSTATE"));
				b.setZbztime((String) m.get("ZBZTIME"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return b;
	}

	public int updateMedicalpzPinSum(MedicalafterDTO m, String type) {
		int i = 0;
		JzMedicalafter record = new JzMedicalafter();
		record.setMaId(m.getMaId());
		if ("pz".equals(type)) {
			record.setPzPrinum(Long.valueOf(m.getPzPrinum()));
		} else if ("app".equals(type)) {
			record.setAppPrinum(Long.valueOf(m.getAppPrinum()));
		}
		i = jzMedicalafterDAO.updateByPrimaryKeySelective(record);
		return i;
	}

	@SuppressWarnings("rawtypes")
	public MedicalafterDTO findSumPayDbbx(MedicalafterDTO m) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(m.getEndtime());
		int year = calendar.get(calendar.YEAR);
		try {
			String sql = "select x.idcard, sum(x.pay_total) as total, sum(x.pay_medicare) as medicare, sum(x.pay_outmedicare) as outmedicare,"
					+ " sum(x.pay_assist) as assist, sum(x.pay_ciassist) as ciassist, sum(x.pay_business) as business "
					+ " from v_pay_dbbx x "
					+ " where x.idcard = '"
					+ m.getPaperid()
					+ "' "
					+ " and to_char(x.end_date, 'yyyy') = '"
					+ year
					+ "' "
					+ " group by x.idcard ";
			ExecutSQL executSQL = new ExecutSQL();
			executSQL.setExecutsql(sql);
			List<HashMap> maps = executSQLDAO.queryAll(executSQL);
			if (maps.size() > 0) {
				HashMap map = maps.get(0);
				m.setSumtotalcost((BigDecimal) map.get("TOTAL"));
				m.setSuminsurepay((BigDecimal) map.get("MEDICARE"));
				m.setSumoutpay((BigDecimal) map.get("OUTMEDICARE"));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return m;
	}

	/**
	 * RC 180 TOTALCOST 1880670.08 INSUREPAY 893508.16 ASISSTPAY 307103.92
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public String queryMaStat(String jwhere) {
		String u = "";
		ExecutSQL executSQL = new ExecutSQL();
		executSQL.setExecutsql(jwhere);
		List<HashMap> maps;
		try {
			maps = executSQLDAO.queryAll(executSQL);
			if (maps.size() > 0) {
				u = "【救助人次：" + maps.get(0).get("RC") + "  救助金总额："
						+ maps.get(0).get("ASISSTPAY") + "元】 ";
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return u;
	}

	public MemberBaseinfoDAO getMemberBaseinfoDAO() {
		return memberBaseinfoDAO;
	}

	public void setMemberBaseinfoDAO(MemberBaseinfoDAO memberBaseinfoDAO) {
		this.memberBaseinfoDAO = memberBaseinfoDAO;
	}

	public void setToolsmenu(String toolsmenu) {
		this.toolsmenu = toolsmenu;
	}

	public String getToolsmenu() {
		return toolsmenu;
	}

	public void setJzBizDAO(JzBizDAO jzBizDAO) {
		this.jzBizDAO = jzBizDAO;
	}

	public JzBizDAO getJzBizDAO() {
		return jzBizDAO;
	}

	public ExecutSQLDAO getExecutSQLDAO() {
		return executSQLDAO;
	}

	public void setExecutSQLDAO(ExecutSQLDAO executSQLDAO) {
		this.executSQLDAO = executSQLDAO;
	}

	public Pager getPager() {
		return pager;
	}

	public void setPager(Pager pager) {
		this.pager = pager;
	}

	public TestSsnDAO getTestSsnDAO() {
		return testSsnDAO;
	}

	public void setTestSsnDAO(TestSsnDAO testSsnDAO) {
		this.testSsnDAO = testSsnDAO;
	}

	public JzMedicalafterDAO getJzMedicalafterDAO() {
		return jzMedicalafterDAO;
	}

	public void setJzMedicalafterDAO(JzMedicalafterDAO jzMedicalafterDAO) {
		this.jzMedicalafterDAO = jzMedicalafterDAO;
	}

	public JzActDAO getJzActDAO() {
		return jzActDAO;
	}

	public void setJzActDAO(JzActDAO jzActDAO) {
		this.jzActDAO = jzActDAO;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List<MedicalafterDTO> queryMaBillStat(String sql) {
		List<MedicalafterDTO> list = new ArrayList<MedicalafterDTO>();
		ExecutSQL executSQL = new ExecutSQL();
		executSQL.setExecutsql(sql);
		List<HashMap> maps;
		/*
		 * DS 1 ON_NO 220201 BATCHNAME 2014年12月医疗救助 RC 6 ASISSTPAY 10295.04
		 */

		try {
			maps = executSQLDAO.queryAll(executSQL);
			for (HashMap s : maps) {
				MedicalafterDTO e = new MedicalafterDTO();
				e.setDs((String) s.get("DS"));
				e.setBatchname((String) s.get("BATCHNAME"));
				e.setRc((BigDecimal) s.get("RC"));
				e.setOnNo((String) s.get("ON_NO"));
				e.setAsisstpay((BigDecimal) s.get("ASISSTPAY"));
				e.setApprovecontent((String) s.get("ORGNAME"));
				list.add(e);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public void saveMaBatchDone() {
		String sql = "";
		try {
			ExecutSQL executSQL = new ExecutSQL();
			// 1
			sql = "insert into jz_mabills (bill_id, ma_id, "
					+ " familyno,  membername,  paperid,  ctime, utime,  assispay,  ds, sb_id,  batchname, mastername,   bank_account ,bank_account1) "
					+ " select XMABILL.Nextval,  ma.ma_id,  ma.familyno, ma.membername,  ma.paperid, "
					+ " sysdate, sysdate, ma.asisstpay, sb.ds, sb_id, sb.sb_batchname  ,ma.mastername, ma.bank_account,  ma.bank_account1 "
					+ " from jz_medicalafter ma, (select sb.sb_batchname, sb.sb_id,  sb.sb_disposests, "
					+ " '1' as ds,   sb.on_no from salvationbatch@cs sb, salvationoperation@cs so "
					+ " where so.so_id = sb.so_id and so.st_id = '4'  and sb.sb_disposests = '处理中' "
					+ " union all select sb.sb_batchname, sb.sb_id, sb.sb_disposests,  '2' as ds, "
					+ " sb.on_no  from salvationbatch@nc sb, salvationoperation@nc so "
					+ " where so.so_id = sb.so_id  and so.st_id = '4' and sb.sb_disposests = '处理中') sb "
					+ " where sb.on_no = substr(ma.on_no, 1, 6) and sb.ds = ma.member_type and ma.implsts = '0' "
					+ " and ma.approveresult = '1' ";
			System.out.println(sql);
			executSQL.setExecutsql(sql);
			executSQLDAO.updateSQL(executSQL);
			// 2
			sql = "update jz_medicalafter ma set ma.implsts = '1' where ma.ma_id in "
					+ " (select b.ma_id from jz_mabills b where b.sb_id in "
					+ " (select sb.sb_id from salvationbatch@cs sb, salvationoperation@cs so "
					+ " where so.so_id = sb.so_id and so.st_id = '4'  and sb.sb_disposests = '处理中' "
					+ " union all  select sb.sb_id from salvationbatch@nc sb, salvationoperation@nc so "
					+ " where so.so_id = sb.so_id  and so.st_id = '4' "
					+ " and sb.sb_disposests = '处理中'))";
			System.out.println(sql);
			executSQL.setExecutsql(sql);
			executSQLDAO.updateSQL(executSQL);

			sql = " delete from batch_almsreckoning@cs b  where b.st_id = '4' "
					+ " and b.sb_id in (select sb.sb_id   from salvationbatch@cs sb, salvationoperation@cs so "
					+ " where so.so_id = sb.so_id    and so.st_id = '4'    and sb.sb_disposests = '处理中')";
			System.out.println(sql);
			executSQL.setExecutsql(sql);
			executSQLDAO.updateSQL(executSQL);

			sql = " delete from batch_almsreckoning@nc b  where b.st_id = '4' "
					+ " and b.sb_id in (select sb.sb_id   from salvationbatch@nc sb, salvationoperation@nc so "
					+ " where so.so_id = sb.so_id    and so.st_id = '4'    and sb.sb_disposests = '处理中')";
			System.out.println(sql);
			executSQL.setExecutsql(sql);
			executSQLDAO.updateSQL(executSQL);

			// 3
			sql = " insert into batch_almsreckoning@cs (bar_id, bar_subject, bar_money, "
					+ " bar_master, bar_fmidcard, bar_bank_accounts,  sb_id,  ar_id, "
					+ " bar_familyno, bar_awardflag, bar_familyid, on_no,  sa_id, "
					+ " bar_familycount,  st_id) select xbatch_almsreckoning.nextval@cs, "
					+ " sb.batchname,  sb.assispay, fi.mastername, fi.paperid, "
					+ " fi.accounts, sb.sb_id, null, fi.familyno, "
					+ " null, fi.familyid,  fi.on_no, null,  fi.salcount, '4' "
					+ " from familyinfo@cs fi, (select max(b.sb_id) as sb_id, "
					+ " max(b.batchname) as batchname, max(b.assispay) as assispay, "
					+ " max(b.familyno) as familyno from jz_mabills b "
					+ " where b.ds = '1'  group by b.familyno) sb "
					+ " where fi.familyno = sb.familyno";
			System.out.println(sql);
			executSQL.setExecutsql(sql);
			executSQLDAO.updateSQL(executSQL);

			// 4
			sql = "insert into batch_almsreckoning@nc (bar_id, "
					+ " bar_subject,  bar_money,     bar_master,     bar_fmidcard, "
					+ " bar_bank_accounts,     sb_id,     bar_familyno,     bar_awardflag, "
					+ " bar_familyid,     on_no,     sa_id,     bar_familycount, "
					+ " st_id)    select xbatch_almsreckoning.nextval@nc, "
					+ " sb.batchname,    sb.assispay,      fi.mastername, "
					+ " fi.paperid,      fi.accounts,      sb.sb_id, "
					+ " fi.familyno,     null,    fi.familyid, "
					+ " fi.on_no,    null,     fi.salcount,   '4' "
					+ " from familyinfo@nc fi, (select max(b.sb_id) as sb_id,   max(b.batchname) as batchname, "
					+ " max(b.assispay) as assispay, max(b.familyno) as familyno   from jz_mabills b "
					+ " where b.ds = '2'  group by b.familyno) sb   where fi.familyno = sb.familyno";
			System.out.println(sql);
			executSQL.setExecutsql(sql);
			executSQLDAO.updateSQL(executSQL);

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List<CheckDTO> getMonths() {
		String sql = " select * from ( select  distinct(substr( b.batchname,1,8)) as b  from jz_mabills b   ) order by b";
		List<CheckDTO> list = new ArrayList<CheckDTO>();
		ExecutSQL executSQL = new ExecutSQL();
		executSQL.setExecutsql(sql);
		List<HashMap> maps;
		/*
		 * DS 1 ON_NO 220201 BATCHNAME 2014年12月医疗救助 RC 6 ASISSTPAY 10295.04
		 */

		try {
			maps = executSQLDAO.queryAll(executSQL);
			for (HashMap s : maps) {
				CheckDTO e = new CheckDTO();
				e.setDs((String) s.get("B"));
				list.add(e);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	public JzMabillsDAO getJzMabillsDAO() {
		return jzMabillsDAO;
	}

	public void setJzMabillsDAO(JzMabillsDAO jzMabillsDAO) {
		this.jzMabillsDAO = jzMabillsDAO;
	}

	@Override
	public void saveCommitMaBatch() {
		try {
			ExecutSQL executSQL = new ExecutSQL();
			String sql = "update salvationbatch@cs sb  set sb.sb_disposests = '待统计' "
					+ " where sb.sb_id in (select sb.sb_id from salvationbatch@cs sb, salvationoperation@cs so "
					+ " where so.so_id = sb.so_id and so.st_id = '4'  and sb.sb_disposests = '处理中')";
			executSQL.setExecutsql(sql);
			executSQLDAO.updateSQL(executSQL);

			sql = "update salvationbatch@nc sb  set sb.sb_disposests = '待统计' "
					+ " where sb.sb_id in (select sb.sb_id from salvationbatch@nc sb, salvationoperation@nc so "
					+ " where so.so_id = sb.so_id and so.st_id = '4'  and sb.sb_disposests = '处理中')";
			executSQL.setExecutsql(sql);
			executSQLDAO.updateSQL(executSQL);

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void saveCancelMaBatch(String batchname) {
		try {
			String sql = "";
			sql = "update jz_medicalafter t  set t.implsts = '0' "
					+ "where t.ma_id in (select b.ma_id from jz_mabills b "
					+ "where b.batchname like '" + batchname + "%')";
			ExecutSQL executSQL = new ExecutSQL();
			executSQL.setExecutsql(sql);
			executSQLDAO.updateSQL(executSQL);
			sql = "delete   batch_almsreckoning@cs b  where b.st_id = '4'   "
					+ "and b.sb_id in (select distinct (b.sb_id) "
					+ " from jz_mabills b	  where b.batchname like '"
					+ batchname + "%'  and b.ds = '1')";
			executSQL.setExecutsql(sql);
			executSQLDAO.updateSQL(executSQL);
			sql = "delete   batch_almsreckoning@nc b	where b.st_id = '4'"
					+ "	and b.sb_id in (select distinct (b.sb_id)  "
					+ "from jz_mabills b	where b.batchname like " + "'"
					+ batchname + "%'	 and b.ds = '2')";
			executSQL.setExecutsql(sql);
			executSQLDAO.updateSQL(executSQL);

			sql = "update salvationbatch@cs sb  set sb.sb_disposests = '处理中' where sb.sb_id in (select sb.sb_id"
					+ " from salvationbatch@cs sb, salvationoperation@cs so  where so.so_id = sb.so_id  "
					+ "and so.st_id = '4'   and sb.sb_disposests = '待统计' and sb.sb_batchname like '"
					+ batchname + "%')";
			executSQL.setExecutsql(sql);
			executSQLDAO.updateSQL(executSQL);

			sql = "update salvationbatch@nc sb  set sb.sb_disposests = '处理中' where sb.sb_id in (select sb.sb_id"
					+ " from salvationbatch@nc sb, salvationoperation@nc so  where so.so_id = sb.so_id  "
					+ "and so.st_id = '4'   and sb.sb_disposests = '待统计' and sb.sb_batchname like '"
					+ batchname + "%')";

			executSQL.setExecutsql(sql);
			executSQLDAO.updateSQL(executSQL);

			sql = " delete jz_mabills b   where b.sb_id in "
					+ "(select distinct (b.sb_id) from "
					+ "jz_mabills b where b.batchname like '" + batchname
					+ "%')";
			executSQL.setExecutsql(sql);
			executSQLDAO.updateSQL(executSQL);

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void updateMedicalafterAcc(String sql) {
		try {
			ExecutSQL executSQL = new ExecutSQL();

			System.out.println(sql);
			executSQL.setExecutsql(sql);

			executSQLDAO.updateSQL(executSQL);
		} catch (SQLException e) {

			e.printStackTrace();
		}
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List<MedicalafterDTO> findstatus(String sql) {
		List<MedicalafterDTO> list = new ArrayList<MedicalafterDTO>();
		ExecutSQL executSQL = new ExecutSQL();
		executSQL.setExecutsql(sql);
		List<HashMap> maps;
		try {
			maps = executSQLDAO.queryAll(executSQL);
			for (HashMap s : maps) {
				MedicalafterDTO e = new MedicalafterDTO();
				e.setMaxdate((String) s.get("MAXDATE"));
				e.setStname((String) s.get("STNAME"));
				list.add(e);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	public void saveccc(String xml1, String datatype, String memberid) {
		try {
			ExecutSQL executSQL = new ExecutSQL();
			String sql_s = " select a.idc, a.n, a.fn, a.s1, a.s2, a.ttt, a.stst, a.t1, a.t2, a.f, cc.nextval@cs as ccc_id, a.familyid, "
					+ "'"
					+ xml1
					+ "' as context01 "
					+ " from (select idcard15to18(fmi.fm_paperid) as idc, "
					+ " fmi.fm_name as n, "
					+ " fmi.f_familyno as fn, "
					+ " s1.ss_state as s1, "
					+ " s2.ss_state as s2, "
					+ " (case "
					+ " WHEN fmi.fm_personstate = '正常' and "
					+ " s1.ss_state = '在保户' and s2.ss_state = '在保户' THEN "
					+ " '2' "
					+ " WHEN fmi.fm_personstate = '正常' and "
					+ " s1.ss_state = '在保户' THEN "
					+ " '1' "
					+ " ELSE "
					+ " '0' "
					+ " END) as ttt, "
					+ " to_char(sysdate, 'yyyyMMdd') as stst, "
					+ " sysdate as t1, "
					+ " sysdate as t2, "
					+ " 1 as f, "
					+ " fmi.f_familyid as familyid "
					+ " from familymemberinfoall"
					+ datatype
					+ " fmi "
					+ " left join salvationstatus"
					+ datatype
					+ " s1 "
					+ " on fmi.f_familyid = s1.ss_ot_id "
					+ " and fn_checkidcard(fmi.fm_paperid) = 1 "
					+ " and fn_checkidcard(idcard15to18(fmi.fm_paperid)) = 1 "
					+ " and s1.st_id = '1' "
					+ " left join salvationstatus"
					+ datatype
					+ " s2 "
					+ " on fmi.f_familyid = s2.ss_ot_id "
					+ " and fn_checkidcard(fmi.fm_paperid) = 1 "
					+ " and fn_checkidcard(idcard15to18(fmi.fm_paperid)) = 1 "
					+ " and s2.st_id = '31' "
					+ " where fmi.fm_id ='"
					+ memberid + "') a ";
			executSQL.setExecutsql(sql_s);
			List<HashMap> m = executSQLDAO.queryAll(executSQL);
			String idc = (String) m.get(0).get("IDC");
			String n = (String) m.get(0).get("N");
			String fn = (String) m.get(0).get("FN");
			String s1 = (String) m.get(0).get("S1");
			String s2 = (String) m.get(0).get("S2");
			String ttt = (String) m.get(0).get("TTT");
			String stst = (String) m.get(0).get("STST");
			SimpleDateFormat format = new SimpleDateFormat(
					"yyyy-MM-dd HH:mm:ss");
			Date ctime = (Date) m.get(0).get("T1");
			Date utime = (Date) m.get(0).get("T2");
			String ct = format.format(ctime);
			String ut = format.format(utime);
			BigDecimal f = (BigDecimal) m.get(0).get("F");
			BigDecimal cccId = (BigDecimal) m.get(0).get("CCC_ID");
			String fid = (String) m.get(0).get("FAMILYID");
			String context01 = (String) m.get(0).get("CONTEXT01");
			String sql_i = " insert into ccc"
					+ datatype
					+ " (idc, n, fn, s1, s2, ttt, stst, ctime, utime, f, ccc_id, fid, context01) "
					+ " values ('" + idc + "','" + n + "','" + fn + "','" + s1
					+ "','" + s2 + "','" + ttt + "','" + stst + "',to_date('"
					+ ct + "','yyyy-mm-dd hh24:mi:ss')" + ",to_date('" + ut
					+ "','yyyy-mm-dd hh24:mi:ss')" + ",'" + f + "','" + cccId
					+ "','" + fid + "','" + context01 + "')";
			executSQL.setExecutsql(sql_i);
			executSQLDAO.updateSQL(executSQL);
			String sql_ii = " insert into sync_logs "
					+ "(logid, col1, col2, col3, col4, col5, col6, opertime) "
					+ " values   (ax.nextval, '" + idc + "', '" + n + "', '"
					+ fn + "', '" + ttt + "', '" + stst + "','" + context01
					+ "', sysdate)";
			executSQL.setExecutsql(sql_ii);
			executSQLDAO.updateSQL(executSQL);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public List<YBCheckDTO> findcheckhistory(String pid) {
		List<YBCheckDTO> list = new ArrayList<YBCheckDTO>();
		try {
			ExecutSQL executSQL = new ExecutSQL();
			String sql = "select to_char(t.ctime,'yyyy-mm-dd hh24:mi:ss') as a ,to_char(t.utime,'yyyy-mm-dd hh24:mi:ss') as b, t.context01 as c from mv_sync_logs t where t.idc=FUNC_AAC00215_18('"
					+ pid + "')";
			executSQL.setExecutsql(sql);

			List<HashMap> maps = executSQLDAO.queryAll(executSQL);

			for (HashMap s : maps) {
				YBCheckDTO e = new YBCheckDTO();
				e.setSsn1((String) s.get("A"));
				e.setSsn2((String) s.get("B"));
				e.setMessage((String) s.get("C"));
				String a = "<?xml version=\"1.0\" encoding=\"GB2312\"?>"
						+ e.getMessage() + "";
				DocumentBuilderFactory factory = DocumentBuilderFactory
						.newInstance();
				DocumentBuilder builder = factory.newDocumentBuilder();
				ByteArrayInputStream in = new ByteArrayInputStream(a.getBytes());
				Document doc = builder.parse(in);

				NodeList nls = doc.getElementsByTagName("医保编号");

				String b = "";
				for (int i = 0; i < nls.getLength(); i++) {
					b = b + "医保编号:" + nls.item(i).getTextContent() + ""
							+ System.lineSeparator();
				}
				e.setMessage(b);
				list.add(e);
			}

		} catch (SQLException | ParserConfigurationException | SAXException
				| IOException e) {
			e.printStackTrace();
		}
		return list;
	}

}
