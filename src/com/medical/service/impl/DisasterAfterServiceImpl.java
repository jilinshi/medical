package com.medical.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.medical.dao.JzDisasterafterDAO;
import com.medical.dto.DisasterafterDTO;
import com.medical.model.JzDisasterafter;
import com.medical.model.JzDisasterafterExample;
import com.medical.model.JzMedicalafter;
import com.medical.service.DisasterAfterService;

public class DisasterAfterServiceImpl implements DisasterAfterService {
	static Logger log = Logger.getLogger(DisasterAfterServiceImpl.class);
	private JzDisasterafterDAO jzDisasterafterDAO;
	
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

	public JzDisasterafterDAO getJzDisasterafterDAO() {
		return jzDisasterafterDAO;
	}

	public void setJzDisasterafterDAO(JzDisasterafterDAO jzDisasterafterDAO) {
		this.jzDisasterafterDAO = jzDisasterafterDAO;
	}
}
