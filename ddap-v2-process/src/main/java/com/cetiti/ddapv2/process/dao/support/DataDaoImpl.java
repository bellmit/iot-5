package com.cetiti.ddapv2.process.dao.support;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.cetiti.ddapv2.process.dao.DataDao;
import com.cetiti.ddapv2.process.dao.PaginationJdbcTemplate;
import com.cetiti.ddapv2.process.model.Data;
import com.cetiti.ddapv2.process.model.Page;

@Repository
public class DataDaoImpl implements DataDao {
	
	@Resource(name="ddapJdbcTemplate")
	private PaginationJdbcTemplate jdbcTemplate;
	
	@Override
	public int insertData(String deviceId, String data) {
		return insertData(deviceId, data, null);
	}
	
	@Override
	public int insertData(String deviceId, String data, Date timestamp) {
		if(null==deviceId||null==data){
			return 0;
		}
		if(null==timestamp){
			timestamp = new Date();
		}
		return this.jdbcTemplate.update("insert into ddap_data (device_id, data, time_stamp ) "
				+ "values (?, ?, ?)",
				new Object[]{
						deviceId,
						data,
						timestamp.getTime()/1000
				});
	}
	
	@Override
	public List<Data> selectData(String deviceId, Date startTime, Date endTime, Page<?> page) {
		if(null==deviceId||null==startTime){
			return null;
		}
		if(null==endTime){
			endTime = new Date();
		}
		String sql = "select device_id, data, time_stamp from ddap_data "
				+ "where device_id = ? and time_stamp between ? and ? order by time_stamp desc";
		return this.jdbcTemplate.queryPagination(sql, 
				new Object[]{deviceId, startTime.getTime()/1000, endTime.getTime()/1000},
				page.getPageNum(), page.getPageSize(), new DataMapper());
	}
	
	@Override
	public int countData(String deviceId, Date startTime, Date endTime) {
		if(null==deviceId||null==startTime){
			return 0;
		}
		if(null==endTime){
			endTime = new Date();
		}
		String sql = "select count(*) from ddap_data "
				+ "where device_id = ? and time_stamp between ? and ?";
		return this.jdbcTemplate.queryForObject(sql, Integer.class,
				new Object[]{deviceId, startTime.getTime()/1000, endTime.getTime()/1000});
	}

	@Override
	public Data selectLatestData(String deviceId) {
		if(null==deviceId){
			return null;
		}
		List<Data> datas = this.jdbcTemplate.query("select device_id, data, time_stamp from ddap_data "
				+ "where device_id = ? order by time_stamp desc limit 1",
				new Object[]{deviceId}, new DataMapper());
		if(null==datas||datas.size()<1){
			return null;
		}
		return datas.get(0);
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
