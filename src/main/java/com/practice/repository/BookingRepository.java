
package com.practice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.practice.entity.Booking;

public interface BookingRepository extends JpaRepository<Booking, Integer> {

}
