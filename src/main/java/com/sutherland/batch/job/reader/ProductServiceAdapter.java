package com.sutherland.batch.job.reader;

import java.util.ArrayList;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sutherland.batch.job.model.Product;
import com.sutherland.batch.job.service.ProductService;

@Component
public class ProductServiceAdapter implements InitializingBean {

	@Autowired
	private ProductService service;

	private ArrayList<Product> products;

	@Override
	public void afterPropertiesSet() throws Exception {

		this.products = service.getProducts();

	}

	public Product nextProduct() {
		if (products.size() > 0) {
			return products.remove(0);
		} else
			return null;

	}

	public ProductService getService() {
		return service;
	}

	public void setService(ProductService service) {
		this.service = service;
	}

	public ArrayList<Product> getProducts() {
		return products;
	}

	public void setProducts(ArrayList<Product> products) {
		this.products = products;
	}
}