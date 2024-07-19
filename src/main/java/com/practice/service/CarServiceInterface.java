package com.practice.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.practice.dtorequest.CarRequest;
import com.practice.dtoresponse.CarResponse;
import com.practice.entity.Car;
import com.practice.utility.ResponseStructure;

public interface CarServiceInterface {

	ResponseEntity<ResponseStructure<CarResponse>> addCar(CarRequest car);

	ResponseEntity<ResponseStructure<CarResponse>> findCarById(int carServiceId);

	ResponseEntity<ResponseStructure<CarResponse>> updateCarById(int carServiceId, CarRequest car);

	ResponseEntity<ResponseStructure<CarResponse>> deleteCar(int carServiceId);

	ResponseEntity<ResponseStructure<List<CarResponse>>> findAllCars();

}
