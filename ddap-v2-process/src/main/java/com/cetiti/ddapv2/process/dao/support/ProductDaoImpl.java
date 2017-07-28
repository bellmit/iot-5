package com.cetiti.ddapv2.process.dao.support;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.cetiti.ddapv2.process.dao.ProductDao;
import com.cetiti.ddapv2.process.model.Product;

@Repository
public class ProductDaoImpl implements ProductDao {

	private JdbcTemplate jdbcTemplate;
	
	@Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }
	
	@Override
	public int insertProduct(Product product) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int deleteProduct(String productId) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int updateProduct(Product product) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Product selectProduct(String productId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Product> selectProductList() {
		// TODO Auto-generated method stub
		return null;
	}

}
