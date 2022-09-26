package dev.bank.bankstatement.controller;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import dev.bank.bankstatement.entity.Address;
import dev.bank.bankstatement.entity.Gender;
import dev.bank.bankstatement.entity.User;
import dev.bank.bankstatement.jwt.JwtTokenGenerator;
import dev.bank.bankstatement.repository.AddressRepository;
import dev.bank.bankstatement.repository.UserRepository;
import dev.bank.bankstatement.service.UserService;
import dev.bank.bankstatement.service.UserServiceImpl;

@RestController
@CrossOrigin(origins="*") //사용할 포트지정 가능. 에스크리터 쓰면 전부 요청 가능
@RequestMapping("users") // react 프로그램 혹은 Postman 등에서 request 전송 시 localhost:8085/users로 요청 시 처리를 담당할 컨트롤러
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private AddressRepository addressRepository;
	
	@Autowired
	private JwtTokenGenerator jwtTokenGenerator;
	
	
	/** Java Doc 작성용 주석
	 * 
	 *  해당 메서드에 대한 간단한 동작 서술 -> ex) 모든 User list를 반환한다.
	 * 
	 *  
	 * 
	 *  @return List<User>  -> 설명용 어노테이션
	 *  
	 */
	@GetMapping // 'GET' http://localhost:8085/users 요청 시 호출되는 메서드(핸들러)
	public List<User.Response> getUsers() {
		System.out.println("getUsers 호출");
		
//		return new User().builder().id("Moon").gender(Gender.MALE).build();
		
		List<User> users = userService.findAllUsers();
		List<User.Response> response = User.Response.toResponseList(users);
		return response;
	}
	
	
	/**
	 * id에 해당하는 단일 user 정보를 User entity 타입으로 반환한다.
	 * 
	 * @param id - 조회하고자 하는 User의 id
	 * @return User - 조회된 User Entity 객체
	 */
	
	@GetMapping("/{id}") // 'GET' localhost:8085/users/mooonoe의 정보를 찾는 메소드
	public User.Response getUser(@PathVariable String id) {
		//TODO: id 값 null check 이따하기
		User foundUser = userService.findUserByID(id);
		
		return User.Response.toResponse(foundUser);
		
	}
	
	
	/**
	 * 
	 * 새로운 User를 등록한다.
	 * @param user -> user 객체
	 * @author Moon
	 * @return User().builder().id()
	 */
	
	@PostMapping
	public ResponseEntity<User.Response> createUser(@RequestBody @Valid User.Request request) {
		//createUser 메서드 호출 시켜서 Postman으로 입력받은 값을 출력
		System.out.println(request);
		
//		convert user request(dto) -> user entity
		User user = User.Request.toEntity(request);
		
		System.out.println(request.getAddress());
		
		Address address = request.getAddress();
		// save user to db
		User sevedUser = userService.saveUser(user);
		
		address.setUser(sevedUser);
		addressRepository.save(address);
		
//		return User.Response.toResponse(sevedUser); //responseDTO로 반환하는 방식
		
		User.Response response = User.Response.toResponse(sevedUser); //ResponseEntity로 반환하는 방식
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}
	
	@PostMapping("auth/sign-in")
	public ResponseEntity<User.Response> signIn(@RequestBody User.Request request) {
		// 프론트에서 보낸 id, password를 eclipse 콘솔에 출력되로록
		
		//로그인된 유저 확인
		User loninUser = userService.loginUser(request);
		
		//로그인된 유저에 대한 토큰 생성
		String token = jwtTokenGenerator.generateToken(loninUser);
		System.out.println(token);
		
		//클라이언트에 응답할 데이터로 변환
		User.Response response = User.Response.toResponse(loninUser);
		
		response.setToken(token);
		
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	
	
	@PutMapping
	public List<User.Response> updateUser(@RequestBody User.Request request){
		List<User> users = userService.updateUser(request);
		return User.Response.toResponseList(users); // DTO로 변환 후 반환
	}
	
	@DeleteMapping
	public List<User.Response> deleteUser(@RequestParam("id") String id){
		List<User> users = userService.deleteUser(id);
		return User.Response.toResponseList(users);
	   
}
}
