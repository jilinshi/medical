package com.medical.dao;

import com.medical.model.JzMedicalafter;
import com.medical.model.JzMedicalafterExample;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

public class JzMedicalafterDAOImpl extends SqlMapClientDaoSupport implements JzMedicalafterDAO {

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table YLJZN.JZ_MEDICALAFTER
     *
     * @ibatorgenerated Mon Jun 08 10:57:47 CST 2015
     */
    public JzMedicalafterDAOImpl() {
        super();
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table YLJZN.JZ_MEDICALAFTER
     *
     * @ibatorgenerated Mon Jun 08 10:57:47 CST 2015
     */
    public int countByExample(JzMedicalafterExample example) {
        Integer count = (Integer)  getSqlMapClientTemplate().queryForObject("JZ_MEDICALAFTER.ibatorgenerated_countByExample", example);
        return count;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table YLJZN.JZ_MEDICALAFTER
     *
     * @ibatorgenerated Mon Jun 08 10:57:47 CST 2015
     */
    public int deleteByExample(JzMedicalafterExample example) {
        int rows = getSqlMapClientTemplate().delete("JZ_MEDICALAFTER.ibatorgenerated_deleteByExample", example);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table YLJZN.JZ_MEDICALAFTER
     *
     * @ibatorgenerated Mon Jun 08 10:57:47 CST 2015
     */
    public int deleteByPrimaryKey(BigDecimal maId) {
        JzMedicalafter key = new JzMedicalafter();
        key.setMaId(maId);
        int rows = getSqlMapClientTemplate().delete("JZ_MEDICALAFTER.ibatorgenerated_deleteByPrimaryKey", key);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table YLJZN.JZ_MEDICALAFTER
     *
     * @ibatorgenerated Mon Jun 08 10:57:47 CST 2015
     */
    public BigDecimal insert(JzMedicalafter record) {
        Object newKey = getSqlMapClientTemplate().insert("JZ_MEDICALAFTER.ibatorgenerated_insert", record);
        return (BigDecimal) newKey;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table YLJZN.JZ_MEDICALAFTER
     *
     * @ibatorgenerated Mon Jun 08 10:57:47 CST 2015
     */
    public BigDecimal insertSelective(JzMedicalafter record) {
        Object newKey = getSqlMapClientTemplate().insert("JZ_MEDICALAFTER.ibatorgenerated_insertSelective", record);
        return (BigDecimal) newKey;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table YLJZN.JZ_MEDICALAFTER
     *
     * @ibatorgenerated Mon Jun 08 10:57:47 CST 2015
     */
    @SuppressWarnings("unchecked")
    public List<JzMedicalafter> selectByExample(JzMedicalafterExample example) {
        List<JzMedicalafter> list = getSqlMapClientTemplate().queryForList("JZ_MEDICALAFTER.ibatorgenerated_selectByExample", example);
        return list;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table YLJZN.JZ_MEDICALAFTER
     *
     * @ibatorgenerated Mon Jun 08 10:57:47 CST 2015
     */
    public JzMedicalafter selectByPrimaryKey(BigDecimal maId) {
        JzMedicalafter key = new JzMedicalafter();
        key.setMaId(maId);
        JzMedicalafter record = (JzMedicalafter) getSqlMapClientTemplate().queryForObject("JZ_MEDICALAFTER.ibatorgenerated_selectByPrimaryKey", key);
        return record;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table YLJZN.JZ_MEDICALAFTER
     *
     * @ibatorgenerated Mon Jun 08 10:57:47 CST 2015
     */
    public int updateByExampleSelective(JzMedicalafter record, JzMedicalafterExample example) {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        int rows = getSqlMapClientTemplate().update("JZ_MEDICALAFTER.ibatorgenerated_updateByExampleSelective", parms);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table YLJZN.JZ_MEDICALAFTER
     *
     * @ibatorgenerated Mon Jun 08 10:57:47 CST 2015
     */
    public int updateByExample(JzMedicalafter record, JzMedicalafterExample example) {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        int rows = getSqlMapClientTemplate().update("JZ_MEDICALAFTER.ibatorgenerated_updateByExample", parms);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table YLJZN.JZ_MEDICALAFTER
     *
     * @ibatorgenerated Mon Jun 08 10:57:47 CST 2015
     */
    public int updateByPrimaryKeySelective(JzMedicalafter record) {
        int rows = getSqlMapClientTemplate().update("JZ_MEDICALAFTER.ibatorgenerated_updateByPrimaryKeySelective", record);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table YLJZN.JZ_MEDICALAFTER
     *
     * @ibatorgenerated Mon Jun 08 10:57:47 CST 2015
     */
    public int updateByPrimaryKey(JzMedicalafter record) {
        int rows = getSqlMapClientTemplate().update("JZ_MEDICALAFTER.ibatorgenerated_updateByPrimaryKey", record);
        return rows;
    }

    /**
     * This class was generated by Apache iBATIS ibator.
     * This class corresponds to the database table YLJZN.JZ_MEDICALAFTER
     *
     * @ibatorgenerated Mon Jun 08 10:57:47 CST 2015
     */
    private static class UpdateByExampleParms extends JzMedicalafterExample {
        private Object record;

        public UpdateByExampleParms(Object record, JzMedicalafterExample example) {
            super(example);
            this.record = record;
        }

        public Object getRecord() {
            return record;
        }
    }
}