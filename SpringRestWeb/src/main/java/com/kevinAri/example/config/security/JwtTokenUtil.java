package com.kevinAri.example.config.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtTokenUtil implements Serializable {

	private static final long serialVersionUID = -2550185165626007488L;

	public static final long JWT_TOKEN_VALIDITY = 2*60;

	@Value("${spring.jwt.secret}")
	private String secret;

	public String getUsernameFromToken(String token) {
		return getClaimFromToken(token, Claims::getSubject);
	}

	public Date getIssuedAtDateFromToken(String token) {
		return getClaimFromToken(token, Claims::getIssuedAt);
	}

	public Date getExpirationDateFromToken(String token) {
		return getClaimFromToken(token, Claims::getExpiration);
	}

	private <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = getAllClaimsFromToken(token);
		return claimsResolver.apply(claims);
	}

	private Claims getAllClaimsFromToken(String token) {
		return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
	}

	private Boolean isTokenExpired(String token) {
		final Date expiration = getExpirationDateFromToken(token);
		return expiration.before(new Date());
	}

	public Boolean ignoreTokenExpiration(String token) {
		// here you specify tokens, for that the expiration is ignored
		if (token.equals("eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJqYXZhaW51c2UiLCJleHAiOjE2Njc0NzI4NzQsImlhdCI6MTY2NzQ3Mjc1NH0.-NMVbOnWLWU8_1QKuxZmhJk_SKAd5wl9smzn9k5wf_gD-qTK_4ZQikYFGsgSivY5DojfzxlY10UgFj3Zl2fFMA")) {
			return true;
		} else if (token.equals("eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJrZXZpbiIsImV4cCI6MTY2Nzg4NjY5MCwiaWF0IjoxNjY3ODg2NTcwfQ._SRlsfrskkhJ6bcuN02RqvCOsfW7DadIzcuKiKMWiPdKwrpOl_RzpEzg52_l60CldYpobHyPeQkZYh_xLYmzRw")) {
			return true;
		} else {
			return false;
		}
	}

	public String generateToken(UserDetails userDetails) {
		Map<String, Object> claims = new HashMap<>();
		return doGenerateToken(claims, userDetails.getUsername());
	}

	private String doGenerateToken(Map<String, Object> claims, String subject) {

		return Jwts.builder().setClaims(claims)
				.setSubject(subject)
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY*1000))
				.signWith(SignatureAlgorithm.HS512, secret).compact();
	}

	public Boolean canTokenBeRefreshed(String token) {
		return (!isTokenExpired(token) || ignoreTokenExpiration(token));
	}

	public Boolean validateToken(String token, UserDetails userDetails) {
//		System.out.println(ignoreTokenExpiration(token));
		if (ignoreTokenExpiration(token)) {
			return true;
		} else {
			final String username = getUsernameFromToken(token);
			return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
		}
	}
}
