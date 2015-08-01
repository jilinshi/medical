/**
 * GetPersonPay.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.tempuri;

public class GetPersonPay  implements java.io.Serializable {
    private java.lang.String ybNumber;

    private java.lang.String hospitalID;

    private java.lang.String serialNo;

    private java.lang.String endTime;

    private java.lang.Integer bizType;

    public GetPersonPay() {
    }

    public GetPersonPay(
           java.lang.String ybNumber,
           java.lang.String hospitalID,
           java.lang.String serialNo,
           java.lang.String endTime,
           java.lang.Integer bizType) {
           this.ybNumber = ybNumber;
           this.hospitalID = hospitalID;
           this.serialNo = serialNo;
           this.endTime = endTime;
           this.bizType = bizType;
    }


    /**
     * Gets the ybNumber value for this GetPersonPay.
     * 
     * @return ybNumber
     */
    public java.lang.String getYbNumber() {
        return ybNumber;
    }


    /**
     * Sets the ybNumber value for this GetPersonPay.
     * 
     * @param ybNumber
     */
    public void setYbNumber(java.lang.String ybNumber) {
        this.ybNumber = ybNumber;
    }


    /**
     * Gets the hospitalID value for this GetPersonPay.
     * 
     * @return hospitalID
     */
    public java.lang.String getHospitalID() {
        return hospitalID;
    }


    /**
     * Sets the hospitalID value for this GetPersonPay.
     * 
     * @param hospitalID
     */
    public void setHospitalID(java.lang.String hospitalID) {
        this.hospitalID = hospitalID;
    }


    /**
     * Gets the serialNo value for this GetPersonPay.
     * 
     * @return serialNo
     */
    public java.lang.String getSerialNo() {
        return serialNo;
    }


    /**
     * Sets the serialNo value for this GetPersonPay.
     * 
     * @param serialNo
     */
    public void setSerialNo(java.lang.String serialNo) {
        this.serialNo = serialNo;
    }


    /**
     * Gets the endTime value for this GetPersonPay.
     * 
     * @return endTime
     */
    public java.lang.String getEndTime() {
        return endTime;
    }


    /**
     * Sets the endTime value for this GetPersonPay.
     * 
     * @param endTime
     */
    public void setEndTime(java.lang.String endTime) {
        this.endTime = endTime;
    }


    /**
     * Gets the bizType value for this GetPersonPay.
     * 
     * @return bizType
     */
    public java.lang.Integer getBizType() {
        return bizType;
    }


    /**
     * Sets the bizType value for this GetPersonPay.
     * 
     * @param bizType
     */
    public void setBizType(java.lang.Integer bizType) {
        this.bizType = bizType;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof GetPersonPay)) return false;
        GetPersonPay other = (GetPersonPay) obj;
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
            ((this.hospitalID==null && other.getHospitalID()==null) || 
             (this.hospitalID!=null &&
              this.hospitalID.equals(other.getHospitalID()))) &&
            ((this.serialNo==null && other.getSerialNo()==null) || 
             (this.serialNo!=null &&
              this.serialNo.equals(other.getSerialNo()))) &&
            ((this.endTime==null && other.getEndTime()==null) || 
             (this.endTime!=null &&
              this.endTime.equals(other.getEndTime()))) &&
            ((this.bizType==null && other.getBizType()==null) || 
             (this.bizType!=null &&
              this.bizType.equals(other.getBizType())));
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
        if (getHospitalID() != null) {
            _hashCode += getHospitalID().hashCode();
        }
        if (getSerialNo() != null) {
            _hashCode += getSerialNo().hashCode();
        }
        if (getEndTime() != null) {
            _hashCode += getEndTime().hashCode();
        }
        if (getBizType() != null) {
            _hashCode += getBizType().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(GetPersonPay.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://tempuri.org/", ">GetPersonPay"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ybNumber");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "YbNumber"));
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
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("serialNo");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "SerialNo"));
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
        elemField.setFieldName("bizType");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "BizType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
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
