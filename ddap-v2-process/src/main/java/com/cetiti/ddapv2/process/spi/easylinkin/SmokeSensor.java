package com.cetiti.ddapv2.process.spi.easylinkin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.cetiti.ddapv2.process.model.Data;
import com.cetiti.ddapv2.process.model.DataBuilder;

public class SmokeSensor extends EasyLinkInSensor{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(SmokeSensor.class);
	
	@Override
	public Data decode(LoraData payload) {
		if(null==payload||null==payload.getMac()||
				null==payload.getData()||payload.getData().length()!=4){
			LOGGER.warn("SmokeSensor payload [{}] incomplete.", payload);
			return null;
		}
		int cdata = Character.digit(payload.getData().charAt(3), 16);
		DataBuilder db = new DataBuilder(payload.getMac());
		if((cdata&0x08)>0){
			db.setAntiDismantleState(DataBuilder.STATE_ABNORMAL);
		}
		if((cdata&0x04)>0){
			db.setWorkingState(DataBuilder.STATE_ABNORMAL);
		}
		if((cdata&0x02)>0){
			db.setPowerState(DataBuilder.STATE_ABNORMAL);
		}
		if((cdata&0x01)>0){
			db.setData("hasSmoke", DataBuilder.STATE_ABNORMAL);
		}else{
			db.setData("hasSmoke", DataBuilder.STATE_NORMAL);
		}
		db.setData("rawData", payload.getData());
		
		return db.build();
	}

}
