package com.practice.mapper;

import org.springframework.stereotype.Component;

import com.practice.dtorequest.ServiceRequest;
import com.practice.dtoresponse.ServiceResponse;
import com.practice.entity.CarService;

@Component
public class ServiceMapper {

	public CarService mapToCarService(ServiceRequest serviceRequest) {
		
		CarService carService= new CarService();
		carService.setType(serviceRequest.getType());
		carService.setCost(serviceRequest.getCost());
		carService.setDescription(serviceRequest.getDescription());
		
		return carService;
	}
	
	public ServiceResponse mapToServiceResponse(CarService carService) {
		
		ServiceResponse serviceResponse= new ServiceResponse();
		serviceResponse.setId(carService.getId());
		serviceResponse.setType(carService.getType());
		serviceResponse.setCost(carService.getCost());
		serviceResponse.setDescription(carService.getDescription());
		return serviceResponse;
		
		
	}
	
	
	
}
