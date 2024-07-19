package com.practice.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.practice.dtorequest.ServiceRequest;
import com.practice.dtoresponse.ServiceResponse;
import com.practice.utility.ResponseStructure;

public interface CarServiceService {

	
	
	ResponseEntity<ResponseStructure<ServiceResponse>> addService(ServiceRequest serviceRequest);

	ResponseEntity<ResponseStructure<ServiceResponse>> findServiceById(int carId);

	ResponseEntity<ResponseStructure<ServiceResponse>> updateServiceById(int carId, ServiceRequest serviceRequest);

	ResponseEntity<ResponseStructure<ServiceResponse>> deleteService(int carId);

	ResponseEntity<ResponseStructure<List<ServiceResponse>>> findAllServices();
	
	
	
}
