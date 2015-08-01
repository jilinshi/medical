package com.medical.dto;

import java.math.BigDecimal;
import java.util.Date;

public class MedicalafterDTO {
	private BigDecimal maId;
	private String familyno;
	private String membername;
	private String paperid;
	private String ssn;
	private String hospital;
	private String hospitallevel;
	private String sickencontent;
	private Date begintime;
	private Date endtime;
	private String begintimeval;
	private String endtimeval;
	private String approveresult;
	private String approvecontent;
	private BigDecimal totalcost;
	private BigDecimal insurepay;
	private BigDecimal outpay;
	private BigDecimal capay;
	private BigDecimal businesspay;
	private BigDecimal asisstpay;
	private Date createtime;
	private Date updatetime;
	private String memberId;
	private String memberType;
	private String implsts;
	private String tiketno;
	private String medicaltype;
	private String insuretype;
	private String persontype;
	private String onNo;
	private BigDecimal payLine;
	private BigDecimal hospitalpay;

	private String masterName;
	private String masteridcard;
	private String bankaccounts;
	private String bankaccounts1;
	private String ds;
	private String personstate;
	private String assistType;
	private BigDecimal asort;
	private String relmaster;
	private String diagnose;
	private Integer actId;
	private String r;
	private BigDecimal num;// 年度出院次数
	private BigDecimal indate;// 住院天数
	private BigDecimal sumpay;// 累计救助金

	private short famcount; // 家庭人口数
	private String famcountval;
	private String famaddr; // 家庭住址
	private String telephone; // 电话
	private String sex; // 性别
	private Date birthday; // 出生日期
	private String birthdayval;
	private String fSalvationcount;
	private String fPersoncount;
	private String fAccounts;
	private String filepath;
	private String filename;
	private String wsflag;// 住院类别
	private String pzPrinum;
	private String appPrinum;

	private BigDecimal sumtotalcost;
	private BigDecimal suminsurepay;
	private BigDecimal sumoutpay;
	private BigDecimal sumasisstpay;
	
	private String maxdate;
	private String stname;

	private BigDecimal rc;
	private String batchname;
	
	private String wzzflag; //转诊类别
	private BigDecimal selfpay;
	
	private String hospitalid;

	public BigDecimal getRc() {
		return rc;
	}

	public void setRc(BigDecimal rc) {
		this.rc = rc;
	}

	public String getBatchname() {
		return batchname;
	}

	public void setBatchname(String batchname) {
		this.batchname = batchname;
	}

	public BigDecimal getSumpay() {
		return sumpay;
	}

	public void setSumpay(BigDecimal sumpay) {
		this.sumpay = sumpay;
	}

	public BigDecimal getMaId() {
		return maId;
	}

	public void setMaId(BigDecimal maId) {
		this.maId = maId;
	}

	public String getFamilyno() {
		return familyno;
	}

	public void setFamilyno(String familyno) {
		this.familyno = familyno;
	}

	public String getMembername() {
		return membername;
	}

	public void setMembername(String membername) {
		this.membername = membername;
	}

	public String getPaperid() {
		return paperid;
	}

	public void setPaperid(String paperid) {
		this.paperid = paperid;
	}

	public String getSsn() {
		return ssn;
	}

	public void setSsn(String ssn) {
		this.ssn = ssn;
	}

	public String getHospital() {
		return hospital;
	}

	public void setHospital(String hospital) {
		this.hospital = hospital;
	}

	public String getHospitallevel() {
		return hospitallevel;
	}

	public void setHospitallevel(String hospitallevel) {
		this.hospitallevel = hospitallevel;
	}

	public String getSickencontent() {
		return sickencontent;
	}

	public void setSickencontent(String sickencontent) {
		this.sickencontent = sickencontent;
	}

	public Date getBegintime() {
		return begintime;
	}

	public void setBegintime(Date begintime) {
		this.begintime = begintime;
	}

	public Date getEndtime() {
		return endtime;
	}

	public void setEndtime(Date endtime) {
		this.endtime = endtime;
	}

	public String getApproveresult() {
		return approveresult;
	}

	public void setApproveresult(String approveresult) {
		this.approveresult = approveresult;
	}

	public String getApprovecontent() {
		return approvecontent;
	}

	public void setApprovecontent(String approvecontent) {
		this.approvecontent = approvecontent;
	}

	public BigDecimal getTotalcost() {
		return totalcost;
	}

	public void setTotalcost(BigDecimal totalcost) {
		this.totalcost = totalcost;
	}

	public BigDecimal getInsurepay() {
		return insurepay;
	}

	public void setInsurepay(BigDecimal insurepay) {
		this.insurepay = insurepay;
	}

	public BigDecimal getOutpay() {
		return outpay;
	}

	public void setOutpay(BigDecimal outpay) {
		this.outpay = outpay;
	}

	public BigDecimal getCapay() {
		return capay;
	}

	public void setCapay(BigDecimal capay) {
		this.capay = capay;
	}

	public BigDecimal getBusinesspay() {
		return businesspay;
	}

	public void setBusinesspay(BigDecimal businesspay) {
		this.businesspay = businesspay;
	}

	public BigDecimal getAsisstpay() {
		return asisstpay;
	}

	public void setAsisstpay(BigDecimal asisstpay) {
		this.asisstpay = asisstpay;
	}

	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	public Date getUpdatetime() {
		return updatetime;
	}

	public void setUpdatetime(Date updatetime) {
		this.updatetime = updatetime;
	}

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public String getMemberType() {
		return memberType;
	}

	public void setMemberType(String memberType) {
		this.memberType = memberType;
	}

	public String getImplsts() {
		return implsts;
	}

	public void setImplsts(String implsts) {
		this.implsts = implsts;
	}

	public String getTiketno() {
		return tiketno;
	}

	public void setTiketno(String tiketno) {
		this.tiketno = tiketno;
	}

	public String getMedicaltype() {
		return medicaltype;
	}

	public void setMedicaltype(String medicaltype) {
		this.medicaltype = medicaltype;
	}

	public String getInsuretype() {
		return insuretype;
	}

	public void setInsuretype(String insuretype) {
		this.insuretype = insuretype;
	}

	public String getPersontype() {
		return persontype;
	}

	public void setPersontype(String persontype) {
		this.persontype = persontype;
	}

	public String getMasterName() {
		return masterName;
	}

	public void setMasterName(String masterName) {
		this.masterName = masterName;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getDs() {
		return ds;
	}

	public void setDs(String ds) {
		this.ds = ds;
	}

	public String getPersonstate() {
		return personstate;
	}

	public void setPersonstate(String personstate) {
		this.personstate = personstate;
	}

	public String getAssistType() {
		return assistType;
	}

	public void setAssistType(String assistType) {
		this.assistType = assistType;
	}

	public BigDecimal getAsort() {
		return asort;
	}

	public void setAsort(BigDecimal asort) {
		this.asort = asort;
	}

	public String getRelmaster() {
		return relmaster;
	}

	public void setRelmaster(String relmaster) {
		this.relmaster = relmaster;
	}

	public String getOnNo() {
		return onNo;
	}

	public void setOnNo(String onNo) {
		this.onNo = onNo;
	}

	public BigDecimal getPayLine() {
		return payLine;
	}

	public void setPayLine(BigDecimal payLine) {
		this.payLine = payLine;
	}

	public BigDecimal getHospitalpay() {
		return hospitalpay;
	}

	public void setHospitalpay(BigDecimal hospitalpay) {
		this.hospitalpay = hospitalpay;
	}

	public String getDiagnose() {
		return diagnose;
	}

	public void setDiagnose(String diagnose) {
		this.diagnose = diagnose;
	}

	public String getR() {
		return r;
	}

	public void setR(String r) {
		this.r = r;
	}

	public Integer getActId() {
		return actId;
	}

	public void setActId(Integer actId) {
		this.actId = actId;
	}

	public BigDecimal getNum() {
		return num;
	}

	public void setNum(BigDecimal num) {
		this.num = num;
	}

	public BigDecimal getIndate() {
		return indate;
	}

	public void setIndate(BigDecimal indate) {
		this.indate = indate;
	}

	public String getBegintimeval() {
		return begintimeval;
	}

	public void setBegintimeval(String begintimeval) {
		this.begintimeval = begintimeval;
	}

	public String getEndtimeval() {
		return endtimeval;
	}

	public void setEndtimeval(String endtimeval) {
		this.endtimeval = endtimeval;
	}

	public short getFamcount() {
		return famcount;
	}

	public void setFamcount(short famcount) {
		this.famcount = famcount;
	}

	public String getFamaddr() {
		return famaddr;
	}

	public void setFamaddr(String famaddr) {
		this.famaddr = famaddr;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public String getfSalvationcount() {
		return fSalvationcount;
	}

	public void setfSalvationcount(String fSalvationcount) {
		this.fSalvationcount = fSalvationcount;
	}

	public String getfPersoncount() {
		return fPersoncount;
	}

	public void setfPersoncount(String fPersoncount) {
		this.fPersoncount = fPersoncount;
	}

	public String getfAccounts() {
		return fAccounts;
	}

	public void setfAccounts(String fAccounts) {
		this.fAccounts = fAccounts;
	}

	public String getBirthdayval() {
		return birthdayval;
	}

	public void setBirthdayval(String birthdayval) {
		this.birthdayval = birthdayval;
	}

	public String getFamcountval() {
		return famcountval;
	}

	public void setFamcountval(String famcountval) {
		this.famcountval = famcountval;
	}

	public String getFilepath() {
		return filepath;
	}

	public void setFilepath(String filepath) {
		this.filepath = filepath;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public String getWsflag() {
		return wsflag;
	}

	public void setWsflag(String wsflag) {
		this.wsflag = wsflag;
	}

	public String getPzPrinum() {
		return pzPrinum;
	}

	public void setPzPrinum(String pzPrinum) {
		this.pzPrinum = pzPrinum;
	}

	public String getAppPrinum() {
		return appPrinum;
	}

	public void setAppPrinum(String appPrinum) {
		this.appPrinum = appPrinum;
	}

	public BigDecimal getSumtotalcost() {
		return sumtotalcost;
	}

	public void setSumtotalcost(BigDecimal sumtotalcost) {
		this.sumtotalcost = sumtotalcost;
	}

	public BigDecimal getSuminsurepay() {
		return suminsurepay;
	}

	public void setSuminsurepay(BigDecimal suminsurepay) {
		this.suminsurepay = suminsurepay;
	}

	public BigDecimal getSumoutpay() {
		return sumoutpay;
	}

	public void setSumoutpay(BigDecimal sumoutpay) {
		this.sumoutpay = sumoutpay;
	}

	public BigDecimal getSumasisstpay() {
		return sumasisstpay;
	}

	public void setSumasisstpay(BigDecimal sumasisstpay) {
		this.sumasisstpay = sumasisstpay;
	}

	public String getMasteridcard() {
		return masteridcard;
	}

	public void setMasteridcard(String masteridcard) {
		this.masteridcard = masteridcard;
	}

	public String getBankaccounts() {
		return bankaccounts;
	}

	public void setBankaccounts(String bankaccounts) {
		this.bankaccounts = bankaccounts;
	}

	public String getBankaccounts1() {
		return bankaccounts1;
	}

	public void setBankaccounts1(String bankaccounts1) {
		this.bankaccounts1 = bankaccounts1;
	}

	public String getMaxdate() {
		return maxdate;
	}

	public void setMaxdate(String maxdate) {
		this.maxdate = maxdate;
	}

	public String getStname() {
		return stname;
	}

	public void setStname(String stname) {
		this.stname = stname;
	}

	public String getWzzflag() {
		return wzzflag;
	}

	public void setWzzflag(String wzzflag) {
		this.wzzflag = wzzflag;
	}

	public BigDecimal getSelfpay() {
		return selfpay;
	}

	public void setSelfpay(BigDecimal selfpay) {
		this.selfpay = selfpay;
	}

	public String getHospitalid() {
		return hospitalid;
	}

	public void setHospitalid(String hospitalid) {
		this.hospitalid = hospitalid;
	}

}
