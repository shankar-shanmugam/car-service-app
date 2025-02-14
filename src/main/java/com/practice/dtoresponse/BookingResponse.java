package com.practice.dtoresponse;

import java.util.List;

public class BookingResponse {

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public CarResponse getCar() {
		return car;
	}

	public void setCar(CarResponse car) {
		this.car = car;
	}

	public List<ServiceResponse> getServices() {
		return services;
	}

	public void setServices(List<ServiceResponse> services) {
		this.services = services;
	}

	private int id;

	private CarResponse car;

	private List<ServiceResponse> services;

}
