package com.cetiti.ddapv2.process.dao.support;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.cetiti.ddapv2.process.dao.ProductDao;
import com.cetiti.ddapv2.process.model.Product;
import com.cetiti.ddapv2.process.util.SequenceGenerator;

@Repository
public class ProductDaoImpl implements ProductDao {

	private JdbcTemplate jdbcTemplate;
	
	@Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }
	
	@Override
	public int insertProduct(Product product) {
		if(null==product){
			return 0;
		}
		product.setId(Product.PREFIX_PRODUCT+SequenceGenerator.next());
		product.setCreateTime(new Date());
		product.setDataState(Product.STATE_NEW);
		return this.jdbcTemplate.update("insert into ddap_product (id, name, description, desc_attributes, "
				+ "protocol, data_attributes, product_key, product_secret, data_state, owner, create_time) "
				+ "values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)",
				new Object[]{
						product.getId(),
						product.getName(),
						product.getDescription(),
						product.getDescAttributes(),
						product.getProtocol(),
						product.getDataAttributes(),
						product.getProductKey(),
						product.getProductSecret(),
						String.valueOf(product.getDataState()),
						product.getOwner(),
						product.getCreateTime()
				});
	}

	@Override
	public int deleteProduct(String productId) {
		return this.jdbcTemplate.update("delete from ddap_product where id = ?", productId);
	}

	@Override
	public int updateProduct(Product product) {
		Object[] update = buildUpdateSql(product);
		if(null==update){
			return 0;
		}
		String sql = (String)update[update.length-1];
		Object[] values = new Object[update.length-1];
		System.arraycopy(update, 0, values, 0, update.length-1);
		return this.jdbcTemplate.update(sql, values);
	}

	@Override
	public Product selectProduct(String productId) {
		return this.jdbcTemplate.queryForObject("select "
				+ "id, name, description, desc_attributes, protocol, data_attributes, product_key, product_secret, data_state, owner "
				+ "from ddap_product where id = ?", 
				new Object[]{productId},  new ProductMapper());
	}

	@Override
	public List<Product> selectProductList(Product product) {
		Object[] select = buildSelectSql(product);
		String sql = (String)select[select.length-1];
		Object[] values = new Object[select.length-1];
		System.arraycopy(select, 0, values, 0, select.length-1);
		return this.jdbcTemplate.query(sql, values, new ProductMapper());
	}
	
	private static final class ProductMapper implements RowMapper<Product> {

	    public Product mapRow(ResultSet rs, int rowNum) throws SQLException {
	        Product product = new Product();
	    	product.setId(rs.getString("id"));
	    	product.setName(rs.getString("name"));
	    	product.setDescription(rs.getString("description"));
	    	product.setDescAttributes(rs.getString("desc_attributes"));
	    	product.setProtocol(rs.getString("protocol"));
	    	product.setDataAttributes(rs.getString("data_attributes"));
	    	product.setProductKey(rs.getString("product_key"));
	    	product.setProductSecret(rs.getString("product_secret"));
	    	String state = rs.getString("data_state");
	        if(null!=state){
	        	product.setDataState(state.charAt(0));
	        }
	        product.setOwner(rs.getString("owner"));
	        return product;
	    }
	}
	
	private Object[] buildSelectSql(Product product){
		StringBuilder select = new StringBuilder();
		select.append("select id, name, description, desc_attributes, protocol, data_attributes, product_key, "
				+ "product_secret, data_state, owner from ddap_product where 1");
		if(null==product){
			return new Object[]{select.toString()};
		}
		Object[] values = new Object[20];
		int i = 0;
		if(StringUtils.hasText(product.getId())){
			select.append(" and id = ?");
			values[i] = product.getId();
			i++;
		}
		if(StringUtils.hasText(product.getName())){
			select.append(" and name like ?");
			values[i] = "%"+product.getName()+"%";
			i++;
		}
		if(StringUtils.hasText(product.getProtocol())){
			select.append(" and protocol = ?");
			values[i] = product.getProtocol();
			i++;
		}
		if(StringUtils.hasText(product.getDescAttributes())){
			select.append(" and desc_attributes like ?");
			values[i] = "%"+product.getDescAttributes()+"%";
			i++;
		}
		if(StringUtils.isEmpty(product.getDataAttributes())){
			select.append(" and data_attributes like ?");
			values[i] = "%"+product.getDataAttributes()+"%";
			i++;
		}
		if(StringUtils.hasText(product.getProductKey())){
			select.append(" and product_key = ?");
			values[i] = product.getProductKey();
			i++;
		}
		if(StringUtils.hasText(product.getProductSecret())){
			select.append(" and product_secret = ?");
			values[i] = product.getProductSecret();
			i++;
		}
		if(StringUtils.isEmpty(product.getDataState())){
			select.append(" and data_state = ?");
			values[i] = String.valueOf(product.getDataState());
			i++;
		}
		if(StringUtils.hasText(product.getOwner())){
			select.append(" and owner = ?");
			values[i] = product.getOwner();
			i++;
		}
		values[i] = select.toString();
		i++;
		
		Object[] retn = new Object[i];
		System.arraycopy(values, 0, retn, 0, i);
		
		return retn;
	}
	
	private Object[] buildUpdateSql(Product product) {
		if(null==product||null==product.getId()){
			return null;
		}
		StringBuilder update = new StringBuilder();
		Object[] values = new Object[20];
		int i = 0;
		update.append("update ddap_product set update_time = ?");
		values[i] = new Date(); 
		i++;
		if(!StringUtils.isEmpty(product.getDataState())){
			update.append(", data_state = ?");
			values[i] = String.valueOf(product.getDataState());
			i++;
		}
		if(StringUtils.hasText(product.getName())){
			update.append(", name = ?");
			values[i] = product.getName();
			i++;
		}
		if(StringUtils.hasText(product.getDescription())){
			update.append(", description = ?");
			values[i] = product.getDescription();
			i++;
		}
		if(StringUtils.hasText(product.getDescAttributes())){
			update.append(", desc_attributes = ?");
			values[i] = product.getDescAttributes();
			i++;
		}
		if(StringUtils.hasText(product.getProtocol())){
			update.append(", protocol = ?");
			values[i] = product.getProtocol();
			i++;
		}
		if(StringUtils.hasText(product.getDataAttributes())){
			update.append(", data_attributes = ?");
			values[i] = product.getDataAttributes();
			i++;
		}
		if(StringUtils.hasText(product.getProductKey())){
			update.append(", product_key = ?");
			values[i] = product.getProductKey();
			i++;
		}
		if(StringUtils.hasText(product.getProductSecret())){
			update.append(", product_secret = ?");
			values[i] = product.getProductSecret();
			i++;
		}
		if(StringUtils.hasText(product.getOwner())){
			update.append(", owner = ?");
			values[i] = product.getOwner();
			i++;
		}
		values[i] = product.getId();
		i++;
		
		update.append(" where id = ?");
		values[i] = update.toString();
		i++;
		
		Object[] retn = new Object[i];
		System.arraycopy(values, 0, retn, 0, i);
		
		return retn;
	}

}
