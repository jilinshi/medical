package com.medical.service;

import java.util.List;

import com.medical.dto.DisasterafterDTO;

public interface DisasterAfterService {
	public List<DisasterafterDTO> findDisasteraftersByPaperId(DisasterafterDTO disasterafterDTO);
	public DisasterafterDTO saveDisasterApp(DisasterafterDTO disasterafterDTO);
	public DisasterafterDTO findMemberInfoPrint(DisasterafterDTO m);
	public DisasterafterDTO countAllAssitpay(String paperid);
}
