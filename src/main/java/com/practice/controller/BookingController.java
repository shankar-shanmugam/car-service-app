package com.practice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.practice.dtoresponse.BookingResponse;
import com.practice.entity.Booking;
import com.practice.service.BookingService;
import com.practice.utility.ResponseStructure;

@RestController
public class BookingController {

	@Autowired
	private BookingService bookingService;

	@PostMapping("/cars/{carId}/bookings")
	public ResponseEntity<ResponseStructure<BookingResponse>> createBooking(@PathVariable int carId) {
		return bookingService.createBooking(carId);
	}

}
