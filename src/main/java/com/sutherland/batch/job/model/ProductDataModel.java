package com.sutherland.batch.job.model;

public class ProductDataModel {

	private int id;
	private String productname;
	private String productcategory;
	private String productsubcategory;
	private String productcontainer;
	private String productbasemargin;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getProductname() {
		return productname;
	}

	public void setProductname(String productname) {
		this.productname = productname;
	}

	public String getProductcategory() {
		return productcategory;
	}

	public void setProductcategory(String productcategory) {
		this.productcategory = productcategory;
	}

	public String getProductsubcategory() {
		return productsubcategory;
	}

	public void setProductsubcategory(String productsubcategory) {
		this.productsubcategory = productsubcategory;
	}

	public String getProductcontainer() {
		return productcontainer;
	}

	public void setProductcontainer(String productcontainer) {
		this.productcontainer = productcontainer;
	}

	public String getProductbasemargin() {
		return productbasemargin;
	}

	public void setProductbasemargin(String productbasemargin) {
		this.productbasemargin = productbasemargin;
	}

	@Override
	public String toString() {
		return "ProductDataModel [id=" + id + ", productname=" + productname + ", productcategory=" + productcategory
				+ ", productsubcategory=" + productsubcategory + ", productcontainer=" + productcontainer
				+ ", productbasemargin=" + productbasemargin + "]";
	}

}
