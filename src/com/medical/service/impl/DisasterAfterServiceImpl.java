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

import com.medical.dao.ExecutSQLDAO;
import com.medical.dao.JzDisasterafterDAO;
import com.medical.dto.DisasterafterDTO;
import com.medical.model.ExecutSQL;
import com.medical.model.JzDisasterafter;
import com.medical.model.JzDisasterafterExample;
import com.medical.service.DisasterAfterService;

public class DisasterAfterServiceImpl implements DisasterAfterService {
	static Logger log = Logger.getLogger(DisasterAfterServiceImpl.class);
	private JzDisasterafterDAO jzDisasterafterDAO;
	private ExecutSQLDAO executSQLDAO;
	
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
				sex = "Ů";
			}else if (("1".equals((String) map.get("SEX")))){
				sex = "��";
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
}
