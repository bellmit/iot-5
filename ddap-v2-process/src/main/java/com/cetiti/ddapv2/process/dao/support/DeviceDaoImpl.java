package com.cetiti.ddapv2.process.dao.support;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.cetiti.ddapv2.process.dao.DeviceDao;
import com.cetiti.ddapv2.process.model.Device;

@Repository
public class DeviceDaoImpl implements DeviceDao{
	
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

	@Override
	public int insertDevice(Device device) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int deleteDevice(Device device) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int updateDevice(Device device) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<Device> selectDeviceList() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Device selectDevice(String deviceId) {
		// TODO Auto-generated method stub
		return null;
	}

}
