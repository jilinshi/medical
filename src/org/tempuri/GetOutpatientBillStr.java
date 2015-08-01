/**
 * GetOutpatientBillStr.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.tempuri;

public class GetOutpatientBillStr  implements java.io.Serializable {
    private java.lang.String ybNumber;

    private java.lang.String beginTime;

    private java.lang.String endTime;

    private java.lang.String hospitalID;

    public GetOutpatientBillStr() {
    }

    public GetOutpatientBillStr(
           java.lang.String ybNumber,
           java.lang.String beginTime,
           java.lang.String endTime,
           java.lang.String hospitalID) {
           this.ybNumber = ybNumber;
           this.beginTime = beginTime;
           this.endTime = endTime;
           this.hospitalID = hospitalID;
    }


    /**
     * Gets the ybNumber value for this GetOutpatientBillStr.
     * 
     * @return ybNumber
     */
    public java.lang.String getYbNumber() {
        return ybNumber;
    }


    /**
     * Sets the ybNumber value for this GetOutpatientBillStr.
     * 
     * @param ybNumber
     */
    public void setYbNumber(java.lang.String ybNumber) {
        this.ybNumber = ybNumber;
    }


    /**
     * Gets the beginTime value for this GetOutpatientBillStr.
     * 
     * @return beginTime
     */
    public java.lang.String getBeginTime() {
        return beginTime;
    }


    /**
     * Sets the beginTime value for this GetOutpatientBillStr.
     * 
     * @param beginTime
     */
    public void setBeginTime(java.lang.String beginTime) {
        this.beginTime = beginTime;
    }


    /**
     * Gets the endTime value for this GetOutpatientBillStr.
     * 
     * @return endTime
     */
    public java.lang.String getEndTime() {
        return endTime;
    }


    /**
     * Sets the endTime value for this GetOutpatientBillStr.
     * 
     * @param endTime
     */
    public void setEndTime(java.lang.String endTime) {
        this.endTime = endTime;
    }


    /**
     * Gets the hospitalID value for this GetOutpatientBillStr.
     * 
     * @return hospitalID
     */
    public java.lang.String getHospitalID() {
        return hospitalID;
    }


    /**
     * Sets the hospitalID value for this GetOutpatientBillStr.
     * 
     * @param hospitalID
     */
    public void setHospitalID(java.lang.String hospitalID) {
        this.hospitalID = hospitalID;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof GetOutpatientBillStr)) return false;
        GetOutpatientBillStr other = (GetOutpatientBillStr) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.ybNumber==null && other.getYbNumber()==null) || 
             (this.ybNumber!=null &&
              this.ybNumber.equals(other.getYbNumber()))) &&
            ((this.beginTime==null && other.getBeginTime()==null) || 
             (this.beginTime!=null &&
              this.beginTime.equals(other.getBeginTime()))) &&
            ((this.endTime==null && other.getEndTime()==null) || 
             (this.endTime!=null &&
              this.endTime.equals(other.getEndTime()))) &&
            ((this.hospitalID==null && other.getHospitalID()==null) || 
             (this.hospitalID!=null &&
              this.hospitalID.equals(other.getHospitalID())));
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;
    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = 1;
        if (getYbNumber() != null) {
            _hashCode += getYbNumber().hashCode();
        }
        if (getBeginTime() != null) {
            _hashCode += getBeginTime().hashCode();
        }
        if (getEndTime() != null) {
            _hashCode += getEndTime().hashCode();
        }
        if (getHospitalID() != null) {
            _hashCode += getHospitalID().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(GetOutpatientBillStr.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://tempuri.org/", ">GetOutpatientBillStr"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ybNumber");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "YbNumber"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("beginTime");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "BeginTime"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("endTime");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "EndTime"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("hospitalID");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "HospitalID"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
    }

    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

    /**
     * Get Custom Serializer
     */
    public static org.apache.axis.encoding.Serializer getSerializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanSerializer(
            _javaType, _xmlType, typeDesc);
    }

    /**
     * Get Custom Deserializer
     */
    public static org.apache.axis.encoding.Deserializer getDeserializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanDeserializer(
            _javaType, _xmlType, typeDesc);
    }

}
