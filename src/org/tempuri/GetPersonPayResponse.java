/**
 * GetPersonPayResponse.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.tempuri;

public class GetPersonPayResponse  implements java.io.Serializable {
    private java.lang.String getPersonPayResult;

    public GetPersonPayResponse() {
    }

    public GetPersonPayResponse(
           java.lang.String getPersonPayResult) {
           this.getPersonPayResult = getPersonPayResult;
    }


    /**
     * Gets the getPersonPayResult value for this GetPersonPayResponse.
     * 
     * @return getPersonPayResult
     */
    public java.lang.String getGetPersonPayResult() {
        return getPersonPayResult;
    }


    /**
     * Sets the getPersonPayResult value for this GetPersonPayResponse.
     * 
     * @param getPersonPayResult
     */
    public void setGetPersonPayResult(java.lang.String getPersonPayResult) {
        this.getPersonPayResult = getPersonPayResult;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof GetPersonPayResponse)) return false;
        GetPersonPayResponse other = (GetPersonPayResponse) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.getPersonPayResult==null && other.getGetPersonPayResult()==null) || 
             (this.getPersonPayResult!=null &&
              this.getPersonPayResult.equals(other.getGetPersonPayResult())));
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
        if (getGetPersonPayResult() != null) {
            _hashCode += getGetPersonPayResult().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(GetPersonPayResponse.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://tempuri.org/", ">GetPersonPayResponse"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("getPersonPayResult");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "GetPersonPayResult"));
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
