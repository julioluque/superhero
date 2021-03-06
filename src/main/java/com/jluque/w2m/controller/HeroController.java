package com.jluque.w2m.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jluque.w2m.dto.HeroRequest;
import com.jluque.w2m.dto.HeroResponse;
import com.jluque.w2m.exception.custom.BadRequestCustomException;
import com.jluque.w2m.service.HeroService;
import com.jluque.w2m.utils.anotation.CustomRequestTimed;

@PreAuthorize("authenticated")
@RestController
@RequestMapping("/superheros")

public class HeroController {

	private HeroService service;

	@Autowired
	public void setService(HeroService service) {
		this.service = service;
	}

	@CustomRequestTimed
	@GetMapping("/")
	public ResponseEntity<List<HeroResponse>> getAll() throws Exception {
		List<HeroResponse> response = service.findAll();
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@CustomRequestTimed
	@GetMapping("/{id}")
	public ResponseEntity<HeroResponse> getById(@PathVariable Integer id) throws Exception {
		HeroResponse response = service.findById(id);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@CustomRequestTimed
	@GetMapping()
	@PreAuthorize("hasRole('USER') OR hasRole('MANAGER')")
	public ResponseEntity<List<HeroResponse>> findByName(@RequestParam String name) throws Exception {
		if (name.isEmpty())
			throw new BadRequestCustomException("Name empty");
		List<HeroResponse> response = service.findByName(name);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@CustomRequestTimed
	@PostMapping()
	@PreAuthorize("hasRole('MANAGER')")
	public ResponseEntity<String> saveHero(@RequestBody HeroRequest heroRequest) throws Exception {
		service.saveHero(heroRequest);
		return new ResponseEntity<>("Created!", HttpStatus.CREATED);
	}

	@CustomRequestTimed
	@PutMapping("/{id}")
	@PreAuthorize("hasRole('MANAGER')")
	public ResponseEntity<HeroResponse> updateHero(@PathVariable Integer id, @RequestBody HeroRequest heroRequest) {
		HeroResponse heroResponse = service.updateHero(id, heroRequest);
		return new ResponseEntity<>(heroResponse, HttpStatus.OK);
	}

	@CustomRequestTimed
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteById(@PathVariable Integer id) {
		service.deleteHeroById(id);
		return new ResponseEntity<>("Deleted", HttpStatus.OK);
	}

}
