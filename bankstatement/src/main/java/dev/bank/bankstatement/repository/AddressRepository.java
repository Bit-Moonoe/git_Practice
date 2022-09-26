package dev.bank.bankstatement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import dev.bank.bankstatement.entity.Address;

@Repository
public interface AddressRepository extends JpaRepository<Address, Integer> {
	List<Address> findByUserId(String id);
}
