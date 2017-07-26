package com.cetiti.ddapv2.process.dao.support;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.cetiti.ddapv2.process.dao.AccountDao;
import com.cetiti.ddapv2.process.model.Account;

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
	public int insertUser(Account account) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int deleteUser(String accountName) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int updateUser(Account account) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<Account> selectUserList() {
		// TODO Auto-generated method stub
		return null;
	}
	
	private static final class AccountMapper implements RowMapper<Account> {

	    public Account mapRow(ResultSet rs, int rowNum) throws SQLException {
	        Account account = new Account();
	        account.setId(rs.getString("id"));
	        account.setAccount(rs.getString("account"));
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

}
