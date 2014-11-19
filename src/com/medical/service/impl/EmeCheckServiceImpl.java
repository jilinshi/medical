package com.medical.service.impl;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.medical.common.Pager;
import com.medical.dao.ExecutSQLDAO;
import com.medical.dao.JzEmecheckDAO;
import com.medical.dao.MemberBaseinfoDAO;
import com.medical.dto.EmeCheckDTO;
import com.medical.model.ExecutSQL;
import com.medical.model.JzEmecheck;
import com.medical.model.MemberBaseinfo;
import com.medical.model.MemberBaseinfoExample;
import com.medical.service.EmeCheckService;

public class EmeCheckServiceImpl implements EmeCheckService {
	private Pager pager;
	private JzEmecheckDAO jzEmecheckDAO;
	private ExecutSQLDAO executSQLDAO;
	private MemberBaseinfoDAO memberBaseinfoDAO;

	public ExecutSQLDAO getExecutSQLDAO() {
		return executSQLDAO;
	}

	public JzEmecheckDAO getJzEmecheckDAO() {
		return jzEmecheckDAO;
	}

	public MemberBaseinfoDAO getMemberBaseinfoDAO() {
		return memberBaseinfoDAO;
	}

	public EmeCheckDTO getMemberInfo(String memberId) {
		EmeCheckDTO emeCheckDTO = new EmeCheckDTO();

		MemberBaseinfoExample example = new MemberBaseinfoExample();
		example.createCriteria().andMemberIdEqualTo(memberId);

		List<MemberBaseinfo> rs = memberBaseinfoDAO.selectByExampleWithoutBLOBs(example);

		if (null != rs && rs.size() > 0) {
			MemberBaseinfo member = (MemberBaseinfo) rs.get(0);
			emeCheckDTO.setAddress(member.getAddress());
			emeCheckDTO.setFamilyno(member.getFamilyno());
			emeCheckDTO.setMembername(member.getMembername());
			emeCheckDTO.setLinkmode(member.getLinkmode());
			emeCheckDTO.setMemberId(member.getMemberId());
			emeCheckDTO.setSsn(member.getSsn());
			emeCheckDTO.setSex(member.getSex());
			emeCheckDTO.setAssistType(member.getAssistType());
		}

		return emeCheckDTO;

	}

	public Pager getPager() {
		return pager;
	}

	@SuppressWarnings("unchecked")
	public List<EmeCheckDTO> queryEmeCheckApproves(String jwhere, int cur_page) {

		List<EmeCheckDTO> list = new ArrayList<EmeCheckDTO>();

		String sql = "SELECT E.EMECHECK_ID, " + " E.SSN, "
				+ "  E.RELIEFBECAUSE, " + "  E.INTERVIEW, "
				+ " E.INTERVIEWTIME, " + " E.COMOFSTREET, "
				+ " E.RESULTOFSTREET, " + " E.STREETAPPTIME, "
				+ " E.AUDITOROFSTREET, " + " E.COMOFAREG, "
				+ " E.RESULTOFAREG, " + " E.AREGAPPTIME, "
				+ " E.AUDITOROFAREG, " + " E.AREGMASTER, " + " E.SALMONEY, "
				+ " M.MEMBERNAME, " + " M.PAPERID, " + " M.FAMILYNO, "
				+ " M.RPRADDRESS, " + " M.ADDRESS, " + " M.ASSIST_TYPE , "
				+ "M.LINKMODE ,M.MASTER_NAME ,M.RELMASTER,M.MEMBER_ID"
				+ " FROM JZ_EMECHECK E, MEMBER_BASEINFO M "
				+ " WHERE M.SSN = E.SSN " + jwhere
				+ "  order by M.FAMILYNO , E.INTERVIEWTIME";

		try {
			ExecutSQL executSQL = new ExecutSQL();

			executSQL.setExecutsql(sql);
			pager.setAll(executSQLDAO.queryCnt(executSQL));
			pager.setUrl("showEmeCheckApproves.action");
			pager.setCurrentpage(cur_page);
			pager.setPagesize(14);
			pager.getToolsmenu();

			executSQL.setEnd(pager.getEnd());
			executSQL.setStart(pager.getStart());

			List<HashMap> rs = executSQLDAO.queryRow(executSQL);

			for (HashMap map : rs) {
				EmeCheckDTO dto = new EmeCheckDTO();

				BigDecimal EMECHECK_ID = (BigDecimal) map.get("EMECHECK_ID");
				String SSN = (String) map.get("SSN");
				String RELIEFBECAUSE = (String) map.get("RELIEFBECAUSE");
				String INTERVIEW = (String) map.get("INTERVIEW");
				Date INTERVIEWTIME = (Date) map.get("INTERVIEWTIME");

				String COMOFSTREET = (String) map.get("COMOFSTREET");
				BigDecimal RESULTOFSTREET = (BigDecimal) map
						.get("RESULTOFSTREET");
				if (null == RESULTOFSTREET) {
					RESULTOFSTREET = new BigDecimal(0);
				}
				Date STREETAPPTIME = (Date) map.get("STREETAPPTIME");
				String AUDITOROFSTREET = (String) map.get("AUDITOROFSTREET");

				String COMOFAREG = (String) map.get("COMOFAREG");
				BigDecimal RESULTOFAREG = (BigDecimal) map.get("RESULTOFAREG");
				if (null == RESULTOFAREG) {
					RESULTOFAREG = new BigDecimal(0);
				}
				Date AREGAPPTIME = (Date) map.get("AREGAPPTIME");
				String AUDITOROFAREG = (String) map.get("AUDITOROFAREG");

				String AREGMASTER = (String) map.get("AREGMASTER");
				BigDecimal SALMONEY = (BigDecimal) map.get("SALMONEY");
				if (null == SALMONEY) {
					SALMONEY = new BigDecimal(0);
				}
				String MEMBERID = (String) map.get("MEMBER_ID");

				String MEMBERNAME = (String) map.get("MEMBERNAME");
				if (null == MEMBERNAME || "".equals(MEMBERNAME)) {
					MEMBERNAME = "";
				}
				String PAPERID = (String) map.get("PAPERID");
				if (null == PAPERID || "".equals(PAPERID)) {
					PAPERID = "";
				}
				String FAMILYNO = (String) map.get("FAMILYNO");
				if (null == FAMILYNO || "".equals(FAMILYNO)) {
					FAMILYNO = "";
				}
				String ADDRESS = (String) map.get("ADDRESS");
				if (null == ADDRESS || "".equals(ADDRESS)) {
					ADDRESS = "";
				}
				Date BIRTHDAY = (Date) map.get("BIRTHDAY");

				String LINKMODE = (String) map.get("LINKMODE");
				String MASTER_NAME = (String) map.get("MASTER_NAME");
				String RELMASTER = (String) map.get("RELMASTER");
				String SEX = (String) map.get("SEX");

				dto.setAddress(ADDRESS);
				dto.setAregapptime(AREGAPPTIME);
				dto.setAregmaster(AREGMASTER);
				dto.setAuditorofareg(AUDITOROFAREG);
				dto.setAuditorofstreet(AUDITOROFSTREET);
				dto.setBirthday(BIRTHDAY);
				dto.setComofareg(COMOFAREG);
				dto.setComofstreet(COMOFSTREET);
				dto.setEmecheckId(EMECHECK_ID);
				dto.setFamilyno(FAMILYNO);
				dto.setInterview(INTERVIEW);
				dto.setInterviewtime(INTERVIEWTIME);
				dto.setLinkmode(LINKMODE);
				dto.setMasterName(MASTER_NAME);
				dto.setRelmaster(RELMASTER);
				dto.setPaperid(PAPERID);
				dto.setReliefbecause(RELIEFBECAUSE);
				dto.setResultofareg(RESULTOFAREG.shortValue());
				dto.setResultofstreet(RESULTOFSTREET.shortValue());
				dto.setSalmoney(SALMONEY);
				dto.setMemberId(MEMBERID);
				dto.setStreetapptime(STREETAPPTIME);
				dto.setSsn(SSN);
				dto.setMembername(MEMBERNAME);
				dto.setSex(SEX);
				list.add(dto);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	public List<EmeCheckDTO> queryEmeCheckViews(String jwhere, int cur_page) {

		List<EmeCheckDTO> list = new ArrayList<EmeCheckDTO>();

		String sql = "SELECT M.MEMBER_ID,M.SSN ,"
				+ " M.MEMBERNAME, "
				+ " M.PAPERID, "
				+ " M.FAMILYNO, "
				+ " M.RPRADDRESS, "
				+ " M.ADDRESS, "
				+ " M.ASSIST_TYPE  , M.BIRTHDAY "
				+ " FROM MEMBER_BASEINFO M "
				+ " WHERE NOT EXISTS ( SELECT 1 FROM JZ_EMECHECK E WHERE E.SSN =M.SSN "
				+ "AND (E.RESULTOFAREG = 0 OR E.RESULTOFAREG IS NULL)) "
				+ jwhere + " order by M.FAMILYNO";

		try {
			ExecutSQL executSQL = new ExecutSQL();

			executSQL.setExecutsql(sql);
			pager.setAll(executSQLDAO.queryCnt(executSQL));
			pager.setUrl("showEmeCheckViews.action");
			pager.setCurrentpage(cur_page);
			pager.setPagesize(14);
			pager.getToolsmenu();

			executSQL.setEnd(pager.getEnd());
			executSQL.setStart(pager.getStart());

			List<HashMap> rs = executSQLDAO.queryRow(executSQL);

			for (HashMap map : rs) {
				EmeCheckDTO dto = new EmeCheckDTO();

				String MEMBERID = (String) map.get("MEMBER_ID");

				String MEMBERNAME = (String) map.get("MEMBERNAME");
				if (null == MEMBERNAME || "".equals(MEMBERNAME)) {
					MEMBERNAME = "";
				}
				String PAPERID = (String) map.get("PAPERID");
				if (null == PAPERID || "".equals(PAPERID)) {
					PAPERID = "";
				}
				String FAMILYNO = (String) map.get("FAMILYNO");
				if (null == FAMILYNO || "".equals(FAMILYNO)) {
					FAMILYNO = "";
				}
				String SSN = (String) map.get("SSN");
				if (null == SSN || "".equals(SSN)) {
					SSN = "";
				}
				String ADDRESS = (String) map.get("ADDRESS");
				if (null == ADDRESS || "".equals(ADDRESS)) {
					ADDRESS = "";
				}
				Date BIRTHDAY = (Date) map.get("BIRTHDAY");

				dto.setAddress(ADDRESS);
				dto.setFamilyno(FAMILYNO);
				dto.setMembername(MEMBERNAME);
				dto.setSsn(SSN);
				dto.setBirthday(BIRTHDAY);
				dto.setPaperid(PAPERID);
				dto.setMemberId(MEMBERID);
				list.add(dto);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return list;
	}

	// 区县
	public void saveAregApproves(EmeCheckDTO emeCheckDTO) {
		JzEmecheck record = new JzEmecheck();
		record.setAregapptime(new Date());
		record.setAuditorofareg(emeCheckDTO.getAuditorofareg());
		record.setComofareg(emeCheckDTO.getComofareg());
		record.setEmecheckId(emeCheckDTO.getEmecheckId());
		record.setResultofareg(emeCheckDTO.getResultofareg());
		record.setSalmoney(emeCheckDTO.getSalmoney());
		record.setAregmaster(emeCheckDTO.getAregmaster());
		this.jzEmecheckDAO.updateByPrimaryKeySelective(record);
	}

	// 走访
	public void saveInterview(EmeCheckDTO emeCheckDTO) {
		try {
			JzEmecheck record = new JzEmecheck();
			record.setInterview(emeCheckDTO.getInterview());
			record.setInterviewtime(new Date());
			record.setReliefbecause(emeCheckDTO.getReliefbecause());
			record.setSsn(emeCheckDTO.getSsn());
			this.jzEmecheckDAO.insertSelective(record);
		} catch (RuntimeException re) {
			re.printStackTrace();
		}
	}

	// 街道
	public void saveStreetApproves(EmeCheckDTO emeCheckDTO) {
		JzEmecheck record = new JzEmecheck();
		record.setStreetapptime(new Date());
		record.setAuditorofstreet(emeCheckDTO.getAuditorofstreet());
		record.setComofstreet(emeCheckDTO.getComofstreet());
		record.setEmecheckId(emeCheckDTO.getEmecheckId());
		record.setResultofstreet(emeCheckDTO.getResultofstreet());
		this.jzEmecheckDAO.updateByPrimaryKeySelective(record);
	}

	public void setExecutSQLDAO(ExecutSQLDAO executSQLDAO) {
		this.executSQLDAO = executSQLDAO;
	}

	public void setJzEmecheckDAO(JzEmecheckDAO jzEmecheckDAO) {
		this.jzEmecheckDAO = jzEmecheckDAO;
	}

	public void setMemberBaseinfoDAO(MemberBaseinfoDAO memberBaseinfoDAO) {
		this.memberBaseinfoDAO = memberBaseinfoDAO;
	}

	public void setPager(Pager pager) {
		this.pager = pager;
	}

	public EmeCheckDTO getEmeCheckInfo(String emecheckId, String memberId) {

		JzEmecheck jzEmecheck = this.jzEmecheckDAO
				.selectByPrimaryKey(new BigDecimal(emecheckId));

		EmeCheckDTO emeCheckDTO = new EmeCheckDTO();

		MemberBaseinfoExample example = new MemberBaseinfoExample();
		example.createCriteria().andMemberIdEqualTo(memberId);

		List<MemberBaseinfo> rs = memberBaseinfoDAO.selectByExampleWithoutBLOBs(example);

		if (null != rs && rs.size() > 0) {
			MemberBaseinfo member = (MemberBaseinfo) rs.get(0);
			emeCheckDTO.setAddress(member.getAddress());
			emeCheckDTO.setFamilyno(member.getFamilyno());
			emeCheckDTO.setMembername(member.getMembername());
			emeCheckDTO.setLinkmode(member.getLinkmode());
			emeCheckDTO.setMemberId(member.getMemberId());
			emeCheckDTO.setSsn(member.getSsn());
			emeCheckDTO.setSex(member.getSex());
			emeCheckDTO.setAssistType(member.getAssistType());
			emeCheckDTO.setPaperid(member.getPaperid());
			emeCheckDTO.setBirthday(member.getBirthday());
			emeCheckDTO.setMasterName(member.getMasterName());
			emeCheckDTO.setRelmaster(member.getRelmaster());
		}
		emeCheckDTO.setAregapptime(jzEmecheck.getAregapptime());
		emeCheckDTO.setAregmaster(jzEmecheck.getAregmaster());
		emeCheckDTO.setAuditorofareg(jzEmecheck.getAuditorofareg());
		emeCheckDTO.setAuditorofstreet(jzEmecheck.getAuditorofstreet());
		emeCheckDTO.setComofareg(jzEmecheck.getComofareg());
		emeCheckDTO.setComofstreet(jzEmecheck.getComofstreet());
		emeCheckDTO.setEmecheckId(jzEmecheck.getEmecheckId());
		emeCheckDTO.setInterview(jzEmecheck.getInterview());
		emeCheckDTO.setInterviewtime(jzEmecheck.getInterviewtime());
		emeCheckDTO.setReliefbecause(jzEmecheck.getReliefbecause());
		emeCheckDTO.setResultofareg(jzEmecheck.getResultofareg());
		emeCheckDTO.setResultofstreet(jzEmecheck.getResultofstreet());
		emeCheckDTO.setSalmoney(jzEmecheck.getSalmoney());
		emeCheckDTO.setStreetapptime(jzEmecheck.getStreetapptime());

		return emeCheckDTO;
	}

}
