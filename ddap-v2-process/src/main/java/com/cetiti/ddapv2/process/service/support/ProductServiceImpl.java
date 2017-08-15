package com.cetiti.ddapv2.process.service.support;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.cetiti.ddapv2.process.dao.ProductDao;
import com.cetiti.ddapv2.process.model.Account;
import com.cetiti.ddapv2.process.model.Product;
import com.cetiti.ddapv2.process.service.ProductService;
import com.cetiti.ddapv2.process.util.EncryptUtil;
import com.cetiti.ddapv2.process.util.MessageContext;
import com.cetiti.ddapv2.process.util.MessageUtil;

/**
 * @Description TODO
 * @author Wuwuhao
 * @date 2017年8月15日
 * 
 */
@Service
public class ProductServiceImpl implements ProductService{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ProductServiceImpl.class);
	@Resource
	private ProductDao productDao;
	@Resource
	private MessageUtil msgUtil;
	
	@Override
	public boolean addProduct(Account account, Product product) {
		if(null==account||null==account.getAccount()||null==product){
			return false;
		}
		product.setProductKey(EncryptUtil.generateProductKey(product));
		product.setProductSecret(EncryptUtil.generateProductSecret(product));
		product.setOwner(account.getAccount());
		try{
			productDao.insertProduct(product);
		}catch (Exception e) {
			MessageContext.setMsg(msgUtil.get("db.exception"));
			LOGGER.error("addProduct [{}]", e.getMessage());
			return false;
		}
		
		return true;
	}

	@Override
	public boolean deleteProduct(Account account, String productId) {
		if(null==account||null==account.getAccount()||null==productId){
			return false;
		}
		Product product = productDao.selectProduct(productId);
		if(null==product){
			return true;
		}
		if(!account.isAdmin()&&!account.getAccount().equals(product.getOwner())){
			MessageContext.setMsg(msgUtil.get("permission.deny"));
			return false;
		}
		try{
			productDao.deleteProduct(productId);
		}catch (Exception e) {
			MessageContext.setMsg(msgUtil.get("db.exception"));
			LOGGER.error("deleteProduct [{}]", e.getMessage());
			return false;
		}
		return true;
	}

	@Override
	public boolean updateProduct(Account account, Product product) {
		if(null==account||null==account.getAccount()
				||null==product||null==product.getId()){
			return false;
		}
		Product dbProduct = productDao.selectProduct(product.getId());
		if(null==dbProduct){
			MessageContext.setMsg(msgUtil.get("parameter.not.exist", product.getId()));
			return false;
		}
		if(!account.isAdmin()&&!account.getAccount().equals(dbProduct.getOwner())){
			MessageContext.setMsg(msgUtil.get("permission.deny"));
			return false;
		}
		try{
			productDao.updateProduct(product);
		}catch (Exception e) {
			MessageContext.setMsg(msgUtil.get("db.exception"));
			LOGGER.error("updateProduct [{}]", e.getMessage());
			return false;
		}
		return true;
	}

	@Override
	public List<Product> getProductList(Account account, Product product) {
		if(null==account||null==account.getAccount()){
			return new ArrayList<>();
		}
		if(null==product){
			product = new Product();
		}
		if(!account.isAdmin()){
			product.setOwner(account.getAccount());
		}
		try{
			return productDao.selectProductList(product);
		}catch (Exception e) {
			MessageContext.setMsg(msgUtil.get("db.exception"));
			LOGGER.error("getProductList [{}]", e.getMessage());
		}
		return new ArrayList<>();
	}

}
