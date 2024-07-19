package com.practice.mapper;

import org.springframework.stereotype.Component;

import com.practice.dtorequest.CarRequest;
import com.practice.dtoresponse.CarResponse;
import com.practice.entity.Car;

@Component
public class CarMapper {

	public Car mapToCar(CarRequest carRequest) {
		
		Car c= new Car();
		c.setBrand(carRequest.getBrand());
		c.setModel(carRequest.getModel());
		return c;

	}
	
	public CarResponse mapToCarResponse(Car c) {
		
		CarResponse cr= new CarResponse();
		cr.setCarId(c.getId());
		cr.setBrand(c.getBrand());
		cr.setModel(c.getModel());
		return cr;
	}
	
}
