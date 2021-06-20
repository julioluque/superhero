package com.jluque.w2m.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jluque.w2m.dto.LoginRequest;
import com.jluque.w2m.dto.LoginResponse;
import com.jluque.w2m.service.impl.LoginService;
import com.jluque.w2m.utils.anotation.CustomRequestTimed;

@RestController
@RequestMapping("/auth")
public class LoginController {

	@Autowired
	private LoginService service;

	@CustomRequestTimed
	@GetMapping()
	public ResponseEntity<String> echotest() {
		return new ResponseEntity<>("Get auth OK!", HttpStatus.OK);
	}

	@CustomRequestTimed
	@PostMapping()
	public ResponseEntity<LoginResponse> generateJwt(@RequestBody LoginRequest usuario) throws Exception {
		LoginResponse response = service.validar(usuario);
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}
}
