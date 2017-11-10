package com.cetiti.ddapv2.process.spi.easylinkin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.cetiti.ddapv2.process.model.Data;
import com.cetiti.ddapv2.process.model.DataBuilder;

public class FlammableGasSensor extends EasyLinkInSensor {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(FlammableGasSensor.class);
	
	@Override
	public Data decode(LoraData payload) {
		if(null==payload||null==payload.getMac()||
				null==payload.getData()||payload.getData().length()!=6){
			LOGGER.warn("FlammableGasSensor payload [{}] incomplete.", payload);
			return null;
		}
		String sdata = payload.getData();
		DataBuilder db = new DataBuilder(payload.getMac());
		int state = Integer.parseUnsignedInt(sdata.substring(2, 4), 16);
		if(state==2){
			db.setData("gasWarning", DataBuilder.STATE_ABNORMAL);
		}else{
			db.setData("gasWarning", DataBuilder.STATE_NORMAL);
		}
		state = Integer.parseUnsignedInt(sdata.substring(4, 6), 16);
		if((state&0x40)>0){
			db.setWorkingState(DataBuilder.STATE_ABNORMAL);
		}
		if((state&0x10)>0){
			db.setPowerState(DataBuilder.STATE_ABNORMAL);
		}
		if((state&0x08)>0){
			db.setAntiDismantleState(DataBuilder.STATE_ABNORMAL);
		}
		
		db.setData("rawData", payload.getData());
		
		return db.build();
	}
	
}
