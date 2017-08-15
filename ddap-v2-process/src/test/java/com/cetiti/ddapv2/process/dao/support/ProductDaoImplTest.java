package com.cetiti.ddapv2.process.dao.support;

import static org.junit.Assert.*;

import javax.annotation.Resource;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.cetiti.ddapv2.process.dao.ProductDao;
import com.cetiti.ddapv2.process.model.Product;
import com.cetiti.ddapv2.process.util.EncryptUtil;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/acceptors.xml", "classpath:spring/store.xml"})
public class ProductDaoImplTest extends AbstractJUnit4SpringContextTests {

	@Resource
	private ProductDao productDao;
	
	@Before
	public void setUp() throws Exception {
	}

	@Ignore
	public void testInsertProduct() {
		Product product = new Product();
		product.setName("TH sensor");
		product.setDescription("temperature humidity sensor");
		product.setDescAttributes("department, position");
		product.setProtocol("http");
		product.setDataAttributes("temperture,humidity");
		product.setProductKey(EncryptUtil.generateProductKey(product));
		product.setProductSecret(EncryptUtil.generateProductSecret(product));
		product.setOwner("admin");
		productDao.insertProduct(product);
	}

	@Ignore
	public void testDeleteProduct() {
		productDao.deleteProduct("P1501488322947");
	}

	@Test
	public void testUpdateProduct() {
		Product product = new Product();
		product.setId("P1502708690274");
		product.setName("TH sensoru");
		product.setDescription("temperature humidity sensoru");
		product.setDescAttributes("producer, fix");
		product.setProtocol("mqtt");
		product.setDataAttributes("temperture,humidityu");
		
		product.setOwner("adminu");
		productDao.updateProduct(product);
	}

	@Test
	public void testSelectProduct() {
		System.out.println(productDao.selectProduct("P1502708690274"));
	}

	@Ignore
	public void testSelectProductList() {
		Product product = new Product();
		product.setName("TH sensor");
		product.setDescription("temperature humidity sensor");
		product.setProtocol("http");
		product.setDataAttributes("temperture,humidity");
		//product.setProductKey(EncryptUtil.generateProductKey(product));
		//product.setProductSecret(EncryptUtil.generateProductSecret(product));
		product.setOwner("admin");
		productDao.selectProductList(product).stream().forEach(System.out::println);
	}

}
