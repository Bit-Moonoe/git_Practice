package dev.bank.bankstatement.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.bank.bankstatement.entity.Address;
import dev.bank.bankstatement.entity.User;
import dev.bank.bankstatement.repository.AddressRepository;

@RestController
@RequestMapping("address")
public class AddressController {

	@Autowired
	AddressRepository addressRepository;


	@PostMapping
	public void createAddress(@RequestBody Address address) {
		System.out.println(address);
		address.setUser(User.builder().id("mooonoe").build());
		addressRepository.save(address);
	}

	@GetMapping("{id}")
	public Address.Response getAddress(@PathVariable int id) {
		Address address = addressRepository.findById(id).orElseThrow();
		return Address.Response.toResponse(address);
	}

//	@GetMapping("userid/{id}")
//	public List<Address> getAddressByUserId(@PathVariable String id) {
//		return addressRepository.findByUserId(id);
//
//	}
	

	
	@GetMapping("userid/{id}") 
	public List<Address.Response> getAddressesByUserId(@PathVariable String id) {
		return Address.Response.toResponseList(addressRepository.findByUserId(id));
	}

}
