package com.practice.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.practice.dtoresponse.BookingResponse;
import com.practice.entity.Booking;
import com.practice.entity.Car;
import com.practice.entity.CarService;
import com.practice.entity.Cart;
import com.practice.entity.Contract;
import com.practice.entity.User;
import com.practice.exception.CarNotFoundByIdException;
import com.practice.exception.UserNotFoundByIdException;
import com.practice.mapper.BookingMapper;
import com.practice.repository.BookingRepository;
import com.practice.repository.CarRepository;
import com.practice.repository.ContractRepository;
import com.practice.repository.UserRepository;
import com.practice.service.BookingService;
import com.practice.utility.ResponseStructure;

@Service
public class BookingServiceImpl implements BookingService {

	@Autowired
	private BookingRepository bookingRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private CarRepository carRepository;

	@Autowired
	private BookingMapper bookingMapper;

	@Autowired
	private ContractRepository contractRepository;

	@Override
	public ResponseEntity<ResponseStructure<BookingResponse>> createBooking(int carId) {

		String email = SecurityContextHolder.getContext().getAuthentication().getName();

		User customer = userRepository.findByEmail(email)
				.orElseThrow(() -> new UserNotFoundByIdException("User not found"));

		Car car = carRepository.findById(carId).orElseThrow(() -> new CarNotFoundByIdException("Car ID not found"));

		Booking booking = new Booking();
		booking.setCar(car);
		booking.setCustomer(customer);

		Booking savedBooking = bookingRepository.save(booking);
		Cart cart = customer.getCart();
		List<CarService> services = cart.getCarService();

		List<Contract> contractList = new ArrayList<>(services.stream().map(service -> {
			Contract contract = new Contract();
			contract.setCarService(service);
			contract.setBooking(savedBooking);
			return contractRepository.save(contract);
		}).toList());
		savedBooking.setContracts(contractList);
		bookingRepository.save(savedBooking);
		BookingResponse bookingResponse = bookingMapper.mapToBookingResponse(savedBooking);

		return ResponseEntity.status(HttpStatus.CREATED)
				.body(new ResponseStructure<BookingResponse>().setStatusCode(HttpStatus.CREATED.value())
						.setMessage("Bookings created successfully").setData(bookingResponse));
	}
}
