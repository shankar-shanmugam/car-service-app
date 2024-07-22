package com.practice.serviceImpl;

import java.util.List;
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

	return	ResponseEntity.status(HttpStatus.CREATED).body(new ResponseStructure<ServiceResponse>()
				.setStatusCode(HttpStatus.CREATED.value())
				.setData( serviceMapper.mapToServiceResponse(serviceRepository.save(serviceMapper.mapToCarService(serviceRequest))))
				.setMessage(" car service added successfully"));	
	}

	@Override
	public ResponseEntity<ResponseStructure<ServiceResponse>> findServiceById(int carServiceId) {

		return serviceRepository.findById(carServiceId).map(carService -> ResponseEntity.status(HttpStatus.FOUND)
				.body(new ResponseStructure<ServiceResponse>().setStatusCode(HttpStatus.FOUND.value())
						.setMessage("car service found").setData(serviceMapper.mapToServiceResponse(carService))))
				.orElseThrow(() -> new ServiceNotFoundByIdException("Service ID not found"));
	}

	@Override
	public ResponseEntity<ResponseStructure<ServiceResponse>> updateServiceById(int carServiceId,
			ServiceRequest updatedServiceRequest) {
	
		return serviceRepository.findById(carServiceId).map(exCarService -> {
			
			CarService carService = serviceMapper.mapToCarService(updatedServiceRequest);
			carService.setId(exCarService.getId());
			
			return ResponseEntity.status(HttpStatus.OK)
					.body(new ResponseStructure<ServiceResponse>()
							.setStatusCode(HttpStatus.OK.value())
							.setMessage("car service updated successfully")
							.setData(serviceMapper.mapToServiceResponse(serviceRepository.save(carService))));
			
		}).orElseThrow(() -> new ServiceNotFoundByIdException("Service ID not found"));

	}

	@Override
	public ResponseEntity<ResponseStructure<ServiceResponse>> deleteService(int carServiceId) {
				
	return	serviceRepository.findById(carServiceId).map(carService->{
		
		serviceRepository.delete(carService);	
		return	ResponseEntity.status(HttpStatus.OK).body(new ResponseStructure<ServiceResponse>()
					.setStatusCode(HttpStatus.OK.value())
					.setMessage("car service deleted successfully").setData(serviceMapper.mapToServiceResponse(carService)));
				
		}).orElseThrow(()->new ServiceNotFoundByIdException("service ID not found"));
		
	}

	@Override
	public ResponseEntity<ResponseStructure<List<ServiceResponse>>> findAllServices() {
		
		
		return ResponseEntity.status(HttpStatus.FOUND)
				.body(new ResponseStructure<List<ServiceResponse>>()
				.setStatusCode(HttpStatus.FOUND.value())
				.setMessage("All car service fetched successfully")
				.setData(serviceRepository.findAll()
						.stream()
						.map(serviceMapper::mapToServiceResponse) // (service->serviceMapper.mapToServiceResponse(service))
						.toList()));
	}
}
