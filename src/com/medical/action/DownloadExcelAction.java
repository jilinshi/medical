package com.medical.action;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.medical.common.ExportExcel;
import com.medical.dto.BizCheckDTO;
import com.medical.dto.UserInfoDTO;
import com.medical.service.SearchService;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class DownloadExcelAction extends ActionSupport {

	private SearchService searchService;

	private static final long serialVersionUID = 1L;

	private String type;
	private String sql;
	private HashMap<String, Object> title = new HashMap<String, Object>();
	private String fileName;

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	@SuppressWarnings("rawtypes")
	public String execute() throws Exception {
		Map session = ActionContext.getContext().getSession();

		if ("1".equals(type)) {
			String jwhere = (String) session.get("sql");
			if (null == jwhere || "".equals(jwhere) || "null".equals(jwhere)) {
				jwhere = " and 1=1";
			}
			this.sql = "SELECT BIZ.BIZ_ID, BIZ.BIZ_TYPE, BIZ.BIZ_NAME, "
					+ " BIZ.IDCARD,  BIZ.DIAGNOSE_NAME, BIZ.FAMILY_ID, "
					+ " BIZ.SSN, BIZ.NAME, BIZ.OUT_FLAG, BIZ.ALLMONEY, "
					+ " a.ASSISTPAY, a.INSURANCEPAY, DEPT.NAME as HANME, "
					+ " BIZ.BEGIN_DATE, BIZ.END_DATE, BIZ.OPERTIME ,'a'as member_id , 'a' as ds"
					+ " FROM JZ_BIZ BIZ, DM_BIZDEPT DEPT  ,"
					+ " (select t.biz_id, sum(t.assistmoney) as ASSISTPAY, "
					+ " sum(t.insurancepay) AS INSURANCEPAY from JZ_BIZ_HISTORY T "
					+ " group by t.biz_id) a "
					+ " WHERE DEPT.HOSPITAL_ID = BIZ.HOSPITAL_ID "
					+ " and biz.ASSIST_FLAG = '1' " + jwhere
					+ " and a.biz_id = biz.biz_id " + " AND BIZ.STAUS = '1'  "
					+ " order by BIZ.OPERTIME, BIZ.FAMILY_ID";

			title.put("FAMILY_ID", "家庭编号");
			title.put("NAME", "姓名");
			title.put("DIAGNOSE_NAME", "患病名称");
			title.put("HANME", "医院");
			title.put("SSN", "社会救助号");
			title.put("OPERTIME", "救助时间");
			title.put("IDCARD", "身份证号");
			title.put("ASSISTPAY", "救助金额");
			title.put("INSURANCEPAY", "统筹金额");
			title.put("ALLMONEY", "总金额");
		}
		if ("2".equals(type)) {
			UserInfoDTO user = (UserInfoDTO) session.get("user");
			String orgno = user.getOrganizationId();
			String orgno1 = orgno.substring(0, 2) + "0"
					+ orgno.substring(2, orgno.length());
			sql = "select  * from JZ_CHRONICAPPROVE "
					+ "where (FAMILY_ID like '" + orgno
					+ "%' and FLAG = 1 and APRLEVEL = -1) or "
					+ "(FAMILY_ID like '" + orgno1
					+ "%' and FLAG = 1 and APRLEVEL = -1)";
			title.put("FAMILY_ID", "家庭编号");
			title.put("NAME", "姓名");
			title.put("SSN", "社会救助号");
			title.put("MEMBER_TYPE", "数据来源");
		}
		if ("3".equals(type)) {
			String jwhere = (String) session.get("sql");
			if (null == jwhere || "".equals(jwhere) || "null".equals(jwhere)) {
				jwhere = " and 1=1";
			}
			sql = "select biz.biz_id,  biz.ssn, d.name as HANME, biz.biz_type, "
					+ " biz.family_no as FAMILY_ID , biz.name as NAME,  biz.id_card  as IDCARD,  e.name as DIAGNOSE_NAME, e.icdcode, "
					+ " c.ASSISTPAY,  c.ALLMONEY, c.payself, biz.begin_time, "
					+ " biz.end_time,  biz.oper_time from jz_biz@med biz, "
					+ " (select sum(b.pay_total) as ALLMONEY, sum(b.pay_assist) as ASSISTPAY, "
					+ " sum(b.PAY_SELF) as payself,  b.biz_id "
					+ " from jz_pay@med b  where mod(b.seq, 2) = 1  and b.sts = 1  and 1 = 1 "
					+ " group by b.biz_id) c,   bizdept@med d,  icd10@med e "
					+ " where c.biz_id = biz.biz_id  and biz.assist_flag = 1 and biz.biz_type = 3 "
					+ " and d.hospital_id(+) = biz.hospital_id and e.icd_id(+) = biz.icd_id   "
					+ jwhere + " order by biz.end_time desc";

			sql = " select * from chronic_remote biz where 1=1  " + jwhere
					+ " order by biz.end_time desc";
			System.out.println("导出sql:" + sql);

			title.put("FAMILY_ID", "家庭编号");
			title.put("NAME", "姓名");
			title.put("HANME", "药店");
			title.put("SSN", "社会救助号");
			title.put("OPER_TIME", "救助时间");
			title.put("IDCARD", "身份证号");
			title.put("ASSISTPAY", "救助金额");
		}
		if ("4".equals(type)) {
			String jwhere = (String) session.get("sql");
			sql = jwhere;
			title.put("HNAME", "医院");
			title.put("ASSISTPAY", "救助金额");
		}
		if ("5".equals(type)) {
			// NAME HNAME HOSPITAL_ID BIZ_TYPE SSN FAMILY_ID BEGIN_DATE END_DATE
			// INDAYS IN_DISEASE_NAME DIAGNOSE_NAME
			// FIN_DISEASE_NAME SAL CNT BIZ_NAME_SHOW DEPTNAME
			BizCheckDTO jwhere = (BizCheckDTO) session.get("sql");
			sql = "SELECT BIZ.BIZ_ID, " + " BIZ.NAME," + " DEPT.NAME AS HNAME,"
					+ " DEPT.HOSPITAL_ID," + " BIZ.BIZ_TYPE," + " SSN,"
					+ " FAMILY_ID," + " BEGIN_DATE," + " BIZ.END_DATE,"
					+ " ROUND(BIZ.END_DATE - BIZ.BEGIN_DATE) AS INDAYS,"
					+ " IN_DISEASE_NAME," + " DIAGNOSE_NAME,"
					+ " FIN_DISEASE_NAME," + " " + " SAL," + " CNT,"
					+ " DM.BIZ_NAME_SHOW"
					+ ", BIZ.IN_DEPT_NAME as deptname FROM JZ_BIZ BIZ,"
					+ " DM_BIZ_TYPE DM," + " (SELECT T.BIZ_ID,"
					+ " SUM(DECODE(T.FUNC_ID, '111', T.REAL_PAY)) AS CNT,"
					+ " SUM(DECODE(T. FUNC_ID, 'Z01', T.REAL_PAY)) AS SAL"
					+ " FROM JZ_PAYLIST T" + " WHERE T.SUM_FLAG = 1"
					+ " GROUP BY T.BIZ_ID) FY," + " DM_BIZDEPT DEPT"
					+ " WHERE DM.BIZ_TYPE = BIZ.BIZ_TYPE"
					+ " AND BIZ.BIZ_ID = FY.BIZ_ID(+)"
					+ " AND DEPT.HOSPITAL_ID = BIZ.HOSPITAL_ID";
			sql = sql + jwhere.getJwhere()
					+ " order by BEGIN_DATE desc, FAMILY_ID";
			title.put("HNAME", "医院");
			title.put("FAMILY_ID", "家庭编号");
			title.put("BEGIN_DATE", "入院时间 ");
			title.put("END_DATE", "出院时间");
			title.put("DIAGNOSE_NAME", "患病名称");
			title.put("NAME", "姓名");
			title.put("SSN", "社会救助号");
		}
		if ("6".equals(type)) {
			// // CS CS1 CS2 ORGNAME
			sql = (String) session.get("sql");
			title.put("ORGNAME", "慢性病名称");
			title.put("CS1", "城市人数");
			title.put("CS2", "农村人数");
			title.put("CS", "总人数");
		}
		if ("7".equals(type)) {
			String jwhere = (String) session.get("jwhere");
			sql = " SELECT MA.MA_ID,MA.FAMILYNO as FAMILYNO,MA.MEMBERNAME as MEMBERNAME,MA.PAPERID as PAPERID,MA.SSN as SSN,MA.HOSPITAL as HOSPITAL,MA.HOSPITALLEVEL as HOSPITALEVEL, "
					+ " MA.SICKENCONTENT as SICKENCONTENT,MA.BEGINTIME as BEGINTIME,MA.ENDTIME as ENDTIME,MA.APPROVERESULT as APPROVERESULT,MA.APPROVECONTENT as APPROVECONTENT,MA.TOTALCOST as TOTALCOST, "
					+ " MA.INSUREPAY as INSUREPAY,MA.OUTPAY as OUTPAY,MA.CAPAY as CAPAY,MA.BUSINESSPAY as BUSINESSPAY,MA.ASISSTPAY as ASISSTPAY,MA.CREATETIME as CREATETIME,MA.UPDATETIME as UPDATETIME, "
					+ " MA.MEMBER_ID as MEMBER_ID,MA.MEMBER_TYPE as MEMBER_TYPE,MA.IMPLSTS as IMPLSTS,MA.TIKETNO as TIKETNO,MA.MEDICALTYPE as MEDICALTYPE,MA.INSURETYPE as INSURETYPE,MA.PERSONTYPE as PERSONTYPE, "
					+ " MA.ON_NO as ON_NO,MA.PAY_LINE as PAY_LINE,MA.HOSPITALPAY as HOSPITALPAY,MA.DIAGNOSE as DIAGNOSE,MA.FAMCOUNT as FAMCOUNT,MA.FAMADDR as FAMADDR,MA.TELEPHONE as TELEPHONE,MA.SEX as SEX, "
					+ " (CASE MA.MEMBER_TYPE WHEN '1' THEN '城市' WHEN '2' THEN '农村' END) AS DSTXT,"
					+ " MA.BIRTHDAY  as BIRTHDAY FROM JZ_MEDICALAFTER MA WHERE 1=1"
					+ jwhere + " ORDER BY MA.UPDATETIME DESC,MA.FAMILYNO ";
			title.put("FAMILYNO", "家庭编号");
			title.put("MEMBERNAME", "姓名");
			title.put("SEX", "性别");
			title.put("PAPERID", "身份证号码");
			title.put("SSN", "医保卡号");
			title.put("HOSPITAL", "医院名称");
			title.put("HOSPITALEVEL", "医院级别");
			title.put("BEGINTIME", "入院世间");
			title.put("ENDTIME", "出院时间");
			title.put("TOTALCOST", "总费用");
			title.put("INSUREPAY", "统筹支付");
			title.put("OUTPAY", "目录外费用");
			title.put("CAPAY", "大病保险");
			title.put("ASISSTPAY", "救助金额");
			title.put("PAY_LINE", "起助线");
			title.put("HOSPITALPAY", "医院补助");
			title.put("FAMADDR", "家庭住址");
			title.put("DSTXT", "来源");

		}

		/*
		 * FAMILYNO 220020114080121 MEMBERNAME 张寿清 PAPERID 220211194304280611
		 * ASSISPAY 319.35 BATCHNAME 2014年12月医疗救助 MASTERNAME 张寿清 MASTERIDCARD
		 * BANK_ACCOUNT 0720602011009800641449 BANK_ACCOUNT1
		 */

		if ("8".equals(type)) {
			String jwhere = (String) session.get("jwhere");
			sql = jwhere;
			title.put("FAMILYNO", "家庭编号");
			title.put("MEMBERNAME", "姓名");
			title.put("PAPERID", "身份证号");
			title.put("ASSISPAY", "救助金");
			title.put("BATCHNAME", "发放批次");
			title.put("MASTERNAME", "户主姓名");
			title.put("BANK_ACCOUNT", "家庭银行账号");
			title.put("BANK_ACCOUNT1", "个人银行账号");
		}
		String f = new String("生成excel".getBytes("gb2312"), "ISO8859-1");
		fileName = "attachment; filename=" + f + ".xls";
		return super.execute();
	}

	@SuppressWarnings("rawtypes")
	public InputStream getExcelFile() {
		ByteArrayInputStream bais = null;
		List<HashMap> rs = searchService.getAll(sql);
		ExportExcel ee = new ExportExcel();
		ByteArrayOutputStream baos = ee.genExcelData(title, rs);
		if (null != baos) {
			byte[] ba = baos.toByteArray();
			bais = new ByteArrayInputStream(ba);
		}
		return bais;
	}

	public SearchService getSearchService() {
		return searchService;
	}

	public String getSql() {
		return sql;
	}

	public HashMap<String, Object> getTitle() {
		return title;
	}

	public String getType() {
		return type;
	}

	public void setSearchService(SearchService searchService) {
		this.searchService = searchService;
	}

	public void setSql(String sql) {
		this.sql = sql;
	}

	public void setTitle(HashMap<String, Object> title) {
		this.title = title;
	}

	public void setType(String type) {
		this.type = type;
	}
}
