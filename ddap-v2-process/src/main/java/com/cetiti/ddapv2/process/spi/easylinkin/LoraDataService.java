package com.cetiti.ddapv2.process.spi.easylinkin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.cetiti.ddapv2.process.model.Data;
import com.cetiti.ddapv2.process.service.DataService;
import com.cetiti.ddapv2.process.spi.Codec;
import com.cetiti.ddapv2.process.util.JsonUtil;
import com.cetiti.ddapv2.process.util.LocalCache;

@Service
public class LoraDataService {

	@Resource
	private JsonUtil jsonUtil;
	@Resource
	private LocalCache localCache;
	@Resource
	private DataService dataService;
	
	private Map<String, Codec<?, ?>> sensors = new HashMap<>();
	

	public String process(String content) {
		if(!StringUtils.hasText(content)){
			return null;
		}
		List<LoraData> dataList = parse(content);
		for(LoraData lora: dataList){
			if(LoraData.DATA_TYPE_HEARTBEAT == lora.getDataType()){
				continue;
			}
			Codec codec = sensors.get(lora.getAppeui());
			Data data;
			if(null==codec){
				data = new Data();
				Map<String, Object> rawdata = new HashMap<>();
				rawdata.put("rawData", lora.getData());
				rawdata.put("appeui", lora.getAppeui());
				data.setMapData(rawdata);
			}else{
				data = codec.decode(lora);
			}
			String deviceId = localCache.getDeviceIdBySerialNumberAndProductId(lora.getMac(), lora.getAppeui());
			if(null==deviceId){
				data.setDeviceId(lora.getMac());
			}else{
				data.setDeviceId(deviceId);
			}
			dataService.process(data);
		}
		return jsonUtil.toJson(new Response());
	}

	private List<LoraData> parse(String content) {
		List<LoraData> dataList = new ArrayList<>();
		if(content.indexOf("[")<0||content.indexOf("{")<content.indexOf("[")){
			LoraData data = jsonUtil.beanFromJson(content, LoraData.class);
			dataList.add(data);
		}else{
			dataList = jsonUtil.listFromJson(content, LoraData.class);
		}
		return dataList;
	}

}
