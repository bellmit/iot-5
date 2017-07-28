package com.cetiti.ddapv2.process.dao;

import java.util.List;

import com.cetiti.ddapv2.process.model.Product;

public interface ProductDao {
	
	int insertProduct(Product product);
	
	int deleteProduct(String productId);
	
	int updateProduct(Product product);
	
	Product selectProduct(String productId);
	
	List<Product> selectProductList();

}
