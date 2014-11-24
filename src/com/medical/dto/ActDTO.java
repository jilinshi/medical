package com.medical.dto;

import java.math.BigDecimal;

public class ActDTO {
	
	private Integer actId;
	private String memberId;
	private String hospitalId;
	private String centerId;
	private Short actYear;
	private String actBizType;
	private Short actBizTimes;
	private BigDecimal actBizMoney;
	private Short actBizInhospitalTimes;
	private BigDecimal actBizMoney2;
	private BigDecimal actBizMoneyOld;
	private BigDecimal addMz;
	private BigDecimal addZy;
	private String memberType;
	
	public Integer getActId() {
		return actId;
	}
	public void setActId(Integer actId) {
		this.actId = actId;
	}
	public String getMemberId() {
		return memberId;
	}
	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}
	public String getHospitalId() {
		return hospitalId;
	}
	public void setHospitalId(String hospitalId) {
		this.hospitalId = hospitalId;
	}
	public String getCenterId() {
		return centerId;
	}
	public void setCenterId(String centerId) {
		this.centerId = centerId;
	}
	public Short getActYear() {
		return actYear;
	}
	public void setActYear(Short actYear) {
		this.actYear = actYear;
	}
	public String getActBizType() {
		return actBizType;
	}
	public void setActBizType(String actBizType) {
		this.actBizType = actBizType;
	}
	public Short getActBizTimes() {
		return actBizTimes;
	}
	public void setActBizTimes(Short actBizTimes) {
		this.actBizTimes = actBizTimes;
	}
	public BigDecimal getActBizMoney() {
		return actBizMoney;
	}
	public void setActBizMoney(BigDecimal actBizMoney) {
		this.actBizMoney = actBizMoney;
	}
	public Short getActBizInhospitalTimes() {
		return actBizInhospitalTimes;
	}
	public void setActBizInhospitalTimes(Short actBizInhospitalTimes) {
		this.actBizInhospitalTimes = actBizInhospitalTimes;
	}
	public BigDecimal getActBizMoney2() {
		return actBizMoney2;
	}
	public void setActBizMoney2(BigDecimal actBizMoney2) {
		this.actBizMoney2 = actBizMoney2;
	}
	public BigDecimal getActBizMoneyOld() {
		return actBizMoneyOld;
	}
	public void setActBizMoneyOld(BigDecimal actBizMoneyOld) {
		this.actBizMoneyOld = actBizMoneyOld;
	}
	public BigDecimal getAddMz() {
		return addMz;
	}
	public void setAddMz(BigDecimal addMz) {
		this.addMz = addMz;
	}
	public BigDecimal getAddZy() {
		return addZy;
	}
	public void setAddZy(BigDecimal addZy) {
		this.addZy = addZy;
	}
	public String getMemberType() {
		return memberType;
	}
	public void setMemberType(String memberType) {
		this.memberType = memberType;
	}
	
}
