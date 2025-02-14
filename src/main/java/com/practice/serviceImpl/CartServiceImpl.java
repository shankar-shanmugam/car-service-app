package com.practice.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.practice.dtoresponse.CartResponse;
import com.practice.dtoresponse.ServiceResponse;
import com.practice.entity.CarService;
import com.practice.entity.Cart;
import com.practice.exception.ServiceNotFoundByIdException;
import com.practice.exception.ServiceNotFoundInCartException;
import com.practice.exception.UserNotFoundByIdException;
import com.practice.mapper.ServiceMapper;
import com.practice.repository.CarServiceRepository;
import com.practice.repository.CartRepository;
import com.practice.repository.UserRepository;
import com.practice.service.CartService;
import com.practice.utility.ResponseStructure;

@Service
public class CartServiceImpl implements CartService {

	private final CarServiceRepository carServiceRepository;

	private final UserRepository userRepository;

	private final CartRepository cartRepository;

	private final ServiceMapper serviceMapper;

	public CartServiceImpl(CarServiceRepository carServiceRepository, UserRepository userRepository,
			CartRepository cartRepository, ServiceMapper serviceMapper) {
		this.carServiceRepository = carServiceRepository;
		this.userRepository = userRepository;
		this.cartRepository = cartRepository;
		this.serviceMapper = serviceMapper;
	}

	@Override
	public ResponseEntity<ResponseStructure<CartResponse>> addServiceToCart(int carServiceId) {

		return carServiceRepository.findById(carServiceId).map(carService -> {

			String email = SecurityContextHolder.getContext().getAuthentication().getName();

			return userRepository.findByEmail(email).map(user -> {

				Cart cart = user.getCart();
				if (cart.getCarService() == null)
					cart.setCarService(new ArrayList<CarService>());

				cart.getCarService().add(carService);
				Cart savedCart = cartRepository.save(cart);
												
				CartResponse cartResponse = new CartResponse();
				cartResponse.setId(savedCart.getId());
				List<CarService> carServices = savedCart.getCarService();

				cartResponse.setServices(carServices.stream().map((serviceMapper::mapToServiceResponse)).toList());

				return ResponseEntity.status(HttpStatus.CREATED)
						.body(new ResponseStructure<CartResponse>().setStatusCode(HttpStatus.CREATED.value())
								.setMessage("Service added to cart successfully").setData(cartResponse));

			}).orElseThrow(() -> new UserNotFoundByIdException("User not found"));

		}).orElseThrow(() -> new ServiceNotFoundByIdException("Service ID not found in database"));
		}

	@Override
	public ResponseEntity<ResponseStructure<CartResponse>> deleteServiceInCart(int carServiceId) {

		return carServiceRepository.findById(carServiceId).map(carService -> {
			String email = SecurityContextHolder.getContext().getAuthentication().getName();

			return userRepository.findByEmail(email).map(user -> {
				Cart cart = user.getCart();
				boolean removed = cart.getCarService().remove(carService);

				if (removed) {
					cartRepository.save(cart);
					CartResponse cartResponse = new CartResponse();
					cartResponse.setId(cart.getId());
					List<CarService> carServices = cart.getCarService();

					cartResponse.setServices(carServices.stream().map((serviceMapper::mapToServiceResponse)).toList());
					return ResponseEntity.ok(new ResponseStructure<CartResponse>().setStatusCode(HttpStatus.OK.value())
							.setMessage("Service removed from cart successfully").setData(cartResponse));
				} else {
					throw new ServiceNotFoundInCartException("Service not found in cart");
				}
			}).orElseThrow(() -> new UserNotFoundByIdException("User not found"));
		}).orElseThrow(() -> new ServiceNotFoundByIdException("Service ID not found in database"));
	}

	@Override
	public ResponseEntity<ResponseStructure<CartResponse>> fetchAllInCart() {

		return userRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName())
				.map(user -> {
					Cart cart = user.getCart();
					CartResponse cartResponse = new CartResponse();
					cartResponse.setId(cart.getId());
					List<CarService> carServices = cart.getCarService();

					cartResponse.setServices(carServices.stream().map((serviceMapper::mapToServiceResponse)).toList());

					return ResponseEntity.ok(new ResponseStructure<CartResponse>().setStatusCode(HttpStatus.OK.value())
							.setMessage("All services fetched successfully").setData(cartResponse));

				}).orElseThrow(() -> new UserNotFoundByIdException("User not found"));
	}

}
