package com.practice.serviceImpl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.practice.dtorequest.CarRequest;
import com.practice.dtoresponse.CarResponse;
import com.practice.entity.Car;
import com.practice.exception.CarNotFoundByIdException;
import com.practice.mapper.CarMapper;
import com.practice.repository.CarRepository;
import com.practice.service.CarServiceInterface;
import com.practice.utility.ResponseStructure;

@Service
public class CarServiceImpl implements CarServiceInterface {

	private CarRepository repo;
	private CarMapper carMapper;

	public CarServiceImpl(CarRepository repo, CarMapper carMapper) {
		this.repo = repo;
		this.carMapper = carMapper;
	}

	@Override
	public ResponseEntity<ResponseStructure<CarResponse>> addCar(CarRequest carRequest) {

		Car car = carMapper.mapToCar(carRequest);
		Car a = repo.save(car);
		CarResponse mapToCarResponse = carMapper.mapToCarResponse(a);
		ResponseStructure<CarResponse> rs = new ResponseStructure<>();
		rs.setStatusCode(HttpStatus.CREATED.value());
		rs.setMessage("Car object added successfully");
		rs.setData(mapToCarResponse);

		return new ResponseEntity<ResponseStructure<CarResponse>>(rs, HttpStatus.CREATED);

	}

	@Override
	public ResponseEntity<ResponseStructure<CarResponse>> updateCarById(int carId, CarRequest updatedCar) {

		Optional<Car> var = repo.findById(carId);
		if (var.isPresent()) {
			Car existingCar = var.get();
			Car car = carMapper.mapToCar(updatedCar);
			car.setId(existingCar.getId());

			Car a = repo.save(car);
			CarResponse mapToCarResponse = carMapper.mapToCarResponse(a);
			ResponseStructure<CarResponse> rs = new ResponseStructure<>();
			rs.setStatusCode(HttpStatus.OK.value());
			rs.setMessage("Car object updated successfully");
			rs.setData(mapToCarResponse);

			return new ResponseEntity<ResponseStructure<CarResponse>>(rs, HttpStatus.OK);
		} else {
			throw new CarNotFoundByIdException("Car Id you Entered is Not Found!!");
		}
	}

	@Override
	public ResponseEntity<ResponseStructure<CarResponse>> deleteCar(int carId) {

		Optional<Car> var = repo.findById(carId);
		if (var.isPresent()) {
			Car car = var.get();
			repo.delete(car);
			CarResponse carResponse = carMapper.mapToCarResponse(car);
			ResponseStructure<CarResponse> rs = new ResponseStructure<>();
			rs.setStatusCode(HttpStatus.OK.value());
			rs.setMessage("Car object deleted successfully");
			rs.setData(carResponse);

			return new ResponseEntity<ResponseStructure<CarResponse>>(rs, HttpStatus.OK);
		} else {
			throw new CarNotFoundByIdException("Car Id you Entered is Not Found!!");
		}

	}

	@Override
	public ResponseEntity<ResponseStructure<List<CarResponse>>> findAllCars() {

		List<Car> carList = repo.findAll();

		List<CarResponse> carResponseDTOList = carList.stream().map(carMapper::mapToCarResponse)
				.collect(Collectors.toList());

		ResponseStructure<List<CarResponse>> rs = new ResponseStructure<>();
		rs.setStatusCode(HttpStatus.FOUND.value());
		rs.setMessage("List of cars fetched successfully ");
		rs.setData(carResponseDTOList);

		return new ResponseEntity<ResponseStructure<List<CarResponse>>>(rs, HttpStatus.FOUND);

	}

	@Override
	public ResponseEntity<ResponseStructure<CarResponse>> findCarById(int carId) {

		Optional<Car> car = repo.findById(carId);
		if (car.isPresent()) {
			Car carobj = car.get();
			CarResponse carResponse = carMapper.mapToCarResponse(carobj);
			ResponseStructure<CarResponse> rs = new ResponseStructure<>();
			rs.setStatusCode(HttpStatus.FOUND.value());
			rs.setMessage("car object found successfully ");
			rs.setData(carResponse);

			return new ResponseEntity<ResponseStructure<CarResponse>>(rs, HttpStatus.FOUND);

		} else {
			throw new CarNotFoundByIdException("Car Id you Entered is Not Found!!");
		}
	}

}
