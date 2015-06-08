package dbbx;

public class IServiceYljzJlcProxy implements IServiceYljzJlc {
  private String _endpoint = null;
  private IServiceYljzJlc iServiceYljzJlc = null;
  
  public IServiceYljzJlcProxy() {
    _initIServiceYljzJlcProxy();
  }
  
  public IServiceYljzJlcProxy(String endpoint) {
    _endpoint = endpoint;
    _initIServiceYljzJlcProxy();
  }
  
  private void _initIServiceYljzJlcProxy() {
    try {
      iServiceYljzJlc = (new ServiceYljzJlcLocator()).getBasicHttpBinding_IServiceYljzJlc();
      if (iServiceYljzJlc != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)iServiceYljzJlc)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)iServiceYljzJlc)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (iServiceYljzJlc != null)
      ((javax.xml.rpc.Stub)iServiceYljzJlc)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public IServiceYljzJlc getIServiceYljzJlc() {
    if (iServiceYljzJlc == null)
      _initIServiceYljzJlcProxy();
    return iServiceYljzJlc;
  }
  
  public java.lang.String getUserLogs(java.lang.String hospitalID, java.lang.String id, java.lang.String mode, java.lang.String work, java.lang.String begdt, java.lang.String enddt, java.lang.String status) throws java.rmi.RemoteException{
    if (iServiceYljzJlc == null)
      _initIServiceYljzJlcProxy();
    return iServiceYljzJlc.getUserLogs(hospitalID, id, mode, work, begdt, enddt, status);
  }
  
  public java.lang.String getAccBizID(java.lang.String ssn, java.lang.String serialno) throws java.rmi.RemoteException{
    if (iServiceYljzJlc == null)
      _initIServiceYljzJlcProxy();
    return iServiceYljzJlc.getAccBizID(ssn, serialno);
  }
  
  public java.lang.String getDbbxScopeEx(java.lang.String paperid, java.lang.String nYear) throws java.rmi.RemoteException{
    if (iServiceYljzJlc == null)
      _initIServiceYljzJlcProxy();
    return iServiceYljzJlc.getDbbxScopeEx(paperid, nYear);
  }
  
  public java.lang.String getDbbxScopeMinValue(java.lang.Integer ds) throws java.rmi.RemoteException{
    if (iServiceYljzJlc == null)
      _initIServiceYljzJlcProxy();
    return iServiceYljzJlc.getDbbxScopeMinValue(ds);
  }
  
  public java.lang.String getDbbxValue(java.math.BigDecimal sumPreScope, java.math.BigDecimal zfyMony, java.math.BigDecimal tcMony, java.math.BigDecimal noPay, java.lang.Integer wsFlag, java.lang.Integer ds) throws java.rmi.RemoteException{
    if (iServiceYljzJlc == null)
      _initIServiceYljzJlcProxy();
    return iServiceYljzJlc.getDbbxValue(sumPreScope, zfyMony, tcMony, noPay, wsFlag, ds);
  }
  
  public java.lang.String getHospitalLic(java.lang.String hospitalID) throws java.rmi.RemoteException{
    if (iServiceYljzJlc == null)
      _initIServiceYljzJlcProxy();
    return iServiceYljzJlc.getHospitalLic(hospitalID);
  }
  
  public java.lang.String getIdentityCheck(java.lang.String idCard) throws java.rmi.RemoteException{
    if (iServiceYljzJlc == null)
      _initIServiceYljzJlcProxy();
    return iServiceYljzJlc.getIdentityCheck(idCard);
  }
  
  public java.lang.String getExistInReg(java.lang.String idCard) throws java.rmi.RemoteException{
    if (iServiceYljzJlc == null)
      _initIServiceYljzJlcProxy();
    return iServiceYljzJlc.getExistInReg(idCard);
  }
  
  public java.lang.String getBizID() throws java.rmi.RemoteException{
    if (iServiceYljzJlc == null)
      _initIServiceYljzJlcProxy();
    return iServiceYljzJlc.getBizID();
  }
  
  public java.lang.String setRuralBill(java.lang.String pageno, java.lang.String name, java.lang.String sex, java.lang.String familyno, java.lang.String paperid, java.lang.String address, java.lang.String ssn, java.lang.String hospitalname, java.lang.String operhostid, java.lang.String begintime, java.lang.String endtime, java.lang.String selltime, java.lang.String sickname, java.lang.String sickid, java.lang.String treatmenttype, java.lang.String treatmenttypeid, java.math.BigDecimal i111, java.math.BigDecimal i001, java.math.BigDecimal i116, java.math.BigDecimal i201, java.math.BigDecimal i999, java.lang.String oper, java.lang.String paperpoto, java.lang.String accpoto, java.lang.String notespoto, java.lang.String dbbxpoto, java.lang.String lb_ws, java.lang.String lb_zz) throws java.rmi.RemoteException{
    if (iServiceYljzJlc == null)
      _initIServiceYljzJlcProxy();
    return iServiceYljzJlc.setRuralBill(pageno, name, sex, familyno, paperid, address, ssn, hospitalname, operhostid, begintime, endtime, selltime, sickname, sickid, treatmenttype, treatmenttypeid, i111, i001, i116, i201, i999, oper, paperpoto, accpoto, notespoto, dbbxpoto, lb_ws, lb_zz);
  }
  
  public java.lang.String updateRuralBill(java.lang.String ruralID, java.lang.String pageno, java.lang.String name, java.lang.String sex, java.lang.String familyno, java.lang.String paperid, java.lang.String address, java.lang.String ssn, java.lang.String hospitalname, java.lang.String operhostid, java.lang.String begintime, java.lang.String endtime, java.lang.String selltime, java.lang.String sickname, java.lang.String sickid, java.lang.String treatmenttype, java.lang.String treatmenttypeid, java.math.BigDecimal i111, java.math.BigDecimal i001, java.math.BigDecimal i116, java.math.BigDecimal i201, java.math.BigDecimal i999, java.lang.String oper, java.lang.String paperpoto, java.lang.String accpoto, java.lang.String notespoto, java.lang.String dbbxpoto, java.lang.String lb_ws, java.lang.String lb_zz) throws java.rmi.RemoteException{
    if (iServiceYljzJlc == null)
      _initIServiceYljzJlcProxy();
    return iServiceYljzJlc.updateRuralBill(ruralID, pageno, name, sex, familyno, paperid, address, ssn, hospitalname, operhostid, begintime, endtime, selltime, sickname, sickid, treatmenttype, treatmenttypeid, i111, i001, i116, i201, i999, oper, paperpoto, accpoto, notespoto, dbbxpoto, lb_ws, lb_zz);
  }
  
  public java.lang.String setRuralInReg(java.lang.String pageno, java.lang.String name, java.lang.String sex, java.lang.String familyno, java.lang.String paperid, java.lang.String address, java.lang.String ssn, java.lang.String hospitalname, java.lang.String operhostid, java.lang.String inDept, java.lang.String inArea, java.lang.String begintime, java.lang.String sickname, java.lang.String sickid, java.lang.String treatmenttype, java.lang.String treatmenttypeid, java.lang.String oper, java.lang.String lb_ws) throws java.rmi.RemoteException{
    if (iServiceYljzJlc == null)
      _initIServiceYljzJlcProxy();
    return iServiceYljzJlc.setRuralInReg(pageno, name, sex, familyno, paperid, address, ssn, hospitalname, operhostid, inDept, inArea, begintime, sickname, sickid, treatmenttype, treatmenttypeid, oper, lb_ws);
  }
  
  public java.lang.String getRuralInRegList(java.lang.String paperid, java.lang.String hospitalCode) throws java.rmi.RemoteException{
    if (iServiceYljzJlc == null)
      _initIServiceYljzJlcProxy();
    return iServiceYljzJlc.getRuralInRegList(paperid, hospitalCode);
  }
  
  public java.lang.String setPhotoPath(java.lang.String bizID, java.lang.String confirm_id, java.lang.String photoPath) throws java.rmi.RemoteException{
    if (iServiceYljzJlc == null)
      _initIServiceYljzJlcProxy();
    return iServiceYljzJlc.setPhotoPath(bizID, confirm_id, photoPath);
  }
  
  public java.lang.String setPhotoPath2(java.lang.String bizID, java.lang.String photoPath) throws java.rmi.RemoteException{
    if (iServiceYljzJlc == null)
      _initIServiceYljzJlcProxy();
    return iServiceYljzJlc.setPhotoPath2(bizID, photoPath);
  }
  
  public java.lang.String calculateCharge(java.lang.String BIZ_ID, java.lang.String hosType) throws java.rmi.RemoteException{
    if (iServiceYljzJlc == null)
      _initIServiceYljzJlcProxy();
    return iServiceYljzJlc.calculateCharge(BIZ_ID, hosType);
  }
  
  public java.lang.String getConfirmFlag(java.lang.String BIZID) throws java.rmi.RemoteException{
    if (iServiceYljzJlc == null)
      _initIServiceYljzJlcProxy();
    return iServiceYljzJlc.getConfirmFlag(BIZID);
  }
  
  public java.lang.String getConfirm_ID(java.lang.String SSN) throws java.rmi.RemoteException{
    if (iServiceYljzJlc == null)
      _initIServiceYljzJlcProxy();
    return iServiceYljzJlc.getConfirm_ID(SSN);
  }
  
  public java.lang.String chargeCancel(java.lang.String BIZ_ID) throws java.rmi.RemoteException{
    if (iServiceYljzJlc == null)
      _initIServiceYljzJlcProxy();
    return iServiceYljzJlc.chargeCancel(BIZ_ID);
  }
  
  public java.lang.String bizInfoQue(java.lang.String hospitalID, java.lang.String SSN, java.lang.String operUID, java.lang.String operTimeBeg, java.lang.String operTimeEnd, java.lang.String chargeType, java.lang.String patientType) throws java.rmi.RemoteException{
    if (iServiceYljzJlc == null)
      _initIServiceYljzJlcProxy();
    return iServiceYljzJlc.bizInfoQue(hospitalID, SSN, operUID, operTimeBeg, operTimeEnd, chargeType, patientType);
  }
  
  public java.lang.String checkOutFlag(java.lang.String hospitalID, java.lang.String SSN, java.lang.String begDT, java.lang.String endDT) throws java.rmi.RemoteException{
    if (iServiceYljzJlc == null)
      _initIServiceYljzJlcProxy();
    return iServiceYljzJlc.checkOutFlag(hospitalID, SSN, begDT, endDT);
  }
  
  public java.lang.String checkOutFlagInfo(java.lang.String hospitalID, java.lang.String SSN, java.lang.String begDT, java.lang.String endDT, java.lang.String illType) throws java.rmi.RemoteException{
    if (iServiceYljzJlc == null)
      _initIServiceYljzJlcProxy();
    return iServiceYljzJlc.checkOutFlagInfo(hospitalID, SSN, begDT, endDT, illType);
  }
  
  public java.lang.String checkFeeBatchOutFlag(java.lang.String SERIAL_NO) throws java.rmi.RemoteException{
    if (iServiceYljzJlc == null)
      _initIServiceYljzJlcProxy();
    return iServiceYljzJlc.checkFeeBatchOutFlag(SERIAL_NO);
  }
  
  public java.lang.String getQueryXMLStr(java.lang.String SQL) throws java.rmi.RemoteException{
    if (iServiceYljzJlc == null)
      _initIServiceYljzJlcProxy();
    return iServiceYljzJlc.getQueryXMLStr(SQL);
  }
  
  public java.lang.String getAlreadyGet(java.lang.String BIZID) throws java.rmi.RemoteException{
    if (iServiceYljzJlc == null)
      _initIServiceYljzJlcProxy();
    return iServiceYljzJlc.getAlreadyGet(BIZID);
  }
  
  public java.lang.String setAlreadyGet(java.lang.String BIZID) throws java.rmi.RemoteException{
    if (iServiceYljzJlc == null)
      _initIServiceYljzJlcProxy();
    return iServiceYljzJlc.setAlreadyGet(BIZID);
  }
  
  public java.lang.String getOutFlag(java.lang.String SSN, java.lang.String serialNO, java.lang.String feeBatch) throws java.rmi.RemoteException{
    if (iServiceYljzJlc == null)
      _initIServiceYljzJlcProxy();
    return iServiceYljzJlc.getOutFlag(SSN, serialNO, feeBatch);
  }
  
  public java.lang.String BIZDataInsert(java.lang.String BIZSQL, java.lang.String payListSQL, java.lang.String feeInfoSQL, java.lang.String feeListSQL) throws java.rmi.RemoteException{
    if (iServiceYljzJlc == null)
      _initIServiceYljzJlcProxy();
    return iServiceYljzJlc.BIZDataInsert(BIZSQL, payListSQL, feeInfoSQL, feeListSQL);
  }
  
  public java.lang.String BIZExcSQL(java.lang.String BIZSQL) throws java.rmi.RemoteException{
    if (iServiceYljzJlc == null)
      _initIServiceYljzJlcProxy();
    return iServiceYljzJlc.BIZExcSQL(BIZSQL);
  }
  
  public java.lang.String encodeXML(java.lang.String flag, java.lang.String msg, java.lang.String val) throws java.rmi.RemoteException{
    if (iServiceYljzJlc == null)
      _initIServiceYljzJlcProxy();
    return iServiceYljzJlc.encodeXML(flag, msg, val);
  }
  
  public java.lang.String getBizINList(java.lang.String hospital_id, java.lang.String ssn, java.lang.String begtime, java.lang.String endtime) throws java.rmi.RemoteException{
    if (iServiceYljzJlc == null)
      _initIServiceYljzJlcProxy();
    return iServiceYljzJlc.getBizINList(hospital_id, ssn, begtime, endtime);
  }
  
  public java.lang.String delBizRecord(java.lang.String bizid) throws java.rmi.RemoteException{
    if (iServiceYljzJlc == null)
      _initIServiceYljzJlcProxy();
    return iServiceYljzJlc.delBizRecord(bizid);
  }
  
  public java.lang.String getPayItem() throws java.rmi.RemoteException{
    if (iServiceYljzJlc == null)
      _initIServiceYljzJlcProxy();
    return iServiceYljzJlc.getPayItem();
  }
  
  public java.lang.String getAssistIllness() throws java.rmi.RemoteException{
    if (iServiceYljzJlc == null)
      _initIServiceYljzJlcProxy();
    return iServiceYljzJlc.getAssistIllness();
  }
  
  public java.lang.String getAssistIllnessNC() throws java.rmi.RemoteException{
    if (iServiceYljzJlc == null)
      _initIServiceYljzJlcProxy();
    return iServiceYljzJlc.getAssistIllnessNC();
  }
  
  public java.lang.String readMembers(java.lang.String SSN) throws java.rmi.RemoteException{
    if (iServiceYljzJlc == null)
      _initIServiceYljzJlcProxy();
    return iServiceYljzJlc.readMembers(SSN);
  }
  
  public java.lang.String getRuralRecord(java.lang.String ssn, java.lang.String hostid, java.lang.String begdt, java.lang.String enddt, java.lang.String accdesc, java.lang.String treatmenttype) throws java.rmi.RemoteException{
    if (iServiceYljzJlc == null)
      _initIServiceYljzJlcProxy();
    return iServiceYljzJlc.getRuralRecord(ssn, hostid, begdt, enddt, accdesc, treatmenttype);
  }
  
  public java.lang.String setRuralStatus(java.lang.String invoice, java.lang.String oper, java.lang.String accdesc) throws java.rmi.RemoteException{
    if (iServiceYljzJlc == null)
      _initIServiceYljzJlcProxy();
    return iServiceYljzJlc.setRuralStatus(invoice, oper, accdesc);
  }
  
  public java.lang.String bizInfoAccPrintList(java.lang.String hospitalID, java.lang.String SSN, java.lang.String operUID, java.lang.String operTimeBeg, java.lang.String operTimeEnd, java.lang.String patientType, java.lang.String gatherFlag) throws java.rmi.RemoteException{
    if (iServiceYljzJlc == null)
      _initIServiceYljzJlcProxy();
    return iServiceYljzJlc.bizInfoAccPrintList(hospitalID, SSN, operUID, operTimeBeg, operTimeEnd, patientType, gatherFlag);
  }
  
  public java.lang.String verifyRuralRepeat(java.lang.String paperid, java.lang.String accdate, java.lang.String allmoney, java.lang.String money) throws java.rmi.RemoteException{
    if (iServiceYljzJlc == null)
      _initIServiceYljzJlcProxy();
    return iServiceYljzJlc.verifyRuralRepeat(paperid, accdate, allmoney, money);
  }
  
  public java.lang.String getUserList(java.lang.String hospitalID) throws java.rmi.RemoteException{
    if (iServiceYljzJlc == null)
      _initIServiceYljzJlcProxy();
    return iServiceYljzJlc.getUserList(hospitalID);
  }
  
  public java.lang.String getUserLists(java.lang.String hospitalID, java.lang.String id) throws java.rmi.RemoteException{
    if (iServiceYljzJlc == null)
      _initIServiceYljzJlcProxy();
    return iServiceYljzJlc.getUserLists(hospitalID, id);
  }
  
  public java.lang.String verifyUserPassw(java.lang.String hospitalID, java.lang.String name, java.lang.String passw) throws java.rmi.RemoteException{
    if (iServiceYljzJlc == null)
      _initIServiceYljzJlcProxy();
    return iServiceYljzJlc.verifyUserPassw(hospitalID, name, passw);
  }
  
  public java.lang.String getUserInfo(java.lang.String hospitalID, java.lang.String name) throws java.rmi.RemoteException{
    if (iServiceYljzJlc == null)
      _initIServiceYljzJlcProxy();
    return iServiceYljzJlc.getUserInfo(hospitalID, name);
  }
  
  public java.lang.String verifyUser(java.lang.String hospitalID, java.lang.String name) throws java.rmi.RemoteException{
    if (iServiceYljzJlc == null)
      _initIServiceYljzJlcProxy();
    return iServiceYljzJlc.verifyUser(hospitalID, name);
  }
  
  public java.lang.String getDeptList(java.lang.String deptid) throws java.rmi.RemoteException{
    if (iServiceYljzJlc == null)
      _initIServiceYljzJlcProxy();
    return iServiceYljzJlc.getDeptList(deptid);
  }
  
  public java.lang.String getDeptID(java.lang.String name) throws java.rmi.RemoteException{
    if (iServiceYljzJlc == null)
      _initIServiceYljzJlcProxy();
    return iServiceYljzJlc.getDeptID(name);
  }
  
  
}