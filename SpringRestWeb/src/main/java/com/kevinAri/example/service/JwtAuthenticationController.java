package com.kevinAri.example.service;

import com.kevinAri.example.config.security.JwtTokenUtil;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.Objects;


@RestController
@CrossOrigin
public class JwtAuthenticationController {
	private static class JwtResponse implements Serializable {
		private final String jwttoken;

		public JwtResponse(String jwttoken) {
			this.jwttoken = jwttoken;
		}

		public String getToken() {
			return this.jwttoken;
		}
	}
	@Setter
	@Getter
	public static class JwtRequest implements Serializable {
		private String username;
		private String password;

		//need default constructor for JSON Parsing
		public JwtRequest() {
		}

		public JwtRequest(String username, String password) {
			this.setUsername(username);
			this.setPassword(password);
		}
	}

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Qualifier("jwtUserDetailsService")
	@Autowired
	private UserDetailsService jwtUserDetailsService;

	@RequestMapping(value = "/authenticate", method = RequestMethod.POST)
	public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest)
			throws Exception {

		authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());

		final UserDetails userDetails = jwtUserDetailsService
				.loadUserByUsername(authenticationRequest.getUsername());
		final String token = jwtTokenUtil.generateToken(userDetails);

		System.out.println(jwtTokenUtil.getUsernameFromToken(token));
		System.out.println(jwtTokenUtil.getIssuedAtDateFromToken(token));
		System.out.println(jwtTokenUtil.getExpirationDateFromToken(token));
		System.out.println(authenticationRequest.getPassword());

		return ResponseEntity.ok(new JwtResponse(token));
	}

	private void authenticate(String username, String password) throws Exception {
		Objects.requireNonNull(username);
		Objects.requireNonNull(password);

		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		} catch (DisabledException e) {
			System.out.println("e1");
			throw new Exception("USER_DISABLED", e);
		} catch (BadCredentialsException e) {
			System.out.println("e2");
			throw new Exception("INVALID_CREDENTIALS", e);
		}
	}
}
