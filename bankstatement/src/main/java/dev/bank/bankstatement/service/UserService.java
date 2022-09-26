package dev.bank.bankstatement.service;

import java.util.List;

import org.springframework.stereotype.Service;

import dev.bank.bankstatement.entity.User;


@Service
public interface UserService {
	
	List<User> findAllUsers();
	
	User findUserByID(String id);
	
	User saveUser(User newUser);
	
	User loginUser(User.Request request);
	
	List<User> updateUser(User.Request request);
	
	List<User> deleteUser(String id);

}
