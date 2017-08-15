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

import com.cetiti.ddapv2.process.dao.DeviceDao;
import com.cetiti.ddapv2.process.model.Device;
import com.cetiti.ddapv2.process.util.SequenceGenerator;

@Repository
public class DeviceDaoImpl implements DeviceDao{
	
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

	@Override
	public int insertDevice(Device device) {
		if(null==device){
			return 0;
		}
		device.setId(Device.PREFIX_DEVICE+SequenceGenerator.next());
		device.setCreateTime(new Date());
		device.setDataState(Device.STATE_NEW);
		return this.jdbcTemplate.update("insert into ddap_device (id, name, description, desc_attributes, "
				+ "product_id, device_status, longitude, latitude, device_key, device_secret, data_state, owner, create_time) "
				+ "values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)",
				new Object[]{
						device.getId(),
						device.getName(),
						device.getDescription(),
						device.getDescAttributes(),
						device.getProductId(),
						String.valueOf(device.getDeviceStatus()),
						device.getLongitude(),
						device.getLatitude(),
						device.getDeviceKey(),
						device.getDeviceSecret(),
						String.valueOf(device.getDataState()),
						device.getOwner(),
						device.getCreateTime()
				});
	}

	@Override
	public int deleteDevice(String deviceId) {
		return this.jdbcTemplate.update("delete from ddap_device where id = ?", deviceId);
	}

	@Override
	public int updateDevice(Device device) {
		Object[] update = buildUpdateSql(device);
		if(null==update){
			return 0;
		}
		String sql = (String)update[update.length-1];
		Object[] values = new Object[update.length-1];
		System.arraycopy(update, 0, values, 0, update.length-1);
		return this.jdbcTemplate.update(sql, values);
	}
	
	@Override
	public List<Device> selectDeviceList(Device device) {
		Object[] select = buildSelectSql(device);
		String sql = (String)select[select.length-1];
		Object[] values = new Object[select.length-1];
		System.arraycopy(select, 0, values, 0, select.length-1);
		return this.jdbcTemplate.query(sql, values, new DeviceMapper());
	}

	@Override
	public Device selectDevice(String deviceId) {
		return this.jdbcTemplate.queryForObject("select "
				+ "id, name, description, desc_attributes, product_id, device_status, longitude, "
				+ "latitude, device_key, device_secret, data_state, owner "
				+ "from ddap_device where id = ?", 
				new Object[]{deviceId},  new DeviceMapper());
	}
	
	private static final class DeviceMapper implements RowMapper<Device> {

	    public Device mapRow(ResultSet rs, int rowNum) throws SQLException {
	        Device device = new Device();
	    	device.setId(rs.getString("id"));
	    	device.setName(rs.getString("name"));
	    	device.setDescription(rs.getString("description"));
	    	device.setDescAttributes(rs.getString("desc_attributes"));
	    	device.setProductId(rs.getString("product_id"));
	    	String status = rs.getString("device_status");
	    	if(null!=status){
	    		device.setDeviceStatus(status.charAt(0));
	    	}
	    	device.setLongitude(rs.getDouble("longitude"));
	    	device.setLatitude(rs.getDouble("latitude"));
	    	device.setDeviceKey(rs.getString("device_key"));
	    	device.setDeviceSecret(rs.getString("device_secret"));
	    	String state = rs.getString("data_state");
	        if(null!=state){
	        	device.setDataState(state.charAt(0));
	        }
	        device.setOwner(rs.getString("owner"));
	        return device;
	    }
	}
	
	private Object[] buildSelectSql(Device device) {
		StringBuilder select = new StringBuilder();
		select.append("select id, name, description, desc_attributes, product_id, device_status, "
				+ "longitude, latitude, device_key, device_secret, data_state, owner from ddap_device where 1");
		if(null==device){
			return new Object[]{select.toString()};
		}
		Object[] values = new Object[20];
		int i = 0;
		if(StringUtils.hasText(device.getId())){
			select.append(" and id = ?");
			values[i] = device.getId();
			i++;
		}
		if(StringUtils.hasText(device.getName())){
			select.append(" and name like ?");
			values[i] = "%"+device.getName()+"%";
			i++;
		}
		if(StringUtils.hasText(device.getDescAttributes())){
			select.append(" and desc_attributes like ?");
			values[i] = "%"+device.getDescAttributes()+"%";
			i++;
		}
		if(StringUtils.hasText(device.getProductId())){
			select.append(" and product_id = ?");
			values[i] = device.getProductId();
			i++;
		}
		if(StringUtils.isEmpty(device.getDeviceStatus())){
			select.append(" and device_status = ?");
			values[i] = String.valueOf(device.getDeviceStatus());
			i++;
		}
		if(device.getLongitude()>0&&device.getLatitude()>0){
			select.append(" and abs(longitude - ?) < 0.03 and abs(latitude - ?) < 0.03");
			values[i] = device.getLongitude();
			i++;
			values[i] = device.getLatitude();
			i++;
		}
		if(StringUtils.hasText(device.getDeviceKey())){
			select.append(" and device_key = ?");
			values[i] = device.getDeviceKey();
			i++;
		}
		if(StringUtils.hasText(device.getDeviceSecret())){
			select.append(" and device_secret = ?");
			values[i] = device.getDeviceSecret();
			i++;
		}
		if(StringUtils.isEmpty(device.getDataState())){
			select.append(" and data_state = ?");
			values[i] = String.valueOf(device.getDataState());
			i++;
		}
		if(StringUtils.hasText(device.getOwner())){
			select.append(" and owner = ?");
			values[i] = device.getOwner();
			i++;
		}
		values[i] = select.toString();
		i++;
		
		Object[] retn = new Object[i];
		System.arraycopy(values, 0, retn, 0, i);
		
		return retn;
	}
	
	private Object[] buildUpdateSql(Device device) {
		if(null==device||null==device.getId()){
			return null;
		}
		StringBuilder update = new StringBuilder();
		Object[] values = new Object[20];
		int i = 0;
		update.append("update ddap_device set update_time = ?");
		values[i] = new Date(); 
		i++;
		if(!StringUtils.isEmpty(device.getDataState())){
			update.append(", data_state = ?");
			values[i] = String.valueOf(device.getDataState());
			i++;
		}
		if(StringUtils.hasText(device.getName())){
			update.append(", name = ?");
			values[i] = device.getName();
			i++;
		}
		if(StringUtils.hasText(device.getDescription())){
			update.append(", description = ?");
			values[i] = device.getDescription();
			i++;
		}
		if(StringUtils.hasText(device.getDescAttributes())){
			update.append(", desc_attributes = ?");
			values[i] = device.getDescAttributes();
			i++;
		}
		if(StringUtils.hasText(device.getProductId())){
			update.append(", product_id = ?");
			values[i] = device.getProductId();
			i++;
		}
		if(!StringUtils.isEmpty(device.getDeviceStatus())){
			update.append(", device_status = ?");
			values[i] = String.valueOf(device.getDeviceStatus());
			i++;
		}
		if(device.getLongitude()>0){
			update.append(", longitude = ?");
			values[i] = device.getLongitude();
			i++;
		}
		if(device.getLatitude()>0){
			update.append(", latitude = ?");
			values[i] = device.getLatitude();
			i++;
		}
		if(StringUtils.hasText(device.getDeviceKey())){
			update.append(", device_key = ?");
			values[i] = device.getDeviceKey();
			i++;
		}
		if(StringUtils.hasText(device.getDeviceSecret())){
			update.append(", device_secret = ?");
			values[i] = device.getDeviceSecret();
			i++;
		}
		if(StringUtils.hasText(device.getOwner())){
			update.append(", owner = ?");
			values[i] = device.getOwner();
			i++;
		}
		values[i] = device.getId();
		i++;
		
		update.append(" where id = ?");
		values[i] = update.toString();
		i++;
		
		Object[] retn = new Object[i];
		System.arraycopy(values, 0, retn, 0, i);
		
		return retn;
	}


}
