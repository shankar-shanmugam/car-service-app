package com.practice.serviceImpl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.practice.dtorequest.CarRequest;
import com.practice.dtoresponse.CarResponse;
import com.practice.dtoresponse.ServiceResponse;
import com.practice.entity.Car;
import com.practice.entity.CarService;
import com.practice.exception.CarNotFoundByIdException;
import com.practice.exception.ServiceNotFoundByIdException;
import com.practice.mapper.CarMapper;
import com.practice.repository.CarRepository;
import com.practice.service.CarServiceInterface;
import com.practice.utility.ResponseStructure;

@Service
public class CarServiceImpl implements CarServiceInterface {

	private final CarRepository repo;
	private final CarMapper carMapper;

	public CarServiceImpl(CarRepository repo, CarMapper carMapper) {
		this.repo = repo;
		this.carMapper = carMapper;
	}

	@Override
	public ResponseEntity<ResponseStructure<CarResponse>> addCar(CarRequest carRequest) {

		Car car = repo.save(carMapper.mapToCar(carRequest));
		CarResponse CarResponse = carMapper.mapToCarResponse(car);
		
		return	ResponseEntity.status(HttpStatus.CREATED).body(new ResponseStructure<CarResponse>()
				.setStatusCode(HttpStatus.CREATED.value()).setData(CarResponse).setMessage(" car object added successfully "));
	}

	@Override
	public ResponseEntity<ResponseStructure<CarResponse>> updateCarById(int carId, CarRequest updatedCar) {

			return repo.findById(carId).map(exCar -> {
			
			Car car = carMapper.mapToCar(updatedCar);
			car.setId(exCar.getId());
			CarResponse carResponse = carMapper.mapToCarResponse(repo.save(car));
			
		return	 ResponseEntity.status(HttpStatus.OK)

			 .body(new ResponseStructure<CarResponse>()
						.setStatusCode(HttpStatus.OK.value())
						.setMessage("car updated successfully")
						.setData(carResponse));
	}).orElseThrow(() -> new CarNotFoundByIdException("Car ID not found"));
			}

	@Override
	public ResponseEntity<ResponseStructure<CarResponse>> deleteCar(int carId) {

	return	repo.findById(carId).map(car->{
			
				repo.delete(car);
				
			return	ResponseEntity.status(HttpStatus.OK).body(
						
						new ResponseStructure<CarResponse>().setStatusCode(HttpStatus.OK.value())
							.setMessage("car object deleted successfully")
							.setData(carMapper.mapToCarResponse(car)));
				
		}).orElseThrow(()-> new CarNotFoundByIdException("Car Id you Entered is Not Found!! in database"));
				
	}

	@Override
	public ResponseEntity<ResponseStructure<List<CarResponse>>> findAllCars() {

		List<CarResponse> carResponseDTOList = repo.findAll().stream()
				.map(carMapper::mapToCarResponse)
				.toList();
		return ResponseEntity.status(HttpStatus.FOUND)
				.body(new ResponseStructure<List<CarResponse>>()
				.setStatusCode(HttpStatus.OK.value())
				.setMessage("List of cars fetched successfully")
				.setData(carResponseDTOList));
	}

	@Override
	public ResponseEntity<ResponseStructure<CarResponse>> findCarById(int carId) {
	
	return	repo.findById(carId) //[Optional<Car>]
			.map(car-> ResponseEntity				// return statement not needed because of single line 
					.status(HttpStatus.FOUND)
					.body(new ResponseStructure<CarResponse>()
							.setStatusCode(HttpStatus.FOUND.value())
							.setMessage("car object found successfully ")
							.setData(carMapper.mapToCarResponse(car)))) // [Optional<ResponseStructure<CarResponse>>]
		.orElseThrow(()->
		new CarNotFoundByIdException("Car Id you Entered is Not Found!! in database") // here you should not throw
				);
	}

}
