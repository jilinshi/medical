package com.medical.action;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.medical.dto.ChronicBillDTO;
import com.medical.dto.ChronicBillEmployDTO;
import com.medical.dto.ChronicBillStatDTO;
import com.medical.dto.ChronicStatusDTO;
import com.medical.dto.OrganDTO;
import com.medical.dto.UserInfoDTO;
import com.medical.model.JzChronicstatusExample;
import com.medical.model.JzChronicstatusExample.Criteria;
import com.medical.service.ChronicBillService;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class ChronicBillAction extends ActionSupport {
	private static final long serialVersionUID = 1L;
	private ChronicBillService chronicBillService;
	private BigDecimal money;
	private BigDecimal opt;
	private ChronicBillStatDTO statinfo;
	private String subject;
	private Integer cur_page;
	private String toolsmenu;
	private String term;
	private String value;
	private String result;
	private List<ChronicStatusDTO> css;
	private ChronicStatusDTO chronicStatusDTO;
	private ChronicBillDTO chronicBillDTO;
	private List<ChronicBillDTO> cbs;
	private List<OrganDTO> orgs;
	private String operational;
	private String oid;
	private String opertime1;
	private String opertime2;
	private ChronicBillEmployDTO chronicBillEmployDTO;

	@SuppressWarnings("rawtypes")
	public String generatebillInit() {
		Map session = ActionContext.getContext().getSession();
		UserInfoDTO user = (UserInfoDTO) session.get("user");
		String orgno = user.getOrganizationId();
		if (4 == orgno.length()) {
			setStatinfo(chronicBillService.findCountInfo());
			return SUCCESS;
		} else {
			result = "��û�в����˹��ܵ�Ȩ�ޣ�";
			return "result";
		}
	}

	public String saveGeneratebill() {
		chronicBillService.saveBatchBill(opt.intValue(), subject,
				(null == money ? new BigDecimal("0") : money));
		setResult("�������,���ؼ���!");
		return SUCCESS;
	}

	public String fixgeneratebillquery() {
		return SUCCESS;
	}

	@SuppressWarnings("rawtypes")
	public String querychronicinit() {
		Map session = ActionContext.getContext().getSession();
		UserInfoDTO user = (UserInfoDTO) session.get("user");
		String orgno = user.getOrganizationId();
		if (orgno.length() == 4) {
			return SUCCESS;
		} else {
			result = "��û�в����˹��ܵ�Ȩ�ޣ�";
			return "result";
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String querychronic() {
		Map session = ActionContext.getContext().getSession();
		JzChronicstatusExample e = new JzChronicstatusExample();
		if (null == cur_page) {
			cur_page = 1;
			Criteria c = e.createCriteria();
			if ("ssn".equals(term)) {
				c.andSsnEqualTo(value);
			} else if ("name".equals(term)) {
				c.andNameLike("%" + value + "%");
			}
			c.andStateEqualTo("1");
			c.andFlagEqualTo("1");
			session.put("sql", e);
		} else {
			e = (JzChronicstatusExample) session.get("sql");
		}
		e.setOrderByClause("family_id desc");
		css = chronicBillService.findChronicList(
				"page/business/chronic/querychronic.action", cur_page, e);
		setToolsmenu(chronicBillService.getPager().getToolsmenu());
		return SUCCESS;
	}

	public void validateSaveGeneratebill() {
		this.clearActionErrors();
		if (null == opt) {
			this.addActionError("��ѡ����Ҫ�������ֲ���!");
		} else if (opt.intValue() == 1 || opt.intValue() == 2) {
			if (null == money || money.doubleValue() <= 0) {
				this.addActionError("����ȷ��дÿ��Ҫ���ŵĽ��!");
			}
		}
		if (null == subject || "".equals(subject)) {
			this.addActionError("�����𷢷����Ʋ���Ϊ��!");
		}
		super.validate();
	}

	public String chronichandleinit() {
		cbs = chronicBillService.findChronicBillBySSN(
				chronicStatusDTO.getMemberId(),
				chronicStatusDTO.getMemberType());
		chronicBillDTO = new ChronicBillDTO();
		chronicBillDTO.setSsn(chronicStatusDTO.getSsn());
		chronicBillDTO.setMemberId(chronicStatusDTO.getMemberId());
		chronicBillDTO.setMemberType(chronicStatusDTO.getMemberType());
		if (null != cbs && cbs.size() > 0) {
			chronicBillDTO.setBalance(cbs.get((cbs.size() - 1)).getBalance());
			HashMap<String, String> m = this.chronicBillService
					.findChronicStatBySsn(chronicStatusDTO.getMemberId(),
							chronicStatusDTO.getMemberType());
			result = "����������" + m.get("JZCS") + "��    ������" + m.get("FFJE")
					+ "Ԫ";
		} else {
			chronicBillDTO.setBalance(new BigDecimal(0));
			//result = "û���˵���¼���ܽ��в�¼����";
			//return "result";
		}
		return SUCCESS;
	}

	public String chronichandle() {
		if (null == opt) {
			result = "��ѡ�������ʽ!";
		} else {
			ChronicBillEmployDTO cbedto = chronicBillService
					.findChronicBillEmploy(chronicBillEmployDTO);
			if (cbedto.getEmployeeId() != null) {
				result = chronicBillService.saveChronicBill(chronicBillDTO,
						chronicStatusDTO, opt.toString());
			} else {
				result = "Ȩ�����벻��ȷ������������!";
			}
		}
		cbs = chronicBillService.findChronicBillBySSN(
				chronicBillDTO.getMemberId(), chronicBillDTO.getMemberType());
		if (null != cbs && cbs.size() > 0) {
			chronicBillDTO = new ChronicBillDTO();
			chronicBillDTO.setBalance(cbs.get((cbs.size() - 1)).getBalance());
		}
		return SUCCESS;
	}

	@SuppressWarnings("rawtypes")
	public String querychronicbilldetailinit() {
		Map session = ActionContext.getContext().getSession();
		UserInfoDTO userinfo = (UserInfoDTO) session.get("user");
		String orgid = userinfo.getOrganizationId();
		setOrgs(chronicBillService.getOrganList(orgid));
		return SUCCESS;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String querychronicbilldetail() {
		Map session = ActionContext.getContext().getSession();
		String sql = "select bill.*, mem.PAPERID from jz_chronicbill bill ,member_baseinfo mem where "
				+ "1=1  and  mem.MEMBER_ID=bill.member_id and mem.ds=bill.member_type ";
		String var = value;
		if (null == cur_page) {
			cur_page = 1;

			if (!"".equals(var)) {
				if ("=".equals(operational)) {
					var = " = '" + var + "'";
				} else if ("like".equals(operational)) {
					var = "like  '%" + var + "%'";
				} else {
					var = "";
				}

				if ("SSN".equals(term)) {
					sql = sql + " and bill.SSN  " + var;
				} else if ("FAMILYNO".equals(term)) {
					sql = sql + " and bill.family_id  " + var;
				} else if ("MEMBERNAME".equals(term)) {
					sql = sql + " and  bill.name  " + var;
				} else if ("PAPERID".equals(term)) {
					sql = sql + " and mem.PAPERID= '" + value + "')";
				} else {
				}
			}
			if ("".equals(opertime1) && "".equals(opertime2)) {
				sql = sql + " and 1=1 ";
			} else if ("".equals(opertime1) && !"".equals(opertime2)) {
				sql = sql + " AND  bill.opttime  <= TO_DATE('"
						+ opertime2.substring(0, 10) + "', 'yyyy-MM-dd')";
			} else if ("".equals(opertime2) && !"".equals(opertime1)) {
				sql = sql + " AND  bill.opttime  <= TO_DATE('"
						+ opertime1.substring(0, 10) + "', 'yyyy-MM-dd')";
			} else {
				sql = sql + " AND  bill.opttime  BETWEEN TO_DATE('"
						+ opertime1.substring(0, 10)
						+ "', 'yyyy-MM-dd') AND TO_DATE('"
						+ opertime2.substring(0, 10)
						+ " 23:59:59', 'yyyy-MM-dd hh24:mi:ss')";
			}
			if (oid == null || "".equals(oid)) {

			} else {
				sql = sql + " and ( bill.family_id like '" + this.oid
						+ "%' or bill.family_id like '" + oid.substring(0, 2)
						+ "0" + oid.substring(2, oid.length())
						+ "%' ) and mem.on_no like '" + oid + "%' ";
			}
			if ("1".equals(subject)) {
				sql = sql
						+ " and instr(bill.subject ,'�˷�')=0  and bill.payout=0";
			}
			sql = sql + " order by bill.family_id,bill.member_id,bill.opttime";
			session.put("sql", sql);
		} else {
			sql = (String) session.get("sql");
		}
		cbs = chronicBillService.findChronicBills(
				"querychronicbilldetail.action", cur_page, sql);
		setToolsmenu(chronicBillService.getPager().getToolsmenu());
		UserInfoDTO userinfo = (UserInfoDTO) session.get("user");
		String orgid = userinfo.getOrganizationId();
		setOrgs(chronicBillService.getOrganList(orgid));
		return SUCCESS;
	}

	public ChronicBillService getChronicBillService() {
		return chronicBillService;
	}

	public BigDecimal getMoney() {
		return money;
	}

	public BigDecimal getOpt() {
		return opt;
	}

	public ChronicBillStatDTO getStatinfo() {
		return statinfo;
	}

	public String getSubject() {
		return subject;
	}

	public void setChronicBillService(ChronicBillService chronicBillService) {
		this.chronicBillService = chronicBillService;
	}

	public void setMoney(BigDecimal money) {
		this.money = money;
	}

	public void setOpt(BigDecimal opt) {
		this.opt = opt;
	}

	public void setStatinfo(ChronicBillStatDTO statinfo) {
		this.statinfo = statinfo;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public void setToolsmenu(String toolsmenu) {
		this.toolsmenu = toolsmenu;
	}

	public String getToolsmenu() {
		return toolsmenu;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getResult() {
		return result;
	}

	public void setCss(List<ChronicStatusDTO> css) {
		this.css = css;
	}

	public List<ChronicStatusDTO> getCss() {
		return css;
	}

	public Integer getCur_page() {
		return cur_page;
	}

	public void setCur_page(Integer curPage) {
		cur_page = curPage;
	}

	public String getTerm() {
		return term;
	}

	public void setTerm(String term) {
		this.term = term;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public ChronicStatusDTO getChronicStatusDTO() {
		return chronicStatusDTO;
	}

	public void setChronicStatusDTO(ChronicStatusDTO chronicStatusDTO) {
		this.chronicStatusDTO = chronicStatusDTO;
	}

	public ChronicBillDTO getChronicBillDTO() {
		return chronicBillDTO;
	}

	public void setChronicBillDTO(ChronicBillDTO chronicBillDTO) {
		this.chronicBillDTO = chronicBillDTO;
	}

	public List<ChronicBillDTO> getCbs() {
		return cbs;
	}

	public void setCbs(List<ChronicBillDTO> cbs) {
		this.cbs = cbs;
	}

	public void setOrgs(List<OrganDTO> orgs) {
		this.orgs = orgs;
	}

	public List<OrganDTO> getOrgs() {
		return orgs;
	}

	public String getOperational() {
		return operational;
	}

	public void setOperational(String operational) {
		this.operational = operational;
	}

	public String getOid() {
		return oid;
	}

	public void setOid(String oid) {
		this.oid = oid;
	}

	public String getOpertime1() {
		return opertime1;
	}

	public void setOpertime1(String opertime1) {
		this.opertime1 = opertime1;
	}

	public String getOpertime2() {
		return opertime2;
	}

	public void setOpertime2(String opertime2) {
		this.opertime2 = opertime2;
	}

	public ChronicBillEmployDTO getChronicBillEmployDTO() {
		return chronicBillEmployDTO;
	}

	public void setChronicBillEmployDTO(
			ChronicBillEmployDTO chronicBillEmployDTO) {
		this.chronicBillEmployDTO = chronicBillEmployDTO;
	}

}
