package com.cetiti.ddapv2.process.dao.support;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.cetiti.ddapv2.process.dao.DataDao;
import com.cetiti.ddapv2.process.model.Data;

@Repository
public class DataDaoImpl implements DataDao {
	
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }
	
	@Override
	public int insertData(String deviceId, String data) {
		if(null==deviceId||null==data){
			return 0;
		}
		return this.jdbcTemplate.update("insert into ddap_data (device_id, data, time_stamp ) "
				+ "values (?, ?, ?)",
				new Object[]{
						deviceId,
						data,
						System.currentTimeMillis()/1000
				});
	}

	@Override
	public List<Data> selectData(String deviceId, int timestamp) {	
		if(null==deviceId||timestamp==0){
			return null;
		}
		String sql = "select device_id, data, time_stamp from ddap_data where device_id = ? and time_stamp > ?";
		return this.jdbcTemplate.query(sql, new Object[]{deviceId, timestamp}, new DataMapper());
	}
	
	private static final class DataMapper implements RowMapper<Data> {

	    public Data mapRow(ResultSet rs, int rowNum) throws SQLException {
	        Data data = new Data();
	        data.setDeviceId(rs.getString("device_id"));
	        data.setStrData(rs.getString("data"));
	        data.setTimeStamp(rs.getInt("time_stamp"));
	    	
	        return data;
	    }
	}

}
