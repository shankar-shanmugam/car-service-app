package com.practice.serviceImpl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.practice.dtorequest.ServiceRequest;
import com.practice.dtoresponse.ServiceResponse;
import com.practice.entity.CarService;
import com.practice.exception.ServiceNotFoundByIdException;
import com.practice.mapper.ServiceMapper;
import com.practice.repository.ServiceRepository;
import com.practice.service.CarServiceService;
import com.practice.utility.ResponseStructure;

@Service
public class CarServiceServiceImpl implements CarServiceService {

	private final ServiceMapper serviceMapper;
	private final ServiceRepository serviceRepository;

	public CarServiceServiceImpl(ServiceMapper serviceMapper, ServiceRepository serviceRepository) {
		this.serviceMapper = serviceMapper;
		this.serviceRepository = serviceRepository;
	}

	@Override
	public ResponseEntity<ResponseStructure<ServiceResponse>> addService(ServiceRequest serviceRequest) {
		CarService carService = serviceMapper.mapToCarService(serviceRequest);
		CarService savedService = serviceRepository.save(carService);
		ServiceResponse serviceResponse = serviceMapper.mapToServiceResponse(savedService);

		ResponseStructure<ServiceResponse> rs = new ResponseStructure<>();
		rs.setStatusCode(HttpStatus.CREATED.value());
		rs.setMessage("Car service added successfully");
		rs.setData(serviceResponse);

		return new ResponseEntity<ResponseStructure<ServiceResponse>>(rs, HttpStatus.CREATED);
	}

	@Override
	public ResponseEntity<ResponseStructure<ServiceResponse>> findServiceById(int carServiceId) {
		Optional<CarService> carService = serviceRepository.findById(carServiceId);
		if (carService.isPresent()) {
			ServiceResponse serviceResponse = serviceMapper.mapToServiceResponse(carService.get());
			ResponseStructure<ServiceResponse> rs = new ResponseStructure<>();
			rs.setStatusCode(HttpStatus.OK.value());
			rs.setMessage("Car service found");
			rs.setData(serviceResponse);
			return new ResponseEntity<ResponseStructure<ServiceResponse>>(rs, HttpStatus.OK);
		} else {
			throw new ServiceNotFoundByIdException("Service ID not found");
		}
	}

	@Override
	public ResponseEntity<ResponseStructure<ServiceResponse>> updateServiceById(int carServiceId,
			ServiceRequest updatedServiceRequest) {
		Optional<CarService> existingServiceOptional = serviceRepository.findById(carServiceId);
		if (existingServiceOptional.isPresent()) {
			CarService existingService = existingServiceOptional.get();
			CarService carService = serviceMapper.mapToCarService(updatedServiceRequest);
			carService.setId(existingService.getId());
			CarService updatedService = serviceRepository.save(carService);

			ServiceResponse serviceResponse = serviceMapper.mapToServiceResponse(updatedService);
			ResponseStructure<ServiceResponse> rs = new ResponseStructure<>();
			rs.setStatusCode(HttpStatus.OK.value());
			rs.setMessage("Car service updated successfully");
			rs.setData(serviceResponse);
			return new ResponseEntity<ResponseStructure<ServiceResponse>>(rs, HttpStatus.OK);
		} else {
			throw new ServiceNotFoundByIdException("Service ID not found");
		}
	}

	@Override
	public ResponseEntity<ResponseStructure<ServiceResponse>> deleteService(int carServiceId) {
		Optional<CarService> existingServiceOptional = serviceRepository.findById(carServiceId);
		if (existingServiceOptional.isPresent()) {
			CarService carService = existingServiceOptional.get();
			serviceRepository.delete(carService);
			ServiceResponse serviceResponse = serviceMapper.mapToServiceResponse(carService);
			ResponseStructure<ServiceResponse> rs = new ResponseStructure<>();
			rs.setStatusCode(HttpStatus.OK.value());
			rs.setMessage("Car service deleted successfully");
			rs.setData(serviceResponse);
			return new ResponseEntity<ResponseStructure<ServiceResponse>>(rs, HttpStatus.OK);
		} else {
			throw new ServiceNotFoundByIdException("Service ID not found");
		}
	}

	@Override
	public ResponseEntity<ResponseStructure<List<ServiceResponse>>> findAllServices() {
		List<CarService> services = serviceRepository.findAll();
		List<ServiceResponse> serviceResponses = services.stream().map(serviceMapper::mapToServiceResponse)
				.collect(Collectors.toList());

		ResponseStructure<List<ServiceResponse>> rs = new ResponseStructure<>();
		rs.setStatusCode(HttpStatus.OK.value());
		rs.setMessage("All car services fetched successfully");
		rs.setData(serviceResponses);
		return new ResponseEntity<ResponseStructure<List<ServiceResponse>>>(rs, HttpStatus.OK);
	}
}
