/**
 * ServiceMainSoap.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package dbbx;

public interface ServiceMainSoap extends java.rmi.Remote {
    public java.lang.String idCheck(java.lang.String hospital_ID, java.lang.String family_No, java.lang.String name, java.lang.String photoFileName) throws java.rmi.RemoteException;
    public java.lang.String checkSSN(java.lang.String hospital_ID, java.lang.String SSN, java.lang.String photoFileName) throws java.rmi.RemoteException;
    public java.lang.String getJzMatchDes(java.lang.String SSN) throws java.rmi.RemoteException;
    public java.lang.String setAssistFlag(java.lang.String bizID) throws java.rmi.RemoteException;
    public java.lang.String setPhotoPath(java.lang.String bizID, java.lang.String confirm_id, java.lang.String photoPath) throws java.rmi.RemoteException;
    public java.lang.String setPhotoPath2(java.lang.String bizID, java.lang.String photoPath) throws java.rmi.RemoteException;
    public java.lang.String calculateCharge(java.lang.String BIZ_ID, java.lang.String hosType) throws java.rmi.RemoteException;
    public java.lang.String getConfirmFlag(java.lang.String BIZID) throws java.rmi.RemoteException;
    public java.lang.String getConfirm_ID(java.lang.String SSN) throws java.rmi.RemoteException;
    public java.lang.String chargeCancel(java.lang.String BIZ_ID) throws java.rmi.RemoteException;
    public java.lang.String bizInfoQue(java.lang.String hospitalID, java.lang.String SSN, java.lang.String operUID, java.lang.String operTimeBeg, java.lang.String operTimeEnd, java.lang.String chargeType, java.lang.String patientType) throws java.rmi.RemoteException;
    public java.lang.String checkOutFlag(java.lang.String hospitalID, java.lang.String SSN, java.lang.String begDT, java.lang.String endDT) throws java.rmi.RemoteException;
    public java.lang.String checkOutFlagInfo(java.lang.String hospitalID, java.lang.String SSN, java.lang.String begDT, java.lang.String endDT, java.lang.String illType) throws java.rmi.RemoteException;
    public java.lang.String checkFeeBatchOutFlag(java.lang.String SERIAL_NO) throws java.rmi.RemoteException;
    public java.lang.String getAlreadyGet(java.lang.String BIZID) throws java.rmi.RemoteException;
    public java.lang.String setAlreadyGet(java.lang.String BIZID) throws java.rmi.RemoteException;
    public java.lang.String getOutFlag(java.lang.String SSN, java.lang.String serialNO, java.lang.String feeBatch) throws java.rmi.RemoteException;
    public java.lang.String BIZDataInsert(java.lang.String BIZSQL, java.lang.String payListSQL, java.lang.String feeInfoSQL, java.lang.String feeListSQL) throws java.rmi.RemoteException;
    public java.lang.String BIZExcSQL(java.lang.String BIZSQL) throws java.rmi.RemoteException;
    public java.lang.String BIZExcSQLEX(java.lang.String BIZSQL) throws java.rmi.RemoteException;
    public java.lang.String savePic(java.lang.String base64Image, java.lang.String bizid, java.lang.String extfn) throws java.rmi.RemoteException;
    public java.lang.String execFunEx(java.lang.String xml) throws java.rmi.RemoteException;
    public java.lang.String getBizINList(java.lang.String hospital_id, java.lang.String ssn, java.lang.String begtime, java.lang.String endtime) throws java.rmi.RemoteException;
    public java.lang.String delBizRecord(java.lang.String bizid) throws java.rmi.RemoteException;
    public java.lang.String getPayItem() throws java.rmi.RemoteException;
    public java.lang.String getAssistIllness() throws java.rmi.RemoteException;
    public java.lang.String getAssistIllnessNC() throws java.rmi.RemoteException;
    public java.lang.String getManualRecord(java.lang.String ssn, java.lang.String out_flag) throws java.rmi.RemoteException;
    public java.lang.String setManualStatus(java.lang.String invoice) throws java.rmi.RemoteException;
    public java.lang.String readMembers(java.lang.String SSN) throws java.rmi.RemoteException;
    public java.lang.String getRuralRecord(java.lang.String ssn, java.lang.String hostid, java.lang.String begdt, java.lang.String enddt, java.lang.String accdesc, java.lang.String treatmenttype) throws java.rmi.RemoteException;
    public java.lang.String setRuralStatus(java.lang.String invoice, java.lang.String oper, java.lang.String accdesc) throws java.rmi.RemoteException;
    public java.lang.String bizInfoAccPrintList(java.lang.String hospitalID, java.lang.String SSN, java.lang.String operUID, java.lang.String operTimeBeg, java.lang.String operTimeEnd, java.lang.String patientType, java.lang.String gatherFlag) throws java.rmi.RemoteException;
    public java.lang.String webLicense(java.lang.String hostID) throws java.rmi.RemoteException;
    public java.lang.String verifyRuralRepeat(java.lang.String SSN, java.lang.String accdate, java.lang.String allmoney, java.lang.String money) throws java.rmi.RemoteException;
    public java.lang.String getUserList(java.lang.String hospitalID) throws java.rmi.RemoteException;
    public java.lang.String getUserLists(java.lang.String hospitalID, java.lang.String id) throws java.rmi.RemoteException;
    public java.lang.String verifyUserPassw(java.lang.String hospitalID, java.lang.String name, java.lang.String passw) throws java.rmi.RemoteException;
    public java.lang.String getUserInfo(java.lang.String hospitalID, java.lang.String name) throws java.rmi.RemoteException;
    public java.lang.String verifyUser(java.lang.String hospitalID, java.lang.String name) throws java.rmi.RemoteException;
    public java.lang.String getDeptList(java.lang.String deptid) throws java.rmi.RemoteException;
    public java.lang.String getDeptID(java.lang.String name) throws java.rmi.RemoteException;
    public java.lang.String getUserLogs(java.lang.String hospitalID, java.lang.String id, java.lang.String mode, java.lang.String work, java.lang.String begdt, java.lang.String enddt, java.lang.String status) throws java.rmi.RemoteException;
    public java.lang.String getAccBizID(java.lang.String ssn, java.lang.String serialno) throws java.rmi.RemoteException;
    public java.lang.String getDbbxScope(java.lang.String paperid) throws java.rmi.RemoteException;
    public java.lang.String getDbbxScopeMinValue(int ds) throws java.rmi.RemoteException;
    public java.lang.String getDbbxValue(java.math.BigDecimal sumPreScope, java.math.BigDecimal zfyMony, java.math.BigDecimal tcMony, java.math.BigDecimal noPay, int wsFlag, int ds) throws java.rmi.RemoteException;
}
