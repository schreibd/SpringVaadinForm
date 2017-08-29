package com.example.testApplication.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.testApplication.entities.Address;

public interface AddressRepository extends JpaRepository<Address, Long> {

	@Query("SELECT a FROM Address a where a.streetAndNr = :streetAndNr and a.postcode = :postcode and a.city = :city")
	public Optional<Address> findAddressComplete(@Param("streetAndNr") String streetAndNr,
			@Param("postcode") String postcode, @Param("city") String city);
	
	@Query("SELECT a FROM Address a where a.id = :id")
	public Optional<Address> findAddressById(@Param("id") Long id);

}
