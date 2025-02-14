package com.practice.mapper;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.practice.dtoresponse.BookingResponse;
import com.practice.dtoresponse.ServiceResponse;
import com.practice.entity.Booking;
import com.practice.entity.CarService;
import com.practice.entity.Contract;

@Component
public class BookingMapper {

	@Autowired
	private CarMapper carMapper;

	@Autowired
	private ServiceMapper serviceMapper;

	public BookingResponse mapToBookingResponse(Booking booking) {

		BookingResponse response = new BookingResponse();
		response.setId(booking.getId());
		response.setCar(carMapper.mapToCarResponse(booking.getCar()));

		List<Contract> contracts = booking.getContracts();
		List<ServiceResponse> serviceResponses = contracts.stream().map(contract -> {

			CarService service = contract.getCarService();
			ServiceResponse serviceResponse = serviceMapper.mapToServiceResponse(service);
			return serviceResponse;

		}).toList();
		response.setServices(serviceResponses);
		return response;
	}

}
