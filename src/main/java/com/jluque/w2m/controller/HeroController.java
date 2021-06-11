package com.jluque.w2m.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import com.jluque.w2m.service.HeroService;

import lombok.extern.log4j.Log4j2;

@RestController
@RequestMapping("/superheros")
@Log4j2
public class HeroController {

	private HeroService service;

	@Autowired
	public void setService(HeroService service) {
		this.service = service;
	}

	@GetMapping("/")
	public ResponseEntity<List<HeroResponse>> getAll() {
		List<HeroResponse> response = null;
		try {
			response = service.findAll();

			if (response.isEmpty()) {
				log.info("getAll: Not Found");
				return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
			}

		} catch (Exception e) {
			e.printStackTrace();
			log.info("getAll: Internal Server Error");
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		log.info("getAll: Success");
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<HeroResponse> getById(@PathVariable Integer id) {
		HeroResponse response = null;
		try {
			response = service.findById(id);

			if (response == null) {
				log.info("getById: Not Found");
				return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
			}

		} catch (Exception e) {
			e.printStackTrace();
			log.info("getById: Internal Server Error");
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		log.info("getById: Success");
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@GetMapping()
	public ResponseEntity<List<HeroResponse>> findByName(@RequestParam String name) {
		List<HeroResponse> response = null;
		try {
			response = service.findByName(name);

			if (response.isEmpty()) {
				log.info("findByName: Not Found");
				return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
			}

		} catch (Exception e) {
			e.printStackTrace();
			log.info("findByName: Internal Server Error");
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		log.info("findByName: Success");
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@PostMapping()
	public ResponseEntity<String> saveHero(@RequestBody HeroRequest heroRequest) {
		try {
			service.saveHero(heroRequest);
			return new ResponseEntity<>("Created!", HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>("Error!", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping("/{id}")
	public ResponseEntity<HeroResponse> updateHero(@PathVariable Integer id, @RequestBody HeroRequest heroRequest) {
		HeroResponse heroResponse = null;
		try {
			heroResponse = service.updateHero(id, heroRequest);
			if (heroResponse == null) {
				log.info("updateHero: Not Found");
				return new ResponseEntity<>(heroResponse, HttpStatus.NOT_FOUND);
			}

		} catch (Exception e) {
			log.info("updateHero: Internal Server Error");
			return new ResponseEntity<>(heroResponse, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		log.info("updateHero: Success");
		return new ResponseEntity<>(heroResponse, HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteById(@PathVariable Integer id) {
		try {
			service.deleteHeroById(id);
		} catch (Exception e) {
			log.info("deleteById: Internal Server Error");
			return new ResponseEntity<>("deleteById: Error", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		log.info("deleteById: Success");
		return new ResponseEntity<>("deleteById: deleted", HttpStatus.OK);
	}

}
