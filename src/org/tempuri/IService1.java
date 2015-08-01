/**
 * IService1.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.tempuri;

public interface IService1 extends java.rmi.Remote {
    public java.lang.String getData(java.lang.Integer value) throws java.rmi.RemoteException;
    public org.datacontract.schemas._2004._07.WcfMedExchange.CompositeType getDataUsingDataContract(org.datacontract.schemas._2004._07.WcfMedExchange.CompositeType composite) throws java.rmi.RemoteException;
    public java.lang.String setAssistStatusAll(java.lang.Long bizNo) throws java.rmi.RemoteException;
    public java.lang.String setAssistStatusBatch(java.lang.Long bizNo, java.lang.String orgCode) throws java.rmi.RemoteException;
    public java.lang.String setAssistStatusSingle(java.lang.String IDNumber, java.lang.String memberName, java.lang.String familyNo, java.lang.String assistType, java.lang.String stsTime) throws java.rmi.RemoteException;
    public java.lang.String getMedicareInfoAll() throws java.rmi.RemoteException;
    public java.lang.String getMedicareInfoBatch(java.lang.Long bizNo, java.lang.String orgCode) throws java.rmi.RemoteException;
    public java.lang.String getMedicareInfoSingle(java.lang.String IDNumber, java.lang.String memberName) throws java.rmi.RemoteException;
    public java.lang.String getChronicDiseList() throws java.rmi.RemoteException;
    public java.lang.String getHospitalList() throws java.rmi.RemoteException;
    public java.lang.String getHosList() throws java.rmi.RemoteException;
    public java.lang.String getChronicDisePersonAll() throws java.rmi.RemoteException;
    public java.lang.String getChronicDisePersonBatch(java.lang.Long bizNo, java.lang.String orgCode) throws java.rmi.RemoteException;
    public java.lang.String getChronicDisePersonSingle(java.lang.String IDNumber, java.lang.String memberName) throws java.rmi.RemoteException;
    public java.lang.String getCbxx(java.lang.String ybNumber) throws java.rmi.RemoteException;
    public org.tempuri.GetOutpatientBillResponseGetOutpatientBillResult getOutpatientBill(java.lang.String ybNumber, java.lang.String beginTime, java.lang.String endTime, java.lang.String hospitalID) throws java.rmi.RemoteException;
    public java.lang.String getOutpatientBillStr(java.lang.String ybNumber, java.lang.String beginTime, java.lang.String endTime, java.lang.String hospitalID) throws java.rmi.RemoteException;
    public org.tempuri.GetInpatientRegResponseGetInpatientRegResult getInpatientReg(java.lang.String ybNumber, java.lang.String beginTime, java.lang.String endTime, java.lang.String hospitalID) throws java.rmi.RemoteException;
    public java.lang.String getInpatientRegStr(java.lang.String ybNumber, java.lang.String beginTime, java.lang.String endTime, java.lang.String hospitalID) throws java.rmi.RemoteException;
    public org.tempuri.GetInpatientBillResponseGetInpatientBillResult getInpatientBill(java.lang.String ybNumber, java.lang.String hospitalID, java.lang.String serialNo) throws java.rmi.RemoteException;
    public java.lang.String getInpatientBillStr(java.lang.String ybNumber, java.lang.String hospitalID, java.lang.String serialNo) throws java.rmi.RemoteException;
    public org.tempuri.GetInpatientBillListResponseGetInpatientBillListResult getInpatientBillList(java.lang.String ybNumber, java.lang.String hospitalID, java.lang.String serialNo) throws java.rmi.RemoteException;
    public java.lang.String getPersonPay(java.lang.String ybNumber, java.lang.String hospitalID, java.lang.String serialNo, java.lang.String endTime, java.lang.Integer bizType) throws java.rmi.RemoteException;
}
