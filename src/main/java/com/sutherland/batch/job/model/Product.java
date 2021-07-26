package com.sutherland.batch.job.model;

import java.math.BigDecimal;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "product")
@XmlAccessorType(XmlAccessType.FIELD)
public class Product {

	@XmlElement(name = "productId")
	private Integer productID;
	
	@XmlElement(name = "productName")
	private String prodName;

	@XmlElement(name = "price")
	private BigDecimal price;
	
	@XmlElement(name = "unit")
	private Integer unit;
	
	@XmlElement(name = "productDesc")
	private String productDesc;

	public Integer getProductID() {
		return productID;
	}

	public void setProductID(Integer productID) {
		this.productID = productID;
	}

	public String getProdName() {
		return prodName;
	}

	public void setProdName(String prodName) {
		this.prodName = prodName;
	}

	public String getProductDesc() {
		return productDesc;
	}

	public void setProductDesc(String productDesc) {
		this.productDesc = productDesc;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public Integer getUnit() {
		return unit;
	}

	public void setUnit(Integer unit) {
		this.unit = unit;
	}

	@Override
	public String toString() {
		return "Product{" + "productID=" + productID + ", productName='" + prodName + '\'' + ", productDesc='"
				+ productDesc + '\'' + ", price=" + price + ", unit=" + unit + '}';
	}
}
