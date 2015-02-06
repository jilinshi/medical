package com.medical.service;

import java.util.List;

import com.medical.common.Pager;
import com.medical.dto.DisasterafterDTO;
import com.medical.dto.OrganDTO;
import com.medical.model.JzDisasterafterExample;

public interface DisasterAfterService {
	public List<DisasterafterDTO> findDisasteraftersByPaperId(DisasterafterDTO disasterafterDTO);
	public DisasterafterDTO saveDisasterApp(DisasterafterDTO disasterafterDTO);
	public DisasterafterDTO findMemberInfoPrint(DisasterafterDTO m);
	public DisasterafterDTO countAllAssitpay(String paperid);
	public List<DisasterafterDTO> queryDisasterafters(JzDisasterafterExample example, Integer curpage, String url);
	public String getToolsmenu();
	public Pager getPager();
	public List<OrganDTO> getOrganList(String organid);
	public int deleteDisasterafter(DisasterafterDTO m);
}
