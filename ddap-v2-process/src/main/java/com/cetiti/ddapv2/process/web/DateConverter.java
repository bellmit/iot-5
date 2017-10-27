package com.cetiti.ddapv2.process.web;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.cetiti.ddapv2.process.util.MessageContext;

@Component
public class DateConverter implements Converter<String, Date> {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(DateConverter.class);
	
	@Override
    public Date convert(String stringDate) {
		if(!StringUtils.hasText(stringDate)){
			MessageContext.setMsg("dateformat [yyyy-MM-dd hh:mm:ss]");
			return null;
		}
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        try {
            return simpleDateFormat.parse(stringDate);
        } catch (ParseException e) {
        	MessageContext.setMsg("dateformat [yyyy-MM-dd hh:mm:ss]");
            LOGGER.error("date [{}] convert error. [{}]", stringDate, e.getMessage());
        }
        return null;
    }
	
}
