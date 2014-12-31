package com.medical.service.impl;

import java.io.File;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;

import com.medical.common.FileHandle;
import com.medical.common.Pager;
import com.medical.dao.ExecutSQLDAO;
import com.medical.dao.JzAnnexDAO;
import com.medical.dao.JzAspapproveDAO;
import com.medical.dao.JzChronicapproveDAO;
import com.medical.dao.JzChronicbillDAO;
import com.medical.dao.JzChronicstatusDAO;
import com.medical.dao.MemberBaseinfoDAO;
import com.medical.dao.SysTOrganizationDAO;
import com.medical.dto.AspApproveDTO;
import com.medical.dto.ChronicApproveDTO;
import com.medical.dto.CurrectChronicDTO;
import com.medical.dto.OrganDTO;
import com.medical.dto.PersonDTO;
import com.medical.model.ExecutSQL;
import com.medical.model.JzAnnex;
import com.medical.model.JzAnnexExample;
import com.medical.model.JzAspapprove;
import com.medical.model.JzAspapproveExample;
import com.medical.model.JzChronicapprove;
import com.medical.model.JzChronicapproveExample;
import com.medical.model.JzChronicbill;
import com.medical.model.JzChronicbillExample;
import com.medical.model.JzChronicstatus;
import com.medical.model.JzChronicstatusExample;
import com.medical.model.MemberBaseinfo;
import com.medical.model.MemberBaseinfoExample;
import com.medical.model.SysTOrganization;
import com.medical.model.SysTOrganizationExample;
import com.medical.service.ChronicApproveService;
import com.medical.system.DictionaryHandle;

public class ChronicApproveServiceImpl implements ChronicApproveService {
	static Logger log = Logger.getLogger(ChronicApproveServiceImpl.class);

	private JzChronicapproveDAO jzChronicapproveDAO;
	private ExecutSQLDAO executSQLDAO;
	private MemberBaseinfoDAO memberBaseinfoDAO;
	private JzAnnexDAO jzAnnexDAO;
	private Pager pager;
	private JzChronicstatusDAO jzChronicstatusDAO;
	private DictionaryHandle dictionaryHandle;
	private JzChronicbillDAO jzChronicbillDAO;
	private JzAspapproveDAO jzAspapproveDAO;
	private SysTOrganizationDAO sysTOrganizationDAO;

	public List<ChronicApproveDTO> findApprove(String url, int curpage,
			JzChronicapproveExample example) {
		List<ChronicApproveDTO> list = new ArrayList<ChronicApproveDTO>();
		pager.setCurrentpage(curpage);
		pager.setAll(jzChronicapproveDAO.countByExample(example));
		pager.setUrl(url);
		pager.setPagesize(16);

		List<JzChronicapprove> rs = jzChronicapproveDAO.selectByExample(
				example, pager.getStart(), pager.getPagesize());

		for (JzChronicapprove s : rs) {
			ChronicApproveDTO e = new ChronicApproveDTO();
			copyChronicApproveDTO(e, s);
			list.add(e);
		}
		return list;
	}

	@SuppressWarnings({ "rawtypes" })
	public List<ChronicApproveDTO> findApprove1(String url, Integer curpage,
			String sql) {
		List<ChronicApproveDTO> list = new ArrayList<ChronicApproveDTO>();

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
				ChronicApproveDTO e = new ChronicApproveDTO();
				e.setChronicapproveId((BigDecimal) s.get("CHRONICAPPROVE_ID"));
				e.setFamilyId((String) s.get("FAMILY_ID"));
				e.setFamilyno((String) s.get("FAMILY_ID"));
				e.setName((String) s.get("NAME"));
				e.setSsn((String) s.get("SSN"));
				BigDecimal entity = (BigDecimal) s.get("ENTITY");
				if (null == entity || "".equals(entity.toString())) {

				} else {
					e.setEntity(new Integer(entity.toString()));
				}

				e.setEntityval((String) s.get("ENTITYVAL"));
				BigDecimal app1 = (BigDecimal) s.get("APRRESULT1");
				BigDecimal app2 = (BigDecimal) s.get("APRRESULT2");
				BigDecimal app3 = (BigDecimal) s.get("APRRESULT3");
				if (null != app1) {
					e.setAprresult1(new Short(app1.toString()));
				}
				if (null != app2) {
					e.setAprresult2(new Short(app2.toString()));
				}
				if (null != app3) {
					e.setAprresult3(new Short(app3.toString()));
				}
				e.setFlag(new Short(s.get("FLAG").toString()));
				e.setStatus(new Short(s.get("STATUS").toString()));
				e.setPaperid((String) s.get("PAPERID"));
				e.setState((String) s.get("STATE"));
				e.setMemberId((String) s.get("MEMBER_ID"));
				e.setMemberType((String) s.get("MEMBER_TYPE"));
				// APRPERSON1 APRRESULT2 APRIDEA2 APRTIME2
				e.setAprtime1((Date) s.get("APRTIME1"));
				e.setAprtime2((Date) s.get("APRTIME2"));
				e.setAprtime3((Date) s.get("APRTIME3"));
				e.setPs((String) s.get("PS"));
				list.add(e);
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return list;
	}

	@SuppressWarnings({ "rawtypes" })
	public List<ChronicApproveDTO> findApprove2(String url, Integer curpage,
			String sql) {
		List<ChronicApproveDTO> list = new ArrayList<ChronicApproveDTO>();

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
				ChronicApproveDTO e = new ChronicApproveDTO();
				e.setChronicapproveId((BigDecimal) s.get("CHRONICAPPROVE_ID"));
				e.setFamilyId((String) s.get("FAMILY_ID"));
				e.setFamilyno((String) s.get("FAMILY_ID"));
				e.setName((String) s.get("NAME"));
				e.setSsn((String) s.get("SSN"));
				e.setEntityval((String) s.get("ENTITY"));
				e.setFlag(new Short(s.get("FLAG").toString()));
				e.setStatus(new Short(s.get("STATE").toString()));
				e.setPaperid((String) s.get("PAPERID"));
				e.setState((String) s.get("STATE"));
				e.setMemberId((String) s.get("MEMBER_ID"));
				e.setMemberType((String) s.get("MEMBER_TYPE"));
				e.setAprtime((Date) s.get("APRTIME"));
				e.setSts((String) s.get("STS"));
				e.setRemark((String) s.get("REMARK"));

				e.setAnFilename((String) s.get("AN_FILENAME"));
				if (null == e.getAnFilename() || "".equals(e.getAnFilename())) {
					e.setAnFilename("无");
				} else {
					e.setAnFilename("有");
				}
				list.add(e);
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return list;
	}

	@SuppressWarnings({ "rawtypes" })
	@Override
	public List<CurrectChronicDTO> findApprove3(String url, Integer cur_page,
			String sql) {
		System.out.println(sql);
		List<CurrectChronicDTO> list = new ArrayList<CurrectChronicDTO>();

		ExecutSQL executSQL = new ExecutSQL();
		executSQL.setExecutsql(sql);

		pager.setCurrentpage(cur_page);
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
				CurrectChronicDTO e = new CurrectChronicDTO();
				String ENTITYS = (String) s.get("ENTITYS");
				e.setEntitys(ENTITYS);
				String ENTITYVAL = (String) s.get("ENTITYVAL");
				e.setEntityval(ENTITYVAL);
				BigDecimal APRRESULT1 = (BigDecimal) s.get("APRRESULT1");
				if (null != APRRESULT1) {
					e.setAprresult1(APRRESULT1.shortValue());
				}
				String APRIDEA1 = (String) s.get("APRIDEA1");
				e.setApridea1(APRIDEA1);
				Date APRTIME1 = (Date) s.get("APRTIME1");
				e.setApptime(APRTIME1);
				String APRPERSON1 = (String) s.get("APRPERSON1");
				e.setAprperson1(APRPERSON1);
				BigDecimal APRRESULT2 = (BigDecimal) s.get("APRRESULT2");
				if (null != APRRESULT2) {
					e.setAprresult2(APRRESULT2.shortValue());
				}
				String APRIDEA2 = (String) s.get("APRIDEA2");
				e.setApridea2(APRIDEA2);
				Date APRTIME2 = (Date) s.get("APRTIME2");
				e.setAprtime2(APRTIME2);
				String APRPERSON2 = (String) s.get("APRPERSON2");
				e.setAprperson2(APRPERSON2);

				BigDecimal APRRESULT3 = (BigDecimal) s.get("APRRESULT3");
				if (null != APRRESULT3) {
					e.setAprresult3(APRRESULT3.shortValue());
				}
				String APRIDEA3 = (String) s.get("APRIDEA3");
				e.setApridea3(APRIDEA3);
				Date APRTIME3 = (Date) s.get("APRTIME3");
				e.setAprtime3(APRTIME3);
				String APRPERSON3 = (String) s.get("APRPERSON3");
				e.setAprperson3(APRPERSON3);
				BigDecimal APRLEVEL = (BigDecimal) s.get("APRLEVEL");
				if (null != APRLEVEL) {
				e.setAprlevel(APRLEVEL.shortValue());}
				BigDecimal FLAG = (BigDecimal) s.get("FLAG");
				if (null != FLAG) {
				e.setFlag(FLAG.shortValue());}
				BigDecimal STATUS = (BigDecimal) s.get("STATUS");
				if (null != STATUS) {
				e.setStatus(STATUS.shortValue());}
				BigDecimal CHRONICSTATUS_ID1 = (BigDecimal) s
						.get("CHRONICSTATUS_ID1");
				e.setChronicstatusId1(CHRONICSTATUS_ID1.longValue());
				String FAMILY_ID = (String) s.get("FAMILY_ID");
				e.setFamilyId(FAMILY_ID);
				String NAME = (String) s.get("NAME");
				e.setName(NAME);
				String SSN = (String) s.get("SSN");
				e.setSsn(SSN);
				BigDecimal ENTITY = (BigDecimal) s.get("ENTITY");
				if (null != ENTITY) {
					e.setEntity(ENTITY.intValue());
				}
				String STATE = (String) s.get("STATE");
				e.setState(STATE);
				Date APPTIME = (Date) s.get("APPTIME");
				e.setApptime(APPTIME);
				String FLAG1 = (String) s.get("FLAG1");
				e.setFlag1(FLAG1);
				String MEMBER_ID = (String) s.get("MEMBER_ID");
				e.setMemberId(MEMBER_ID);
				String MEMBER_TYPE = (String) s.get("MEMBER_TYPE");
				e.setMemberType(MEMBER_TYPE);
				String PAPERID = (String) s.get("PAPERID");
				e.setPaperid(PAPERID);
				Date CREATTIME = (Date) s.get("CREATTIME");
				e.setCreattime(CREATTIME);
				String ENTITYVALS = (String) s.get("ENTITYVALS");
				e.setEntityvals(ENTITYVALS);
				String CAPID = (String) s.get("CAPID");
				e.setCapid(CAPID);
				String YW = (String) s.get("YW");
				e.setYw(YW);
				String PS = (String) s.get("PS");
				e.setPs(PS);
				String AREA = (String) s.get("AREA");
				e.setArea(AREA);
				Date CANCELTIME = (Date) s.get("CANCELTIME");
				e.setCanceltime(CANCELTIME);
				String CANCELIDEA = (String) s.get("CANCELIDEA");
				e.setCancelidea(CANCELIDEA);
				String YWVAL = (String) s.get("YWVAL");
				e.setYwval(YWVAL);
				list.add(e);
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return list;

	}

	@Override
	public ChronicApproveDTO saveApprove(ChronicApproveDTO chronicApproveDTO,
			String orgno) {
		try {
			JzChronicapprove po = null;
			if (orgno.length() == 8) {
				po = new JzChronicapprove();
				po.setFamilyId(chronicApproveDTO.getFamilyId());
				po.setName(chronicApproveDTO.getName());
				po.setSsn(chronicApproveDTO.getSsn());
				if (chronicApproveDTO.getEntitys().length > 0) {
					String temp = "";
					String[] entitys = chronicApproveDTO.getEntitys();
					for (int i = 0; i < entitys.length; i++) {
						String s = entitys[i];
						temp = temp + s + ",";
						if (i == 1) {
							po.setEntity(new Integer(s));
						}
					}
					po.setEntitys(temp);
				}
				po.setAprresult1(chronicApproveDTO.getAprresult1());
				po.setApridea1(chronicApproveDTO.getApridea1());
				po.setAprtime1(chronicApproveDTO.getAprtime1());
				po.setAprperson1(chronicApproveDTO.getAprperson1());
				po.setFlag((short) 1);
				po.setAprlevel((short) 3);
				po.setMemberId(chronicApproveDTO.getMemberId());
				po.setMemberType(chronicApproveDTO.getMemberType());
				po.setEntityval(chronicApproveDTO.getEntityval());
				if (2 == chronicApproveDTO.getAprresult1()) {
					po.setStatus((short) 2);
				} else {
					po.setStatus((short) 0);

				}
				if (null == chronicApproveDTO.getChronicapproveId()) {
					chronicApproveDTO.setChronicapproveId(jzChronicapproveDAO
							.insertSelective(po));
				} else {
					po.setChronicapproveId(chronicApproveDTO
							.getChronicapproveId());
					jzChronicapproveDAO.updateByPrimaryKeySelective(po);
				}
			} else if (null != chronicApproveDTO.getChronicapproveId()
					&& orgno.length() == 6) {
				// 区县终审
				po = jzChronicapproveDAO.selectByPrimaryKey(chronicApproveDTO
						.getChronicapproveId());
				if (2 == chronicApproveDTO.getAprresult2()) {
					po.setStatus((short) 2);
					po.setFlag((short) 1);
					po.setAprlevel((short) 2);
				} else {
					po.setAprlevel((short) 2);
				}
				po.setAprresult2(chronicApproveDTO.getAprresult2());
				po.setApridea2(chronicApproveDTO.getApridea2());
				po.setAprtime2(chronicApproveDTO.getAprtime2());
				po.setAprperson2(chronicApproveDTO.getAprperson2());
				po.setEntityval(chronicApproveDTO.getEntityval());

				if (chronicApproveDTO.getEntitys().length > 0) {
					String temp = "";
					String[] entitys = chronicApproveDTO.getEntitys();
					for (int i = 0; i < entitys.length; i++) {
						String s = entitys[i];
						temp = temp + s + ",";
						if (i == 1) {
							po.setEntity(new Integer(s));
						}
					}
					po.setEntitys(temp);
				}
				jzChronicapproveDAO.updateByPrimaryKeySelective(po);
			} else if (null != chronicApproveDTO.getChronicapproveId()
					&& orgno.length() == 4) {
				po = jzChronicapproveDAO.selectByPrimaryKey(chronicApproveDTO
						.getChronicapproveId());
				String status = "";
				if (1 == chronicApproveDTO.getAprresult3()) {
					po.setStatus((short) 1);
					po.setAprlevel((short) -1);
					po.setFlag((short) 1);
					status = "1";
				} else if (2 == chronicApproveDTO.getAprresult3()) {
					po.setStatus((short) 2);
					po.setFlag((short) 0);
					status = "0";
				} else if (3 == chronicApproveDTO.getAprresult3()) {
					po.setStatus((short) 0);
					po.setFlag((short) 1);
					status = "0";
				}
				po.setAprresult3(chronicApproveDTO.getAprresult3());
				po.setApridea3(chronicApproveDTO.getApridea3());
				po.setAprtime3(chronicApproveDTO.getAprtime3());
				po.setAprperson3(chronicApproveDTO.getAprperson3());
				jzChronicapproveDAO.updateByPrimaryKeySelective(po);
				saveChronicstatus(po, status);
			}

			chronicApproveDTO = findChronicApproveDTOByID(chronicApproveDTO);
			chronicApproveDTO.setLinkmode(dictionaryHandle
					.getDictValue(chronicApproveDTO.getEntity().toString()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return chronicApproveDTO;
	}

	private void saveChronicstatus(JzChronicapprove po, String status) {
		JzChronicstatusExample example = new JzChronicstatusExample();
		example.createCriteria().andMemberIdEqualTo(po.getMemberId())
				.andMemberTypeEqualTo(po.getMemberType());
		List<JzChronicstatus> s = jzChronicstatusDAO.selectByExample(example);
		JzChronicstatus record;
		if (null != s && s.size() > 0) {
			record = s.get(0);
			record.setState(status);
			record.setApptime(new Date());
			record.setFlag("1");
			record.setCapid(po.getChronicapproveId().toString());
			jzChronicstatusDAO.updateByPrimaryKey(record);
		} else {
			record = new JzChronicstatus();
			record.setState(status);
			record.setApptime(new Date());
			record.setEntity(po.getEntity());
			record.setFamilyId(po.getFamilyId());
			record.setFlag("1");
			record.setName(po.getName());
			record.setSsn(po.getSsn());
			record.setMemberId(po.getMemberId());
			record.setMemberType(po.getMemberType());
			record.setCapid(po.getChronicapproveId().toString());
			jzChronicstatusDAO.insertSelective(record);
		}
	}

	public List<ChronicApproveDTO> findApprovesBySSN(
			ChronicApproveDTO chronicApproveDTO) {
		return null;
	}

	public List<ChronicApproveDTO> findCheckApproves(
			ChronicApproveDTO chronicApproveDTO) {
		List<ChronicApproveDTO> list = new ArrayList<ChronicApproveDTO>();
		JzChronicapproveExample example = new JzChronicapproveExample();
		List<Short> values = new ArrayList<Short>();
		values.add((short) 1);
		values.add((short) 0);
		example.createCriteria().andFlagEqualTo((short) 1).andStatusIn(values)
				.andMemberIdEqualTo(chronicApproveDTO.getMemberId())
				.andMemberTypeEqualTo(chronicApproveDTO.getMemberType());
		List<JzChronicapprove> rs = jzChronicapproveDAO
				.selectByExample(example);
		for (JzChronicapprove s : rs) {
			ChronicApproveDTO e = new ChronicApproveDTO();
			copyChronicApproveDTO(e, s);
			list.add(e);
		}
		return list;
	}

	public List<PersonDTO> findChronicMembersByFamilyno(String familyno) {
		List<PersonDTO> list = new ArrayList<PersonDTO>();
		MemberBaseinfoExample example = new MemberBaseinfoExample();
		List<String> values = new ArrayList<String>();
		values.add("11");
		values.add("10");
		example.createCriteria().andFamilynoEqualTo(familyno)
				.andAssistTypeIn(values).andPersonstateEqualTo("正常");
		List<MemberBaseinfo> rs = memberBaseinfoDAO.selectByExampleWithoutBLOBs(example);
		for (MemberBaseinfo s : rs) {
			PersonDTO e = new PersonDTO();
			e.setMembername(s.getMembername());
			e.setSsn(s.getSsn());
			e.setSex(s.getSex());
			e.setPaperid(s.getPaperid());
			e.setFamilyno(s.getFamilyno());
			e.setMemberId(s.getMemberId());
			e.setMemberType(s.getDs());
			list.add(e);
		}
		return list;
	}

	// 查询体检进行中
	public List<PersonDTO> findAspMembersByFamilyno(String url,
			Integer curPage, MemberBaseinfoExample example) {
		List<PersonDTO> list = new ArrayList<PersonDTO>();
		pager = new Pager();
		pager.setCurrentpage(curPage);
		pager.setAll(memberBaseinfoDAO.countByExample(example));
		pager.setUrl(url);
		pager.setPagesize(16);
		pager.genToolsmenu();
		List<MemberBaseinfo> rs = memberBaseinfoDAO.selectByExampleWithoutBLOBs(example,
				pager.getStart(), pager.getPagesize());
		for (MemberBaseinfo s : rs) {
			PersonDTO e = new PersonDTO();
			e.setMembername(s.getMembername());
			e.setSsn(s.getSsn());
			e.setSex(s.getSex());
			e.setPaperid(s.getPaperid());
			e.setFamilyno(s.getFamilyno());
			e.setIsybsqdb(s.getIsybsqdb().shortValue());
			e.setMemberId(s.getMemberId());
			e.setMemberType(s.getDs());
			list.add(e);
		}
		return list;
	}

	public ChronicApproveDTO findChronicApproveDTOByID(
			ChronicApproveDTO chronicApproveDTO) {
		ChronicApproveDTO e = new ChronicApproveDTO();
		e.setChronicapproveId(chronicApproveDTO.getChronicapproveId());
		JzChronicapprove s = jzChronicapproveDAO.selectByPrimaryKey(e
				.getChronicapproveId());
		copyChronicApproveDTO(e, s);
		e = findChronicMembersBySSN(e);
		return e;
	}

	private void copyChronicApproveDTO(ChronicApproveDTO e, JzChronicapprove s) {
		e.setChronicapproveId(s.getChronicapproveId());
		e.setFamilyId(s.getFamilyId());
		e.setName(s.getName());
		e.setSsn(s.getSsn());
		e.setEntity(s.getEntity());
		e.setStatus(s.getStatus());
		e.setFlag(s.getFlag());
		e.setAprresult1(s.getAprresult1());
		e.setApridea1(s.getApridea1());
		e.setAprtime1(s.getAprtime1());
		e.setAprperson1(s.getAprperson1());
		e.setAprresult2(s.getAprresult2());
		e.setApridea2(s.getApridea2());
		e.setAprtime2(s.getAprtime2());
		e.setAprperson2(s.getAprperson2());
		e.setAprresult3(s.getAprresult3());
		e.setApridea3(s.getApridea3());
		e.setAprtime3(s.getAprtime3());
		e.setAprperson3(s.getAprperson3());
		e.setAprlevel(s.getAprlevel());
		e.setAprresult(null);
		e.setApridea(null);
		e.setAprtime(null);
		e.setAprperson(null);
		e.setMemberId(s.getMemberId());
		e.setMemberType(s.getMemberType());
		e.setEntityval(s.getEntityval());
		String[] entitys = null;
		String sss = s.getEntitys();
		if (null == sss || "".equals(sss)) {
			entitys = new String[0];
			e.setEntitys(entitys);
		} else {
			e.setEntitys(sss.split(","));
		}
	}

	public ChronicApproveDTO findChronicMembersBySSN(ChronicApproveDTO e) {
		MemberBaseinfoExample example = new MemberBaseinfoExample();
		example.createCriteria().andMemberIdEqualTo(e.getMemberId())
				.andDsEqualTo(e.getMemberType());
		List<MemberBaseinfo> rs = memberBaseinfoDAO.selectByExampleWithoutBLOBs(example);
		for (MemberBaseinfo s : rs) {
			e.setName(s.getMembername());
			e.setFamilyId(s.getFamilyno());
			e.setFamilyno(s.getFamilyno());
			e.setSsn(s.getSsn());
			e.setPaperid(s.getPaperid());
			e.setRelmaster(s.getRelmaster());
			e.setSex(s.getSex());
			e.setMasterName(s.getMasterName());
			e.setBirthday(s.getBirthday());
			e.setRprkind(s.getRprkind());
			e.setRprtype(s.getRprtype());
			e.setRpraddress(s.getRpraddress());
			e.setLinkmode(s.getLinkmode());
			e.setAddress(s.getAddress());
			e.setMemberId(s.getMemberId());
			e.setMemberType(s.getDs());
		}
		return e;
	}

	public void removeChronicApprove(ChronicApproveDTO chronicApproveDTO) {
		JzChronicapprove record = jzChronicapproveDAO
				.selectByPrimaryKey(chronicApproveDTO.getChronicapproveId());
		record.setFlag((short) 0);
		jzChronicapproveDAO.updateByPrimaryKeySelective(record);
		JzChronicstatusExample example = new JzChronicstatusExample();
		example.createCriteria().andMemberIdEqualTo(record.getMemberId())
				.andMemberTypeEqualTo(record.getMemberType());
		List<JzChronicstatus> rs = jzChronicstatusDAO.selectByExample(example);
		for (JzChronicstatus s : rs) {
			s.setFlag("0");
			s.setState("0");
			jzChronicstatusDAO.updateByPrimaryKey(s);
			JzChronicbillExample v = new JzChronicbillExample();
			v.createCriteria().andMemberIdEqualTo(s.getMemberId())
					.andMemberTypeEqualTo(s.getMemberType());
			v.setOrderByClause("chronicbill_id desc");
			List<JzChronicbill> bills = jzChronicbillDAO.selectByExample(v);
			if (bills != null && bills.size() > 0) {
				BigDecimal balance = bills.get(0).getBalance();
				JzChronicbill e = new JzChronicbill();
				if (balance.compareTo(new BigDecimal(0)) >= 0) {
					e.setBalance(new BigDecimal(0));
					e.setIncome(balance.multiply(new BigDecimal(-1)));
					e.setPayout(new BigDecimal(0));
					e.setFamilyId(s.getFamilyId());
					e.setName(s.getName());
					e.setSsn(s.getSsn());
					e.setSubject("作废清零");
					e.setOpttime(new Date());
					jzChronicbillDAO.insertSelective(e);
				}
			}
		}

	}

	public PersonDTO findPersonBySsn(PersonDTO personDTO) {
		try {
			MemberBaseinfoExample example = new MemberBaseinfoExample();
			example.createCriteria().andSsnEqualTo(personDTO.getSsn());
			List<MemberBaseinfo> rs = memberBaseinfoDAO.selectByExampleWithoutBLOBs(example);
			for (MemberBaseinfo s : rs) {
				personDTO.setFamilyno(s.getFamilyno());
				personDTO.setSsn(s.getSsn());
				personDTO.setMembername(s.getMembername());
				personDTO.setPaperid(s.getPaperid());
				personDTO.setMemberId(s.getMemberId());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return personDTO;
	}

	public void saveHealthrecord(String annexdetail, String ssn,
			String memberid, Short annextype, File file) {
		JzAnnex record = new JzAnnex();
		record.setAnnexpath(file.getPath());
		record.setMemberId(memberid);
		record.setAnnexname(file.getName());
		record.setSsn(ssn);
		record.setAnnextype(annextype);
		record.setAnnexdetail(annexdetail);
		jzAnnexDAO.insertSelective(record);
	}

	public HashMap<String, String> findHrsBySsn(String memberId, Short type) {
		HashMap<String, String> list = new HashMap<String, String>();
		JzAnnexExample example = new JzAnnexExample();
		example.createCriteria().andMemberIdEqualTo(memberId)
				.andAnnextypeEqualTo(type);
		List<JzAnnex> rs = jzAnnexDAO.selectByExample(example);
		for (JzAnnex s : rs) {
			String e = new String();
			e = "\\" + memberId + "\\" + s.getAnnexname();
			list.put(s.getAnnexdetail(), e);
		}
		return list;
	}

	@SuppressWarnings("rawtypes")
	public List<ChronicApproveDTO> findPrinthr(
			ChronicApproveDTO chronicApproveDTO) {
		List<ChronicApproveDTO> list = new ArrayList<ChronicApproveDTO>();
		try {
			String sql = "select app.*, "
					+ "  mem.PAPERID, "
					+ "   mem.FAMILYNO, "
					+ "  mem.RELMASTER, "
					+ "  mem.SEX, "
					+ "  mem.MASTER_NAME, "
					+ "  mem.BIRTHDAY, "
					+ "  mem.ASSIST_TYPE, mem.MEMBER_ID, mem.begintime , "
					+ "  trunc((to_char(sysdate, 'yyyyMMdd') - to_char(mem.BIRTHDAY, 'yyyyMMdd')) / 10000) as age, "
					+ "  decode(mem.ASSIST_TYPE,11,'城市低保',10,'分类施保',01,'农村低保',00,'非低保',null) as assistname "
					+ " from jz_chronicapprove app, member_baseinfo mem"
					+ " where mem.ssn = app.ssn"
					+ " and app.chronicapprove_id = '"
					+ chronicApproveDTO.getChronicapproveId() + "'";
			ExecutSQL executSQL = new ExecutSQL();
			executSQL.setExecutsql(sql);
			List<HashMap> rs = executSQLDAO.queryAll(executSQL);
			for (HashMap s : rs) {
				ChronicApproveDTO e = new ChronicApproveDTO();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
				BigDecimal APRRESULT1 = (BigDecimal) s.get("APRRESULT1");
				String a = "";
				String b = "";
				if (null != APRRESULT1) {
					e.setAprresult1(APRRESULT1.shortValue());
				}
				b = (String) s.get("APRIDEA1");
				if (null == b || "".equals(b)) {
					e.setApridea1("");
				} else {
					e.setApridea1((String) s.get("APRIDEA1"));
				}
				Date APRTIME1 = (Date) s.get("APRTIME1");
				if (null != APRTIME1) {
					e.setAprtime1(APRTIME1);
					a = sdf.format((Date) s.get("APRTIME1"));
					e.setSaprtime1(a);
				} else {
					e.setSaprtime1("年月日");
				}
				b = (String) s.get("APRIDEA2");
				if (null == b || "".equals(b)) {
					e.setApridea2("");
				} else {
					e.setApridea2((String) s.get("APRIDEA2"));
				}
				BigDecimal APRRESULT2 = (BigDecimal) s.get("APRRESULT2");
				if (null != APRRESULT2) {
					e.setAprresult2(APRRESULT2.shortValue());
				}
				Date APRTIME2 = (Date) s.get("APRTIME2");
				if (null != APRTIME2) {
					e.setAprtime2(APRTIME2);
					a = sdf.format((Date) s.get("APRTIME2"));
					e.setSaprtime2(a);
				} else {
					e.setSaprtime2("年月日");
				}

				b = (String) s.get("APRIDEA3");
				if (null == b || "".equals(b)) {
					e.setApridea3("");
				} else {
					e.setApridea3((String) s.get("APRIDEA3"));
				}

				BigDecimal APRRESULT3 = (BigDecimal) s.get("APRRESULT3");
				if (null != APRRESULT3) {
					e.setAprresult3(APRRESULT3.shortValue());
				}
				Date APRTIME3 = (Date) s.get("APRTIME3");
				if (null != APRTIME3) {
					e.setAprtime3(APRTIME3);
					a = sdf.format((Date) s.get("APRTIME3"));
					e.setSaprtime3(a);
				} else {
					e.setSaprtime3("年月日");
				}
				Date BEGINTIME = (Date) s.get("BEGINTIME");
				if (null != BEGINTIME) {
					sdf = new SimpleDateFormat("yyyy-MM-dd");
					a = sdf.format(BEGINTIME);
					e.setBegintime(a);
				}
				e.setPaperid((String) s.get("PAPERID"));
				e.setFamilyId((String) s.get("FAMILYNO"));
				e.setFamilyno((String) s.get("MEMBER_ID"));
				e.setRelmaster((String) s.get("RELMASTER"));
				e.setSex((String) s.get("SEX"));
				e.setMasterName((String) s.get("MASTER_NAME"));
				e.setBirthday((Date) s.get("BIRTHDAY"));
				e.setRprkind((String) s.get("ASSISTNAME"));
				e.setRprtype(s.get("AGE").toString());
				e.setName((String) s.get("NAME"));
				e.setSsn((String) s.get("SSN"));
				e.setLinkmode(dictionaryHandle.getDictValue(s.get("ENTITY")
						.toString()));
				list.add(e);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	public List<AspApproveDTO> findPrintasp(AspApproveDTO aspApproveDTO) {
		List<AspApproveDTO> list = new ArrayList<AspApproveDTO>();
		try {
			String sql = "select app.*, "
					+ " mem.PAPERID, "
					+ " mem.FAMILYNO, "
					+ " mem.RELMASTER, "
					+ " mem.SEX, "
					+ " mem.MASTER_NAME, "
					+ " mem.BIRTHDAY,mem.linkmode , "
					+ " mem.ASSIST_TYPE, mem.MEMBER_ID,"
					+ " trunc((to_char(sysdate, 'yyyyMMdd') - to_char(mem.BIRTHDAY, 'yyyyMMdd')) / 10000) as age, "
					+ " decode(mem.ASSIST_TYPE,11,'城市低保',10,'分类施保',01,'农村低保',00,'非低保',null) as assistname "
					+ " from jz_aspapprove app, member_baseinfo mem"
					+ " where  mem.member_id = app.member_id and mem.ds=app.membertype and mem.ds='"
					+ aspApproveDTO.getMemberType() + "' and mem.member_id ='"
					+ aspApproveDTO.getMemberId() + "' "
					+ " and app.aspapprove_id = '"
					+ aspApproveDTO.getAspapproveId() + "'";
			ExecutSQL executSQL = new ExecutSQL();
			executSQL.setExecutsql(sql);
			List<HashMap> rs = executSQLDAO.queryAll(executSQL);
			for (HashMap s : rs) {
				AspApproveDTO e = new AspApproveDTO();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
				BigDecimal APRRESULT1 = (BigDecimal) s.get("APRRESULT1");
				String a = "";
				String b = "";
				if (null != APRRESULT1) {
					e.setAprresult1(APRRESULT1.shortValue());
				}
				b = (String) s.get("APRIDEA1");
				if (null == b || "".equals(b)) {
					e.setApridea1("");
				} else {
					e.setApridea1((String) s.get("APRIDEA1"));
				}
				Date APRTIME1 = (Date) s.get("APRTIME1");
				if (null != APRTIME1) {
					e.setAprtime1(APRTIME1);
					a = sdf.format((Date) s.get("APRTIME1"));
					e.setSaprtime1(a);
				} else {
					e.setSaprtime1("年月日");
				}
				b = (String) s.get("APRIDEA2");
				if (null == b || "".equals(b)) {
					e.setApridea2("");
				} else {
					e.setApridea2((String) s.get("APRIDEA2"));
				}
				BigDecimal APRRESULT2 = (BigDecimal) s.get("APRRESULT2");
				if (null != APRRESULT2) {
					e.setAprresult2(APRRESULT2.shortValue());
				}
				Date APRTIME2 = (Date) s.get("APRTIME2");
				if (null != APRTIME2) {
					e.setAprtime2(APRTIME2);
					a = sdf.format((Date) s.get("APRTIME2"));
					e.setSaprtime2(a);
				} else {
					e.setSaprtime2("年月日");
				}

				b = (String) s.get("APRIDEA3");
				if (null == b || "".equals(b)) {
					e.setApridea3("");
				} else {
					e.setApridea3((String) s.get("APRIDEA3"));
				}

				BigDecimal APRRESULT3 = (BigDecimal) s.get("APRRESULT3");
				if (null != APRRESULT3) {
					e.setAprresult3(APRRESULT3.shortValue());
				}
				Date APRTIME3 = (Date) s.get("APRTIME3");
				if (null != APRTIME3) {
					e.setAprtime3(APRTIME3);
					a = sdf.format((Date) s.get("APRTIME3"));
					e.setSaprtime3(a);
				} else {
					e.setSaprtime3("年月日");
				}
				Date BEGINTIME = (Date) s.get("BEGINTIME");
				if (null != BEGINTIME) {
					sdf = new SimpleDateFormat("yyyy-MM-dd");
					a = sdf.format(BEGINTIME);
					e.setBegintime(a);
				}
				e.setPaperid((String) s.get("PAPERID"));
				e.setFamilyId((String) s.get("FAMILYNO"));
				e.setFamilyno((String) s.get("MEMBER_ID"));
				e.setRelmaster((String) s.get("RELMASTER"));
				e.setSex((String) s.get("SEX"));
				e.setMasterName((String) s.get("MASTER_NAME"));
				e.setBirthday((Date) s.get("BIRTHDAY"));
				e.setRprkind((String) s.get("ASSISTNAME"));
				e.setRprtype(s.get("AGE").toString());
				e.setName((String) s.get("NAME"));
				e.setSsn((String) s.get("SSN"));
				e.setAprmedical((String) s.get("APRMEDICAL"));
				log.error("申报病种>>>>>>>" + e.getAprmedical());
				String LINKMODE = (String) s.get("LINKMODE");
				if (null == LINKMODE || "".equals(LINKMODE)) {
					e.setLinkmode("");
				} else {
					e.setLinkmode((String) s.get("LINKMODE"));
				}
				list.add(e);
			}
			sql = "update jz_aspapprove a  set a.printcount =a.printcount+1 where a.aspapprove_id='"
					+ aspApproveDTO.getAspapproveId() + "'";
			executSQL.setExecutsql(sql);
			executSQLDAO.updateSQL(executSQL);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	public HashMap<String, String> findPrinthrMap(String familyno, String fid) {
		try {
			String sql = "select (select asorgname "
					+ " from sys_t_organization "
					+ " where organization_id = substr(mem.FAMILYNO, 1, 6)) as a, "
					+ " (select asorgname "
					+ " from sys_t_organization "
					+ " where organization_id = substr(mem.FAMILYNO, 1, 8)) as b, "
					+ " (select asorgname "
					+ " from sys_t_organization "
					+ " where organization_id = substr(mem.FAMILYNO, 1, 10)) as c "
					+ " from member_baseinfo mem " + " where mem.MEMBER_ID = '"
					+ familyno + "'";
			ExecutSQL executSQL = new ExecutSQL();
			executSQL.setExecutsql(sql);

			List<HashMap> rs = executSQLDAO.queryAll(executSQL);
			FileHandle y = new FileHandle();
			if (null != rs && rs.size() > 0) {
				HashMap map = rs.get(0);
				String filepath = y.picpath3 + "\\" + fid + "\\" + familyno
						+ "\\" + familyno + ".jpg";
				System.out.println(filepath);
				File file = new File(filepath);
				if (file.exists()) {
					map.put("ANNEXPATH", y.picpath3 + "\\" + fid + "\\"
							+ familyno + "\\" + familyno + ".jpg");
				} else {
				}
				return map;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public HashMap<String, String> findPrintAspMap(String familyno, String fid,
			String memberId) {
		try {
			String sql = "select (select asorgname "
					+ " from sys_t_organization "
					+ " where organization_id = substr('" + fid
					+ "', 1, 6)) as a, " + " (select asorgname "
					+ " from sys_t_organization "
					+ " where organization_id = substr('" + fid
					+ "', 1, 8)) as b, " + " (select asorgname "
					+ " from sys_t_organization "
					+ " where organization_id = substr('" + fid
					+ "', 1, 10)) as c, (select organization_id "
					+ " from sys_t_organization "
					+ " where organization_id = substr('" + fid
					+ "', 1, 10)) as d " + " , (select max (mem.ASSIST_TYPE) "
					+ " from member_baseinfo mem " + " where mem.FAMILYNO = '"
					+ familyno + "') e  from dual";
			System.out.println(sql);
			ExecutSQL executSQL = new ExecutSQL();
			executSQL.setExecutsql(sql);

			List<HashMap> rs = executSQLDAO.queryAll(executSQL);
			FileHandle y = new FileHandle();
			if (null != rs && rs.size() > 0) {
				HashMap map = rs.get(0);
				String filepath = y.picpath3 + "\\" + fid + "\\" + memberId
						+ "\\" + memberId + ".jpg";
				System.out.println(filepath);
				File file = new File(filepath);
				if (file.exists()) {
					map.put("ANNEXPATH", y.picpath3 + "\\" + fid + "\\"
							+ memberId + "\\" + memberId + ".jpg");
				} else {
				}
				String ass = (String) map.get("E");
				if ("11".equals(ass)) {
					map.put("F", "分类");
				} else if ("10".equals(ass)) {
					map.put("F", "调整");
				} else if ("00".equals(ass)) {
					map.put("F", "新申请");
				}
				return map;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	// asp
	public AspApproveDTO findAspApproveById(BigDecimal aspapproveId) {
		AspApproveDTO e = new AspApproveDTO();
		JzAspapprove s = jzAspapproveDAO.selectByPrimaryKey(aspapproveId);
		e.setAspapproveId(s.getAspapproveId());
		e.setFamilyId(s.getFamilyId());
		e.setName(s.getName());
		e.setSsn(s.getSsn());
		e.setEntity(s.getEntity());
		e.setStatus(s.getStatus());
		e.setFlag(s.getFlag());
		e.setAprresult1(s.getAprresult1());
		e.setApridea1(s.getApridea1());
		e.setAprtime1(s.getAprtime1());
		e.setAprperson1(s.getAprperson1());
		e.setAprresult2(s.getAprresult2());
		e.setApridea2(s.getApridea2());
		e.setAprtime2(s.getAprtime2());
		e.setAprperson2(s.getAprperson2());
		e.setAprresult3(s.getAprresult3());
		e.setApridea3(s.getApridea3());
		e.setAprtime3(s.getAprtime3());
		e.setAprperson3(s.getAprperson3());
		e.setAprlevel(s.getAprlevel());
		e.setAprresult(null);
		e.setApridea(null);
		e.setAprtime(null);
		e.setAprperson(null);
		e.setAprmedical(s.getAprmedical());
		e.setMemberId(s.getMemberId());
		e.setMemberType(s.getMembertype());
		AspApproveDTO x = findAspApproveBySsn(e.getMemberId(),
				e.getMemberType());
		e.setPaperid(x.getPaperid());
		return e;
	}

	public AspApproveDTO findAspApproveBySsn(String memberId, String memberType) {
		AspApproveDTO aspApproveDTO = new AspApproveDTO();
		try {
			MemberBaseinfoExample example = new MemberBaseinfoExample();
			example.createCriteria().andMemberIdEqualTo(memberId)
					.andDsEqualTo(memberType);
			List<MemberBaseinfo> rs = memberBaseinfoDAO.selectByExampleWithoutBLOBs(example);
			for (MemberBaseinfo s : rs) {
				aspApproveDTO.setFamilyno(s.getFamilyno());
				aspApproveDTO.setSsn(s.getSsn());
				aspApproveDTO.setName(s.getMembername());
				aspApproveDTO.setPaperid(s.getPaperid());
				aspApproveDTO.setMemberId(s.getMemberId());
				aspApproveDTO.setFamilyId(s.getFamilyno());
				aspApproveDTO.setMemberType(s.getDs());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return aspApproveDTO;
	}

	public AspApproveDTO saveAspApprove(AspApproveDTO aspApproveDTO,
			String orgno) {
		try {
			JzAspapprove po = null;
			if (orgno.length() == 8) {
				po = new JzAspapprove();
				po.setMemberId(aspApproveDTO.getMemberId());
				po.setFamilyId(aspApproveDTO.getFamilyId());
				po.setName(aspApproveDTO.getName());
				po.setSsn(aspApproveDTO.getSsn());
				po.setAprmedical(aspApproveDTO.getAprmedical());
				po.setEntity(aspApproveDTO.getEntity());
				po.setAprresult1(aspApproveDTO.getAprresult1());
				po.setApridea1(aspApproveDTO.getApridea1());
				po.setAprtime1(aspApproveDTO.getAprtime1());
				po.setAprperson1(aspApproveDTO.getAprperson1());
				po.setFlag((short) 1);
				po.setAprlevel((short) 3);
				po.setMembertype(aspApproveDTO.getMemberType());
				if (2 == aspApproveDTO.getAprresult1()) {
					po.setStatus((short) 2);
				} else {
					po.setStatus((short) 0);

				}
				if (null == aspApproveDTO.getAspapproveId()) {
					aspApproveDTO.setAspapproveId(jzAspapproveDAO
							.insertSelective(po));
				} else {
					po.setAspapproveId(aspApproveDTO.getAspapproveId());
					jzAspapproveDAO.updateByPrimaryKeySelective(po);
				}
			} else if (null != aspApproveDTO.getAspapproveId()
					&& orgno.length() == 6) {
				po = jzAspapproveDAO.selectByPrimaryKey(aspApproveDTO
						.getAspapproveId());
				if (1 == aspApproveDTO.getAprresult2()) {
					po.setStatus((short) 1);
					po.setAprlevel((short) -1);
					po.setFlag((short) 1);
				} else if (2 == aspApproveDTO.getAprresult2()) {
					po.setStatus((short) 2);
					po.setFlag((short) 0);
				} else if (3 == aspApproveDTO.getAprresult2()) {
					po.setStatus((short) 0);
					po.setFlag((short) 1);
				}
				po.setAprresult2(aspApproveDTO.getAprresult2());
				po.setApridea2(aspApproveDTO.getApridea2());
				po.setAprtime2(aspApproveDTO.getAprtime2());
				po.setAprperson2(aspApproveDTO.getAprperson2());
				jzAspapproveDAO.updateByPrimaryKeySelective(po);
			} else if (null != aspApproveDTO.getAspapproveId()
					&& orgno.length() == 4) {/*
											 * po =
											 * jzAspapproveDAO.selectByPrimaryKey
											 * (aspApproveDTO
											 * .getAspapproveId()); if (1 ==
											 * aspApproveDTO.getAprresult3()) {
											 * po.setStatus((short) 1);
											 * po.setAprlevel((short) -1);
											 * po.setFlag((short) 1); } else if
											 * (2 ==
											 * aspApproveDTO.getAprresult3()) {
											 * po.setStatus((short) 2);
											 * po.setFlag((short) 0); } else if
											 * (3 ==
											 * aspApproveDTO.getAprresult3()) {
											 * po.setStatus((short) 0);
											 * po.setFlag((short) 1); }
											 * po.setAprresult3
											 * (aspApproveDTO.getAprresult3());
											 * po.setApridea3(aspApproveDTO.
											 * getApridea3());
											 * po.setAprtime3(aspApproveDTO
											 * .getAprtime3());
											 * po.setAprperson3(
											 * aspApproveDTO.getAprperson3());
											 * jzAspapproveDAO
											 * .updateByPrimaryKeySelective(po);
											 */
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return aspApproveDTO;
	}

	public List<AspApproveDTO> findAspApprove(String url, Integer curpage,
			JzAspapproveExample example) {
		List<AspApproveDTO> list = new ArrayList<AspApproveDTO>();
		pager.setCurrentpage(curpage);
		pager.setAll(jzAspapproveDAO.countByExample(example));
		pager.setUrl(url);
		pager.setPagesize(16);
		pager.genToolsmenu();
		List<JzAspapprove> rs = jzAspapproveDAO.selectByExample(example,
				pager.getStart(), pager.getPagesize());

		for (JzAspapprove s : rs) {
			AspApproveDTO e = new AspApproveDTO();
			copyAspApproveDTO(e, s);
			list.add(e);
		}
		return list;
	}

	public List<AspApproveDTO> findAspApprovesBySsn(String memberId,
			String memberType) {
		List<AspApproveDTO> list = new ArrayList<AspApproveDTO>();
		JzAspapproveExample example = new JzAspapproveExample();
		List<Short> values = new ArrayList<Short>();
		values.add((short) 0);
		example.createCriteria().andMemberIdEqualTo(memberId)
				.andStatusIn(values).andFlagEqualTo(new Short((short) 1))
				.andMembertypeEqualTo(memberType);
		List<JzAspapprove> rs = jzAspapproveDAO.selectByExample(example);
		for (JzAspapprove s : rs) {
			AspApproveDTO e = new AspApproveDTO();
			copyAspApproveDTO(e, s);
			list.add(e);
		}
		return list;
	}

	private void copyAspApproveDTO(AspApproveDTO e, JzAspapprove s) {
		e.setAspapproveId(s.getAspapproveId());
		e.setFamilyId(s.getFamilyId());
		e.setName(s.getName());
		e.setSsn(s.getSsn());
		e.setEntity(s.getEntity());
		e.setStatus(s.getStatus());
		e.setFlag(s.getFlag());
		e.setAprresult1(s.getAprresult1());
		e.setApridea1(s.getApridea1());
		e.setAprtime1(s.getAprtime1());
		e.setAprperson1(s.getAprperson1());
		e.setAprresult2(s.getAprresult2());
		e.setApridea2(s.getApridea2());
		e.setAprtime2(s.getAprtime2());
		e.setAprperson2(s.getAprperson2());
		e.setAprresult3(s.getAprresult3());
		e.setApridea3(s.getApridea3());
		e.setAprtime3(s.getAprtime3());
		e.setAprperson3(s.getAprperson3());
		e.setAprlevel(s.getAprlevel());
		e.setAprresult(null);
		e.setApridea(null);
		e.setAprtime(null);
		e.setAprperson(null);
		e.setMemberId(s.getMemberId());
		e.setPrintcount(s.getPrintcount());
		e.setAprmedical(s.getAprmedical());
		e.setMemberType(s.getMembertype());
	}

	public void removeAsp(BigDecimal aspapproveId) {
		jzAspapproveDAO.deleteByPrimaryKey(aspapproveId);
	}

	@SuppressWarnings("unchecked")
	public PersonDTO findAspBySsn(PersonDTO personDTO) {
		try {
			String a = "";
			if (!"".equals(personDTO.getSsn())) {
				a = " and a.ssn = '" + personDTO.getSsn() + "'";
			} else if (!"".equals(personDTO.getPaperid())) {
				a = " and mem.PAPERID = '" + personDTO.getPaperid() + "'";
			} else {
				return personDTO;
			}

			String sql = "select a.name, a.family_id, a.ssn, mem.MEMBER_ID, mem.PAPERID "
					+ "from jz_aspapprove a, member_baseinfo mem "
					+ " where  mem.member_id = a.member_id "
					+ " and a.status = 1 " + " and a.flag = 1 " + a;
			ExecutSQL executSQL = new ExecutSQL();
			executSQL.setExecutsql(sql);
			List<HashMap> rs = executSQLDAO.queryAll(executSQL);
			for (HashMap s : rs) {
				personDTO.setSsn((String) s.get("SSN"));
				personDTO.setMembername((String) s.get("NAME"));
				personDTO.setMemberId((String) s.get("MEMBER_ID"));
				personDTO.setPaperid((String) s.get("PAPERID"));
				personDTO.setFamilyno((String) s.get("FAMILY_ID"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return personDTO;
	}

	@SuppressWarnings("unchecked")
	public List<AspApproveDTO> findAspsBySsn(PersonDTO personDTO) {
		ArrayList<AspApproveDTO> list = new ArrayList<AspApproveDTO>();
		try {
			String sql = "select * from jz_aspapprove a , member_baseinfo mem where mem.FAMILYNO = '"
					+ personDTO.getFamilyno()
					+ "'"
					+ "  and a.member_id <> '"
					+ personDTO.getMemberId()
					+ "' and mem.MEMBER_ID =a.member_id and    mem.ds = a.membertype ";
			ExecutSQL executSQL = new ExecutSQL();
			executSQL.setExecutsql(sql);
			List<HashMap> rs = executSQLDAO.queryAll(executSQL);
			// ASPAPPROVE_ID FAMILY_ID NAME SSN ENTITY STATUS FLAG APRRESULT1
			// APRIDEA1 APRTIME1 APRPERSON1 APRRESULT2 APRIDEA2 APRTIME2
			// APRPERSON2 APRRESULT3 APRIDEA3 APRTIME3 APRPERSON3 APRLEVEL
			// APRMEDICAL
			for (HashMap s : rs) {
				AspApproveDTO e = new AspApproveDTO();
				e.setAprmedical((String) s.get("APRMEDICAL"));
				e.setName((String) s.get("NAME"));
				e.setSsn((String) s.get("SSN"));
				BigDecimal a1 = (BigDecimal) s.get("APRRESULT1");
				BigDecimal a2 = (BigDecimal) s.get("APRRESULT2");
				BigDecimal a3 = (BigDecimal) s.get("APRRESULT3");
				String temp = "";
				if (null == a1) {
				} else {
					if ("1".equals(a1.toString())) {
						temp = "1.街道：同意";
					}
					if ("2".equals(a1.toString())) {
						temp = "1.街道：不同意";
					}
					if ("3".equals(a1.toString())) {
						temp = "1.街道：体检中";
					}
				}
				if (null == a2) {
				} else {
					if ("1".equals(a2.toString())) {
						temp = temp + "2.区县：同意";
					}
					if ("2".equals(a2.toString())) {
						temp = temp + "2.区县：不同意";
					}
					if ("3".equals(a2.toString())) {
						temp = temp + "2.区县：体检中";
					}
				}
				if (null == a3) {
				} else {
					if ("1".equals(a3.toString())) {
						temp = temp + "3.市级：同意";
					}
					if ("2".equals(a3.toString())) {
						temp = temp + "3.市级：不同意";
					}
					if ("3".equals(a3.toString())) {
						temp = temp + "3.市级：体检中";
					}
				}
				e.setAprperson(temp);

				JzAnnexExample example = new JzAnnexExample();
				example.createCriteria().andSsnEqualTo(e.getMemberId())
						.andAnnextypeEqualTo((short) 2);
				List<JzAnnex> as = jzAnnexDAO.selectByExample(example);

				String asss = "";
				for (JzAnnex ss : as) {
					asss = asss + "<a target=\"_blank\" href=\" ~/pic/"
							+ FileHandle.getInstance().webpath2 + "/"
							+ e.getMemberId() + "/" + ss.getAnnexname() + "\">"
							+ ss.getAnnexdetail() + "</a>";
				}
				e.setAddress(asss);
				list.add(e);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public void saveYBSQ(String type, String memberId) {
		ExecutSQL executSQL = new ExecutSQL();
		try {
			String sql = "update family@cs fam "
					+ " set fam.isybsqdb = "
					+ type
					+ " "
					+ " where fam.f_familyid in "
					+ " (select fm.f_familyid from familymember@cs fm where fm.fm_id = '"
					+ memberId + "')";
			executSQL.setExecutsql(sql);
			executSQLDAO.updateSQL(executSQL);
			sql = "update aspinfo@cs a set a.ftype = '"
					+ type
					+ "' , a.ifover=1 where a.ftype is null "
					+ " and a.fid in (select fmi.f_familyid from familymember@cs fmi where fmi.fm_id = '"
					+ memberId + "')";
			executSQL.setExecutsql(sql);
			executSQLDAO.updateSQL(executSQL);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// asp

	@SuppressWarnings({ "rawtypes" })
	public List<ChronicApproveDTO> findChronicMemberStat(String orgno) {
		String orgno1 = orgno.substring(0, 2) + "0"
				+ orgno.substring(2, orgno.length());
		List<ChronicApproveDTO> list = new ArrayList<ChronicApproveDTO>();
		ExecutSQL executSQL = new ExecutSQL();
		try {
			String sql = "select (select o.asorgname "
					+ " from sys_t_organization o "
					+ " where o.organization_id = org.organization_id) as orgname, "
					+ " sum(decode(MEMBER_TYPE, 1, 1, 0)) as c1, "
					+ " sum(decode(MEMBER_TYPE, 2, 1, 0)) as c2 "
					+ " from sys_t_organization org, "
					+ " (select decode(MEMBER_TYPE, " + " 1, " + " FAMILY_ID, "
					+ " substr(FAMILY_ID, 1, 2) || substr(FAMILY_ID, 4)) fno, "
					+ " MEMBER_TYPE " + " from JZ_CHRONICSTATUS  "
					+ " where (FLAG = 1 and state = 1 and FAMILY_ID like '"
					+ orgno + "%') "
					+ " or (FLAG = 1 and state = 1 and FAMILY_ID like '"
					+ orgno1 + "%')) "
					+ " where fno like org.organization_id || '%' "
					+ " and (org.organization_id = '" + orgno
					+ "' or org.parentorgid = '" + orgno + "') "
					+ " group by org.organization_id "
					+ " order by org.organization_id";
			executSQL.setExecutsql(sql);
			List<HashMap> rs = executSQLDAO.queryAll(executSQL);
			for (HashMap s : rs) {
				ChronicApproveDTO e = new ChronicApproveDTO();
				e.setAddress((String) s.get("ORGNAME"));
				e.setApridea(s.get("C1").toString());
				e.setApridea1(s.get("C2").toString());
				list.add(e);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	@SuppressWarnings({ "rawtypes" })
	@Override
	public ChronicApproveDTO findCardView(ChronicApproveDTO chronicApproveDTO) {
		List<ChronicApproveDTO> list = new ArrayList<ChronicApproveDTO>();
		ExecutSQL executSQL = new ExecutSQL();
		try {
			String type = chronicApproveDTO.getMemberType();
			String memeberId = chronicApproveDTO.getMemberId();
			String a = "";
			if ("1".equals(type)) {
				a = "@cs";
			} else if ("2".equals(type)) {
				a = "@nc";
			}
			String sql = "select a.an_filename as a, t.fm_name as b "
					+ " from annex" + a + "  a, familymemberinfo" + a + " t "
					+ "where a.fmid(+)= t.fm_id and t.fm_id='" + memeberId
					+ "'";
			executSQL.setExecutsql(sql);
			List<HashMap> rs = executSQLDAO.queryAll(executSQL);
			for (HashMap s : rs) {
				ChronicApproveDTO e = new ChronicApproveDTO();
				e.setAddress((String) s.get("A"));
				e.setMasterName((String) s.get("B"));
				e.setMemberId(memeberId);
				e.setMemberType(type);
				list.add(e);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (null != list && list.size() > 0) {

		} else {
			ChronicApproveDTO e = new ChronicApproveDTO();
			e.setMasterName("该对象已经被删除");
			list.add(e);
		}
		return list.get(0);
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

	@SuppressWarnings("rawtypes")
	public List<ChronicApproveDTO> findChronicstat(String sql) {
		List<ChronicApproveDTO> list = new ArrayList<ChronicApproveDTO>();
		ExecutSQL executSQL = new ExecutSQL();
		try {
			// CS CS1 CS2 ORGNAME
			executSQL.setExecutsql(sql);
			List<HashMap> rs = executSQLDAO.queryAll(executSQL);
			for (HashMap s : rs) {
				ChronicApproveDTO e = new ChronicApproveDTO();
				e.setAddress((String) s.get("ORGNAME"));
				e.setApridea(s.get("CS1").toString());
				e.setApridea1(s.get("CS2").toString());
				e.setApridea3(s.get("CS").toString());
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

	public void setMemberBaseinfoDAO(MemberBaseinfoDAO memberBaseinfoDAO) {
		this.memberBaseinfoDAO = memberBaseinfoDAO;
	}

	public MemberBaseinfoDAO getMemberBaseinfoDAO() {
		return memberBaseinfoDAO;
	}

	public Pager getPager() {
		return pager;
	}

	public void setPager(Pager pager) {
		this.pager = pager;
	}

	public void setJzChronicapproveDAO(JzChronicapproveDAO jzChronicapproveDAO) {
		this.jzChronicapproveDAO = jzChronicapproveDAO;
	}

	public JzChronicapproveDAO getJzChronicapproveDAO() {
		return jzChronicapproveDAO;
	}

	public void setJzAnnexDAO(JzAnnexDAO jzAnnexDAO) {
		this.jzAnnexDAO = jzAnnexDAO;
	}

	public JzAnnexDAO getJzAnnexDAO() {
		return jzAnnexDAO;
	}

	public JzChronicstatusDAO getJzChronicstatusDAO() {
		return jzChronicstatusDAO;
	}

	public void setJzChronicstatusDAO(JzChronicstatusDAO jzChronicstatusDAO) {
		this.jzChronicstatusDAO = jzChronicstatusDAO;
	}

	public void setDictionaryHandle(DictionaryHandle dictionaryHandle) {
		this.dictionaryHandle = dictionaryHandle;
	}

	public DictionaryHandle getDictionaryHandle() {
		return dictionaryHandle;
	}

	public JzChronicbillDAO getJzChronicbillDAO() {
		return jzChronicbillDAO;
	}

	public void setJzChronicbillDAO(JzChronicbillDAO jzChronicbillDAO) {
		this.jzChronicbillDAO = jzChronicbillDAO;
	}

	public void setJzAspapproveDAO(JzAspapproveDAO jzAspapproveDAO) {
		this.jzAspapproveDAO = jzAspapproveDAO;
	}

	public JzAspapproveDAO getJzAspapproveDAO() {
		return jzAspapproveDAO;
	}

	public SysTOrganizationDAO getSysTOrganizationDAO() {
		return sysTOrganizationDAO;
	}

	public void setSysTOrganizationDAO(SysTOrganizationDAO sysTOrganizationDAO) {
		this.sysTOrganizationDAO = sysTOrganizationDAO;
	}

}
