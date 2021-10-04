package com.fyndna.app.vo;

//import javax.xml.bind.annotation.XmlAccessType;
//import javax.xml.bind.annotation.XmlAccessorType;
//import javax.xml.bind.annotation.XmlElement;
//import javax.xml.bind.annotation.XmlRootElement;
//
//@XmlRootElement(name="extractdata")
//@XmlAccessorType(XmlAccessType.FIELD)

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

//@ToString
@XmlRootElement
@XmlType(propOrder={"custNo", "name", "country"})
public class Customer {

  private int custNo;
  private String name;
  private String country;

  public Customer() {

  }

  public Customer(int inp) {
  	this.custNo=inp;
  }

  public Customer(int custNumber, String name, String country) {
      this.custNo = custNumber;
      this.name = name;
      this.country = country;
  }

  public int getCustNo() {
      return custNo;
  }

  public void setCustNo(int custNo) {
      this.custNo = custNo;
  }

  public String getName() {
      return name;
  }

  public void setName(String name) {
      this.name = name;
  }

  public String getCountry() {
      return country;
  }

  public void setCountry(String country) {
      this.country = country;
  }

}