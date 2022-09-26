package dev.bank.bankstatement.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor @AllArgsConstructor
@Getter @Setter
@Builder
@Entity
public class Address {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ADDRESS_ID")
	private int id;
	
	@Column(nullable = false)
	private String mainAddress; // 메인 주소
	
	@Column(nullable = false)
	private String addressDetail; // 상세주소
	
	@Column(nullable = false)
	private String postalCode; // 우편번호
	
	@Column(nullable = false)
	private String country; // 국적
	
	@ManyToOne
	@JoinColumn(name = "USER_ID")
	private User user;
	
	public void setUser(User user) {
		this.user = user;
		user.getAddresses().add(this);
	}
	
	
	@Builder
	@NoArgsConstructor @AllArgsConstructor
	@Getter @Setter
	public static class Response{
		private String mainAddress;
		private String addressDetail;
		private String postalCode;
		private String country;
		
		public static Response toResponse(Address address) {
			return Response.builder()
					.mainAddress(address.getMainAddress())
					.addressDetail(address.getAddressDetail())
					.postalCode(address.getPostalCode())
					.country(address.getCountry())
					.build();
		}
		
		public static List<Address.Response> toResponseList(final List<Address> addresses){
			List<Address.Response> list = new ArrayList<>();
			for (Address address  : addresses) {
				list.add(toResponse(address));
			}
			
			return list;
		}
	}
}
