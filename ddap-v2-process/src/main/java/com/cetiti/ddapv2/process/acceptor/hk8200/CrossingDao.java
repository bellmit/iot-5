package com.cetiti.ddapv2.process.acceptor.hk8200;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.cetiti.ddapv2.process.dao.DeviceDao;
import com.cetiti.ddapv2.process.dao.PaginationJdbcTemplate;
import com.cetiti.ddapv2.process.model.Device;
import com.cetiti.ddapv2.process.model.Item;
import com.cetiti.ddapv2.process.model.Page;
import com.cetiti.ddapv2.process.util.JsonUtil;

/**
 * @Description TODO
 * @author Wuwuhao
 * @date 2017年10月23日
 * 
 */
@Repository
public class CrossingDao {
	
	@Resource(name="hik82JdbcTemplate")
	private PaginationJdbcTemplate jdbcTemplate;
	@Resource
	private DeviceDao deviceDao;
	@Resource
	private JsonUtil jsonUtil;
	
	private Map<String, String> comments = new HashMap<>();
	
	public static final String CROSSIONG_PRODUCT_ID = "PHIK82CROSSING";
	public static final String CROSSIONG_OWNER = "HIK82";
	
	List<Map<String, Object>> selectCrossingList(Date updateTime, Page<?> page){
		String sql = "select * from BMS_CROSSING_INFO";
		if(null!=updateTime){
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			sql += " where UPDATETIME > to_timestamp('"+dateFormat.format(updateTime)+"','YYYY-MM-DD HH24:MI:SS')";
		}
		//return jdbcTemplate.queryForList("select * from BMS_CROSSING_INFO");
		return jdbcTemplate.queryPagination(sql, page.getPageNum(), page.getPageSize(), new CamelMapper());
	}
	
	Map<String, Object> selectCrossing(String crossinId){
		List<Map<String, Object>> crossingList = jdbcTemplate.query(
				"select * from BMS_CROSSING_INFO where CROSSING_ID = ?", new Object[]{crossinId}, new CamelMapper());
		if(null==crossingList||crossingList.size()<1){
			return null;
		}
		return crossingList.get(0);
	}
	
	Map<String, String> selectColumnComments(String tableName){
		List<Map<String, Object>> comments = jdbcTemplate.queryForList("select COLUMN_NAME, COMMENTS from "
				+ "user_col_comments cc where cc.table_name= ?", new Object[]{tableName});
		Map<String, String> retn = new HashMap<>();
		StringBuilder camelKey = new StringBuilder();
		for(Map<String, Object> map:comments){
			if(null==map.get("COLUMN_NAME")||null==map.get("COMMENTS")){
				continue;
			}
			camelKey.delete(0, camelKey.length());
			String columnLabel = map.get("COLUMN_NAME").toString().toLowerCase();
			if(!columnLabel.contains("_")){
				retn.put(columnLabel, map.get("COMMENTS").toString());
				continue;
			}
			for(int j=0,len = columnLabel.length();j<len;j++){
				char c = columnLabel.charAt(j);
				if(c != '_'){
					camelKey.append(c);
					continue;	
				}
				if(++j < len){
					camelKey.append(Character.toUpperCase(columnLabel.charAt(j)));
				} 
			}
			retn.put(camelKey.toString(), map.get("COMMENTS").toString());
		}
		return retn;
	}
	
	private Map<String, String> getcomments(){
		if(this.comments.size()<1){
			this.comments = selectColumnComments("BMS_CROSSING_INFO");
		}
		return this.comments;
	}
	
	int countCrossing(Date updateTime){
		String sql = "select count(*) from BMS_CROSSING_INFO";
		if(null!=updateTime){
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			sql += " where UPDATETIME > to_timestamp('"+dateFormat.format(updateTime)+"','YYYY-MM-DD HH24:MI:SS')";
		}
		return jdbcTemplate.queryForObject(sql, Integer.class);
	}
	
	public static final class CamelMapper implements RowMapper<Map<String, Object>> {
		@Override
		public Map<String, Object> mapRow(ResultSet rs, int rowNum) throws SQLException {
			StringBuilder camelKey = new StringBuilder();
			Map<String,Object> camelKeyMap = new HashMap<String,Object>();
			int column = rs.getMetaData().getColumnCount();
			for(int i=1;i<=column;i++){
				camelKey.delete(0, camelKey.length());
				String columnLabel = rs.getMetaData().getColumnLabel(i).toLowerCase();
				if(!columnLabel.contains("_")){
					camelKeyMap.put(columnLabel, rs.getObject(i));
					continue;
				}
				for(int j=0,len = columnLabel.length();j<len;j++){
					char c = columnLabel.charAt(j);
					if(c != '_'){
						camelKey.append(c);
						continue;	
					}
					if(++j < len){
						camelKey.append(Character.toUpperCase(columnLabel.charAt(j)));
					} 
				}
				camelKeyMap.put(camelKey.toString(), rs.getObject(i));
			}
			return camelKeyMap;
		}
	}
	
	private int updateOrInsert(Map<String, Object> crossingInfo){
		Device device = new Device();
		Object crossindId = crossingInfo.get("crossingId");
		crossingInfo.remove("rn");
		if(null==crossindId){
			return 0;
		}
		device.setSerialNumber(crossindId.toString());
		device.setProductId(CROSSIONG_PRODUCT_ID);
		List<Device> dbDevices = deviceDao.selectDeviceList(device);
		if(null==dbDevices||dbDevices.size()<1){
			device.setOwner(CROSSIONG_OWNER);
			Object name = crossingInfo.get("crossingName");
			if(null!=name){
				device.setName(name.toString());
			}
			Object longitude = crossingInfo.get("longitude");
			if(null!=longitude){
				device.setLongitude(Long.parseLong((String)longitude));
			}
			crossingInfo.remove("longitude");
			Object latitude = crossingInfo.get("latitude");
			if(null!=latitude){
				device.setLatitude(Long.parseLong((String)latitude));
			}
			crossingInfo.remove("latitude");
			crossingInfo.remove("updatetime");
			Map<String, Object> dbItemMap = new HashMap<>();
			crossingInfo.entrySet().stream().forEach((e)->{
				Item item = new Item(getcomments().get(e.getKey()),e.getKey(),e.getValue());
				dbItemMap.put(e.getKey(), item);});
			device.setDescAttributes(jsonUtil.toJson(dbItemMap.values()));
			return deviceDao.insertDevice(device);
		}else{
			device = dbDevices.get(0);
			Object name = crossingInfo.get("crossingName");
			if(null!=name){
				device.setName(name.toString());
			}
			Object longitude = crossingInfo.get("longitude");
			if(null!=longitude){
				device.setLongitude(Long.parseLong((String)longitude));
			}
			crossingInfo.remove("longitude");
			Object latitude = crossingInfo.get("latitude");
			if(null!=latitude){
				device.setLatitude(Long.parseLong((String)latitude));
			}
			crossingInfo.remove("latitude");
			crossingInfo.remove("updatetime");
			List<Item> itemList = jsonUtil.listFromJson(device.getDescAttributes(), Item.class);
			Map<String, Object> dbItemMap = new HashMap<>();
			if(null!=itemList){
				itemList.stream().forEach((e)->{dbItemMap.put(e.getKey(), e);});
			}
			crossingInfo.entrySet().stream().forEach((e)->{
				Item item = new Item(getcomments().get(e.getKey()),e.getKey(),e.getValue());
				dbItemMap.put(e.getKey(), item);});
			
			device.setDescAttributes(jsonUtil.toJson(dbItemMap.values()));
			return deviceDao.updateDeviceBySerialNumberAndProductId(device);
		}
	}
	
	public void fullUpdate() {
		Date start = new Date();
		int total = countCrossing(null);
		for(int pageNum=1, pageSize = 100, selectedCount=0;
				selectedCount<total; pageNum++,selectedCount+=pageSize){
			Page<?> page = new Page<>(pageNum, pageSize);
			List<Map<String, Object>> crossingList = selectCrossingList(null, page);
			crossingList.parallelStream().forEach(this::updateOrInsert);
		}
		deviceDao.deleteOldDevice(CROSSIONG_PRODUCT_ID, start);
	}
	

}
