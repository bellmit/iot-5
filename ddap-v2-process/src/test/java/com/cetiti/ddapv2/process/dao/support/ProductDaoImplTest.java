package com.cetiti.ddapv2.process.dao.support;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.cetiti.ddapv2.process.dao.ProductDao;
import com.cetiti.ddapv2.process.model.DataItem;
import com.cetiti.ddapv2.process.model.Item;
import com.cetiti.ddapv2.process.model.Page;
import com.cetiti.ddapv2.process.model.Product;
import com.cetiti.ddapv2.process.util.EncryptUtil;
import com.cetiti.ddapv2.process.util.JsonUtil;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/acceptors.xml", "classpath:spring/store.xml"})
public class ProductDaoImplTest extends AbstractJUnit4SpringContextTests {

	@Resource
	private ProductDao productDao;
	@Resource
	private JsonUtil jsonUtil;
	
	@Before
	public void setUp() throws Exception {
	}

	@Ignore
	public void testInsertProduct() {
		Product product = new Product();
		product.setName("TH sensor");
		product.setDescription("temperature humidity sensor");
		List<Item> itemlist = new ArrayList<>();
		Item item1 = new Item("部门", "department", "开发部");
		Item item2 = new Item("维修单位", "repair", "意博");
		itemlist.add(item1);
		itemlist.add(item2);
		product.setDescAttributes(jsonUtil.toJson(itemlist));
		product.setProtocol("http");
		List<DataItem> dataItems = new ArrayList<>();
		DataItem dataItem1 = new DataItem("温度", "temperature", "", "℃");
		DataItem dataItem2 = new DataItem("湿度", "humidity", "", "%");
		dataItems.add(dataItem1);
		dataItems.add(dataItem2);
		product.setDataAttributes(jsonUtil.toJson(dataItems));
		
		product.setProductKey(EncryptUtil.generateProductKey(product));
		product.setProductSecret(EncryptUtil.generateProductSecret(product));
		product.setOwner("admin");
		productDao.insertProduct(product);
	}

	@Ignore
	public void testDeleteProduct() {
		productDao.deleteProduct("P1501488322947");
	}

	@Ignore
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

	@Ignore
	public void testSelectProduct() {
		System.out.println(productDao.selectProduct("P1502708690274"));
	}

	@Ignore
	public void testSelectProductList() {
		Product product = new Product();
		/*product.setName("TH sensor");
		product.setDescription("temperature humidity sensor");
		product.setProtocol("http");
		product.setDataAttributes("temperture,humidity");
		product.setOwner("admin");*/
		productDao.selectProductList(product).stream().forEach(System.out::println);
	}
	
	@Test
	public void testSelectProductPage() {
		Product product = new Product();
		/*product.setName("TH sensor");
		product.setDescription("temperature humidity sensor");
		product.setProtocol("http");
		product.setDataAttributes("temperture,humidity");
		product.setOwner("admin");*/
		Page<?> page = new Page<>();
		page.setPageNum(1);
		page.setPageSize(4);
		productDao.selectProductList(product, page).stream().forEach(System.out::println);
	}
	
	@Test
	public void testCountProduct(){
		Product product = new Product();
		product.setId("P1504784699718");
		int count = productDao.countProduct(product);
		System.out.println(count);
	}

}
