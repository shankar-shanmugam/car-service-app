package com.practice.dtoresponse;

import java.util.List;

public class CartResponse {

	private int id;
	
	private List<ServiceResponse> services;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public List<ServiceResponse> getServices() {
		return services;
	}

	public void setServices(List<ServiceResponse> services) {
		this.services = services;
	}
	
	
}
