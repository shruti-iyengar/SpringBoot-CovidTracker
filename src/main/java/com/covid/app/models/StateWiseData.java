package com.covid.app.models;

import lombok.Data;

@Data
public class StateWiseData {
	
	private String country;
	private String state;
	private Long todayCount;
	private Long deltaCount;
}
