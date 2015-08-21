//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2015.08.21 at 11:39:11 AM EEST 
//


package com.ipr.pa.policyclient.ws.crystal.schemas.entity;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="extCorrData" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="correlationId" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="crystalCorrId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="PropertyList" type="{http://pa.ipr.com/policyclient/ws/crystal/schemas/entity}PropertyList"/>
 *         &lt;element name="EntityList" type="{http://pa.ipr.com/policyclient/ws/crystal/schemas/entity}EntityList"/>
 *         &lt;element name="status">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;enumeration value="OK"/>
 *               &lt;enumeration value="FAILED"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "extCorrData",
    "crystalCorrId",
    "propertyList",
    "entityList",
    "status"
})
@XmlRootElement(name = "EntityListMessage")
public class EntityListMessage {

    protected EntityListMessage.ExtCorrData extCorrData;
    protected String crystalCorrId;
    @XmlElement(name = "PropertyList", required = true)
    protected PropertyList propertyList;
    @XmlElement(name = "EntityList", required = true)
    protected EntityList entityList;
    @XmlElement(required = true, nillable = true)
    protected String status;

    /**
     * Gets the value of the extCorrData property.
     * 
     * @return
     *     possible object is
     *     {@link EntityListMessage.ExtCorrData }
     *     
     */
    public EntityListMessage.ExtCorrData getExtCorrData() {
        return extCorrData;
    }

    /**
     * Sets the value of the extCorrData property.
     * 
     * @param value
     *     allowed object is
     *     {@link EntityListMessage.ExtCorrData }
     *     
     */
    public void setExtCorrData(EntityListMessage.ExtCorrData value) {
        this.extCorrData = value;
    }

    /**
     * Gets the value of the crystalCorrId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCrystalCorrId() {
        return crystalCorrId;
    }

    /**
     * Sets the value of the crystalCorrId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCrystalCorrId(String value) {
        this.crystalCorrId = value;
    }

    /**
     * Gets the value of the propertyList property.
     * 
     * @return
     *     possible object is
     *     {@link PropertyList }
     *     
     */
    public PropertyList getPropertyList() {
        return propertyList;
    }

    /**
     * Sets the value of the propertyList property.
     * 
     * @param value
     *     allowed object is
     *     {@link PropertyList }
     *     
     */
    public void setPropertyList(PropertyList value) {
        this.propertyList = value;
    }

    /**
     * Gets the value of the entityList property.
     * 
     * @return
     *     possible object is
     *     {@link EntityList }
     *     
     */
    public EntityList getEntityList() {
        return entityList;
    }

    /**
     * Sets the value of the entityList property.
     * 
     * @param value
     *     allowed object is
     *     {@link EntityList }
     *     
     */
    public void setEntityList(EntityList value) {
        this.entityList = value;
    }

    /**
     * Gets the value of the status property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStatus() {
        return status;
    }

    /**
     * Sets the value of the status property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStatus(String value) {
        this.status = value;
    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="correlationId" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *       &lt;/sequence>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "correlationId"
    })
    public static class ExtCorrData {

        @XmlElement(required = true)
        protected String correlationId;

        /**
         * Gets the value of the correlationId property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getCorrelationId() {
            return correlationId;
        }

        /**
         * Sets the value of the correlationId property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setCorrelationId(String value) {
            this.correlationId = value;
        }

    }

}
