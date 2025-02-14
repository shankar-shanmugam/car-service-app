package com.practice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.practice.entity.Contract;

public interface ContractRepository extends JpaRepository<Contract, Integer> {

}
