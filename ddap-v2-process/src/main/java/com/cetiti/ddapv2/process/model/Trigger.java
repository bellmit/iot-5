package com.cetiti.ddapv2.process.model;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class Trigger extends Thing {
	
	private String lambda;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss")
	private Date date;

	public String getLambda() {
		return lambda;
	}

	public void setLambda(String lambda) {
		this.lambda = lambda;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	
}
