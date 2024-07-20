package com.practice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.practice.dtorequest.CarRequest;
import com.practice.dtoresponse.CarResponse;
import com.practice.entity.Car;
import com.practice.mapper.CarMapper;
import com.practice.service.CarServiceInterface;
import com.practice.utility.ResponseStructure;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/cars")
public class CarController {

	
	private final CarServiceInterface carServiceInterface;
	
	public CarController(CarServiceInterface carServiceInterface) {
		this.carServiceInterface = carServiceInterface;
	}

	@PostMapping
	public ResponseEntity<ResponseStructure<CarResponse>> addCar(@RequestBody CarRequest carRequest) {
	
		return carServiceInterface.addCar(carRequest);	
	}
	
	  @GetMapping("/{carId}")
	public ResponseEntity<ResponseStructure<CarResponse>> findCarById(@PathVariable int carId) {
		
		return carServiceInterface.findCarById(carId);
		
	}
	
	@GetMapping
	public ResponseEntity<ResponseStructure<List<CarResponse>>> findAllCars() {
		
		return carServiceInterface.findAllCars();
		
	}
	
	@PutMapping("/{carId}")
	public ResponseEntity<ResponseStructure<CarResponse>> updateCar(@PathVariable int carId, @RequestBody CarRequest carRequest) {
		
		return carServiceInterface.updateCarById(carId, carRequest);
	}
	
	@DeleteMapping("{carId}")
	public ResponseEntity<ResponseStructure<CarResponse>> deleteCar(@PathVariable int carId){
		
		return carServiceInterface.deleteCar(carId);
	}
}
