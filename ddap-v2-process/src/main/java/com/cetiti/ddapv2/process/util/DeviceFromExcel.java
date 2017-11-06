package com.cetiti.ddapv2.process.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.cetiti.ddapv2.process.model.Device;
import com.cetiti.ddapv2.process.model.Item;

/**
 * @Description TODO
 * @author Wuwuhao
 * @date 2017年11月2日
 * 
 */
@Component
public class DeviceFromExcel {
	
	@Resource
	private JsonUtil jsonUtil;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(DeviceFromExcel.class);
	
	public List<Device> readDeviceList(MultipartFile mf){
		if(null==mf){
			return null;
		}
		XSSFWorkbook xssfWorkbook = null;
		InputStream is = null;
		try{
			is = mf.getInputStream();
			xssfWorkbook = new XSSFWorkbook(is);
			return readDeviceList(xssfWorkbook);
		}catch (IOException e) {
			LOGGER.error("read [{}] io exception [{}]", mf.getOriginalFilename(), e.getMessage());
			MessageContext.setMsg("io exception");
			return null;
		}finally {
			try{
				is.close();
			}catch (IOException e) {
				LOGGER.error("close inputstream [{}] exception", mf.getOriginalFilename(), e.getMessage());
			}
		}
		
	}
	
	public List<Device> readDeviceList(File file) {
		if(null==file){
			return null;
		}
		XSSFWorkbook xssfWorkbook = null;
		try{
			xssfWorkbook = new XSSFWorkbook(OPCPackage.open(file));
		}catch (InvalidFormatException e) {
			LOGGER.error("[{}] invalid format. [{}]", file.getName(), e.getMessage());
			MessageContext.setMsg("invalid format");
			return null;
		}catch (IOException e) {
			LOGGER.error("read file [{}] io exception [{}]", file.getName(), e.getMessage());
			MessageContext.setMsg("io exception");
			return null;
		}
		return readDeviceList(xssfWorkbook);
	}
	
	private List<Device> readDeviceList(XSSFWorkbook xssfWorkbook)  {
		
		List<Device> deviceList = new ArrayList<>();
		Map<String, String> valueMap = new HashMap<>();
		Map<String,String> descMap = new HashMap<>();
		int sheetNum = xssfWorkbook.getNumberOfSheets();
		for(int s=0; s<sheetNum; s++){
			XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(s);
		    int firstRowNum = xssfSheet.getFirstRowNum();
		    int lastRowNum = xssfSheet.getLastRowNum();
		    XSSFRow headerRow = xssfSheet.getRow(firstRowNum);
		    if(null==headerRow){
		    	continue;
		    }
		    int firstCellNum = headerRow.getFirstCellNum();
	        int lastCellNum = headerRow.getLastCellNum();
	        String[] header = new String[lastCellNum-firstCellNum+1];
	        
	        for(int i=firstCellNum; i<lastCellNum; i++){
	        	XSSFCell cell = headerRow.getCell(i);
	        	if(null==cell){
	        		continue;
	        	}
	        	String[] values = cell.getStringCellValue().split("/");
	        	header[i] = values[0];
	        	if(values.length>1){
	        		descMap.put(values[0], values[1]);
	        	}
	        }
	        
	        for(int i=firstRowNum+1; i<=lastRowNum; i++){
	        	XSSFRow row = xssfSheet.getRow(i);
	        	if(null==row){
	        		continue;
	        	}
	        	for(int j=firstCellNum; j<lastCellNum; j++){
	        		XSSFCell cell = row.getCell(j);
	            	if(null==cell){
	            		continue;
	            	}
	            	int type = cell.getCellType();
	            	if(type==HSSFCell.CELL_TYPE_STRING){
	            		valueMap.put(header[j], cell.getStringCellValue());
	            	}
	            	if(type==HSSFCell.CELL_TYPE_NUMERIC){
	            		valueMap.put(header[j], String.valueOf(cell.getNumericCellValue()));
	            	}
	        	}
	        	deviceList.add(buildDevice(valueMap, descMap));
	        	valueMap.clear();
	        }
	        descMap.clear();
		}
		
	    return deviceList;
	}
	
	private Device buildDevice(Map<String, String> valueMap, Map<String, String>descMap){
		Device device = new Device();
		if(null!=valueMap.get("name")){
			device.setName(valueMap.get("name"));
			valueMap.remove("name");
		}
		if(null!=valueMap.get("description")){
			device.setDescription(valueMap.get("description"));
			valueMap.remove("description");
		}
		if(null!=valueMap.get("owner")){
			device.setOwner(valueMap.get("owner"));
			valueMap.remove("owner");
		}
		if(null!=valueMap.get("productId")){
			device.setProductId(valueMap.get("productId"));
			valueMap.remove("productId");
		}
		if(null!=valueMap.get("serialNumber")){
			device.setSerialNumber(valueMap.get("serialNumber"));
			valueMap.remove("serialNumber");
		}
		if(null!=valueMap.get("address")){
			device.setAddress(valueMap.get("address"));
			valueMap.remove("address");
		}
		if(null!=valueMap.get("longitude")){
			try{
				device.setLongitude(Double.parseDouble(valueMap.get("longitude")));
			}catch (NumberFormatException e) {
				LOGGER.error("parse longitude [{}] exception.", valueMap.get("longitude"));
			}
			valueMap.remove("longitude");
		}
		if(null!=valueMap.get("latitude")){
			try{
				device.setLatitude(Double.parseDouble(valueMap.get("latitude")));
			}catch (NumberFormatException e) {
				LOGGER.error("parse latitude [{}] exception.", valueMap.get("latitude"));
			}
			valueMap.remove("latitude");
		}
		if(valueMap.size()>0){
			List<Item> items = new ArrayList<>();
			for(Entry<String, String> entry:valueMap.entrySet()){
				items.add(new Item(descMap.get(entry.getKey()), entry.getKey(), entry.getValue()));
			}
			device.setDescAttributeList(items);
			device.setDescAttributes(jsonUtil.toJson(items));
		}
		
		return device;
	}
	
}
