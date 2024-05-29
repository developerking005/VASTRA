package com.backend.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.backend.controller.model.Address;



public interface AddressRepository extends JpaRepository<Address, Long>{

}
