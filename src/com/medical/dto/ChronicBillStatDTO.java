package com.medical.dto;

import java.math.BigDecimal;

public class ChronicBillStatDTO {
	private BigDecimal zrc;// �ܾ����˴�
	private BigDecimal ndzrc;// ��������˴�
	private BigDecimal zxf;// �����ѽ��
	private BigDecimal ndzxf;// ��������ѽ��
	private BigDecimal dqrs;// ���ܾ�������
	private BigDecimal zbal; //�������˻�û������֮ǰ������ܺ�
	private BigDecimal znbal;
	private BigDecimal ndzsr;//���������
	private BigDecimal zsr;//������

	
	public BigDecimal getZnbal() {
		return znbal;
	}

	public void setZnbal(BigDecimal znbal) {
		this.znbal = znbal;
	}

	public BigDecimal getNdzsr() {
		return ndzsr;
	}

	public void setNdzsr(BigDecimal ndzsr) {
		this.ndzsr = ndzsr;
	}

	public BigDecimal getZsr() {
		return zsr;
	}

	public void setZsr(BigDecimal zsr) {
		this.zsr = zsr;
	}

	public BigDecimal getZrc() {
		return zrc;
	}

	public void setZrc(BigDecimal zrc) {
		this.zrc = zrc;
	}

	public BigDecimal getNdzrc() {
		return ndzrc;
	}

	public void setNdzrc(BigDecimal ndzrc) {
		this.ndzrc = ndzrc;
	}

	public BigDecimal getZxf() {
		return zxf;
	}

	public void setZxf(BigDecimal zxf) {
		this.zxf = zxf;
	}

	public BigDecimal getNdzxf() {
		return ndzxf;
	}

	public void setNdzxf(BigDecimal ndzxf) {
		this.ndzxf = ndzxf;
	}

	public BigDecimal getDqrs() {
		return dqrs;
	}

	public void setDqrs(BigDecimal dqrs) {
		this.dqrs = dqrs;
	}

	public void setZbal(BigDecimal zbal) {
		this.zbal = zbal;
	}

	public BigDecimal getZbal() {
		return zbal;
	}

}
