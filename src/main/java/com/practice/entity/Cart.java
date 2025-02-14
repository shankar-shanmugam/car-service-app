package com.practice.entity;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;

@Entity
public class Cart {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	// @JsonIgnore
//	@JoinColumn(name = "customer")
	@OneToOne(fetch = FetchType.LAZY)
	private User user;
	
	@ManyToMany(fetch = FetchType.EAGER)
	private List<CarService> carService;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}
	

	public void setUser(User user) {
		this.user = user;
	}

	public List<CarService> getCarService() {
		return carService;
	}

	public void setCarService(List<CarService> carService) {
		this.carService = carService;
	}
	
	
}
