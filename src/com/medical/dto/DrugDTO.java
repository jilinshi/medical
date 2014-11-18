package com.medical.dto;

import java.util.Date;

public class DrugDTO {
	/*
	 * ��ĿҩƷ�������� Medi_Item_Name ����ҩƷ��Ŀ���� Item_Name ���� Model ��� Standard ������λ Unit
	 * ���� Price ���� Dosage ��� Money ���� Factory ���÷������� Fee_Date ����ҽ������ Doctor_Name
	 * 
	 */

	private String mediItemName;

	private String itemName;

	private String model;

	private String standard;

	private String unit;

	private String price;

	private String dosage;

	private String money;

	private String factory;

	private Date feeDate;

	private String doctorName;
	
	private String feelistId;

	public String getDoctorName() {
		return doctorName;
	}

	public String getDosage() {
		return dosage;
	}

	public String getFactory() {
		return factory;
	}

	public Date getFeeDate() {
		return feeDate;
	}

	public String getFeelistId() {
		return feelistId;
	}

	public String getItemName() {
		return itemName;
	}

	public String getMediItemName() {
		return mediItemName;
	}

	public String getModel() {
		return model;
	}

	public String getMoney() {
		return money;
	}

	public String getPrice() {
		return price;
	}

	public String getStandard() {
		return standard;
	}

	public String getUnit() {
		return unit;
	}

	public void setDoctorName(String doctorName) {
		this.doctorName = doctorName;
	}

	public void setDosage(String dosage) {
		this.dosage = dosage;
	}

	public void setFactory(String factory) {
		this.factory = factory;
	}

	public void setFeeDate(Date feeDate) {
		this.feeDate = feeDate;
	}

	public void setFeelistId(String feelistId) {
		this.feelistId = feelistId;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public void setMediItemName(String mediItemName) {
		this.mediItemName = mediItemName;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public void setMoney(String money) {
		this.money = money;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public void setStandard(String standard) {
		this.standard = standard;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}


}
