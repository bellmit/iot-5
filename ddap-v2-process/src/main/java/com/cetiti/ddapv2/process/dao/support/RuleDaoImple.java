package com.cetiti.ddapv2.process.dao.support;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;
import com.cetiti.ddapv2.process.dao.PaginationJdbcTemplate;
import com.cetiti.ddapv2.process.dao.RuleDao;
import com.cetiti.ddapv2.process.model.DBModel;
import com.cetiti.ddapv2.process.model.Page;
import com.cetiti.ddapv2.process.model.RuleExpression;
import com.cetiti.ddapv2.process.util.SequenceGenerator;

@Repository
public class RuleDaoImple implements RuleDao {
	
	@Resource(name="ddapJdbcTemplate")
	private PaginationJdbcTemplate jdbcTemplate;
	
	@Override
	public int insertRule(RuleExpression rule) {
		if(null==rule){
			return 0;
		}
		rule.setId(RuleExpression.PREFIX_RULE+SequenceGenerator.next());
		rule.setCreateTime(new Date());
		rule.setDataState(RuleExpression.STATE_NEW);
		return this.jdbcTemplate.update("insert into ddap_rule (id, name, description, product_id, "
				+ "device_id, expression, data_state, owner, create_time) "
				+ "values (?, ?, ?, ?, ?, ?, ?, ?, ?)",
				new Object[]{
						rule.getId(),
						rule.getName(),
						rule.getDescription(),
						rule.getProductId(),
						rule.getDeviceId(),
						rule.getExpression(),
						String.valueOf(rule.getDataState()),
						rule.getOwner(),
						rule.getCreateTime()
				});
	}

	@Override
	public int deleteRule(String ruleId) {
		if(null==ruleId){
			return 0;
		}
		return this.jdbcTemplate.update("delete from ddap_rule where id = ?", ruleId);
	}

	@Override
	public int updateRule(RuleExpression rule) {
		Object[] update = buildUpdateSql(rule);
		if(null==update){
			return 0;
		}
		String sql = (String)update[update.length-1];
		Object[] values = new Object[update.length-1];
		System.arraycopy(update, 0, values, 0, update.length-1);
		return this.jdbcTemplate.update(sql, values);
	}

	@Override
	public RuleExpression selectRule(String ruleId) {
		if(!StringUtils.hasText(ruleId)){
			return null;
		}
		RuleExpression rule = new RuleExpression();
		rule.setId(ruleId);
		List<RuleExpression> rules = selectRuleList(rule);
		if(null==rules||rules.size()<1){
			return null;
		}
		return rules.get(0);
	}

	@Override
	public List<RuleExpression> selectRuleList(RuleExpression rule) {
		Object[] select = buildSelectOrCountSql("select * from ddap_rule", rule);
		String sql = (String)select[select.length-1];
		Object[] values = new Object[select.length-1];
		System.arraycopy(select, 0, values, 0, select.length-1);
		return this.jdbcTemplate.query(sql, values, new RuleMapper());
	}
	
	@Override
	public List<RuleExpression> selectRuleList(String deviceIdOrProductId) {
		if(!StringUtils.hasText(deviceIdOrProductId)){
			return null;
		}
		return this.jdbcTemplate.query("select * from ddap_rule "
				+ "where device_id = ? or product_id = ?" , 
				new Object[]{deviceIdOrProductId, deviceIdOrProductId}, new RuleMapper());
	}
	
	@Override
	public List<RuleExpression> selectRuleList(RuleExpression rule, Page<?> page) {
		Object[] select = buildSelectOrCountSql("select * from ddap_rule", rule);
		String sql = (String)select[select.length-1];
		Object[] values = new Object[select.length-1];
		System.arraycopy(select, 0, values, 0, select.length-1);
		return this.jdbcTemplate.queryPagination(sql, values, page.getPageNum(), 
				page.getPageSize(), new RuleMapper());
	}

	@Override
	public int countRule(RuleExpression rule) {
		Object[] select = buildSelectOrCountSql("select count(*) from ddap_rule", rule);
		String sql = (String)select[select.length-1];
		Object[] values = new Object[select.length-1];
		System.arraycopy(select, 0, values, 0, select.length-1);
		return this.jdbcTemplate.queryForObject(sql, Integer.class, values);
	}
	
	private static final class RuleMapper implements RowMapper<RuleExpression> {

	    public RuleExpression mapRow(ResultSet rs, int rowNum) throws SQLException {
	        RuleExpression rule = new RuleExpression();
	    	rule.setId(rs.getString("id"));
	    	rule.setName(rs.getString("name"));
	    	rule.setDescription(rs.getString("description"));
	    	rule.setProductId(rs.getString("product_id"));
	    	rule.setDeviceId(rs.getString("device_id"));
	    	rule.setExpression(rs.getString("expression"));
	    	String state = rs.getString("data_state");
	        if(null!=state){
	        	rule.setDataState(state.charAt(0));
	        }
	        rule.setOwner(rs.getString("owner"));
	        return rule;
	    }
	}
	
	private Object[] buildSelectOrCountSql(String selectOrCount, RuleExpression rule) {
		StringBuilder select = new StringBuilder();
		select.append(selectOrCount); //select * from ddap_rule
		select.append(" where 1");
		if(null==rule){
			return new Object[]{select.toString()};
		}
		Object[] values = new Object[20];
		int i = 0;
		if(StringUtils.hasText(rule.getId())){
			select.append(" and id = ?");
			values[i] = rule.getId();
			i++;
		}
		if(StringUtils.hasText(rule.getName())){
			select.append(" and name like ?");
			values[i] = "%"+rule.getName()+"%";
			i++;
		}
		if(StringUtils.hasText(rule.getProductId())){
			select.append(" and product_id = ?");
			values[i] = rule.getProductId();
			i++;
		}
		if(StringUtils.hasText(rule.getDeviceId())){
			select.append(" and device_id = ?");
			values[i] = rule.getDeviceId();
			i++;
		}
		if(StringUtils.hasText(rule.getExpression())){
			select.append(" and expression = ?");
			values[i] = rule.getExpression();
			i++;
		}
		if(DBModel.isLegalDataState(rule.getDataState())){
			select.append(" and data_state = ?");
			values[i] = String.valueOf(rule.getDataState());
			i++;
		}
		if(StringUtils.hasText(rule.getOwner())){
			select.append(" and owner = ?");
			values[i] = rule.getOwner();
			i++;
		}
		if(null!=rule.getUpdateTime()){
			select.append(" and create_time >= ? or update_time >= ?");
			values[i] = rule.getUpdateTime();
			i++;
			values[i] = rule.getUpdateTime();
			i++;
		}
		values[i] = select.toString();
		i++;
		
		Object[] retn = new Object[i];
		System.arraycopy(values, 0, retn, 0, i);
		
		return retn;
	}
	
	private Object[] buildUpdateSql(RuleExpression rule) {
		if(null==rule||null==rule.getId()){
			return null;
		}
		StringBuilder update = new StringBuilder();
		Object[] values = new Object[20];
		int i = 0;
		update.append("update ddap_rule set update_time = ?");
		values[i] = new Date(); 
		i++;
		if(DBModel.isLegalDataState(rule.getDataState())){
			update.append(", data_state = ?");
			values[i] = String.valueOf(rule.getDataState());
			i++;
		}
		if(StringUtils.hasText(rule.getName())){
			update.append(", name = ?");
			values[i] = rule.getName();
			i++;
		}
		if(StringUtils.hasText(rule.getDescription())){
			update.append(", description = ?");
			values[i] = rule.getDescription();
			i++;
		}
		if(StringUtils.hasText(rule.getProductId())){
			update.append(", product_id = ?");
			values[i] = rule.getProductId();
			i++;
		}
		if(StringUtils.hasText(rule.getDeviceId())){
			update.append(", device_id = ?");
			values[i] = rule.getDeviceId();
			i++;
		}
		if(StringUtils.hasText(rule.getExpression())){
			update.append(", expression = ?");
			values[i] = rule.getExpression();
			i++;
		}
		if(StringUtils.hasText(rule.getOwner())){
			update.append(", owner = ?");
			values[i] = rule.getOwner();
			i++;
		}
		values[i] = rule.getId();
		i++;
		
		update.append(" where id = ?");
		values[i] = update.toString();
		i++;
		
		Object[] retn = new Object[i];
		System.arraycopy(values, 0, retn, 0, i);
		
		return retn;
	}

}
