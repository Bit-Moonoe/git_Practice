package dev.bank.bankstatement.jwt;

import java.util.Date;

import org.springframework.stereotype.Component;

import dev.bank.bankstatement.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtTokenGenerator {

	private static final long JWT_TOKEN_VALIDITY = 5 * 60 * 60;

	// 토큰 생성
	public String generateToken(User user) {
		return Jwts.builder() // Jwts 찾아보자. jwt = JSON web Token
				.setHeaderParam(Header.TYPE, Header.JWT_TYPE).setIssuer("토근 발급자")
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000))
				.claim("id", user.getId()) // 비공개 클레임
				.signWith(SignatureAlgorithm.HS256, "secret").compact();
	};

	// 토큰 파싱
	// authHeader: Bearer fiffqiofqfoi... 이런식으로 토근이 돼있음.
	public Claims parseToken(String authHeader) {
		// 인증 헤더의 유효성 체크
		if (authHeader == null || !authHeader.startsWith("Bearer "))
			throw new IllegalArgumentException();

		// 토큰 추출
		String token = authHeader.substring("Bearer ".length());
		return Jwts.parser().setSigningKey("secret").parseClaimsJws(token).getBody();

	};

}
