package dev.bank.bankstatement.jwt;

import java.io.IOException;
import java.util.Optional;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.Claims;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {
	
	private final JwtTokenGenerator jwtTokenGenerator;
	
	

	public JwtAuthFilter(JwtTokenGenerator jwtTokenGenerator) {
		this.jwtTokenGenerator = jwtTokenGenerator;
	}



	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		//클라이언트로부터 받은 정로를 추츨 및 검증
		Optional<String> authHeader = Optional.ofNullable(request.getHeader(HttpHeaders.AUTHORIZATION)); //OPtional을 통해 null값 여부 판독
		authHeader.ifPresent(header->{
			Claims claims = jwtTokenGenerator.parseToken(header);
			System.out.println("claims:" + claims);
		});
		filterChain.doFilter(request, response);
		
	}
	
	

}
