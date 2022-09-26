package dev.bank.bankstatement.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import dev.bank.bankstatement.entity.User;
import dev.bank.bankstatement.entity.User.Request;
import dev.bank.bankstatement.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {
	
	
	@Autowired
	private UserRepository userRepository;
	
	@Override
	public List<User> findAllUsers() {
		System.out.println("findAllUsers 호출");		
		return userRepository.findAll();
	}

	@Override
	public User findUserByID(String id) {
		System.out.println("findUserById 함수 호출");
		User foundUser = userRepository.findById(id).orElseThrow(()-> new RuntimeException(String.format("%s에 해당하는 User가 존재하지 않습니다.", id)));
		return foundUser;
	}

	@Override
	public User saveUser(User newUser) {
		return userRepository.save(newUser);
	}

	@Override
	public List<User> updateUser(Request request) {
		final Optional<User> user = userRepository.findById(request.getId());
		if(user.isPresent()) { // ID에 해당하는 User가 존재할 경우
			final User foundUser = user.get(); //DB에 저장되어 있는 기존의 User 정보
			foundUser.setName(request.getName()); 
			//request.getName(): 수정하고자 하는 이름
			foundUser.setGender(request.getGender());
	}
		List<User> users = userRepository.findAll();
		return users;
	}

	@Override
	public List<User> deleteUser(String id) {
		userRepository.deleteById(id);
		
		List<User> users = userRepository.findAll();
		return users;
	}

	@Override
	public User loginUser(Request request) {
		return userRepository.findByIdAndPassword(request.getId(), request.getPassword());
		//sql 식으로 select * from user where id=? and password=?
		// where절 및 and에 관한 sql 쿼리 메소드를 써야한다.
	}

}
