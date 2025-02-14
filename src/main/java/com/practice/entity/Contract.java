package com.practice.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Contract {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	private CarService carService;
	
	public Booking getBooking() {
		return booking;
	}

	public void setBooking(Booking booking) {
		this.booking = booking;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	private Booking booking;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "mechanic")
	private User mechanic;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public CarService getCarService() {
		return carService;
	}

	public void setCarService(CarService carService) {
		this.carService = carService;
	}

	public User getMechanic() {
		return mechanic;
	}

	public void setMechanic(User mechanic) {
		this.mechanic = mechanic;
	}
	
	
}
