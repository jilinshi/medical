package com.medical.action;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.tempuri.IService1;
import org.tempuri.IService1Proxy;

import com.medical.dto.CheckDTO;
import com.medical.dto.OrganDTO;
import com.medical.dto.UserInfoDTO;
import com.medical.dto.YBCheckDTO;
import com.medical.service.BaseinfoService;
import com.medical.service.SearchService;
import com.medical.util.IDCardUtil;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class CheckAction extends ActionSupport {
	private static final long serialVersionUID = 1L;
	private List<CheckDTO> cds;
	private Integer cur_page;
	private String toolsmenu;
	private CheckDTO checkDTO;
	private BaseinfoService baseinfoService;
	private List<OrganDTO> orgs;
	private SearchService searchService;
	private String term;
	private String operational;
	private String value;
	private String oid;
	private String memberId;
	private String ds;
	private String paperid;
	private String membername;
	private List<YBCheckDTO> ybcds;
	private YBCheckDTO ybcheckDTO;
	private String result;
	
	@SuppressWarnings("rawtypes")
	public String checkQueryInit(){
		Map session = ActionContext.getContext().getSession();
		UserInfoDTO user = (UserInfoDTO) session.get("user");
		this.setOrgs(this.searchService.getOrganList(user.getOrganizationId()));
		return SUCCESS;
	}
 	// 医保卡查询
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String checkQuery() { 
		Map session = ActionContext.getContext().getSession();
		UserInfoDTO user = (UserInfoDTO) session.get("user");
		String sql = "";
		String jwhere = "";
		String var = value;
		if (null == cur_page || "".equals(cur_page)) {
			cur_page = 1;
			if (!"".equals(var)) {
				if ("=".equals(operational)) {
					var = " = '" + var + "'";
				} else if ("like".equals(operational)) {
					var = "like  '%" + var + "%'";
				} else {
					var = "";
				}
	
				if ("PAPERID".equals(term)) {
					jwhere = jwhere + " and mem.PAPERID  " + var;
				} else if ("FAMILYNO".equals(term)) {
					jwhere = jwhere + " and mem.FAMILYNO  " + var;
				} else if ("MEMBERNAME".equals(term)) {
					jwhere = jwhere + " and mem.MEMBERNAME  " + var;
				} else {
				}
			}
			sql = " select mem.member_id,mem.ds,mem.membername,mem.paperid,mem.familyno, " 
					+ " mem.ssn,ts.ssn1,ts.ssn2,ts.ssn3,mem.personstate,mem.assist_type,mem.asort "
					+ " from member_baseinfo mem left join test_ssn ts "
					+ " on mem.member_id = ts.member_id and mem.ds = ts.ds "
					+ " where mem.on_no like '" + oid + "%' "
					+ jwhere 
					+ " and mem.o_ps = '正常' and mem.ds = '1' " 
					+ " and substr(mem.assist_type,0,1)='1' ";
			session.put("sql", sql);
		} else {
			sql = (String) session.get("sql");
		}
		cds = baseinfoService
				.findAllMemberInfo(
						"checkQuery.action",
						cur_page, sql);
		setToolsmenu(baseinfoService.getPager().getToolsmenu());
		this.setOrgs(this.searchService.getOrganList(user.getOrganizationId()));
		return SUCCESS;
	}
	
 	// 医保卡核对查询
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String checkSsn() { 
		Map session = ActionContext.getContext().getSession();
		UserInfoDTO user = (UserInfoDTO) session.get("user");
		String sql = "";
		String jwhere = "";
		String var = value;
		if (null == cur_page || "".equals(cur_page)) {
			cur_page = 1;
			if (!"".equals(var)) {
				if ("=".equals(operational)) {
					var = " = '" + var + "'";
				} else if ("like".equals(operational)) {
					var = "like  '%" + var + "%'";
				} else {
					var = "";
				}
	
				if ("PAPERID".equals(term)) {
					jwhere = jwhere + " and mem.PAPERID  " + var;
				} else if ("FAMILYNO".equals(term)) {
					jwhere = jwhere + " and mem.FAMILYNO  " + var;
				} else if ("MEMBERNAME".equals(term)) {
					jwhere = jwhere + " and mem.MEMBERNAME  " + var;
				} else {
				}
			}
			sql = " select mem.member_id,mem.ds,mem.membername,mem.paperid,mem.familyno, " 
					+ " mem.ssn,ts.ssn1,ts.ssn2,ts.ssn3,mem.personstate,mem.assist_type,mem.asort "
					+ " from member_baseinfo mem left join test_ssn ts "
					+ " on mem.member_id = ts.member_id and mem.ds = ts.ds "
					+ " where mem.on_no like '" + oid + "%' "
					+ jwhere 
					+ " and mem.o_ps = '正常' and mem.ds = '1' "
					+ " and substr(mem.assist_type,0,1)='1' ";
					//+ " and ts.ssn1 is null ";
			session.put("sql", sql);
		} else {
			sql = (String) session.get("sql");
		}
		cds = baseinfoService
				.findAllMemberInfo(
						"checkSsn.action",
						cur_page, sql);
		setToolsmenu(baseinfoService.getPager().getToolsmenu());
		this.setOrgs(this.searchService.getOrganList(user.getOrganizationId()));
		return SUCCESS;
	}
	
	@SuppressWarnings("rawtypes")
	public String checkSsnInit(){
		Map session = ActionContext.getContext().getSession();
		UserInfoDTO user = (UserInfoDTO) session.get("user");
		this.setOrgs(this.searchService.getOrganList(user.getOrganizationId()));
		return SUCCESS;
	}
	
	@SuppressWarnings("rawtypes")
	public String installSsnInit() throws UnsupportedEncodingException, DocumentException{
		CheckDTO cdto = new CheckDTO();
		cdto.setMemberId(memberId);
		cdto.setDs(ds);
		//System.out.println("开始时间："+System.currentTimeMillis());
		checkDTO = this.baseinfoService.findMemberInfo(cdto);
		//System.out.println("结束时间："+System.currentTimeMillis());
		try {
			String name=java.net.URLDecoder.decode(membername , "utf-8");
			IService1 iService1 = new IService1Proxy();
			//System.out.println("Webservice开始时间："+System.currentTimeMillis());
			//15位身份证号码要转换为18位
			if(paperid.length()==15){
				paperid = IDCardUtil.from15to18(19, paperid);
			}
			String xml = iService1.getMedicareInfoSingle(paperid.toUpperCase(), name);
			//System.out.println("Webservice结束时间："+System.currentTimeMillis());
			Document document = DocumentHelper.parseText(xml);
			String resultFlag = document.selectSingleNode(
					"//GetMedicareInfoSingle/Result/ResultFlag").getText();
			String message = document.selectSingleNode(
					"//GetMedicareInfoSingle/Result/Message").getText();
			if("1".equals(resultFlag)){
				List list = document.selectNodes("//GetMedicareInfoSingle/NewDataSet/jljzj"); 
				Iterator iter = list.iterator();
				String membername="";
				String paperid="";
				ybcds = new ArrayList<YBCheckDTO>();
				int i=0;
				ybcheckDTO = new YBCheckDTO();
				while(iter.hasNext()) {
					Element ele =(Element)iter.next();
		        	String ssn = ele.element("医保编号").getText();
		        	membername = ele.element("姓名").getText();
		        	paperid = ele.element("身份证号码").getText();
		        	if(i==0){
		        		ybcheckDTO.setSsn1(ssn);
		        	}else if(i==1){
		        		ybcheckDTO.setSsn2(ssn);
		        	}else if(i==2){
		        		ybcheckDTO.setSsn3(ssn);
		        	}
		            i++;
		        }
				
				ybcheckDTO.setYbmembername(membername);
				ybcheckDTO.setYbpaperid(paperid);
				ybcheckDTO.setMessage(message);
				
			}
			
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return SUCCESS;
	}
	
	public String installSsn(){
		JSONObject json = new JSONObject();
		int u=baseinfoService.updateTestSsn(checkDTO);
		json.put("u", u);
		result = json.toString();
		//更新医保数据
		checkDTO = this.baseinfoService.findMemberInfo(checkDTO);
		String status = "";
		if ("1".equals(checkDTO.getAssistType().substring(0, 1))
				&& checkDTO.getAsort().compareTo(new BigDecimal("1"))==0) {
			status = "2";
		} else if ("1".equals(checkDTO.getAssistType().substring(0, 1))
				&& checkDTO.getAsort().compareTo(new BigDecimal("0"))==0) {
			status = "1";
		} else {
			status = "0";
		}
		try {
			IService1 iService1 = new IService1Proxy();
			String icid = checkDTO.getPaperid();
			//System.out.println("Webservice开始时间："+System.currentTimeMillis());
			//15位身份证号码要转换为18位
			if(checkDTO.getPaperid().length()==15){
				icid = IDCardUtil.from15to18(19, paperid);
			}
			Date currentTime = new Date();
			SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
			String dateString = formatter.format(currentTime);
			String xml1 = iService1.setAssistStatusSingle(
					icid.toUpperCase(), checkDTO.getMembername(), checkDTO.getFamilyno(),
					status, dateString);
			System.out.println("---民政to医保同步_start---");
			System.out.println(xml1);
			System.out.println("---民政to医保同步_end---");
		}catch (RemoteException e) {
				e.printStackTrace();
		}
		return SUCCESS;
	}

	public Integer getCur_page() {
		return cur_page;
	}

	public void setCur_page(Integer cur_page) {
		this.cur_page = cur_page;
	}

	public String getToolsmenu() {
		return toolsmenu;
	}

	public void setToolsmenu(String toolsmenu) {
		this.toolsmenu = toolsmenu;
	}

	public List<CheckDTO> getCds() {
		return cds;
	}

	public void setCds(List<CheckDTO> cds) {
		this.cds = cds;
	}

	public BaseinfoService getBaseinfoService() {
		return baseinfoService;
	}

	public void setBaseinfoService(BaseinfoService baseinfoService) {
		this.baseinfoService = baseinfoService;
	}

	public CheckDTO getCheckDTO() {
		return checkDTO;
	}

	public void setCheckDTO(CheckDTO checkDTO) {
		this.checkDTO = checkDTO;
	}
	public List<OrganDTO> getOrgs() {
		return orgs;
	}
	public void setOrgs(List<OrganDTO> orgs) {
		this.orgs = orgs;
	}
	public SearchService getSearchService() {
		return searchService;
	}
	public void setSearchService(SearchService searchService) {
		this.searchService = searchService;
	}
	public String getTerm() {
		return term;
	}
	public void setTerm(String term) {
		this.term = term;
	}
	public String getOperational() {
		return operational;
	}
	public void setOperational(String operational) {
		this.operational = operational;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getOid() {
		return oid;
	}
	public void setOid(String oid) {
		this.oid = oid;
	}
	public String getMemberId() {
		return memberId;
	}
	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}
	public String getDs() {
		return ds;
	}
	public void setDs(String ds) {
		this.ds = ds;
	}
	public String getPaperid() {
		return paperid;
	}
	public void setPaperid(String paperid) {
		this.paperid = paperid;
	}
	public String getMembername() {
		return membername;
	}
	public void setMembername(String membername) {
		this.membername = membername;
	}
	public List<YBCheckDTO> getYbcds() {
		return ybcds;
	}
	public void setYbcds(List<YBCheckDTO> ybcds) {
		this.ybcds = ybcds;
	}
	public YBCheckDTO getYbcheckDTO() {
		return ybcheckDTO;
	}
	public void setYbcheckDTO(YBCheckDTO ybcheckDTO) {
		this.ybcheckDTO = ybcheckDTO;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
}
