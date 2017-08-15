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

import com.cetiti.ddapv2.process.dao.AccountDao;
import com.cetiti.ddapv2.process.model.Account;
import com.cetiti.ddapv2.process.util.SequenceGenerator;

/**
 * @Description TODO
 * @author Wuwuhao
 * @date 2017年7月26日
 * 
 * https://docs.spring.io/spring/docs/current/spring-framework-reference/html/jdbc.html
 */
@Repository
public class AccountDaoImpl implements AccountDao {

	private JdbcTemplate jdbcTemplate;
	
	@Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }
		
	@Override
	public int insertAccount(Account account) {
		if(null==account){
			return 0;
		}
		account.setId(SequenceGenerator.next());
		account.setCreateTime(new Date());
		account.setDataState(Account.STATE_NEW);
		return this.jdbcTemplate.update("insert into ddap_account (id, account, password, "
				+ "role, phone, email, address, u_key, u_secret, data_state, create_time) "
				+ "values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)",
				new Object[]{
					account.getId(),
					account.getAccount(),
					account.getPassword(),
					account.getRole(),
					account.getPhone(),
					account.getEmail(),
					account.getAddress(),
					account.getUserKey(),
					account.getUserSercret(),
					String.valueOf(account.getDataState()),
					account.getCreateTime()
				});
	}

	@Override
	public int deleteAccount(String account) {
		return this.jdbcTemplate.update("delete from ddap_account where account = ?", account);
	}

	@Override
	public int updateAccount(Account account) {
		Object[] update = buildUpdateSql(account);
		if(null==update){
			return 0;
		}
		String sql = (String)update[update.length-1];
		Object[] values = new Object[update.length-1];
		System.arraycopy(update, 0, values, 0, update.length-1);
		return this.jdbcTemplate.update(sql, values);
	}
	
	@Override
	public Account selectAccount(String account) {
		return this.jdbcTemplate.queryForObject("select "
				+ "id, account, password, role, phone, email, address, u_key, u_secret, data_state "
				+ "from ddap_account where account = ?", 
				new Object[]{account},  new AccountMapper());
	}

	@Override
	public List<Account> selectAccountList() {
		return this.jdbcTemplate.query("select "
				+ "id, account, password, role, phone, email, address, u_key, u_secret, data_state "
				+ "from ddap_account", new AccountMapper());
	}
	
	private static final class AccountMapper implements RowMapper<Account> {

	    public Account mapRow(ResultSet rs, int rowNum) throws SQLException {
	        Account account = new Account();
	        account.setId(rs.getString("id"));
	        account.setAccount(rs.getString("account"));
	        account.setPassword(rs.getString("password"));
	        account.setRole(rs.getString("role"));
	        account.setPhone(rs.getString("phone"));
	        account.setEmail(rs.getString("email"));
	        account.setAddress(rs.getString("address"));
	        account.setUserKey(rs.getString("u_key"));
	        account.setUserSercret(rs.getString("u_secret"));
	        String state = rs.getString("data_state");
	        if(null!=state){
	        	account.setDataState(state.charAt(0));
	        }
	        return account;
	    }
	}
	
	private Object[] buildUpdateSql(Account account) {
		if(null==account||null==account.getAccount()){
			return null;
		}
		StringBuilder update = new StringBuilder();
		Object[] values = new Object[20];
		int i = 0;
		update.append("update ddap_account set update_time = ?");
		values[i] = new Date(); 
		i++;
		if(!StringUtils.isEmpty(account.getDataState())){
			update.append(", data_state = ?");
			values[i] = String.valueOf(account.getDataState());
			i++;
		}
		if(StringUtils.hasText(account.getPassword())){
			update.append(", password = ?");
			values[i] = account.getPassword();
			i++;
		}
		if(StringUtils.hasText(account.getRole())){
			update.append(", role = ?");
			values[i] = account.getRole();
			i++;
		}
		if(StringUtils.hasText(account.getPhone())){
			update.append(", phone = ?");
			values[i] = account.getPhone();
			i++;
		}
		if(StringUtils.hasText(account.getEmail())){
			update.append(", email = ?");
			values[i] = account.getEmail();
			i++;
		}
		if(StringUtils.hasText(account.getAddress())){
			update.append(", address = ?");
			values[i] = account.getAddress();
			i++;
		}
		if(StringUtils.hasText(account.getUserKey())){
			update.append(", u_key = ?");
			values[i] = account.getUserKey();
			i++;
		}
		if(StringUtils.hasText(account.getUserSercret())){
			update.append(", u_secret = ?");
			values[i] = account.getUserSercret();
			i++;
		}
		values[i] = account.getAccount();
		i++;
		
		update.append(" where account = ?");
		values[i] = update.toString();
		i++;
		
		Object[] retn = new Object[i];
		System.arraycopy(values, 0, retn, 0, i);
		
		return retn;
	}

}
