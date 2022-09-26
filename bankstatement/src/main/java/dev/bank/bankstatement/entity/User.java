package dev.bank.bankstatement.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

//jpa
@Entity
@Table(name="USERS") // 테이블이 USERS라는 이름으로 생성되도록 설정, 'USER'는 일반적으로 DBMS 예약어로 사용되기 때문에
//lombbok
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class User {
	
	@Id
	@Column(name="USER_ID")
	private String id;
	
	@Column(nullable=false)
	private String password;
	
	@Column(nullable=false)
	private String name;
	
	
	@Enumerated(EnumType.STRING)
	private Gender gender; //별도의 클래스 Gender , enum type
	
	
	@OneToMany(mappedBy = "user")
	private List<Address> addresses;


	@Override
	public String toString() {
		return "User [id=" + id + ", password=" + password + ", name=" + name + ", gender=" + gender + "]";
	}
	
	
	// 요청 받을 때 사용할 User Entity의 DTO
	@Setter @Getter
	@Builder
	@ToString
	public static class Request{
		@NotBlank(message = "id는 공백('',' ')이나 NULL 지정 불가")
		private String id;
		@NotBlank(message = "password는 공백('',' ')이나 NULL 지정 불가")
		private String password;
		@NotBlank(message = "name은 공백('',' ')이나 NULL 지정 불가")
		private String name;
		private Gender gender;
		
		private Address address;
		
		public static User toEntity(final Request request) {
			return User.builder().id(request.getId())
					.password(request.getPassword())
					.name(request.getName())
					.gender(request.getGender())
					.addresses(new ArrayList<>())
					.build();
		}
		
	}
	
	// 서버가 응답할 때 사용할 User Entity의 DTO
	@Setter @Getter
	@Builder
	@ToString
	public static class Response{
		private String id;
		private String name;
		private Gender gender;
		private List<Address.Response> addresses;
		private String token;
		
		public static User.Response toResponse(final User user){
			return Response.builder()
					.id(user.getId())
					.name(user.getName())
					.gender(user.getGender())
					.addresses(Address.Response.toResponseList(user.getAddresses()))
					.build();
		}
		
		public static List<User.Response> toResponseList(final List<User> users){
			List<User.Response> list = new ArrayList<>();
			for (User user : users) {
				list.add(toResponse(user));
			}
			return list;
		}
	}
	
	
}
