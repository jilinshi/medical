package com.medical.dao;

import com.medical.model.JzMabills;
import com.medical.model.JzMabillsExample;
import java.util.List;

public interface JzMabillsDAO {
    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table YLJZN.JZ_MABILLS
     *
     * @ibatorgenerated Thu Dec 04 01:08:35 CST 2014
     */
    int countByExample(JzMabillsExample example);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table YLJZN.JZ_MABILLS
     *
     * @ibatorgenerated Thu Dec 04 01:08:35 CST 2014
     */
    int deleteByExample(JzMabillsExample example);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table YLJZN.JZ_MABILLS
     *
     * @ibatorgenerated Thu Dec 04 01:08:35 CST 2014
     */
    void insert(JzMabills record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table YLJZN.JZ_MABILLS
     *
     * @ibatorgenerated Thu Dec 04 01:08:35 CST 2014
     */
    void insertSelective(JzMabills record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table YLJZN.JZ_MABILLS
     *
     * @ibatorgenerated Thu Dec 04 01:08:35 CST 2014
     */
    List<JzMabills> selectByExample(JzMabillsExample example);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table YLJZN.JZ_MABILLS
     *
     * @ibatorgenerated Thu Dec 04 01:08:35 CST 2014
     */
    int updateByExampleSelective(JzMabills record, JzMabillsExample example);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table YLJZN.JZ_MABILLS
     *
     * @ibatorgenerated Thu Dec 04 01:08:35 CST 2014
     */
    int updateByExample(JzMabills record, JzMabillsExample example);
}