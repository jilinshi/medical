package com.medical.service;

import org.dom4j.Document;
import org.jfree.chart.JFreeChart;

public interface JfreechartService {

	/**
	 * ��������ͼ
	 * 
	 * @return
	 */
	public JFreeChart createBar3D(Document doc, String title, String x, String y);

	/**
	 * ���ɱ�ͼ
	 * 
	 * @return
	 */
	public JFreeChart createPie3D(Document doc, String title);
}
