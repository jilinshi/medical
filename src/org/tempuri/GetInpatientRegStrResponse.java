/**
 * GetInpatientRegStrResponse.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.tempuri;

public class GetInpatientRegStrResponse  implements java.io.Serializable {
    private java.lang.String getInpatientRegStrResult;

    public GetInpatientRegStrResponse() {
    }

    public GetInpatientRegStrResponse(
           java.lang.String getInpatientRegStrResult) {
           this.getInpatientRegStrResult = getInpatientRegStrResult;
    }


    /**
     * Gets the getInpatientRegStrResult value for this GetInpatientRegStrResponse.
     * 
     * @return getInpatientRegStrResult
     */
    public java.lang.String getGetInpatientRegStrResult() {
        return getInpatientRegStrResult;
    }


    /**
     * Sets the getInpatientRegStrResult value for this GetInpatientRegStrResponse.
     * 
     * @param getInpatientRegStrResult
     */
    public void setGetInpatientRegStrResult(java.lang.String getInpatientRegStrResult) {
        this.getInpatientRegStrResult = getInpatientRegStrResult;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof GetInpatientRegStrResponse)) return false;
        GetInpatientRegStrResponse other = (GetInpatientRegStrResponse) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.getInpatientRegStrResult==null && other.getGetInpatientRegStrResult()==null) || 
             (this.getInpatientRegStrResult!=null &&
              this.getInpatientRegStrResult.equals(other.getGetInpatientRegStrResult())));
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
        if (getGetInpatientRegStrResult() != null) {
            _hashCode += getGetInpatientRegStrResult().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(GetInpatientRegStrResponse.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://tempuri.org/", ">GetInpatientRegStrResponse"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("getInpatientRegStrResult");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "GetInpatientRegStrResult"));
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
