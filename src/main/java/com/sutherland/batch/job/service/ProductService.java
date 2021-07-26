package com.sutherland.batch.job.service;

import java.util.ArrayList;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.sutherland.batch.job.model.Product;

@Service
public class ProductService {

	public ArrayList<Product> getProducts() {
		RestTemplate restTemplate = new RestTemplate();
		String url = "http://localhost:9999/products";
		Product[] products = restTemplate.getForObject(url, Product[].class);
		ArrayList<Product> productList = new ArrayList<Product>();
		for (Product p : products)
			productList.add(p);

		return productList;

	}
}
