package com.cetiti.ddapv2.process.service;

import java.util.List;

import com.cetiti.ddapv2.process.model.Product;
import com.cetiti.ddapv2.process.model.Account;

/**
 * @Description TODO
 * @author Wuwuhao
 * @date 2017年7月13日
 * 
 */
public interface ProductService {
	
	boolean addProduct(Account account, Product product);
	
	boolean deleteProduct(Account account, String productId);
	
	boolean updateProduct(Account account, Product product);
	
	List<Product> getProductList(Account account, Product product);
	
}
