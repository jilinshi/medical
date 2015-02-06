package com.medical.service.impl;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;

import com.medical.common.Pager;
import com.medical.dao.ExecutSQLDAO;
import com.medical.dao.JzDisasterafterDAO;
import com.medical.dao.SysTOrganizationDAO;
import com.medical.dto.DisasterafterDTO;
import com.medical.dto.OrganDTO;
import com.medical.model.ExecutSQL;
import com.medical.model.JzAct;
import com.medical.model.JzActExample;
import com.medical.model.JzDisasterafter;
import com.medical.model.JzDisasterafterExample;
import com.medical.model.JzMedicalafter;
import com.medical.model.SysTOrganization;
import com.medical.model.SysTOrganizationExample;
import com.medical.service.DisasterAfterService;

public class DisasterAfterServiceImpl implements DisasterAfterService {
	static Logger log = Logger.getLogger(DisasterAfterServiceImpl.class);
	private JzDisasterafterDAO jzDisasterafterDAO;
	private ExecutSQLDAO executSQLDAO;
	private Pager pager;
	private String toolsmenu;
	private SysTOrganizationDAO sysTOrganizationDAO;
	
	public List<DisasterafterDTO> findDisasteraftersByPaperId(
			DisasterafterDTO disasterafterDTO) {
		List<DisasterafterDTO> dadtos = new ArrayList<DisasterafterDTO>();
		JzDisasterafterExample example = new JzDisasterafterExample();
		com.medical.model.JzDisasterafterExample.Criteria criteria = example
				.createCriteria();
		criteria.andPaperidEqualTo(disasterafterDTO.getPaperid());
		List<JzDisasterafter> info = jzDisasterafterDAO.selectByExample(example);
		for (JzDisasterafter s : info) {
			DisasterafterDTO e = new DisasterafterDTO();
			e.setDaId(s.getDaId());
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
			dadtos.add(e);
		}
		return dadtos;
	}
	
	public DisasterafterDTO saveDisasterApp(DisasterafterDTO disasterafterDTO){
		JzDisasterafter record = new JzDisasterafter();
		if (null == disasterafterDTO.getDaId()) {
			record.setBankAccount(disasterafterDTO.getBankaccounts());
			record.setMastername(disasterafterDTO.getMembername());
			record.setMembername(disasterafterDTO.getMembername());
			record.setPaperid(disasterafterDTO.getPaperid());
			record.setSsn(disasterafterDTO.getSsn());
			record.setHospital(disasterafterDTO.getHospital());
			record.setHospitallevel(disasterafterDTO.getHospitallevel());
			record.setSickencontent(disasterafterDTO.getSickencontent());
			record.setBegintime(disasterafterDTO.getBegintime());
			record.setEndtime(disasterafterDTO.getEndtime());
			record.setApproveresult(disasterafterDTO.getApproveresult());
			record.setApprovecontent(disasterafterDTO.getApprovecontent());
			record.setTotalcost(disasterafterDTO.getTotalcost());
			record.setInsurepay(disasterafterDTO.getInsurepay());
			record.setOutpay(disasterafterDTO.getOutpay());
			record.setCapay(disasterafterDTO.getCapay());
			record.setBusinesspay(disasterafterDTO.getBusinesspay());
			record.setAsisstpay(disasterafterDTO.getAsisstpay());
			record.setCreatetime(new Date());
			record.setUpdatetime(record.getCreatetime());
			record.setMemberId(disasterafterDTO.getMemberId());
			record.setMemberType(disasterafterDTO.getMemberType());
			record.setImplsts("0");
			record.setTiketno(disasterafterDTO.getTiketno());
			record.setMedicaltype(disasterafterDTO.getMedicaltype());
			record.setInsuretype(disasterafterDTO.getInsuretype());
			record.setPersontype("000");
			record.setOnNo(disasterafterDTO.getOnNo());
			record.setPayLine(disasterafterDTO.getPayLine());
			record.setHospitalpay(disasterafterDTO.getHospitalpay());
			record.setDiagnose(disasterafterDTO.getDiagnose());
			record.setSex(disasterafterDTO.getSex());
			record.setTelephone(disasterafterDTO.getTelephone());
			record.setFamaddr(disasterafterDTO.getFamaddr());
			record.setFamcount(disasterafterDTO.getFamcount());
			record.setBirthday(disasterafterDTO.getBirthday());
			record.setWsflag(disasterafterDTO.getWsflag());
			record.setPzPrinum(Long.valueOf("0"));
			record.setAppPrinum(Long.valueOf("0"));
			BigDecimal id = jzDisasterafterDAO.insertSelective(record);
			disasterafterDTO.setDaId(id);
		}
		return disasterafterDTO;
	}
	
	@SuppressWarnings("rawtypes")
	public DisasterafterDTO countAllAssitpay(String paperid){
		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);
		DisasterafterDTO disasterafterDTO = new DisasterafterDTO();
		try {
			String sql =" select * from "
						+ " (select rr.paperid, rr.medicaltype as mt1, count(1) as in_num, sum(rr.asisstpay) as in_sumpay "
						+ " from jz_disasterafter rr "
						+ " where rr.approveresult = '1' "
						+ " and to_char(rr.updatetime, 'yyyy') = '"+year+"' "
						+ " and rr.paperid='"+paperid+"' "
						+ " and rr.medicaltype='1' "
						+ " group by rr.paperid,rr.medicaltype)a, "
						+ " (select rr.medicaltype as mt2, count(1) as out_num, sum(rr.asisstpay) as out_sumpay "
						+ " from jz_disasterafter rr "
						+ " where rr.approveresult = '1' "
						+ " and to_char(rr.updatetime, 'yyyy') = '"+year+"' "
						+ " and rr.paperid='"+paperid+"' "
						+ " and rr.medicaltype='2' "
						+ " group by rr.paperid,rr.medicaltype)b ";
			ExecutSQL executSQL = new ExecutSQL();
			executSQL.setExecutsql(sql);
			List<HashMap> maps = executSQLDAO.queryAll(executSQL);
			if(maps.size()>0){
				HashMap map = maps.get(0);
				disasterafterDTO.setMt1((String) map.get("MT1"));
				disasterafterDTO.setMt2((String) map.get("MT2"));
				disasterafterDTO.setIn_num((BigDecimal) map.get("IN_NUM"));
				disasterafterDTO.setIn_sumpay((BigDecimal) map.get("IN_SUMPAY"));
				disasterafterDTO.setOut_num((BigDecimal) map.get("OUT_NUM"));
				disasterafterDTO.setOut_sumpay((BigDecimal) map.get("OUT_SUMPAY"));
			}else{
				disasterafterDTO.setMt1("");
				disasterafterDTO.setMt2("");
				disasterafterDTO.setIn_num(BigDecimal.ZERO);
				disasterafterDTO.setIn_sumpay(BigDecimal.ZERO);
				disasterafterDTO.setOut_num(BigDecimal.ZERO);
				disasterafterDTO.setOut_sumpay(BigDecimal.ZERO);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return disasterafterDTO;
	}

	@SuppressWarnings("rawtypes")
	public DisasterafterDTO findMemberInfoPrint(DisasterafterDTO m) {
		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);
		DisasterafterDTO disasterafterDTO = new DisasterafterDTO();
		try {
			String sql = " select da_id, jma.membername, jma.paperid, jma.ssn, jma.hospital, "
					+ " jma.hospitallevel, jma.sickencontent, jma.begintime, jma.endtime, jma.approveresult,"
					+ " jma.approvecontent, jma.totalcost, jma.insurepay, jma.outpay, jma.capay, jma.businesspay, "
					+ " jma.asisstpay, jma.createtime, jma.updatetime, jma.member_id, jma.member_type, jma.implsts,"
					+ " jma.tiketno, jma.medicaltype, jma.insuretype, jma.persontype, jma.on_no, jma.pay_line, "
					+ " jma.hospitalpay, jma.diagnose, jma.famcount, jma.famaddr, jma.telephone, jma.sex, jma.birthday,"
					+ " (trunc(jma.endtime, 'dd') - trunc(jma.begintime, 'dd')) as indate, "
					+ " b.num, b.sumpay, "
					+ " jma.pz_prinum, jma.app_prinum "
					+ " from jz_disasterafter jma, "
					+ " (select rr.paperid, count(1) as num, sum(rr.asisstpay) as sumpay "
					+ " from jz_disasterafter rr "
					+ " where rr.approveresult='1' and to_char(rr.updatetime,'yyyy')='"+year+"' group by rr.paperid )b "
					+ " where 1=1 "
					+ " and jma.paperid = b.paperid "
					+ " and jma.da_id = "
					+ m.getDaId();
			ExecutSQL executSQL = new ExecutSQL();
			executSQL.setExecutsql(sql);
			HashMap map = executSQLDAO.queryAll(executSQL).get(0);
			disasterafterDTO.setDaId((BigDecimal) map.get("DA_ID"));
			disasterafterDTO.setMembername((String) map.get("MEMBERNAME"));
			disasterafterDTO.setPaperid((String) map.get("PAPERID"));
			disasterafterDTO.setSsn((String) map.get("SSN"));
			String sex = "";
			if ("0".equals((String) map.get("SEX"))){
				sex = "Å®";
			}else if (("1".equals((String) map.get("SEX")))){
				sex = "ÄÐ";
			}
			disasterafterDTO.setSex(sex);
			disasterafterDTO.setFamaddr((String) map.get("FAMADDR"));
			disasterafterDTO.setPersontype((String) map.get("PERSONTYPE"));
			disasterafterDTO.setMedicaltype((String) map.get("MEDICALTYPE"));
			disasterafterDTO.setSickencontent((String) map.get("SICKENCONTENT"));
			disasterafterDTO.setDiagnose((String) map.get("DIAGNOSE"));
			disasterafterDTO.setTiketno((String) map.get("TIKETNO"));
			disasterafterDTO.setHospital((String) map.get("HOSPITAL"));
			disasterafterDTO.setHospitallevel((String) map.get("HOSPITALLEVEL"));
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			String begintime = sdf.format((Date) map.get("BEGINTIME"));
			String endtime = sdf.format((Date) map.get("ENDTIME"));
			String birthday = sdf.format((Date) map.get("BIRTHDAY"));
			disasterafterDTO.setBegintimeval(begintime);
			disasterafterDTO.setEndtimeval(endtime);
			disasterafterDTO.setBirthdayval(birthday);
			disasterafterDTO.setNum((BigDecimal) map.get("NUM"));
			disasterafterDTO.setIndate((BigDecimal) map.get("INDATE"));
			disasterafterDTO.setTotalcost((BigDecimal) map.get("TOTALCOST"));
			disasterafterDTO.setInsurepay((BigDecimal) map.get("INSUREPAY"));
			disasterafterDTO.setOutpay((BigDecimal) map.get("OUTPAY"));
			disasterafterDTO.setCapay((BigDecimal) map.get("CAPAY"));
			disasterafterDTO.setBusinesspay((BigDecimal) map.get("BUSINESSPAY"));
			disasterafterDTO.setAsisstpay((BigDecimal) map.get("ASISSTPAY"));
			disasterafterDTO.setPayLine((BigDecimal) map.get("PAY_LINE"));
			disasterafterDTO.setHospitalpay((BigDecimal) map.get("HOSPITALPAY"));
			disasterafterDTO.setInsuretype((String) map.get("INSURETYPE"));
			disasterafterDTO.setSumpay((BigDecimal) map.get("SUMPAY"));
			disasterafterDTO.setTelephone((String) map.get("TELEPHONE"));
			BigDecimal famcount = (BigDecimal) map.get("FAMCOUNT");
			disasterafterDTO.setFamcountval(famcount.toString());
			disasterafterDTO.setFamcount(famcount.shortValue());
			BigDecimal pz = (BigDecimal) map.get("PZ_PRINUM");
			disasterafterDTO.setPzPrinum(pz.toString());
			BigDecimal app = (BigDecimal) map.get("APP_PRINUM");
			disasterafterDTO.setAppPrinum(app.toString());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return disasterafterDTO;
	}
	
	public List<DisasterafterDTO> queryDisasterafters(
			JzDisasterafterExample example, Integer curpage, String url) {
		List<JzDisasterafter> info = jzDisasterafterDAO.selectByExample(example);
		List<DisasterafterDTO> dadtos = new ArrayList<DisasterafterDTO>();

		pager.setAll(info.size());
		pager.setUrl(url);
		pager.setCurrentpage(curpage.intValue());
		pager.setPagesize(14);
		pager.getToolsmenu();

		for (int i = 0; i < pager.getPagesize(); i++) {
			if (pager.getStart() + i < pager.getAll()) {
				JzDisasterafter s = info.get(pager.getStart() + i);
				DisasterafterDTO e = new DisasterafterDTO();
				e.setDaId(s.getDaId());
				e.setMembername(s.getMembername());
				e.setPaperid(s.getPaperid());
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
				dadtos.add(e);
			}
		}
		return dadtos;
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
	
	public int deleteDisasterafter(DisasterafterDTO m){
		JzDisasterafter record = new JzDisasterafter();
		record.setDaId(m.getDaId());
		record.setApproveresult("-1");
		record.setUpdatetime(new Date());
		int u = jzDisasterafterDAO.updateByPrimaryKeySelective(record);
		return u;
	}
	
	public JzDisasterafterDAO getJzDisasterafterDAO() {
		return jzDisasterafterDAO;
	}

	public void setJzDisasterafterDAO(JzDisasterafterDAO jzDisasterafterDAO) {
		this.jzDisasterafterDAO = jzDisasterafterDAO;
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

	public String getToolsmenu() {
		return toolsmenu;
	}

	public void setToolsmenu(String toolsmenu) {
		this.toolsmenu = toolsmenu;
	}

	public SysTOrganizationDAO getSysTOrganizationDAO() {
		return sysTOrganizationDAO;
	}

	public void setSysTOrganizationDAO(SysTOrganizationDAO sysTOrganizationDAO) {
		this.sysTOrganizationDAO = sysTOrganizationDAO;
	}
}
