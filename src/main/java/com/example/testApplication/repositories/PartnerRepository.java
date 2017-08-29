package com.example.testApplication.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.testApplication.entities.Partner;

public interface PartnerRepository extends JpaRepository<Partner, Long> {

	@Query("SELECT p FROM Partner p where p.phone = :phone and p.mail = :mail and p.name = :name")
	public Optional<Partner> findPersonComplete(@Param("phone") String phone, @Param("mail") String mail,
			@Param("name") String name);

	@Query("SELECT p FROM Partner p where p.name = :name")
	public Optional<Partner> findPartnerByName(@Param("name") String name);

	
}
