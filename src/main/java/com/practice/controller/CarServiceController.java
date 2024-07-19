package com.practice.controller;


import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.practice.dtorequest.ServiceRequest;
import com.practice.dtoresponse.ServiceResponse;
import com.practice.service.CarServiceService;
import com.practice.utility.ResponseStructure;

@RestController
@RequestMapping("/services")
public class CarServiceController {

    private final CarServiceService carServiceService;

    public CarServiceController(CarServiceService carServiceService) {
        this.carServiceService = carServiceService;
    }

    @PostMapping
    public ResponseEntity<ResponseStructure<ServiceResponse>> addService(@RequestBody ServiceRequest serviceRequest) {
        return carServiceService.addService(serviceRequest);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseStructure<ServiceResponse>> findServiceById(@PathVariable int id) {
        return carServiceService.findServiceById(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseStructure<ServiceResponse>> updateServiceById(@PathVariable int id,@RequestBody ServiceRequest serviceRequest) {
        return carServiceService.updateServiceById(id, serviceRequest);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseStructure<ServiceResponse>> deleteService(@PathVariable int id) {
        return carServiceService.deleteService(id);
    }

    @GetMapping
    public ResponseEntity<ResponseStructure<List<ServiceResponse>>> findAllServices() {
        return carServiceService.findAllServices();
    }
}

