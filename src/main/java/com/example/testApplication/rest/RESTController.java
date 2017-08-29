package com.example.testApplication.rest;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.testApplication.entities.Address;
import com.example.testApplication.entities.Partner;
import com.example.testApplication.exception.NotFoundException;
import com.example.testApplication.exception.NotFoundException.NotFoundExceptionType;
import com.example.testApplication.repositories.AddressRepository;
import com.example.testApplication.repositories.PartnerRepository;

@RestController
public class RESTController {
	PartnerRepository repo;
	AddressRepository addRepo;

	@Autowired
	public RESTController(PartnerRepository repo, AddressRepository addRepo) {
		this.repo = repo;
		this.addRepo = addRepo;
	}

	@RequestMapping(value = "/partners", method = RequestMethod.GET, headers = "Accept=application/json", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public List<Partner> getPartners() {
		return (ArrayList<Partner>) repo.findAll();
	}

	@RequestMapping(value = "/addresses", method = RequestMethod.GET, headers = "Accept=application/json", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public List<Address> getAddresses() {
		return (ArrayList<Address>) addRepo.findAll();
	}

	@RequestMapping(value = "/partner/{companyName}", method = RequestMethod.GET, headers = "Accept=application/json", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<?> getPartner(@PathVariable("companyName") final String companyName)
			throws NotFoundException {
		final String name = companyName.trim();
		final Partner partner = repo.findPartnerByName(name)
				.orElseThrow(() -> new NotFoundException(NotFoundExceptionType.PARTNERNOTFOUNDEXCEPTION));
		return new ResponseEntity<Partner>(partner, HttpStatus.OK);
	}

	@RequestMapping(value = "/address/{id}", method = RequestMethod.GET, headers = "Accept=application/json", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<?> getAddress(@PathVariable("id") final Long id) throws NotFoundException {
		final Address address = addRepo.findAddressById(id)
				.orElseThrow(() -> new NotFoundException(NotFoundExceptionType.ADDRESSNOTFOUNDEXCEPTION));
		return new ResponseEntity<Address>(address, HttpStatus.OK);
	}

}
