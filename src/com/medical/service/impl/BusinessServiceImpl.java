package com.medical.service.impl;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;

import com.medical.common.Pager;
import com.medical.dao.DmBizdeptDAO;
import com.medical.dao.ExecutSQLDAO;
import com.medical.dao.Icd10DAO;
import com.medical.dao.JzActDAO;
import com.medical.dao.JzBizDAO;
import com.medical.dao.JzBizHistoryDAO;
import com.medical.dao.JzManualDAO;
import com.medical.dao.JzPaylistDAO;
import com.medical.dao.MemberBaseinfoDAO;
import com.medical.dao.SysTOrganizationDAO;
import com.medical.dto.BusinessDTO;
import com.medical.dto.ManualDTO;
import com.medical.dto.MedicalafterDTO;
import com.medical.dto.OrganDTO;
import com.medical.dto.PersonDTO;
import com.medical.dto.PrintBillDTO;
import com.medical.dto.VbizDTO;
import com.medical.model.DmBizdept;
import com.medical.model.DmBizdeptExample;
import com.medical.model.ExecutSQL;
import com.medical.model.Icd10;
import com.medical.model.Icd10Example;
import com.medical.model.JzAct;
import com.medical.model.JzActExample;
import com.medical.model.JzBiz;
import com.medical.model.JzBizExample;
import com.medical.model.JzBizHistory;
import com.medical.model.JzManual;
import com.medical.model.JzManualExample;
import com.medical.model.JzPaylist;
import com.medical.model.JzPaylistExample;
import com.medical.model.MemberBaseinfo;
import com.medical.model.MemberBaseinfoExample;
import com.medical.model.MemberBaseinfoExample.Criteria;
import com.medical.model.SysTOrganization;
import com.medical.model.SysTOrganizationExample;
import com.medical.service.BusinessService;
import com.medical.system.DictionaryHandle;
import com.webclient.client.MyClient;

public class BusinessServiceImpl implements BusinessService {
	static Logger log = Logger.getLogger(BusinessServiceImpl.class);
	private DictionaryHandle dictionaryHandle;
	private DmBizdeptDAO dmBizdeptDAO;
	private Icd10DAO icd10DAO;
	private JzActDAO jzActDAO;
	private JzBizDAO jzBizDAO;
	private JzBizHistoryDAO jzBizHistoryDAO;
	private JzPaylistDAO jzPaylistDAO;
	private MemberBaseinfoDAO memberBaseinfoDAO;
	private JzManualDAO jzManualDAO;
	private ExecutSQLDAO executSQLDAO;

	public JzManualDAO getJzManualDAO() {
		return jzManualDAO;
	}

	public void setJzManualDAO(JzManualDAO jzManualDAO) {
		this.jzManualDAO = jzManualDAO;
	}

	private Pager pager;
	private SysTOrganizationDAO sysTOrganizationDAO;

	public String findAccMedicalMoney(BusinessDTO businessDTO) {

		return null;
	}

	/**
	 * 查看业务
	 */
	public BusinessDTO findBiz(String bizId) {

		BusinessDTO businessDTO = new BusinessDTO();

		JzBiz jzBiz = this.jzBizDAO.selectByPrimaryKey(new Integer(bizId));

		JzPaylistExample example = new JzPaylistExample();
		example.createCriteria().andBizIdEqualTo(new Integer(bizId));
		List<JzPaylist> paylist = this.jzPaylistDAO.selectByExample(example);

		HashMap<String, String> map = new HashMap<String, String>();

		for (JzPaylist p : paylist) {
			map.put("" + p.getFuncId(), "" + p.getRealPay());
		}
		businessDTO.setHospitalname(jzBiz.getHospitalName());
		businessDTO.setFamilyno(jzBiz.getFamilyId());
		businessDTO.setCurrenttime(jzBiz.getRegDate());
		businessDTO.setEndtime(jzBiz.getEndDate());
		businessDTO.setBegintime(jzBiz.getBeginDate());
		businessDTO.setMedicaltype(this.getDictionaryHandle().getDictValue(
				"004-" + jzBiz.getAssistType()));
		businessDTO.setDays(new BigDecimal(jzBiz.getInDays()).toString());
		businessDTO.setSickname(jzBiz.getInDiseaseName());
		businessDTO.setHospital(this.dictionaryHandle.getDictValue(jzBiz
				.getHospitalId()));

		BigDecimal predeposit = new BigDecimal(map.get("114").toString());// 预交住院押金
		// 114
		BigDecimal initline = new BigDecimal(map.get("112").toString());// 起付线
		// 112
		BigDecimal selfmoney = new BigDecimal(0);// 自理费用
		BigDecimal selfall = new BigDecimal(map.get("116").toString());// 自付合计
		// 116
		BigDecimal hospitalmoney = new BigDecimal(map.get("111").toString());// 住院费用总额
		// 111
		BigDecimal accountbalance = new BigDecimal(map.get("113").toString());// 个人账户余额
		// 113
		BigDecimal accountpayment = new BigDecimal(map.get("003").toString());// 个人账户支付
		// 003
		BigDecimal planpayment = new BigDecimal(map.get("001").toString());// 统筹基金支付
		// 001
		BigDecimal officialpayment1 = new BigDecimal(0);// 公务员补基本医疗部分 301
		BigDecimal officialpayment = new BigDecimal(map.get("301").toString());// 公务员补助
		// 301
		BigDecimal bigpayment = new BigDecimal(map.get("201").toString());// 大额保险支付
		// 201
		BigDecimal restpayment = new BigDecimal(map.get("202").toString());// 离休统筹支付
		// 202
		BigDecimal injurypayment = new BigDecimal(map.get("501").toString());// 工伤保险支付
		// 501
		BigDecimal bearpayment = new BigDecimal(map.get("701").toString());// 生育保险支付
		// 701
		BigDecimal allmoney = new BigDecimal(map.get("999").toString());// 现金支付总额999

		BigDecimal recdeposit = predeposit.subtract(allmoney);// 退补住院押金 114-999
		BigDecimal selfscale = allmoney.add(accountpayment).subtract(initline)
				.subtract(selfall);// 自付比例 999+003+301+303-112-116-117
		BigDecimal oaccountbalance = accountbalance.add(accountpayment);// 原个人账户余额
		// 113+003
		BigDecimal healthpayment = new BigDecimal(0);// 保健对象补助支付 997+303

		BigDecimal z01 = new BigDecimal(0);
		if (null != map.get("Z01")) {
			z01 = new BigDecimal(map.get("Z01").toString());
		}
		BigDecimal z02 = new BigDecimal(0);
		if (null != map.get("Z02")) {
			z02 = new BigDecimal(map.get("Z02").toString());
		}

		businessDTO.setAccountbalance(accountbalance.toEngineeringString());
		businessDTO.setAccountpayment(accountpayment.toString());
		businessDTO.setAllmoney(allmoney.toString());
		businessDTO.setBearpayment(bearpayment.toString());
		businessDTO.setBigpayment(bigpayment.toString());
		businessDTO.setHealthpayment(healthpayment.toString());
		businessDTO.setInitline(initline.toString());
		businessDTO.setInjurypayment(injurypayment.toString());
		businessDTO.setOaccountbalance(oaccountbalance.toEngineeringString());
		businessDTO.setOfficialpayment(officialpayment.toString());
		businessDTO.setOfficialpayment1(officialpayment1.toString());
		businessDTO.setPlanpayment(planpayment.toString());
		businessDTO.setPredeposit(predeposit.toString());
		businessDTO.setRecdeposit(recdeposit.toString());
		businessDTO.setRestpayment(restpayment.toString());
		businessDTO.setSelfall(selfall.toString());
		businessDTO.setSelfmoney(selfmoney.toString());
		businessDTO.setSelfscale(selfscale.toString());
		businessDTO.setHospitalmoney(hospitalmoney.toString());
		businessDTO.setZ01(z01.toString());
		businessDTO.setZ02(z02.toString());

		return businessDTO;
	}

	public List<VbizDTO> findBizBySsn(PersonDTO personDTO, String biztype) {

		JzBizExample example = new JzBizExample();
		example.createCriteria().andSsnEqualTo(personDTO.getSsn())
				.andGatherFlagEqualTo("0").andStausEqualTo("1")
				.andBizTypeEqualTo(biztype);

		List<JzBiz> jzbizs = jzBizDAO.selectByExample(example);
		List<VbizDTO> vbizs = new ArrayList<VbizDTO>();

		for (JzBiz jzBiz : jzbizs) {
			VbizDTO vbizDTO = new VbizDTO();

			vbizDTO.setBizName(dictionaryHandle.getDictValue(jzBiz.getBizType()));
			vbizDTO.setBizType(jzBiz.getBizType());
			vbizDTO.setHospitalname(dictionaryHandle.getDictValue(jzBiz
					.getHospitalId()));
			vbizDTO.setRegDate(jzBiz.getRegDate());// 业务登记日期
			vbizDTO.setBeginDate(jzBiz.getBeginDate());// 业务开始时间
			vbizDTO.setDiagnoseDate(jzBiz.getDiagnoseDate());// 确诊时间
			vbizDTO.setEndDate(jzBiz.getEndDate());// 出院日期
			vbizDTO.setBizId(jzBiz.getBizId());
			vbizDTO.setSsn(jzBiz.getSsn());
			vbizDTO.setPersonpay(jzBiz.getPersonpay());
			vbizDTO.setAllmoney(jzBiz.getAllmoney());
			vbizDTO.setInsurancepay(jzBiz.getInsurancepay());
			vbizDTO.setAssistpay(jzBiz.getAssistpay());
			vbizs.add(vbizDTO);
		}
		return vbizs;
	}

	public List<VbizDTO> findBizRecordBySsn(PersonDTO personDTO) {
		List<VbizDTO> vbizs = new ArrayList<VbizDTO>();
		try {
			// System.out.println(personDTO.getSsn());
			JzBizExample example = new JzBizExample();
			example.createCriteria().andSsnEqualTo(personDTO.getSsn())
					.andGatherFlagEqualTo("5").andStausEqualTo("1");

			List<JzBiz> jzbizs = jzBizDAO.selectByExample(example);

			for (JzBiz jzBiz : jzbizs) {
				VbizDTO vbizDTO = new VbizDTO();

				vbizDTO.setBizName(dictionaryHandle.getDictValue(jzBiz
						.getBizType()));
				vbizDTO.setBizType(jzBiz.getBizType());
				vbizDTO.setHospitalname(dictionaryHandle.getDictValue(jzBiz
						.getHospitalId()));
				vbizDTO.setHospitalname(jzBiz.getHospitalName());
				vbizDTO.setRegDate(jzBiz.getRegDate());// 业务登记日期
				vbizDTO.setBeginDate(jzBiz.getBeginDate());// 业务开始时间
				vbizDTO.setDiagnoseDate(jzBiz.getDiagnoseDate());// 确诊时间
				vbizDTO.setEndDate(jzBiz.getEndDate());// 出院日期
				vbizDTO.setBizId(jzBiz.getBizId());
				vbizDTO.setSsn(jzBiz.getSsn());
				vbizDTO.setPersonpay(jzBiz.getPersonpay());
				vbizDTO.setAllmoney(jzBiz.getAllmoney());
				vbizDTO.setInsurancepay(jzBiz.getInsurancepay());
				vbizDTO.setAssistpay(jzBiz.getAssistpay());
				vbizs.add(vbizDTO);
			}
		} catch (RuntimeException re) {
			re.printStackTrace();
		}
		return vbizs;
	}

	public BusinessDTO findBusinessDTO(BusinessDTO businessDTO) {
		return null;
	}

	public List<DmBizdept> findHospitalList() {

		DmBizdeptExample example = new DmBizdeptExample();
		example.setOrderByClause("HOSPITAL_ID");
		List<DmBizdept> list = dmBizdeptDAO.selectByExample(example);

		return list;

	}

	public List<PersonDTO> findMedicalFamily(PersonDTO personDTO) {

		List<PersonDTO> persons = new ArrayList<PersonDTO>();

		MemberBaseinfoExample exam = new MemberBaseinfoExample();
		Criteria criteria = exam.createCriteria();
		criteria.andFamilynoEqualTo(personDTO.getFamilyno()).andFamilynoLike(
				personDTO.getOrganizationId() + "%");

		List<MemberBaseinfo> rs = memberBaseinfoDAO
				.selectByExampleWithoutBLOBs(exam);
		for (MemberBaseinfo memberBaseinfo : rs) {
			personDTO = new PersonDTO();
			personDTO.setAddress(memberBaseinfo.getAddress());
			personDTO.setAssistType(this.dictionaryHandle.getDictValue("004-"
					+ memberBaseinfo.getAssistType()));
			personDTO.setAssisTypeId(memberBaseinfo.getAssistType());
			personDTO.setBirthday(memberBaseinfo.getBirthday());
			personDTO.setFamilyno(memberBaseinfo.getFamilyno());
			personDTO.setLinkmode(memberBaseinfo.getLinkmode());
			personDTO.setMasterName(memberBaseinfo.getMasterName());
			personDTO.setMemberId(memberBaseinfo.getMemberId());
			personDTO.setPaperid(memberBaseinfo.getPaperid());
			personDTO.setRelmaster(memberBaseinfo.getRelmaster());
			personDTO.setRpraddress(memberBaseinfo.getRpraddress());
			personDTO.setRprkind(memberBaseinfo.getRprkind());
			personDTO.setRprtype(memberBaseinfo.getRprtype());
			personDTO.setSex(memberBaseinfo.getSex());
			personDTO.setSsn(memberBaseinfo.getSsn());
			personDTO.setMembername(memberBaseinfo.getMembername());
			persons.add(personDTO);
		}

		return persons;
	}

	public HashMap<String, String> findMedicalMoney(BusinessDTO businessDTO,
			String type) {

		MyClient client = new MyClient();
		String result = client.run(businessDTO.getBizId(), type);
		log.error(result);
		String[] s = result.split(",");
		HashMap<String, String> map = new HashMap<String, String>();
		if (s[0].equals("0")) {
			int i = 0;
			for (String value : s) {
				if (i > 1) {
					String title = this.dictionaryHandle.getDictValue("005-"
							+ i);
					map.put(title, value);
				}
				i++;
			}
		} else {
			map.put("1", result);

		}

		return map;
	}

	public PersonDTO findMedicalPerson(PersonDTO personDTO) {
		String assistype = personDTO.getAssistType();
		String ssn = personDTO.getSsn();
		String organizationId = personDTO.getOrganizationId();

		MemberBaseinfoExample exam = new MemberBaseinfoExample();
		Criteria criteria = exam.createCriteria();
		criteria.andSsnEqualTo(ssn).andFamilynoLike(organizationId + "%");

		List<String> ts = new ArrayList<String>();

		if (null == assistype || "".equals(assistype)) {
		} else {
			String t[] = assistype.split("-");
			for (int i = 0; i < t.length; i++) {
				ts.add(t[i]);
			}
			criteria.andAssistTypeIn(ts);
		}

		List<MemberBaseinfo> list = memberBaseinfoDAO
				.selectByExampleWithoutBLOBs(exam);

		if (list != null && list.size() == 1) {

			MemberBaseinfo memberBaseinfo = (MemberBaseinfo) list.get(0);

			personDTO.setAddress(memberBaseinfo.getAddress());
			personDTO.setAssistType(this.dictionaryHandle.getDictValue("004-"
					+ memberBaseinfo.getAssistType()));
			personDTO.setAssisTypeId(memberBaseinfo.getAssistType());
			personDTO.setBirthday(memberBaseinfo.getBirthday());
			personDTO.setFamilyno(memberBaseinfo.getFamilyno());
			personDTO.setLinkmode(memberBaseinfo.getLinkmode());
			personDTO.setMasterName(memberBaseinfo.getMasterName());
			personDTO.setMemberId(memberBaseinfo.getMemberId());
			personDTO.setPaperid(memberBaseinfo.getPaperid());
			personDTO.setRelmaster(memberBaseinfo.getRelmaster());
			personDTO.setRpraddress(memberBaseinfo.getRpraddress());
			personDTO.setRprkind(memberBaseinfo.getRprkind());
			personDTO.setRprtype(memberBaseinfo.getRprtype());
			personDTO.setSex(memberBaseinfo.getSex());
			personDTO.setSsn(memberBaseinfo.getSsn());
			personDTO.setMembername(memberBaseinfo.getMembername());
			personDTO.setOrganizationId(organizationId);
			return personDTO;

		} else {

			return null;
		}
	}

	public List<Icd10> findSickList() {

		Icd10Example example = new Icd10Example();

		return icd10DAO.selectByExample(example);

	}

	public List<Icd10> findSickListByCode(String code, String type) {
		List<Icd10> rs = null;
		Icd10Example example = new Icd10Example();
		try {
			if (null != code && !"".equals(code)) {
				com.medical.model.Icd10Example.Criteria criteria = example
						.createCriteria();
				criteria.andPycodeLike(code.toUpperCase() + "%");
				System.out.println("type    " + type);
				if ("11".equals(type)) {
					System.out.println("type11    " + type);
					criteria.andSalvFlagLike("11%");
				} else {

				}
				rs = icd10DAO.selectByExample(example);
				System.out.println(rs.size());
			}
		} catch (RuntimeException re) {
			re.printStackTrace();
		}
		return rs;
	}

	public DictionaryHandle getDictionaryHandle() {
		return dictionaryHandle;
	}

	/**
	 * 页面取字典
	 * 
	 */
	public String getDictValue(String dvid) {
		return this.dictionaryHandle.getDictValue(dvid);
	}

	public DmBizdeptDAO getDmBizdeptDAO() {
		return dmBizdeptDAO;
	}

	public Icd10DAO getIcd10DAO() {
		return icd10DAO;
	}

	public JzActDAO getJzActDAO() {
		return jzActDAO;
	}

	public JzBizDAO getJzBizDAO() {
		return jzBizDAO;
	}

	public JzBizHistoryDAO getJzBizHistoryDAO() {
		return jzBizHistoryDAO;
	}

	public JzPaylistDAO getJzPaylistDAO() {
		return jzPaylistDAO;
	}

	public MemberBaseinfoDAO getMemberBaseinfoDAO() {
		return memberBaseinfoDAO;
	}

	public List<OrganDTO> getOrganList(String organid) {
		List<SysTOrganization> rs = new ArrayList<SysTOrganization>();
		List<OrganDTO> orgs = new ArrayList<OrganDTO>();

		try {

			SysTOrganizationExample example = new SysTOrganizationExample();
			example.createCriteria().andParentorgidEqualTo(organid);
			example.setOrderByClause("ORGANIZATION_ID");
			rs = this.sysTOrganizationDAO.selectByExample(example);

			rs.add(0, this.sysTOrganizationDAO.selectByPrimaryKey(organid));

			for (SysTOrganization sysTOrganization : rs) {

				OrganDTO organDTO = new OrganDTO();
				organDTO.setFullname(sysTOrganization.getFullname());
				organDTO.setOrgid(sysTOrganization.getOrganizationId());
				organDTO.setOrgname(sysTOrganization.getAsorgname());
				orgs.add(organDTO);

			}

		} catch (RuntimeException re) {
			re.printStackTrace();
		}
		return orgs;
	}

	public Pager getPager() {
		return pager;
	}

	public SysTOrganizationDAO getSysTOrganizationDAO() {
		return sysTOrganizationDAO;
	}

	public List<VbizDTO> queryBizRecordBySsn(JzBizExample example,
			Integer curpage) {
		List<JzBiz> jzbizs = jzBizDAO.selectByExample(example);
		List<VbizDTO> vbizs = new ArrayList<VbizDTO>();

		pager.setAll(jzbizs.size());
		pager.setUrl("queryBizrecord.action");
		pager.setCurrentpage(curpage.intValue());
		pager.setPagesize(14);
		pager.getToolsmenu();

		for (int i = 0; i < pager.getPagesize(); i++) {
			if (pager.getStart() + i < pager.getAll()) {
				JzBiz jzBiz = jzbizs.get(pager.getStart() + i);
				VbizDTO vbizDTO = new VbizDTO();
				vbizDTO.setBizName(dictionaryHandle.getDictValue(jzBiz
						.getBizType()));
				vbizDTO.setBizType(jzBiz.getBizType());
				vbizDTO.setHospitalname(jzBiz.getHospitalName());
				vbizDTO.setRegDate(jzBiz.getRegDate());// 业务登记日期
				vbizDTO.setBeginDate(jzBiz.getBeginDate());// 业务开始时间
				vbizDTO.setDiagnoseDate(jzBiz.getDiagnoseDate());// 确诊时间
				vbizDTO.setEndDate(jzBiz.getEndDate());// 出院日期
				vbizDTO.setBizId(jzBiz.getBizId());
				vbizDTO.setSsn(jzBiz.getSsn());
				vbizDTO.setPersonpay(jzBiz.getPersonpay());
				vbizDTO.setAllmoney(jzBiz.getAllmoney());
				vbizDTO.setInsurancepay(jzBiz.getInsurancepay());
				vbizDTO.setAssistpay(jzBiz.getAssistpay());
				vbizDTO.setName(jzBiz.getName());
				vbizDTO.setIdcard(jzBiz.getIdcard());
				vbizDTO.setFamilyId(jzBiz.getFamilyId());
				vbizs.add(vbizDTO);
			}
		}
		return vbizs;
	}

	public List<VbizDTO> queryBusiByCity(JzBizExample example, Integer curpage) {

		List<JzBiz> jzbizs = jzBizDAO.selectByExample(example);
		List<VbizDTO> vbizs = new ArrayList<VbizDTO>();

		pager.setAll(jzbizs.size());
		pager.setUrl("queryBizrecord.action");
		pager.setCurrentpage(curpage.intValue());
		pager.setPagesize(14);
		pager.getToolsmenu();

		for (int i = 0; i < pager.getPagesize(); i++) {
			if (pager.getStart() + i < pager.getAll()) {
				JzBiz jzBiz = jzbizs.get(pager.getStart() + i);
				VbizDTO vbizDTO = new VbizDTO();
				vbizDTO.setBizName(dictionaryHandle.getDictValue(jzBiz
						.getBizType()));
				vbizDTO.setBizType(jzBiz.getBizType());
				vbizDTO.setHospitalname(dictionaryHandle.getDictValue(jzBiz
						.getHospitalId()));
				vbizDTO.setRegDate(jzBiz.getRegDate());// 业务登记日期
				vbizDTO.setBeginDate(jzBiz.getBeginDate());// 业务开始时间
				vbizDTO.setDiagnoseDate(jzBiz.getDiagnoseDate());// 确诊时间
				vbizDTO.setEndDate(jzBiz.getEndDate());// 出院日期
				vbizDTO.setBizId(jzBiz.getBizId());
				vbizDTO.setSsn(jzBiz.getSsn());
				vbizDTO.setPersonpay(jzBiz.getPersonpay());
				vbizDTO.setAllmoney(jzBiz.getAllmoney());
				vbizDTO.setInsurancepay(jzBiz.getInsurancepay());
				vbizDTO.setAssistpay(jzBiz.getAssistpay());
				vbizDTO.setName(jzBiz.getName());
				vbizDTO.setIdcard(jzBiz.getIdcard());
				vbizDTO.setFamilyId(jzBiz.getFamilyId());
				vbizs.add(vbizDTO);
			}
		}
		return vbizs;
	}

	public List<VbizDTO> queryDailyMedicalBiz(JzBizExample example,
			Integer curpage) {
		List<VbizDTO> vbizs = new ArrayList<VbizDTO>();
		try {
			List<JzBiz> jzbizs = jzBizDAO.selectByExample(example);
			pager.setAll(jzbizs.size());
			pager.setUrl("viewDailyMedical.action");
			pager.setCurrentpage(curpage.intValue());
			pager.setPagesize(14);
			pager.getToolsmenu();

			for (int i = 0; i < pager.getPagesize(); i++) {
				if (pager.getStart() + i < pager.getAll()) {
					JzBiz jzBiz = jzbizs.get(pager.getStart() + i);
					VbizDTO vbizDTO = new VbizDTO();
					vbizDTO.setBizName(dictionaryHandle.getDictValue(jzBiz
							.getBizType()));
					vbizDTO.setBizType(jzBiz.getBizType());
					vbizDTO.setHospitalname(jzBiz.getHospitalName());
					vbizDTO.setRegDate(jzBiz.getRegDate());// 业务登记日期
					vbizDTO.setBeginDate(jzBiz.getBeginDate());// 业务开始时间
					vbizDTO.setDiagnoseDate(jzBiz.getDiagnoseDate());// 确诊时间
					vbizDTO.setEndDate(jzBiz.getEndDate());// 出院日期
					vbizDTO.setBizId(jzBiz.getBizId());
					vbizDTO.setSsn(jzBiz.getSsn());
					vbizDTO.setPersonpay(jzBiz.getPersonpay());
					vbizDTO.setAllmoney(jzBiz.getAllmoney());
					vbizDTO.setInsurancepay(jzBiz.getInsurancepay());
					vbizDTO.setAssistpay(jzBiz.getAssistpay());
					vbizDTO.setName(jzBiz.getName());
					vbizDTO.setIdcard(jzBiz.getIdcard());
					vbizDTO.setFamilyId(jzBiz.getFamilyId());
					vbizs.add(vbizDTO);
				}
			}
		} catch (RuntimeException re) {
			re.printStackTrace();
		}
		return vbizs;
	}

	public List<VbizDTO> queryDailyMedicalBizBySsn(String ssn) {
		List<VbizDTO> vbizs = new ArrayList<VbizDTO>();
		try {
			JzBizExample example = new JzBizExample();
			example.createCriteria().andSsnEqualTo(ssn)
					.andGatherFlagEqualTo("7").andStausEqualTo("1");

			List<JzBiz> jzbizs = jzBizDAO.selectByExample(example);

			for (JzBiz jzBiz : jzbizs) {
				VbizDTO vbizDTO = new VbizDTO();

				vbizDTO.setBizName(dictionaryHandle.getDictValue(jzBiz
						.getBizType()));
				vbizDTO.setBizType(jzBiz.getBizType());
				vbizDTO.setHospitalname(dictionaryHandle.getDictValue(jzBiz
						.getHospitalId()));
				vbizDTO.setHospitalname(jzBiz.getHospitalName());
				vbizDTO.setRegDate(jzBiz.getRegDate());// 业务登记日期
				vbizDTO.setBeginDate(jzBiz.getBeginDate());// 业务开始时间
				vbizDTO.setDiagnoseDate(jzBiz.getDiagnoseDate());// 确诊时间
				vbizDTO.setEndDate(jzBiz.getEndDate());// 出院日期
				vbizDTO.setBizId(jzBiz.getBizId());
				vbizDTO.setSsn(jzBiz.getSsn());
				vbizDTO.setPersonpay(jzBiz.getPersonpay());
				vbizDTO.setAllmoney(jzBiz.getAllmoney());
				vbizDTO.setInsurancepay(jzBiz.getInsurancepay());
				vbizDTO.setAssistpay(jzBiz.getAssistpay());
				vbizs.add(vbizDTO);
			}
		} catch (RuntimeException re) {
			re.printStackTrace();
		}
		return vbizs;
	}

	public List<PersonDTO> queryDailyMedicalMember(String familyno,
			String organizationId) {
		List<PersonDTO> persons = new ArrayList<PersonDTO>();

		MemberBaseinfoExample exam = new MemberBaseinfoExample();
		Criteria criteria = exam.createCriteria();
		criteria.andFamilynoEqualTo(familyno).andFamilynoLike(
				organizationId + "%");

		List<MemberBaseinfo> rs = memberBaseinfoDAO
				.selectByExampleWithoutBLOBs(exam);
		for (MemberBaseinfo memberBaseinfo : rs) {
			PersonDTO personDTO = new PersonDTO();
			personDTO.setAddress(memberBaseinfo.getAddress());
			personDTO.setAssistType(this.dictionaryHandle.getDictValue("004-"
					+ memberBaseinfo.getAssistType()));
			personDTO.setAssisTypeId(memberBaseinfo.getAssistType());
			personDTO.setBirthday(memberBaseinfo.getBirthday());
			personDTO.setFamilyno(memberBaseinfo.getFamilyno());
			personDTO.setLinkmode(memberBaseinfo.getLinkmode());
			personDTO.setMasterName(memberBaseinfo.getMasterName());
			personDTO.setMemberId(memberBaseinfo.getMemberId());
			personDTO.setPaperid(memberBaseinfo.getPaperid());
			personDTO.setRelmaster(memberBaseinfo.getRelmaster());
			personDTO.setRpraddress(memberBaseinfo.getRpraddress());
			personDTO.setRprkind(memberBaseinfo.getRprkind());
			personDTO.setRprtype(memberBaseinfo.getRprtype());
			personDTO.setSex(memberBaseinfo.getSex());
			personDTO.setSsn(memberBaseinfo.getSsn());
			personDTO.setMembername(memberBaseinfo.getMembername());
			persons.add(personDTO);
		}

		return persons;
	}

	public List<VbizDTO> queryPreMedicalBiz(JzBizExample example,
			Integer curpage) {
		List<VbizDTO> vbizs = new ArrayList<VbizDTO>();
		try {
			List<JzBiz> jzbizs = jzBizDAO.selectByExample(example);
			pager.setAll(jzbizs.size());
			pager.setUrl("viewPreMedical.action");
			pager.setCurrentpage(curpage.intValue());
			pager.setPagesize(14);
			pager.getToolsmenu();

			for (int i = 0; i < pager.getPagesize(); i++) {
				if (pager.getStart() + i < pager.getAll()) {
					JzBiz jzBiz = jzbizs.get(pager.getStart() + i);
					VbizDTO vbizDTO = new VbizDTO();
					vbizDTO.setBizName(dictionaryHandle.getDictValue(jzBiz
							.getBizType()));
					vbizDTO.setBizType(jzBiz.getBizType());
					vbizDTO.setHospitalname(jzBiz.getHospitalName());
					vbizDTO.setRegDate(jzBiz.getRegDate());// 业务登记日期
					vbizDTO.setBeginDate(jzBiz.getBeginDate());// 业务开始时间
					vbizDTO.setDiagnoseDate(jzBiz.getDiagnoseDate());// 确诊时间
					vbizDTO.setEndDate(jzBiz.getEndDate());// 出院日期
					vbizDTO.setBizId(jzBiz.getBizId());
					vbizDTO.setSsn(jzBiz.getSsn());
					vbizDTO.setPersonpay(jzBiz.getPersonpay());
					vbizDTO.setAllmoney(jzBiz.getAllmoney());
					vbizDTO.setInsurancepay(jzBiz.getInsurancepay());
					vbizDTO.setAssistpay(jzBiz.getAssistpay());
					vbizDTO.setName(jzBiz.getName());
					vbizDTO.setIdcard(jzBiz.getIdcard());
					vbizDTO.setFamilyId(jzBiz.getFamilyId());
					vbizs.add(vbizDTO);
				}
			}
		} catch (RuntimeException re) {
			re.printStackTrace();
		}
		return vbizs;
	}

	public List<VbizDTO> queryPreMedicalBizBySsn(String ssn) {
		List<VbizDTO> vbizs = new ArrayList<VbizDTO>();
		try {
			JzBizExample example = new JzBizExample();
			example.createCriteria().andSsnEqualTo(ssn)
					.andGatherFlagEqualTo("6").andStausEqualTo("1");

			List<JzBiz> jzbizs = jzBizDAO.selectByExample(example);

			for (JzBiz jzBiz : jzbizs) {
				VbizDTO vbizDTO = new VbizDTO();

				vbizDTO.setBizName(dictionaryHandle.getDictValue(jzBiz
						.getBizType()));
				vbizDTO.setBizType(jzBiz.getBizType());
				vbizDTO.setHospitalname(dictionaryHandle.getDictValue(jzBiz
						.getHospitalId()));
				vbizDTO.setHospitalname(jzBiz.getHospitalName());
				vbizDTO.setRegDate(jzBiz.getRegDate());// 业务登记日期
				vbizDTO.setBeginDate(jzBiz.getBeginDate());// 业务开始时间
				vbizDTO.setDiagnoseDate(jzBiz.getDiagnoseDate());// 确诊时间
				vbizDTO.setEndDate(jzBiz.getEndDate());// 出院日期
				vbizDTO.setBizId(jzBiz.getBizId());
				vbizDTO.setSsn(jzBiz.getSsn());
				vbizDTO.setPersonpay(jzBiz.getPersonpay());
				vbizDTO.setAllmoney(jzBiz.getAllmoney());
				vbizDTO.setInsurancepay(jzBiz.getInsurancepay());
				vbizDTO.setAssistpay(jzBiz.getAssistpay());
				vbizs.add(vbizDTO);
			}
		} catch (RuntimeException re) {
			re.printStackTrace();
		}
		return vbizs;
	}

	public List<PersonDTO> queryPreMedicalMember(String familyno,
			String organizationId) {
		List<PersonDTO> persons = new ArrayList<PersonDTO>();

		MemberBaseinfoExample exam = new MemberBaseinfoExample();
		Criteria criteria = exam.createCriteria();
		criteria.andFamilynoEqualTo(familyno).andFamilynoLike(
				organizationId + "%");

		List<MemberBaseinfo> rs = memberBaseinfoDAO
				.selectByExampleWithoutBLOBs(exam);
		for (MemberBaseinfo memberBaseinfo : rs) {
			PersonDTO personDTO = new PersonDTO();
			personDTO.setAddress(memberBaseinfo.getAddress());
			personDTO.setAssistType(this.dictionaryHandle.getDictValue("004-"
					+ memberBaseinfo.getAssistType()));
			personDTO.setAssisTypeId(memberBaseinfo.getAssistType());
			personDTO.setBirthday(memberBaseinfo.getBirthday());
			personDTO.setFamilyno(memberBaseinfo.getFamilyno());
			personDTO.setLinkmode(memberBaseinfo.getLinkmode());
			personDTO.setMasterName(memberBaseinfo.getMasterName());
			personDTO.setMemberId(memberBaseinfo.getMemberId());
			personDTO.setPaperid(memberBaseinfo.getPaperid());
			personDTO.setRelmaster(memberBaseinfo.getRelmaster());
			personDTO.setRpraddress(memberBaseinfo.getRpraddress());
			personDTO.setRprkind(memberBaseinfo.getRprkind());
			personDTO.setRprtype(memberBaseinfo.getRprtype());
			personDTO.setSex(memberBaseinfo.getSex());
			personDTO.setSsn(memberBaseinfo.getSsn());
			personDTO.setMembername(memberBaseinfo.getMembername());
			persons.add(personDTO);
		}

		return persons;
	}

	@SuppressWarnings("deprecation")
	public void removeBiz(String bizId) {
		JzBiz record = new JzBiz();
		record = jzBizDAO.selectByPrimaryKey(new Integer(bizId));
		boolean flag = false;
		if ("1".equals(record.getStaus())) {
			record.setStaus("0");
			JzBizExample example = new JzBizExample();
			example.createCriteria().andBizIdEqualTo(new Integer(bizId));
			jzBizDAO.updateByExampleSelective(record, example);
			flag = true;
		}
		int b = 0;
		if (null != record && flag) {

			if ("11".equals(record.getBizType())) {

			} else {
				b = 1;
			}

			MemberBaseinfoExample memberBaseinfoExample = new MemberBaseinfoExample();
			memberBaseinfoExample.createCriteria().andSsnEqualTo(
					record.getSsn());
			List<MemberBaseinfo> ms = this.memberBaseinfoDAO
					.selectByExampleWithoutBLOBs(memberBaseinfoExample);
			if (null != ms && ms.size() > 0) {

				MemberBaseinfo member = ms.get(0);

				JzActExample jzactexample = new JzActExample();
				JzAct act = new JzAct();
				short yyear = new BigDecimal(record.getEndDate().getYear())
						.add(new BigDecimal(1900)).shortValue();
				jzactexample.createCriteria()
						.andMemberIdEqualTo(member.getMemberId())
						.andActYearEqualTo(yyear);
				List<JzAct> jzActs = this.jzActDAO
						.selectByExample(jzactexample);
				if (null != jzActs && jzActs.size() > 0) {
					act = jzActs.get(0);
					int c = act.getActBizTimes().intValue();
					c = c - 1;
					act.setActBizTimes(new Short((short) c));

					int d = act.getActBizInhospitalTimes();
					d = d - b;
					act.setActBizInhospitalTimes((short) d);
					BigDecimal m = act.getActBizMoney().subtract(
							record.getAssistpay());
					act.setActBizMoney(m);
					jzActDAO.updateByPrimaryKeySelective(act);
				}
			}
		}
	}

	@SuppressWarnings("deprecation")
	public void saveBizrecord(BusinessDTO businessDTO) {
		JzBiz record = new JzBiz();

		record.setHospitalId("H9999");
		record.setHospitalName(businessDTO.getHospital());
		record.setBeginDate(businessDTO.getBegintime());
		record.setEndDate(businessDTO.getEndtime());
		record.setRegDate(businessDTO.getCurrenttime());
		int b = 0;
		if ("12".equals(businessDTO.getBizType())) {
			record.setBizType("12");
			record.setBizName(this.dictionaryHandle.getDictValue(record
					.getBizType()));
			record.setTreatmentType("120");
			record.setTreatmentName("普通住院");
			b = 1;
		} else if ("11".equals(businessDTO.getBizType())) {
			record.setBizType("11");
			record.setBizName(this.dictionaryHandle.getDictValue(record
					.getBizType()));
			record.setTreatmentType("110");
			record.setTreatmentName("普通门诊");
		}
		record.setGatherFlag("5");
		record.setStaus("1");
		record.setSsn(businessDTO.getSsn());
		record.setFamilyId(businessDTO.getFamilyno());
		record.setOperuid(new BigDecimal(businessDTO.getEmpid()));

		MemberBaseinfoExample example = new MemberBaseinfoExample();
		example.createCriteria().andSsnEqualTo(businessDTO.getSsn())
				.andFamilynoEqualTo(businessDTO.getFamilyno());
		MemberBaseinfo memberBaseinfo = (MemberBaseinfo) memberBaseinfoDAO
				.selectByExampleWithoutBLOBs(example).get(0);

		record.setName(memberBaseinfo.getMembername());
		record.setBirthday(memberBaseinfo.getBirthday());
		record.setIdcard(memberBaseinfo.getPaperid());
		if ("男".equals(memberBaseinfo.getSex())) {
			record.setSex("1");
		} else {
			record.setSex("0");
		}

		String sickname = businessDTO.getSickname();

		// String inDiseaseName = sickname.substring(sickname.indexOf("(") + 1,
		// sickname.lastIndexOf(")"));
		record.setInDiseaseName(sickname);
		// record.setInDisease(businessDTO.getSicknameKey());
		// record.setDiagnose(this.dictionaryHandle.getDictValue("003-"
		// + record.getInDisease()));
		record.setDiagnoseName(sickname);

		record.setAssistType(businessDTO.getMedicaltype());
		record.setInDays(new BigDecimal(businessDTO.getDays()).shortValue());
		record.setAllmoney(new BigDecimal(businessDTO.getHospitalmoney()));
		record.setAssistpay(new BigDecimal(businessDTO.getZ01()));
		record.setInsurancepay(new BigDecimal(businessDTO.getPlanpayment()));
		record.setPersonpay(new BigDecimal(businessDTO.getAllmoney()));
		BigDecimal aa = new BigDecimal(businessDTO.getAllmoney())
				.subtract(new BigDecimal(businessDTO.getZ01()));
		aa = aa.subtract(new BigDecimal(businessDTO.getPlanpayment()));

		record.setPersonpay(aa);
		record.setOutFlag(new Short((short) 1));
		record.setAlreadyGet(new Short((short) 1));
		record.setCheckState(new BigDecimal(1));
		record.setOpertime(new Date());
		int id = this.jzBizDAO.insert(record);
		Integer bizId = new Integer(id);

		JzPaylist pay = new JzPaylist();
		pay.setBizId(bizId);
		pay.setFuncId("114");
		pay.setRealPay(new BigDecimal(businessDTO.getPredeposit()));
		pay.setFundName("预交住院押金");
		pay.setSumFlag("1");
		this.jzPaylistDAO.insert(pay);

		pay.setBizId(bizId);
		pay.setFuncId("112");
		pay.setRealPay(new BigDecimal(businessDTO.getInitline()));
		pay.setFundName("起付线");
		pay.setSumFlag("1");
		this.jzPaylistDAO.insert(pay);

		pay.setBizId(bizId);
		pay.setFuncId("116");
		pay.setRealPay(new BigDecimal(businessDTO.getSelfall()));
		pay.setFundName("自付合计");
		pay.setSumFlag("1");
		this.jzPaylistDAO.insert(pay);

		pay.setBizId(bizId);
		pay.setFuncId("111");
		pay.setRealPay(new BigDecimal(businessDTO.getHospitalmoney()));
		pay.setFundName("住院费用总额");
		pay.setSumFlag("1");
		this.jzPaylistDAO.insert(pay);

		pay.setBizId(bizId);
		pay.setFuncId("003");
		pay.setRealPay(new BigDecimal(businessDTO.getAccountpayment()));
		pay.setFundName("个人账户支付");
		pay.setSumFlag("1");
		this.jzPaylistDAO.insert(pay);

		pay.setBizId(bizId);
		pay.setFuncId("001");
		pay.setRealPay(new BigDecimal(businessDTO.getPlanpayment()));
		pay.setFundName("统筹基金支付");
		pay.setSumFlag("1");
		this.jzPaylistDAO.insert(pay);

		pay.setBizId(bizId);
		pay.setFuncId("301");
		pay.setRealPay(new BigDecimal(businessDTO.getOfficialpayment()));
		pay.setFundName("公务员补基本医疗部分");
		pay.setSumFlag("1");
		this.jzPaylistDAO.insert(pay);

		pay.setBizId(bizId);
		pay.setFuncId("201");
		pay.setRealPay(new BigDecimal(businessDTO.getBigpayment()));
		pay.setFundName("大额保险支付");
		pay.setSumFlag("1");
		this.jzPaylistDAO.insert(pay);

		pay.setBizId(bizId);
		pay.setFuncId("202");
		pay.setRealPay(new BigDecimal(businessDTO.getRestpayment()));
		pay.setFundName("离休统筹支付");
		pay.setSumFlag("1");
		this.jzPaylistDAO.insert(pay);

		pay.setBizId(bizId);
		pay.setFuncId("501");
		pay.setRealPay(new BigDecimal(businessDTO.getInjurypayment()));
		pay.setFundName("工伤保险支付");
		pay.setSumFlag("1");
		this.jzPaylistDAO.insert(pay);

		pay.setBizId(bizId);
		pay.setFuncId("701");
		pay.setRealPay(new BigDecimal(businessDTO.getBearpayment()));
		pay.setFundName("生育保险支付");
		pay.setSumFlag("1");
		this.jzPaylistDAO.insert(pay);

		pay.setBizId(bizId);
		pay.setFuncId("999");
		pay.setRealPay(new BigDecimal(businessDTO.getAllmoney()));
		pay.setFundName("现金支付总额");
		pay.setSumFlag("1");
		this.jzPaylistDAO.insert(pay);

		pay.setBizId(bizId);
		pay.setFuncId("113");
		pay.setRealPay(new BigDecimal(businessDTO.getAccountbalance()));
		pay.setFundName("个人账户余额");
		pay.setSumFlag("1");
		this.jzPaylistDAO.insert(pay);

		pay.setBizId(bizId);
		pay.setFuncId("117");
		pay.setRealPay(new BigDecimal(0));
		pay.setFundName("超商保费用");
		pay.setSumFlag("1");
		this.jzPaylistDAO.insert(pay);

		pay.setBizId(bizId);
		pay.setFuncId("117");
		pay.setRealPay(new BigDecimal(0));
		pay.setFundName("超商保费用");
		pay.setSumFlag("1");
		this.jzPaylistDAO.insert(pay);

		pay.setBizId(bizId);
		pay.setFuncId("Z01");
		pay.setRealPay(new BigDecimal(businessDTO.getZ01()));
		pay.setFundName("救助金额");
		pay.setSumFlag("1");
		this.jzPaylistDAO.insert(pay);

		pay.setBizId(bizId);
		pay.setFuncId("Z02");
		pay.setRealPay(aa);
		pay.setFundName("实际支付");
		pay.setSumFlag("1");
		this.jzPaylistDAO.insert(pay);

		JzBizHistory history = new JzBizHistory();
		history.setAssistmoney(new BigDecimal(businessDTO.getZ01()));
		history.setTotalmoney(new BigDecimal(businessDTO.getHospitalmoney()));
		history.setInsurancepay(new BigDecimal(businessDTO.getPlanpayment()));
		history.setRealpay(aa);
		history.setOpertime(new Date());
		history.setOpertype(new BigDecimal(1));
		history.setBizId(new BigDecimal(bizId));
		history.setPersonacc(new BigDecimal(0));
		history.setOperuid(new BigDecimal(businessDTO.getEmpid()));
		this.jzBizHistoryDAO.insertSelective(history);

		JzActExample jzactexample = new JzActExample();
		JzAct act = new JzAct();
		short yyear = new BigDecimal(businessDTO.getEndtime().getYear()).add(
				new BigDecimal(1900)).shortValue();
		jzactexample.createCriteria()
				.andMemberIdEqualTo(businessDTO.getMemberId())
				.andActYearEqualTo(yyear);
		List<JzAct> jzActs = this.jzActDAO.selectByExample(jzactexample);
		if (null != jzActs && jzActs.size() > 0) {
			act = jzActs.get(0);
			int c = act.getActBizTimes().intValue();
			c = c + 1;
			act.setActBizTimes(new Short((short) c));
			int d = act.getActBizInhospitalTimes();
			d = d + b;
			act.setActBizInhospitalTimes((short) d);
			BigDecimal m = act.getActBizMoney().add(
					new BigDecimal(businessDTO.getZ01()));
			act.setActBizMoney(m);
			jzActDAO.updateByPrimaryKeySelective(act);
		} else {
			act.setActBizTimes(new Short((short) 1));
			act.setActBizMoney(new BigDecimal(businessDTO.getZ01()));
			act.setMemberId(businessDTO.getMemberId());
			act.setActYear(new Short((short) yyear));
			act.setActBizInhospitalTimes((short) b);
			jzActDAO.insertSelective(act);
		}

	}

	@SuppressWarnings("deprecation")
	public void saveDailyMedical(BusinessDTO businessDTO) {
		JzBiz record = new JzBiz();

		record.setHospitalId(businessDTO.getHospital());
		record.setHospitalName(this.dictionaryHandle.getDictValue(businessDTO
				.getHospital()));
		record.setBeginDate(new Date());
		record.setEndDate(new Date());
		record.setRegDate(new Date());

		if ("12".equals(businessDTO.getBizType())) {
			record.setBizType("12");
			record.setBizName(this.dictionaryHandle.getDictValue(record
					.getBizType()));
			record.setTreatmentType("120");
			record.setTreatmentName("普通住院");
		} else if ("11".equals(businessDTO.getBizType())) {
			record.setBizType("11");
			record.setBizName(this.dictionaryHandle.getDictValue(record
					.getBizType()));
			record.setTreatmentType("110");
			record.setTreatmentName("普通门诊");
		}
		record.setGatherFlag("7");
		record.setStaus("1");
		record.setSsn(businessDTO.getSsn());
		record.setFamilyId(businessDTO.getFamilyno());
		record.setOperuid(new BigDecimal(businessDTO.getEmpid()));

		MemberBaseinfoExample example = new MemberBaseinfoExample();
		example.createCriteria().andSsnEqualTo(businessDTO.getSsn());
		MemberBaseinfo memberBaseinfo = (MemberBaseinfo) memberBaseinfoDAO
				.selectByExampleWithoutBLOBs(example).get(0);

		record.setName(memberBaseinfo.getMembername());
		record.setBirthday(memberBaseinfo.getBirthday());
		record.setIdcard(memberBaseinfo.getPaperid());
		if ("男".equals(memberBaseinfo.getSex())) {
			record.setSex("1");
		} else {
			record.setSex("0");
		}

		String sickname = businessDTO.getSickname();
		record.setInDiseaseName(sickname);
		record.setDiagnoseName(sickname);

		record.setAssistType(businessDTO.getMedicaltype());
		record.setInDays(new BigDecimal(0).shortValue());
		record.setAllmoney(new BigDecimal(businessDTO.getZ01()));
		record.setAssistpay(new BigDecimal(businessDTO.getZ01()));
		record.setInsurancepay(new BigDecimal(0));
		record.setPersonpay(new BigDecimal(0));
		record.setOutFlag(new Short((short) 1));
		record.setAlreadyGet(new Short((short) 1));
		record.setCheckState(new BigDecimal(1));
		record.setOpertime(new Date());
		int id = this.jzBizDAO.insert(record);
		Integer bizId = new Integer(id);

		JzPaylist pay = new JzPaylist();
		pay.setBizId(bizId);
		pay.setFuncId("114");
		pay.setRealPay(new BigDecimal(0));
		pay.setFundName("预交住院押金");
		pay.setSumFlag("1");
		this.jzPaylistDAO.insert(pay);

		pay.setBizId(bizId);
		pay.setFuncId("112");
		pay.setRealPay(new BigDecimal(0));
		pay.setFundName("起付线");
		pay.setSumFlag("1");
		this.jzPaylistDAO.insert(pay);

		pay.setBizId(bizId);
		pay.setFuncId("116");
		pay.setRealPay(new BigDecimal(0));
		pay.setFundName("自付合计");
		pay.setSumFlag("1");
		this.jzPaylistDAO.insert(pay);

		pay.setBizId(bizId);
		pay.setFuncId("111");
		pay.setRealPay(new BigDecimal(0));
		pay.setFundName("住院费用总额");
		pay.setSumFlag("1");
		this.jzPaylistDAO.insert(pay);

		pay.setBizId(bizId);
		pay.setFuncId("003");
		pay.setRealPay(new BigDecimal(0));
		pay.setFundName("个人账户支付");
		pay.setSumFlag("1");
		this.jzPaylistDAO.insert(pay);

		pay.setBizId(bizId);
		pay.setFuncId("001");
		pay.setRealPay(new BigDecimal(0));
		pay.setFundName("统筹基金支付");
		pay.setSumFlag("1");
		this.jzPaylistDAO.insert(pay);

		pay.setBizId(bizId);
		pay.setFuncId("301");
		pay.setRealPay(new BigDecimal(0));
		pay.setFundName("公务员补基本医疗部分");
		pay.setSumFlag("1");
		this.jzPaylistDAO.insert(pay);

		pay.setBizId(bizId);
		pay.setFuncId("201");
		pay.setRealPay(new BigDecimal(0));
		pay.setFundName("大额保险支付");
		pay.setSumFlag("1");
		this.jzPaylistDAO.insert(pay);

		pay.setBizId(bizId);
		pay.setFuncId("202");
		pay.setRealPay(new BigDecimal(0));
		pay.setFundName("离休统筹支付");
		pay.setSumFlag("1");
		this.jzPaylistDAO.insert(pay);

		pay.setBizId(bizId);
		pay.setFuncId("501");
		pay.setRealPay(new BigDecimal(0));
		pay.setFundName("工伤保险支付");
		pay.setSumFlag("1");
		this.jzPaylistDAO.insert(pay);

		pay.setBizId(bizId);
		pay.setFuncId("701");
		pay.setRealPay(new BigDecimal(0));
		pay.setFundName("生育保险支付");
		pay.setSumFlag("1");
		this.jzPaylistDAO.insert(pay);

		pay.setBizId(bizId);
		pay.setFuncId("999");
		pay.setRealPay(new BigDecimal(businessDTO.getZ01()));
		pay.setFundName("现金支付总额");
		pay.setSumFlag("1");
		this.jzPaylistDAO.insert(pay);

		pay.setBizId(bizId);
		pay.setFuncId("113");
		pay.setRealPay(new BigDecimal(0));
		pay.setFundName("个人账户余额");
		pay.setSumFlag("1");
		this.jzPaylistDAO.insert(pay);

		pay.setBizId(bizId);
		pay.setFuncId("117");
		pay.setRealPay(new BigDecimal(0));
		pay.setFundName("超商保费用");
		pay.setSumFlag("1");
		this.jzPaylistDAO.insert(pay);

		JzBizHistory history = new JzBizHistory();
		history.setAssistmoney(new BigDecimal(businessDTO.getZ01()));
		history.setTotalmoney(new BigDecimal(businessDTO.getZ01()));
		history.setInsurancepay(new BigDecimal(0));
		history.setTotalmoney(new BigDecimal(businessDTO.getZ01()));
		history.setRealpay(new BigDecimal(businessDTO.getZ01()));
		history.setOpertime(new Date());
		history.setOpertype(new BigDecimal(1));
		history.setBizId(new BigDecimal(bizId));
		history.setPersonacc(new BigDecimal(0));
		history.setOperuid(new BigDecimal(businessDTO.getEmpid()));
		this.jzBizHistoryDAO.insertSelective(history);

		JzActExample jzactexample = new JzActExample();
		JzAct act = new JzAct();
		short yyear = new BigDecimal(new Date().getYear()).add(
				new BigDecimal(1900)).shortValue();
		jzactexample.createCriteria()
				.andMemberIdEqualTo(businessDTO.getMemberId())
				.andActYearEqualTo(yyear);
		List<JzAct> jzActs = this.jzActDAO.selectByExample(jzactexample);
		if (null != jzActs && jzActs.size() > 0) {
			act = jzActs.get(0);
			int c = act.getActBizTimes().intValue();
			c = c + 1;
			act.setActBizTimes(new Short((short) c));
			BigDecimal m = act.getActBizMoney().add(
					new BigDecimal(businessDTO.getZ01()));
			act.setActBizMoney(m);
			jzActDAO.updateByPrimaryKeySelective(act);
		} else {
			act.setActBizTimes(new Short((short) 1));
			act.setActBizMoney(new BigDecimal(businessDTO.getZ01()));
			act.setMemberId(businessDTO.getMemberId());
			act.setActYear(new Short((short) yyear));
			jzActDAO.insertSelective(act);
		}

	}

	public void saveHospitalForCountry(BusinessDTO businessDTO) {

	}

	public void saveHospitalForTown(BusinessDTO businessDTO) {
		JzBiz record = new JzBiz();

		record.setHospitalId(businessDTO.getHospital());
		record.setBeginDate(businessDTO.getBegintime());
		record.setEndDate(businessDTO.getEndtime());
		record.setRegDate(businessDTO.getCurrenttime());
		record.setBizType("12");
		record.setBizName(this.dictionaryHandle.getDictValue(record
				.getBizType()));
		record.setTreatmentType("120");
		record.setTreatmentName("普通住院");
		record.setGatherFlag("0");
		record.setStaus("1");
		record.setSsn(businessDTO.getSsn());
		record.setFamilyId(businessDTO.getFamilyno());
		record.setOperuid(new BigDecimal(businessDTO.getEmpid()));

		MemberBaseinfoExample example = new MemberBaseinfoExample();
		example.createCriteria().andSsnEqualTo(businessDTO.getSsn())
				.andFamilynoEqualTo(businessDTO.getFamilyno());
		MemberBaseinfo memberBaseinfo = (MemberBaseinfo) memberBaseinfoDAO
				.selectByExampleWithoutBLOBs(example).get(0);

		record.setName(memberBaseinfo.getMembername());
		record.setBirthday(memberBaseinfo.getBirthday());
		record.setIdcard(memberBaseinfo.getPaperid());
		if ("男".equals(memberBaseinfo.getSex())) {
			record.setSex("1");
		} else {
			record.setSex("0");
		}

		String sickname = businessDTO.getSickname();

		String inDiseaseName = sickname.substring(sickname.indexOf("(") + 1,
				sickname.lastIndexOf(")"));
		record.setInDiseaseName(inDiseaseName);
		record.setInDisease(businessDTO.getSicknameKey());

		record.setDiagnose(businessDTO.getSicknameKey());
		record.setDiagnoseName(inDiseaseName);

		record.setAssistType(businessDTO.getMedicaltype());
		record.setInDays(new BigDecimal(businessDTO.getDays()).shortValue());

		int id = this.jzBizDAO.insert(record);
		Integer bizId = new Integer(id);

		JzPaylist pay = new JzPaylist();
		pay.setBizId(bizId);
		pay.setFuncId("114");
		pay.setRealPay(new BigDecimal(businessDTO.getPredeposit()));
		pay.setFundName("预交住院押金");
		pay.setSumFlag("1");
		this.jzPaylistDAO.insert(pay);

		pay.setBizId(bizId);
		pay.setFuncId("112");
		pay.setRealPay(new BigDecimal(businessDTO.getInitline()));
		pay.setFundName("起付线");
		pay.setSumFlag("1");
		this.jzPaylistDAO.insert(pay);

		pay.setBizId(bizId);
		pay.setFuncId("116");
		pay.setRealPay(new BigDecimal(businessDTO.getSelfall()));
		pay.setFundName("自付合计");
		pay.setSumFlag("1");
		this.jzPaylistDAO.insert(pay);

		pay.setBizId(bizId);
		pay.setFuncId("111");
		pay.setRealPay(new BigDecimal(businessDTO.getHospitalmoney()));
		pay.setFundName("住院费用总额");
		pay.setSumFlag("1");
		this.jzPaylistDAO.insert(pay);

		pay.setBizId(bizId);
		pay.setFuncId("003");
		pay.setRealPay(new BigDecimal(businessDTO.getAccountpayment()));
		pay.setFundName("个人账户支付");
		pay.setSumFlag("1");
		this.jzPaylistDAO.insert(pay);

		pay.setBizId(bizId);
		pay.setFuncId("001");
		pay.setRealPay(new BigDecimal(businessDTO.getPlanpayment()));
		pay.setFundName("统筹基金支付");
		pay.setSumFlag("1");
		this.jzPaylistDAO.insert(pay);

		pay.setBizId(bizId);
		pay.setFuncId("301");
		pay.setRealPay(new BigDecimal(businessDTO.getOfficialpayment()));
		pay.setFundName("公务员补基本医疗部分");
		pay.setSumFlag("1");
		this.jzPaylistDAO.insert(pay);

		pay.setBizId(bizId);
		pay.setFuncId("201");
		pay.setRealPay(new BigDecimal(businessDTO.getBigpayment()));
		pay.setFundName("大额保险支付");
		pay.setSumFlag("1");
		this.jzPaylistDAO.insert(pay);

		pay.setBizId(bizId);
		pay.setFuncId("202");
		pay.setRealPay(new BigDecimal(businessDTO.getRestpayment()));
		pay.setFundName("离休统筹支付");
		pay.setSumFlag("1");
		this.jzPaylistDAO.insert(pay);

		pay.setBizId(bizId);
		pay.setFuncId("501");
		pay.setRealPay(new BigDecimal(businessDTO.getInjurypayment()));
		pay.setFundName("工伤保险支付");
		pay.setSumFlag("1");
		this.jzPaylistDAO.insert(pay);

		pay.setBizId(bizId);
		pay.setFuncId("701");
		pay.setRealPay(new BigDecimal(businessDTO.getBearpayment()));
		pay.setFundName("生育保险支付");
		pay.setSumFlag("1");
		this.jzPaylistDAO.insert(pay);

		pay.setBizId(bizId);
		pay.setFuncId("999");
		pay.setRealPay(new BigDecimal(businessDTO.getAllmoney()));
		pay.setFundName("现金支付总额");
		pay.setSumFlag("1");
		this.jzPaylistDAO.insert(pay);

		pay.setBizId(bizId);
		pay.setFuncId("113");
		pay.setRealPay(new BigDecimal(businessDTO.getAccountbalance()));
		pay.setFundName("个人账户余额");
		pay.setSumFlag("1");
		this.jzPaylistDAO.insert(pay);

		pay.setBizId(bizId);
		pay.setFuncId("117");
		pay.setRealPay(new BigDecimal(0));
		pay.setFundName("超商保费用");
		pay.setSumFlag("1");
		this.jzPaylistDAO.insert(pay);
	}

	public void saveOutpatientForCountry(BusinessDTO businessDTO) {

	}

	public void saveOutpatientForTown(BusinessDTO businessDTO) {

		JzBiz record = new JzBiz();

		record.setHospitalId(businessDTO.getHospital());
		record.setBeginDate(businessDTO.getBegintime());
		record.setEndDate(businessDTO.getEndtime());
		record.setRegDate(businessDTO.getCurrenttime());
		record.setBizType("11");
		record.setBizName(this.dictionaryHandle.getDictValue(record
				.getBizType()));
		record.setTreatmentType("110");
		record.setTreatmentName("普通门诊");
		record.setGatherFlag("0");
		record.setStaus("1");
		record.setSsn(businessDTO.getSsn());
		record.setFamilyId(businessDTO.getFamilyno());
		record.setOperuid(new BigDecimal(businessDTO.getEmpid()));

		MemberBaseinfoExample example = new MemberBaseinfoExample();
		example.createCriteria().andSsnEqualTo(businessDTO.getSsn())
				.andFamilynoEqualTo(businessDTO.getFamilyno());
		MemberBaseinfo memberBaseinfo = (MemberBaseinfo) memberBaseinfoDAO
				.selectByExampleWithoutBLOBs(example).get(0);

		record.setName(memberBaseinfo.getMembername());
		record.setBirthday(memberBaseinfo.getBirthday());
		record.setIdcard(memberBaseinfo.getPaperid());
		if ("男".equals(memberBaseinfo.getSex())) {
			record.setSex("1");
		} else {
			record.setSex("0");
		}

		String sickname = businessDTO.getSickname();

		String inDiseaseName = sickname.substring(sickname.indexOf("(") + 1,
				sickname.lastIndexOf(")"));
		record.setInDiseaseName(inDiseaseName);
		record.setInDisease(businessDTO.getSicknameKey());
		record.setDiagnose(this.dictionaryHandle.getDictValue("003-"
				+ record.getInDisease()));
		record.setDiagnoseName(inDiseaseName);

		record.setAssistType(businessDTO.getMedicaltype());
		record.setInDays(new BigDecimal(businessDTO.getDays()).shortValue());

		int id = this.jzBizDAO.insert(record);
		Integer bizId = new Integer(id);

		System.out.println("bizId=" + id);

		JzPaylist pay = new JzPaylist();
		pay.setBizId(bizId);
		pay.setFuncId("114");
		pay.setRealPay(new BigDecimal(businessDTO.getPredeposit()));
		pay.setFundName("预交住院押金");
		pay.setSumFlag("1");
		this.jzPaylistDAO.insert(pay);

		pay.setBizId(bizId);
		pay.setFuncId("112");
		pay.setRealPay(new BigDecimal(businessDTO.getInitline()));
		pay.setFundName("起付线");
		pay.setSumFlag("1");
		this.jzPaylistDAO.insert(pay);

		pay.setBizId(bizId);
		pay.setFuncId("116");
		pay.setRealPay(new BigDecimal(businessDTO.getSelfall()));
		pay.setFundName("自付合计");
		pay.setSumFlag("1");
		this.jzPaylistDAO.insert(pay);

		pay.setBizId(bizId);
		pay.setFuncId("111");
		pay.setRealPay(new BigDecimal(businessDTO.getHospitalmoney()));
		pay.setFundName("住院费用总额");
		pay.setSumFlag("1");
		this.jzPaylistDAO.insert(pay);

		pay.setBizId(bizId);
		pay.setFuncId("003");
		pay.setRealPay(new BigDecimal(businessDTO.getAccountpayment()));
		pay.setFundName("个人账户支付");
		pay.setSumFlag("1");
		this.jzPaylistDAO.insert(pay);

		pay.setBizId(bizId);
		pay.setFuncId("001");
		pay.setRealPay(new BigDecimal(businessDTO.getPlanpayment()));
		pay.setFundName("统筹基金支付");
		pay.setSumFlag("1");
		this.jzPaylistDAO.insert(pay);

		pay.setBizId(bizId);
		pay.setFuncId("301");
		pay.setRealPay(new BigDecimal(businessDTO.getOfficialpayment()));
		pay.setFundName("公务员补基本医疗部分");
		pay.setSumFlag("1");
		this.jzPaylistDAO.insert(pay);

		pay.setBizId(bizId);
		pay.setFuncId("201");
		pay.setRealPay(new BigDecimal(businessDTO.getBigpayment()));
		pay.setFundName("大额保险支付");
		pay.setSumFlag("1");
		this.jzPaylistDAO.insert(pay);

		pay.setBizId(bizId);
		pay.setFuncId("202");
		pay.setRealPay(new BigDecimal(businessDTO.getRestpayment()));
		pay.setFundName("离休统筹支付");
		pay.setSumFlag("1");
		this.jzPaylistDAO.insert(pay);

		pay.setBizId(bizId);
		pay.setFuncId("501");
		pay.setRealPay(new BigDecimal(businessDTO.getInjurypayment()));
		pay.setFundName("工伤保险支付");
		pay.setSumFlag("1");
		this.jzPaylistDAO.insert(pay);

		pay.setBizId(bizId);
		pay.setFuncId("701");
		pay.setRealPay(new BigDecimal(businessDTO.getBearpayment()));
		pay.setFundName("生育保险支付");
		pay.setSumFlag("1");
		this.jzPaylistDAO.insert(pay);

		pay.setBizId(bizId);
		pay.setFuncId("999");
		pay.setRealPay(new BigDecimal(businessDTO.getAllmoney()));
		pay.setFundName("现金支付总额");
		pay.setSumFlag("1");
		this.jzPaylistDAO.insert(pay);

		pay.setBizId(bizId);
		pay.setFuncId("113");
		pay.setRealPay(new BigDecimal(businessDTO.getAccountbalance()));
		pay.setFundName("个人账户余额");
		pay.setSumFlag("1");
		this.jzPaylistDAO.insert(pay);

		pay.setBizId(bizId);
		pay.setFuncId("117");
		pay.setRealPay(new BigDecimal(0));
		pay.setFundName("超商保费用");
		pay.setSumFlag("1");
		this.jzPaylistDAO.insert(pay);
	}

	@SuppressWarnings("deprecation")
	public void savePreMedical(BusinessDTO businessDTO) {

		JzBiz record = new JzBiz();

		record.setHospitalId(businessDTO.getHospital());
		record.setHospitalName(this.dictionaryHandle.getDictValue(businessDTO
				.getHospital()));
		record.setBeginDate(new Date());
		record.setEndDate(new Date());
		record.setRegDate(new Date());
		int b = 0;
		if ("12".equals(businessDTO.getBizType())) {
			record.setBizType("12");
			record.setBizName(this.dictionaryHandle.getDictValue(record
					.getBizType()));
			record.setTreatmentType("120");
			record.setTreatmentName("普通住院");
			b = 1;
		} else if ("11".equals(businessDTO.getBizType())) {
			record.setBizType("11");
			record.setBizName(this.dictionaryHandle.getDictValue(record
					.getBizType()));
			record.setTreatmentType("110");
			record.setTreatmentName("普通门诊");
		}
		record.setGatherFlag("6");
		record.setStaus("1");
		record.setSsn(businessDTO.getSsn());
		record.setFamilyId(businessDTO.getFamilyno());
		record.setOperuid(new BigDecimal(businessDTO.getEmpid()));

		MemberBaseinfoExample example = new MemberBaseinfoExample();
		example.createCriteria().andSsnEqualTo(businessDTO.getSsn());
		MemberBaseinfo memberBaseinfo = (MemberBaseinfo) memberBaseinfoDAO
				.selectByExampleWithoutBLOBs(example).get(0);

		record.setName(memberBaseinfo.getMembername());
		record.setBirthday(memberBaseinfo.getBirthday());
		record.setIdcard(memberBaseinfo.getPaperid());
		if ("男".equals(memberBaseinfo.getSex())) {
			record.setSex("1");
		} else {
			record.setSex("0");
		}

		String sickname = businessDTO.getSickname();
		record.setInDiseaseName(sickname);
		record.setDiagnoseName(sickname);

		record.setAssistType(businessDTO.getMedicaltype());
		record.setInDays(new BigDecimal(0).shortValue());
		String mm = businessDTO.getHospitalmoney();
		if (null == mm || "".equals(mm)) {
			mm = "0";
		}
		record.setAllmoney(new BigDecimal(mm));
		record.setAssistpay(new BigDecimal(mm));
		record.setInsurancepay(new BigDecimal(businessDTO.getZ01()));
		record.setPersonpay(new BigDecimal(0));
		record.setOutFlag(new Short((short) 1));
		record.setAlreadyGet(new Short((short) 1));
		record.setCheckState(new BigDecimal(1));
		record.setOpertime(new Date());
		int id = this.jzBizDAO.insert(record);
		Integer bizId = new Integer(id);

		JzPaylist pay = new JzPaylist();
		pay.setBizId(bizId);
		pay.setFuncId("114");
		pay.setRealPay(new BigDecimal(0));
		pay.setFundName("预交住院押金");
		pay.setSumFlag("1");
		this.jzPaylistDAO.insert(pay);

		pay.setBizId(bizId);
		pay.setFuncId("112");
		pay.setRealPay(new BigDecimal(0));
		pay.setFundName("起付线");
		pay.setSumFlag("1");
		this.jzPaylistDAO.insert(pay);

		pay.setBizId(bizId);
		pay.setFuncId("116");
		pay.setRealPay(new BigDecimal(0));
		pay.setFundName("自付合计");
		pay.setSumFlag("1");
		this.jzPaylistDAO.insert(pay);

		pay.setBizId(bizId);
		pay.setFuncId("111");
		pay.setRealPay(new BigDecimal(0));
		pay.setFundName("住院费用总额");
		pay.setSumFlag("1");
		this.jzPaylistDAO.insert(pay);

		pay.setBizId(bizId);
		pay.setFuncId("003");
		pay.setRealPay(new BigDecimal(0));
		pay.setFundName("个人账户支付");
		pay.setSumFlag("1");
		this.jzPaylistDAO.insert(pay);

		pay.setBizId(bizId);
		pay.setFuncId("001");
		pay.setRealPay(new BigDecimal(0));
		pay.setFundName("统筹基金支付");
		pay.setSumFlag("1");
		this.jzPaylistDAO.insert(pay);

		pay.setBizId(bizId);
		pay.setFuncId("301");
		pay.setRealPay(new BigDecimal(0));
		pay.setFundName("公务员补基本医疗部分");
		pay.setSumFlag("1");
		this.jzPaylistDAO.insert(pay);

		pay.setBizId(bizId);
		pay.setFuncId("201");
		pay.setRealPay(new BigDecimal(0));
		pay.setFundName("大额保险支付");
		pay.setSumFlag("1");
		this.jzPaylistDAO.insert(pay);

		pay.setBizId(bizId);
		pay.setFuncId("202");
		pay.setRealPay(new BigDecimal(0));
		pay.setFundName("离休统筹支付");
		pay.setSumFlag("1");
		this.jzPaylistDAO.insert(pay);

		pay.setBizId(bizId);
		pay.setFuncId("501");
		pay.setRealPay(new BigDecimal(0));
		pay.setFundName("工商保险支付");
		pay.setSumFlag("1");
		this.jzPaylistDAO.insert(pay);

		pay.setBizId(bizId);
		pay.setFuncId("701");
		pay.setRealPay(new BigDecimal(0));
		pay.setFundName("生育保险支付");
		pay.setSumFlag("1");
		this.jzPaylistDAO.insert(pay);

		pay.setBizId(bizId);
		pay.setFuncId("999");
		pay.setRealPay(new BigDecimal(0));
		pay.setFundName("现金支付总额");
		pay.setSumFlag("1");
		this.jzPaylistDAO.insert(pay);

		pay.setBizId(bizId);
		pay.setFuncId("113");
		pay.setRealPay(new BigDecimal(0));
		pay.setFundName("个人账户余额");
		pay.setSumFlag("1");
		this.jzPaylistDAO.insert(pay);

		pay.setBizId(bizId);
		pay.setFuncId("117");
		pay.setRealPay(new BigDecimal(0));
		pay.setFundName("超商保费用");
		pay.setSumFlag("1");
		this.jzPaylistDAO.insert(pay);

		JzBizHistory history = new JzBizHistory();
		history.setAssistmoney(new BigDecimal(0));
		history.setTotalmoney(new BigDecimal(0));
		history.setInsurancepay(new BigDecimal(0));
		history.setRealpay(new BigDecimal(0));
		history.setOpertime(new Date());
		history.setOpertype(new BigDecimal(1));
		history.setBizId(new BigDecimal(bizId));
		history.setPersonacc(new BigDecimal(0));
		history.setOperuid(new BigDecimal(businessDTO.getEmpid()));
		this.jzBizHistoryDAO.insertSelective(history);

		JzActExample jzactexample = new JzActExample();
		JzAct act = new JzAct();
		short yyear = new BigDecimal(new Date().getYear()).add(
				new BigDecimal(1900)).shortValue();
		jzactexample.createCriteria()
				.andMemberIdEqualTo(businessDTO.getMemberId())
				.andActYearEqualTo(yyear);
		List<JzAct> jzActs = this.jzActDAO.selectByExample(jzactexample);
		if (null != jzActs && jzActs.size() > 0) {
			act = jzActs.get(0);
			int c = act.getActBizTimes().intValue();
			c = c + 1;
			int d = act.getActBizInhospitalTimes().intValue();
			act.setActBizTimes(new Short((short) c));
			d = d + b;
			act.setActBizInhospitalTimes((short) d);
			BigDecimal m = act.getActBizMoney().add(
					new BigDecimal(businessDTO.getZ01()));
			act.setActBizMoney(m);
			jzActDAO.updateByPrimaryKeySelective(act);
		} else {
			act.setActBizTimes(new Short((short) 1));
			act.setActBizMoney(new BigDecimal(businessDTO.getZ01()));
			act.setMemberId(businessDTO.getMemberId());
			act.setActYear(new Short((short) yyear));
			act.setActBizInhospitalTimes((short) 1);
			jzActDAO.insertSelective(act);
		}

	}

	public void setDictionaryHandle(DictionaryHandle dictionaryHandle) {
		this.dictionaryHandle = dictionaryHandle;
	}

	public void setDmBizdeptDAO(DmBizdeptDAO dmBizdeptDAO) {
		this.dmBizdeptDAO = dmBizdeptDAO;
	}

	public void setIcd10DAO(Icd10DAO icd10DAO) {
		this.icd10DAO = icd10DAO;
	}

	public void setJzActDAO(JzActDAO jzActDAO) {
		this.jzActDAO = jzActDAO;
	}

	public void setJzBizDAO(JzBizDAO jzBizDAO) {
		this.jzBizDAO = jzBizDAO;
	}

	public void setJzBizHistoryDAO(JzBizHistoryDAO jzBizHistoryDAO) {
		this.jzBizHistoryDAO = jzBizHistoryDAO;
	}

	public void setJzPaylistDAO(JzPaylistDAO jzPaylistDAO) {
		this.jzPaylistDAO = jzPaylistDAO;
	}

	public void setMemberBaseinfoDAO(MemberBaseinfoDAO memberBaseinfoDAO) {
		this.memberBaseinfoDAO = memberBaseinfoDAO;
	}

	public void setPager(Pager pager) {
		this.pager = pager;
	}

	public void setSysTOrganizationDAO(SysTOrganizationDAO sysTOrganizationDAO) {
		this.sysTOrganizationDAO = sysTOrganizationDAO;
	}

	public List<ManualDTO> queryManualApproves(String ssn) {
		List<ManualDTO> list = new ArrayList<ManualDTO>();
		try {
			JzManualExample example = new JzManualExample();
			example.createCriteria().andSsnEqualTo(ssn)
					.andCheckstateEqualTo("1");
			List<JzManual> rs = this.jzManualDAO.selectByExample(example);
			for (JzManual m : rs) {
				ManualDTO e = new ManualDTO();
				e.setAssistFlag(m.getAssistFlag());
				e.setAccountbalance(m.getAccountbalance());
				e.setAccountpayment(m.getAccountpayment());
				e.setAddress(m.getAddress());
				e.setAge(m.getAge());
				e.setAsistype(m.getAsistype());
				e.setBearpayment(m.getBearpayment());
				e.setBegintime(m.getBegintime());
				e.setBigpayment(m.getBigpayment());
				e.setCheckstate(m.getCheckstate());
				e.setCurrenttime(m.getCurrenttime());
				e.setDays(m.getDays());
				e.setEmpId(m.getEmpId());
				e.setEndtime(m.getEndtime());
				e.setFamilyno(m.getFamilyno());
				e.setHealthpayment(m.getHealthpayment());
				e.setHospitalmoney(m.getHospitalmoney());
				e.setHospitalname(m.getHospitalname());
				e.setInitline(m.getInitline());
				e.setInjurypayment(m.getInjurypayment());
				e.setInvoice(m.getInvoice());
				e.setLinkmode(m.getLinkmode());
				e.setManualId(m.getManualId());
				e.setManualno(m.getManualno());
				e.setMembername(m.getMembername());
				e.setOaccountbalance(m.getOaccountbalance());
				e.setOfficialpayment(m.getOfficialpayment());
				e.setOfficialpayment1(m.getOfficialpayment1());
				e.setOpertime(m.getOpertime());
				e.setPlanpayment(m.getPlanpayment());
				e.setPredeposit(m.getPredeposit());
				e.setRecdeposit(m.getRecdeposit());
				e.setRestpayment(m.getRestpayment());
				e.setSelfall(m.getSelfall());
				e.setSelfmoney(m.getSelfmoney());
				e.setSelfscale(m.getSelfscale());
				e.setSex(m.getSex());
				e.setSickname(m.getSickname());
				e.setSsn(m.getSsn());
				e.setFilename(m.getFielname());
				list.add(e);
			}
		} catch (RuntimeException re) {
			re.printStackTrace();
		}
		return list;
	}

	public ManualDTO saveManualApprove(ManualDTO m) {
		try {
			JzManual e = new JzManual();
			e.setAccountbalance(m.getAccountbalance());
			e.setAccountpayment(m.getAccountpayment());
			e.setAddress(m.getAddress());
			e.setAge(m.getAge());
			e.setAsistype("城市低保户");
			e.setBearpayment(m.getBearpayment());
			e.setBegintime(m.getBegintime());
			e.setBigpayment(m.getBigpayment());
			e.setCheckstate("1");
			e.setCurrenttime(m.getCurrenttime());
			e.setDays(m.getDays());
			e.setEmpId(m.getEmpId());
			e.setEndtime(m.getEndtime());
			e.setFamilyno(m.getFamilyno());
			e.setHealthpayment(m.getHealthpayment());
			e.setHospitalmoney(m.getHospitalmoney());
			e.setHospitalname(m.getHospitalname());
			e.setInitline(m.getInitline());
			e.setInjurypayment(m.getInjurypayment());
			e.setInvoice(m.getInvoice());
			e.setLinkmode(m.getLinkmode());
			e.setMembername(m.getMembername());
			e.setOaccountbalance(m.getOaccountbalance());
			e.setOfficialpayment(m.getOfficialpayment());
			e.setOfficialpayment1(m.getOfficialpayment1());
			e.setOpertime(new Date());
			e.setPlanpayment(m.getPlanpayment());
			e.setPredeposit(m.getPredeposit());
			e.setRecdeposit(m.getRecdeposit());
			e.setRestpayment(m.getRestpayment());
			e.setSelfall(m.getSelfall());
			e.setSelfmoney(m.getSelfmoney());
			e.setSelfscale(m.getSelfscale());
			e.setSex(m.getSex());
			e.setSickname(m.getSickname());
			e.setSsn(m.getSsn());
			e.setFielname(m.getFilename());
			e.setInHospitalname(m.getInHospitalname());
			e.setOutFlag(m.getOutFlag());
			e.setAssistFlag("0");
			if (null == m.getManualId()) {
				m.setAssistFlag("0");
				m.setManualId(new Long(this.jzManualDAO.insert(e)));
			} else {
				e.setManualId(m.getManualId());
				e.setManualno(m.getManualno());
				this.jzManualDAO.updateByPrimaryKeySelective(e);
			}
		} catch (RuntimeException e) {
			e.printStackTrace();

		}
		return m;
	}

	public ManualDTO findManualApprove(ManualDTO e) {
		JzManual m = this.jzManualDAO.selectByPrimaryKey(e.getManualId());
		e.setAccountbalance(m.getAccountbalance());
		e.setAccountpayment(m.getAccountpayment());
		e.setAddress(m.getAddress());
		e.setAge(m.getAge());
		e.setAsistype(m.getAsistype());
		e.setBearpayment(m.getBearpayment());
		e.setBegintime(m.getBegintime());
		e.setBigpayment(m.getBigpayment());
		e.setCheckstate(m.getCheckstate());
		e.setCurrenttime(m.getCurrenttime());
		e.setDays(m.getDays());
		e.setEmpId(m.getEmpId());
		e.setEndtime(m.getEndtime());
		e.setFamilyno(m.getFamilyno());
		e.setHealthpayment(m.getHealthpayment());
		e.setHospitalmoney(m.getHospitalmoney());
		e.setHospitalname(m.getHospitalname());
		e.setInitline(m.getInitline());
		e.setInjurypayment(m.getInjurypayment());
		e.setInvoice(m.getInvoice());
		e.setLinkmode(m.getLinkmode());
		e.setManualId(m.getManualId());
		e.setManualno(m.getManualno());
		e.setMembername(m.getMembername());
		e.setOaccountbalance(m.getOaccountbalance());
		e.setOfficialpayment(m.getOfficialpayment());
		e.setOfficialpayment1(m.getOfficialpayment1());
		e.setOpertime(m.getOpertime());
		e.setPlanpayment(m.getPlanpayment());
		e.setPredeposit(m.getPredeposit());
		e.setRecdeposit(m.getRecdeposit());
		e.setRestpayment(m.getRestpayment());
		e.setSelfall(m.getSelfall());
		e.setSelfmoney(m.getSelfmoney());
		e.setSelfscale(m.getSelfscale());
		e.setSex(m.getSex());
		e.setSickname(m.getSickname());
		e.setSsn(m.getSsn());
		e.setFilename(m.getFielname());
		e.setAssistFlag(m.getAssistFlag());
		e.setAssistTime(m.getAssistTime());
		e.setInHospitalname(m.getInHospitalname());
		e.setOutFlag(m.getOutFlag());
		return e;
	}

	public void removeManualApprove(ManualDTO manual) {
		this.jzManualDAO.deleteByPrimaryKey(manual.getManualId());
	}

	public List<ManualDTO> queryManualApprove(JzManualExample example,
			Integer curpage) {
		List<JzManual> rs = jzManualDAO.selectByExample(example);
		List<ManualDTO> list = new ArrayList<ManualDTO>();

		pager.setAll(rs.size());
		pager.setUrl("querymanual.action");
		pager.setCurrentpage(curpage.intValue());
		pager.setPagesize(14);
		pager.getToolsmenu();

		for (int i = 0; i < pager.getPagesize(); i++) {
			if (pager.getStart() + i < pager.getAll()) {
				JzManual m = rs.get(pager.getStart() + i);
				ManualDTO e = new ManualDTO();
				e.setAccountbalance(m.getAccountbalance());
				e.setAccountpayment(m.getAccountpayment());
				e.setAddress(m.getAddress());
				e.setAge(m.getAge());
				e.setAsistype(m.getAsistype());
				e.setBearpayment(m.getBearpayment());
				e.setBegintime(m.getBegintime());
				e.setBigpayment(m.getBigpayment());
				e.setCheckstate(m.getCheckstate());
				e.setCurrenttime(m.getCurrenttime());
				e.setDays(m.getDays());
				e.setEmpId(m.getEmpId());
				e.setEndtime(m.getEndtime());
				e.setFamilyno(m.getFamilyno());
				e.setHealthpayment(m.getHealthpayment());
				e.setHospitalmoney(m.getHospitalmoney());
				e.setHospitalname(m.getHospitalname());
				e.setInitline(m.getInitline());
				e.setInjurypayment(m.getInjurypayment());
				e.setInvoice(m.getInvoice());
				e.setLinkmode(m.getLinkmode());
				e.setManualId(m.getManualId());
				e.setManualno(m.getManualno());
				e.setMembername(m.getMembername());
				e.setOaccountbalance(m.getOaccountbalance());
				e.setOfficialpayment(m.getOfficialpayment());
				e.setOfficialpayment1(m.getOfficialpayment1());
				e.setOpertime(m.getOpertime());
				e.setPlanpayment(m.getPlanpayment());
				e.setPredeposit(m.getPredeposit());
				e.setRecdeposit(m.getRecdeposit());
				e.setRestpayment(m.getRestpayment());
				e.setSelfall(m.getSelfall());
				e.setSelfmoney(m.getSelfmoney());
				e.setSelfscale(m.getSelfscale());
				e.setSex(m.getSex());
				e.setSickname(m.getSickname());
				e.setSsn(m.getSsn());
				e.setFilename(m.getFielname());
				e.setAssistFlag(m.getAssistFlag());
				e.setAssistTime(m.getAssistTime());
				e.setInHospitalname(m.getInHospitalname());
				e.setOutFlag(m.getOutFlag());
				list.add(e);
			}
		}
		return list;
	}

	@SuppressWarnings("rawtypes")
	public List<PrintBillDTO> findBillTotalByDept(PrintBillDTO printBillDTO) {
		List<PrintBillDTO> list = new ArrayList<PrintBillDTO>();
		try {

			String b = "";
			if (!"".equals(printBillDTO.getHid())) {
				b = " and x.hospital_id='" + printBillDTO.getHid() + "' ";
			}

			ExecutSQL executSQL = new ExecutSQL();
			String executsql = "select nvl((select d.name from dm_bizdept d where d.hospital_id = x.hospital_id),'合计') as dmname,"
					+ "sum(zycs) as zycs, "
					+ "sum(zyzfy) zyzfy, "
					+ "sum(zyjzj) as zyjzj,"
					+ "sum(zytc) as zytc,"
					+ "sum(zygrzf) as zygrzf,"
					+ "sum(mzcs) as mzcs,"
					+ "sum(mzzfy) as mzzfy,"
					+ "sum(mzjzj) as mzjzj,"
					+ "sum(mztc) as mztc,"
					+ "sum(mzgrzf) as mzgrzf "
					+ "from monthbill x "
					+ "where 1=1   "
					+ b
					+ " and to_date(x.mon, 'yyyy-mm-dd') between to_date('"
					+ printBillDTO.getBeginval().trim()
					+ "', 'yyyy-mm-dd') and to_date('"
					+ printBillDTO.getEndval().trim()
					+ "', 'yyyy-mm-dd') group by rollup(x.hospital_id)";
			executSQL.setExecutsql(executsql);
			List<HashMap> rs = this.executSQLDAO.queryAll(executSQL);
			for (HashMap map : rs) {
				PrintBillDTO e = new PrintBillDTO();
				e.setDmname((String) map.get("DMNAME"));
				e.setZycs((BigDecimal) map.get("ZYCS"));
				e.setZyzfy((BigDecimal) map.get("ZYZFY"));
				e.setZyjzj((BigDecimal) map.get("ZYJZJ"));
				e.setZytc((BigDecimal) map.get("ZYTC"));
				e.setZygrzf((BigDecimal) map.get("ZYGRZF"));
				e.setMzcs((BigDecimal) map.get("MZCS"));
				e.setMzzfy((BigDecimal) map.get("MZZFY"));
				e.setMzjzj((BigDecimal) map.get("MZJZJ"));
				e.setMztc((BigDecimal) map.get("MZTC"));
				e.setMzgrzf((BigDecimal) map.get("MZGRZF"));
				list.add(e);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	public void setExecutSQLDAO(ExecutSQLDAO executSQLDAO) {
		this.executSQLDAO = executSQLDAO;
	}

	public ExecutSQLDAO getExecutSQLDAO() {
		return executSQLDAO;
	}

	@Override
	public void findCountAssist(MedicalafterDTO medicalafterDTO) {
		// TODO Auto-generated method stub

	}

	@SuppressWarnings("rawtypes")
	@Override
	public String findMaBillContent(String m, String ds, String type) {
		StringBuffer sb = new StringBuffer();
		try {
			String sql = "";
			String jwhere = "";
			if ("add".equals(type)) {
				jwhere = " and b.BAR_BANK_ACCOUNTS is null ";
			} else {

			}
			if ("cs".equals(ds)) {
				sql = "select * from batch_almsreckoning@cs b where   b.st_id = '4'  "
						+ jwhere
						+ "and b.sb_id in (select distinct (t.sb_id) from JZ_MABILLS t where t.batchname like '"
						+ m + "%')";
			} else if ("nc".equals(ds)) {
				sql = "select * from batch_almsreckoning@nc b where b.st_id = '4'  "
						+ jwhere
						+ "and b.sb_id in (select distinct (t.sb_id) from JZ_MABILLS t where t.batchname like '"
						+ m + "%')";
			} else {
				sql = "";
			}
			/*
			 * BAR_ID 1747067 BAR_SUBJECT 2014年12月医疗救助 BAR_MONEY 1700.21
			 * BAR_MASTER 王淑申 BAR_FMIDCARD 220221193908270527 BAR_BANK_ACCOUNTS
			 * 0720602011009801119011
			 */
			System.out.println(sql);
			if (!"".equals(sql)) {
				ExecutSQL executSQL = new ExecutSQL();
				executSQL.setExecutsql(sql);
				List<HashMap> rs = this.executSQLDAO.queryAll(executSQL);
				for (HashMap map : rs) {
					String BAR_MASTER = (String) map.get("BAR_MASTER");
					BigDecimal BAR_MONEY = (BigDecimal) map.get("BAR_MONEY");
					String BAR_BANK_ACCOUNTS = (String) map
							.get("BAR_BANK_ACCOUNTS");
					String BAR_FMIDCARD = (String) map.get("BAR_FMIDCARD");
					sb.append(BAR_MASTER + "," + BAR_FMIDCARD + ","
							+ BAR_BANK_ACCOUNTS + "," + BAR_MONEY.toString()
							+ System.getProperty("line.separator"));
				}
				return sb.toString();
			} else {
				return null;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return "";
	}
}
