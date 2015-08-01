package org.tempuri;

public class IService1Proxy implements org.tempuri.IService1 {
  private String _endpoint = null;
  private org.tempuri.IService1 iService1 = null;
  
  public IService1Proxy() {
    _initIService1Proxy();
  }
  
  public IService1Proxy(String endpoint) {
    _endpoint = endpoint;
    _initIService1Proxy();
  }
  
  private void _initIService1Proxy() {
    try {
      iService1 = (new org.tempuri.Service1Locator()).getBasicHttpBinding_IService1();
      if (iService1 != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)iService1)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)iService1)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (iService1 != null)
      ((javax.xml.rpc.Stub)iService1)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public org.tempuri.IService1 getIService1() {
    if (iService1 == null)
      _initIService1Proxy();
    return iService1;
  }
  
  public java.lang.String getData(java.lang.Integer value) throws java.rmi.RemoteException{
    if (iService1 == null)
      _initIService1Proxy();
    return iService1.getData(value);
  }
  
  public org.datacontract.schemas._2004._07.WcfMedExchange.CompositeType getDataUsingDataContract(org.datacontract.schemas._2004._07.WcfMedExchange.CompositeType composite) throws java.rmi.RemoteException{
    if (iService1 == null)
      _initIService1Proxy();
    return iService1.getDataUsingDataContract(composite);
  }
  
  public java.lang.String setAssistStatusAll(java.lang.Long bizNo) throws java.rmi.RemoteException{
    if (iService1 == null)
      _initIService1Proxy();
    return iService1.setAssistStatusAll(bizNo);
  }
  
  public java.lang.String setAssistStatusBatch(java.lang.Long bizNo, java.lang.String orgCode) throws java.rmi.RemoteException{
    if (iService1 == null)
      _initIService1Proxy();
    return iService1.setAssistStatusBatch(bizNo, orgCode);
  }
  
  public java.lang.String setAssistStatusSingle(java.lang.String IDNumber, java.lang.String memberName, java.lang.String familyNo, java.lang.String assistType, java.lang.String stsTime) throws java.rmi.RemoteException{
    if (iService1 == null)
      _initIService1Proxy();
    return iService1.setAssistStatusSingle(IDNumber, memberName, familyNo, assistType, stsTime);
  }
  
  public java.lang.String getMedicareInfoAll() throws java.rmi.RemoteException{
    if (iService1 == null)
      _initIService1Proxy();
    return iService1.getMedicareInfoAll();
  }
  
  public java.lang.String getMedicareInfoBatch(java.lang.Long bizNo, java.lang.String orgCode) throws java.rmi.RemoteException{
    if (iService1 == null)
      _initIService1Proxy();
    return iService1.getMedicareInfoBatch(bizNo, orgCode);
  }
  
  public java.lang.String getMedicareInfoSingle(java.lang.String IDNumber, java.lang.String memberName) throws java.rmi.RemoteException{
    if (iService1 == null)
      _initIService1Proxy();
    return iService1.getMedicareInfoSingle(IDNumber, memberName);
  }
  
  public java.lang.String getChronicDiseList() throws java.rmi.RemoteException{
    if (iService1 == null)
      _initIService1Proxy();
    return iService1.getChronicDiseList();
  }
  
  public java.lang.String getHospitalList() throws java.rmi.RemoteException{
    if (iService1 == null)
      _initIService1Proxy();
    return iService1.getHospitalList();
  }
  
  public java.lang.String getHosList() throws java.rmi.RemoteException{
    if (iService1 == null)
      _initIService1Proxy();
    return iService1.getHosList();
  }
  
  public java.lang.String getChronicDisePersonAll() throws java.rmi.RemoteException{
    if (iService1 == null)
      _initIService1Proxy();
    return iService1.getChronicDisePersonAll();
  }
  
  public java.lang.String getChronicDisePersonBatch(java.lang.Long bizNo, java.lang.String orgCode) throws java.rmi.RemoteException{
    if (iService1 == null)
      _initIService1Proxy();
    return iService1.getChronicDisePersonBatch(bizNo, orgCode);
  }
  
  public java.lang.String getChronicDisePersonSingle(java.lang.String IDNumber, java.lang.String memberName) throws java.rmi.RemoteException{
    if (iService1 == null)
      _initIService1Proxy();
    return iService1.getChronicDisePersonSingle(IDNumber, memberName);
  }
  
  public java.lang.String getCbxx(java.lang.String ybNumber) throws java.rmi.RemoteException{
    if (iService1 == null)
      _initIService1Proxy();
    return iService1.getCbxx(ybNumber);
  }
  
  public org.tempuri.GetOutpatientBillResponseGetOutpatientBillResult getOutpatientBill(java.lang.String ybNumber, java.lang.String beginTime, java.lang.String endTime, java.lang.String hospitalID) throws java.rmi.RemoteException{
    if (iService1 == null)
      _initIService1Proxy();
    return iService1.getOutpatientBill(ybNumber, beginTime, endTime, hospitalID);
  }
  
  public java.lang.String getOutpatientBillStr(java.lang.String ybNumber, java.lang.String beginTime, java.lang.String endTime, java.lang.String hospitalID) throws java.rmi.RemoteException{
    if (iService1 == null)
      _initIService1Proxy();
    return iService1.getOutpatientBillStr(ybNumber, beginTime, endTime, hospitalID);
  }
  
  public org.tempuri.GetInpatientRegResponseGetInpatientRegResult getInpatientReg(java.lang.String ybNumber, java.lang.String beginTime, java.lang.String endTime, java.lang.String hospitalID) throws java.rmi.RemoteException{
    if (iService1 == null)
      _initIService1Proxy();
    return iService1.getInpatientReg(ybNumber, beginTime, endTime, hospitalID);
  }
  
  public java.lang.String getInpatientRegStr(java.lang.String ybNumber, java.lang.String beginTime, java.lang.String endTime, java.lang.String hospitalID) throws java.rmi.RemoteException{
    if (iService1 == null)
      _initIService1Proxy();
    return iService1.getInpatientRegStr(ybNumber, beginTime, endTime, hospitalID);
  }
  
  public org.tempuri.GetInpatientBillResponseGetInpatientBillResult getInpatientBill(java.lang.String ybNumber, java.lang.String hospitalID, java.lang.String serialNo) throws java.rmi.RemoteException{
    if (iService1 == null)
      _initIService1Proxy();
    return iService1.getInpatientBill(ybNumber, hospitalID, serialNo);
  }
  
  public java.lang.String getInpatientBillStr(java.lang.String ybNumber, java.lang.String hospitalID, java.lang.String serialNo) throws java.rmi.RemoteException{
    if (iService1 == null)
      _initIService1Proxy();
    return iService1.getInpatientBillStr(ybNumber, hospitalID, serialNo);
  }
  
  public org.tempuri.GetInpatientBillListResponseGetInpatientBillListResult getInpatientBillList(java.lang.String ybNumber, java.lang.String hospitalID, java.lang.String serialNo) throws java.rmi.RemoteException{
    if (iService1 == null)
      _initIService1Proxy();
    return iService1.getInpatientBillList(ybNumber, hospitalID, serialNo);
  }
  
  public java.lang.String getPersonPay(java.lang.String ybNumber, java.lang.String hospitalID, java.lang.String serialNo, java.lang.String endTime, java.lang.Integer bizType) throws java.rmi.RemoteException{
    if (iService1 == null)
      _initIService1Proxy();
    return iService1.getPersonPay(ybNumber, hospitalID, serialNo, endTime, bizType);
  }
  
  
}