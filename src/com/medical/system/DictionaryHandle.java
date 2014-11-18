package com.medical.system;

import java.util.HashMap;

import org.dom4j.Document;

public interface DictionaryHandle {
	/**
	 * ��ȡxml�ļ�
	 * 
	 * @param filepath
	 * @return
	 */
	public Document readDictXml();

	/**
	 * �����ֵ�ֵ
	 * 
	 * @param dvid
	 * @return
	 */
	public String getDictValue(String dvid);

	/**
	 * ���� xml option
	 * 
	 * @param dsid
	 * @return
	 */
	public Document getOptionsByDSID(String dsid, String selcetValue);

	/**
	 * ���� html option
	 * 
	 * @param dsid
	 * @return
	 */
	public String getOptionsByDS(String dsid, String selcetValue);

	public HashMap<String, String> getDsMap(String dsid);
}
