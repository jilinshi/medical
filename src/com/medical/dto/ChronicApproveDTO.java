package com.medical.dto;

import java.math.BigDecimal;
import java.util.Date;

public class ChronicApproveDTO {
	private BigDecimal chronicapproveId; // 审批表主键ID
	private String familyId; // 家庭ID
	private String name;// 患者姓名
	private String ssn;// 社会保障号
	private Integer entity; // 患病类型
	private String[] entitys;
	private String entityval;
	private Short status; // 审核状态
	private BigDecimal salmoney; // 救助金额
	private Short flag; // 审批单状态（在用否1:在用0:不在用）
	private Short apraim;// 审批目的
	private Short aprresult1; // 审批结果（街道、区、市）
	private String apridea1; // 审批意见
	private Date aprtime1;// 审批时间
	private String aprperson1;// 审批人
	private Short aprresult2;
	private String apridea2;
	private Date aprtime2;
	private String aprperson2;
	private Short aprresult3;
	private String apridea3;
	private Date aprtime3;
	private String aprperson3;
	private String familyno;// 家庭编号
	private Short aprlevel;
	private Short aprresult;
	private String apridea;
	private Date aprtime;
	private String aprperson;
	private String paperid;
	private String relmaster;
	private String sex;
	private String masterName;
	private Date birthday;
	private String rprkind;
	private String rprtype;
	private String rpraddress;
	private String linkmode;
	private String address;
	private String saprtime1;
	private String saprtime2;
	private String saprtime3;
	private String begintime;
	private String memberId;
	private String memberType;
	private String state;
	private String sts;
	private String remark;
	private String anFilename;
	private String ps;
	private String yw;
	

	public String getPs() {
		return ps;
	}

	public void setPs(String ps) {
		this.ps = ps;
	}

	public String getYw() {
		return yw;
	}

	public void setYw(String yw) {
		this.yw = yw;
	}

	public String getAnFilename() {
		
		return anFilename;
	}

	public void setAnFilename(String anFilename) {
		this.anFilename = anFilename;
	}

	public String getSts() {
		return sts;
	}

	public void setSts(String sts) {
		this.sts = sts;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public BigDecimal getChronicapproveId() {
		return chronicapproveId;
	}

	public void setChronicapproveId(BigDecimal chronicapproveId) {
		this.chronicapproveId = chronicapproveId;
	}

	public String getFamilyId() {
		return familyId;
	}

	public void setFamilyId(String familyId) {
		this.familyId = familyId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSsn() {
		return ssn;
	}

	public void setSsn(String ssn) {
		this.ssn = ssn;
	}

	public Integer getEntity() {
		return entity;
	}

	public void setEntity(Integer entity) {
		this.entity = entity;
	}

	public Short getStatus() {
		return status;
	}

	public void setStatus(Short status) {
		this.status = status;
	}

	public BigDecimal getSalmoney() {
		return salmoney;
	}

	public void setSalmoney(BigDecimal salmoney) {
		this.salmoney = salmoney;
	}

	public Short getFlag() {
		return flag;
	}

	public void setFlag(Short flag) {
		this.flag = flag;
	}

	public Short getApraim() {
		return apraim;
	}

	public void setApraim(Short apraim) {
		this.apraim = apraim;
	}

	public Short getAprresult1() {
		return aprresult1;
	}

	public void setAprresult1(Short aprresult1) {
		this.aprresult1 = aprresult1;
	}

	public String getApridea1() {
		return apridea1;
	}

	public void setApridea1(String apridea1) {
		this.apridea1 = apridea1;
	}

	public Date getAprtime1() {
		return aprtime1;
	}

	public void setAprtime1(Date aprtime1) {
		this.aprtime1 = aprtime1;
	}

	public String getAprperson1() {
		return aprperson1;
	}

	public void setAprperson1(String aprperson1) {
		this.aprperson1 = aprperson1;
	}

	public Short getAprresult2() {
		return aprresult2;
	}

	public void setAprresult2(Short aprresult2) {
		this.aprresult2 = aprresult2;
	}

	public String getApridea2() {
		return apridea2;
	}

	public void setApridea2(String apridea2) {
		this.apridea2 = apridea2;
	}

	public Date getAprtime2() {
		return aprtime2;
	}

	public void setAprtime2(Date aprtime2) {
		this.aprtime2 = aprtime2;
	}

	public String getAprperson2() {
		return aprperson2;
	}

	public void setAprperson2(String aprperson2) {
		this.aprperson2 = aprperson2;
	}

	public Short getAprresult3() {
		return aprresult3;
	}

	public void setAprresult3(Short aprresult3) {
		this.aprresult3 = aprresult3;
	}

	public String getApridea3() {
		return apridea3;
	}

	public void setApridea3(String apridea3) {
		this.apridea3 = apridea3;
	}

	public Date getAprtime3() {
		return aprtime3;
	}

	public void setAprtime3(Date aprtime3) {
		this.aprtime3 = aprtime3;
	}

	public String getAprperson3() {
		return aprperson3;
	}

	public void setAprperson3(String aprperson3) {
		this.aprperson3 = aprperson3;
	}

	public void setFamilyno(String familyno) {
		this.familyno = familyno;
	}

	public String getFamilyno() {
		return familyno;
	}

	public String getPaperid() {
		return paperid;
	}

	public void setPaperid(String paperid) {
		this.paperid = paperid;
	}

	public String getRelmaster() {
		return relmaster;
	}

	public void setRelmaster(String relmaster) {
		this.relmaster = relmaster;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getMasterName() {
		return masterName;
	}

	public void setMasterName(String masterName) {
		this.masterName = masterName;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public String getRprkind() {
		return rprkind;
	}

	public void setRprkind(String rprkind) {
		this.rprkind = rprkind;
	}

	public String getRprtype() {
		return rprtype;
	}

	public void setRprtype(String rprtype) {
		this.rprtype = rprtype;
	}

	public String getRpraddress() {
		return rpraddress;
	}

	public void setRpraddress(String rpraddress) {
		this.rpraddress = rpraddress;
	}

	public String getLinkmode() {
		return linkmode;
	}

	public void setLinkmode(String linkmode) {
		this.linkmode = linkmode;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Short getAprresult() {
		return aprresult;
	}

	public void setAprresult(Short aprresult) {
		this.aprresult = aprresult;
	}

	public String getApridea() {
		return apridea;
	}

	public void setApridea(String apridea) {
		this.apridea = apridea;
	}

	public Date getAprtime() {
		return aprtime;
	}

	public void setAprtime(Date aprtime) {
		this.aprtime = aprtime;
	}

	public String getAprperson() {
		return aprperson;
	}

	public void setAprperson(String aprperson) {
		this.aprperson = aprperson;
	}

	public String getSaprtime1() {
		return saprtime1;
	}

	public void setSaprtime1(String saprtime1) {
		this.saprtime1 = saprtime1;
	}

	public String getSaprtime2() {
		return saprtime2;
	}

	public void setSaprtime2(String saprtime2) {
		this.saprtime2 = saprtime2;
	}

	public String getSaprtime3() {
		return saprtime3;
	}

	public void setSaprtime3(String saprtime3) {
		this.saprtime3 = saprtime3;
	}

	public Short getAprlevel() {
		return aprlevel;
	}

	public void setAprlevel(Short aprlevel) {
		this.aprlevel = aprlevel;
	}

	public void setEntityval(String entityval) {
		this.entityval = entityval;
	}

	public String getEntityval() {
		return entityval;
	}

	public void setBegintime(String begintime) {
		this.begintime = begintime;
	}

	public String getBegintime() {
		return begintime;
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

	public void setState(String state) {
		this.state = state;
	}

	public String getState() {
		return state;
	}

	/**
	 * @return the entitys
	 */
	public String[] getEntitys() {
		return entitys;
	}

	/**
	 * @param entitys the entitys to set
	 */
	public void setEntitys(String[] entitys) {
		this.entitys = entitys;
	}

}
