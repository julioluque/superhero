package com.jluque.w2m.service.impl;

import java.util.Date;

import org.springframework.stereotype.Service;

import com.jluque.w2m.dto.LoginRequest;
import com.jluque.w2m.dto.LoginResponse;
import com.jluque.w2m.exception.custom.UnauthorizedCustomException;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class LoginService {

	public LoginResponse validar(LoginRequest usuario) {

		if (!validateCredentials(usuario))
			throw new UnauthorizedCustomException("Credenciales incorrectas");

		return LoginResponse.builder().token(getJwtToken()).build();
	}

	// TODO centralizar con auth0 y pasar datos a DB
	private static boolean validateCredentials(LoginRequest usuario) {
		return (usuario.getUsername().equals("jluque") && usuario.getPassword().equals("1208"));
	}

	private String getJwtToken() {
		String key = "abcd";
		String subject = "Julio Alfredo Luque";
		long tiempo = System.currentTimeMillis();
		Date dateAt = new Date(tiempo);
		Date dateExpiration = new Date(tiempo + 900000);

		return Jwts.builder().signWith(SignatureAlgorithm.HS256, key).setSubject(subject).setIssuedAt(dateAt)
				.setExpiration(dateExpiration).claim("email", "julio.a.luque@gmail.com").compact();
	}
}
