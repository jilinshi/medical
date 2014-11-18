package com.medical.service.impl;

import java.awt.Font;
import java.math.BigDecimal;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.Element;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.StandardChartTheme;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.title.LegendTitle;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

import com.medical.dao.ExecutSQLDAO;
import com.medical.service.JfreechartService;

public class JfreechartServiceImpl implements JfreechartService {
	private ExecutSQLDAO executSQLDAO;
	private JFreeChart chart;

	@SuppressWarnings("unchecked")
	public JFreeChart createBar3D(Document doc, String title, String x, String y) {

		StandardChartTheme standardChartTheme = new StandardChartTheme("name");
		standardChartTheme.setLargeFont(new Font("����", Font.BOLD, 12));// ���Ըı����������
		standardChartTheme.setRegularFont(new Font("����", Font.BOLD, 12));// ���Ըı�ͼ��������
		standardChartTheme.setExtraLargeFont(new Font("����", Font.BOLD, 12));// ���Ըı�ͼ��ı�������
		ChartFactory.setChartTheme(standardChartTheme);// ��������

		DefaultCategoryDataset dataset = new DefaultCategoryDataset();

		Element root = doc.getRootElement();
		Iterator colit = root.elementIterator("col");
		while (colit.hasNext()) {
			Element ele = (Element) colit.next();
			List cols = ele.elements();
			Element v1 = (Element) cols.get(0);
			Element v2 = (Element) cols.get(1);
			Element v3 = (Element) cols.get(2);
			dataset.addValue(new BigDecimal(v1.getText()).doubleValue(), v2
					.getText(), v3.getText());
		}
		chart = ChartFactory.createBarChart3D(title, // ͼ�����
				"", // Ŀ¼�����ʾ��ǩ
				"", // ��ֵ�����ʾ��ǩ
				dataset, // ���ݼ�
				PlotOrientation.VERTICAL, // ͼ����ˮƽ����ֱ
				true, // �Ƿ���ʾͼ��(���ڼ򵥵���״ͼ������false)
				true, // �Ƿ����ɹ���
				false // �Ƿ�����URL����
				);

		CategoryPlot plot = (CategoryPlot) chart.getPlot();

		CategoryAxis categoryAxis = plot.getDomainAxis(); // ȡ�ú���
		categoryAxis.setLabelFont(new Font("����", Font.ITALIC, 22));
		categoryAxis.setCategoryLabelPositions(CategoryLabelPositions.UP_45);

		NumberAxis numberAxis = (NumberAxis) plot.getRangeAxis();// ȡ������
		numberAxis.setLabelFont(new Font("����", Font.ITALIC, 22));

		return chart;
	}

	@SuppressWarnings("unchecked")
	public JFreeChart createPie3D(Document doc, String title) {

		DefaultPieDataset dataset = new DefaultPieDataset();
		Element root = doc.getRootElement();
		Iterator colit = root.elementIterator("col");
		while (colit.hasNext()) {
			Element ele = (Element) colit.next();
			List cols = ele.elements();
			Element orgname = (Element) cols.get(0);
			Element value = (Element) cols.get(1);
			dataset.setValue(orgname.getText(), new Double(value.getText()));
		}
		chart = ChartFactory.createPieChart3D(title, // ͼ�����
				dataset, // ����
				true, // �Ƿ���ʾͼ��
				true, // �Ƿ���ʾ������ʾ
				false // �Ƿ�����URL
				);
		// ��������ͼ����⣬�ı�����
		chart.setTitle(new TextTitle(title, new Font("����", Font.ITALIC, 14)));
		// ȡ��ͳ��ͼ��ĵ�һ��ͼ��
		LegendTitle legend = chart.getLegend(0);
		// �޸�ͼ��������
		legend.setItemFont(new Font("����", Font.BOLD, 12));
		// ��ñ�ͼ��Plot����

		PiePlot plot = (PiePlot) chart.getPlot();
		// ���ñ�ͼ�����ֵı�ǩ����
		plot.setLabelFont(new Font("����", Font.BOLD, 12));
		// �趨����͸���ȣ�0-1.0֮�䣩
		plot.setBackgroundAlpha(0.9f);
		// �趨ǰ��͸���ȣ�0-1.0֮�䣩
		plot.setForegroundAlpha(0.50f);
		// ��ʾ�ٷֱ� "{0}: ({2})"
		plot.setLabelGenerator(new StandardPieSectionLabelGenerator(
				"{0}: ({1}, {2})"));

		return chart;
	}

	public ExecutSQLDAO getExecutSQLDAO() {
		return executSQLDAO;
	}

	public void setExecutSQLDAO(ExecutSQLDAO executSQLDAO) {
		this.executSQLDAO = executSQLDAO;
	}
}
