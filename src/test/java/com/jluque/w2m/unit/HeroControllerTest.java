package com.jluque.w2m.unit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.jluque.w2m.controller.HeroController;
import com.jluque.w2m.dto.HeroRequest;
import com.jluque.w2m.dto.HeroResponse;
import com.jluque.w2m.entity.HeroEntity;
import com.jluque.w2m.exception.custom.BadRequestCustomException;
import com.jluque.w2m.exception.custom.NotFoundCustomException;
import com.jluque.w2m.mapper.HeroMapper;
import com.jluque.w2m.repository.HeroRepository;
import com.jluque.w2m.service.impl.HeroServiceImpl;

class HeroControllerTest {

	@InjectMocks
	private HeroController heroController;

	@Mock
	private HeroServiceImpl service;

	@Mock
	private HeroRepository repository;

	@BeforeEach
	void setup() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void getAll200Test() throws Exception {
		Optional<HeroEntity> heroEntityA = Optional.ofNullable(new HeroEntity());
		heroEntityA.get().setName("Robot");
		List<HeroEntity> heroEntityList = new ArrayList<>();
		heroEntityList.add(heroEntityA.get());

		List<HeroResponse> responseList = heroEntityList.stream().map(HeroMapper::heroEntityToDto)
				.collect(Collectors.toList());
		when(service.findAll()).thenReturn(responseList);

		String expected = "Robot";
		assertEquals(heroController.getAll().getBody().get(0).getName(), expected);
	}

	@Test
	void getAll404Test() throws Exception {
		when(repository.findAll()).thenReturn(Collections.emptyList());
		when(service.findAll()).thenThrow(NotFoundCustomException.class);
		assertThrows(NotFoundCustomException.class, () -> service.findAll());
	}

	@Test
	void getAll500Test() throws Exception {
		when(service.findAll()).thenThrow(RuntimeException.class);
		assertThrows(Exception.class, () -> service.findAll());
	}

	@Test
	void getById200Test() throws Exception {

		Optional<HeroEntity> heroEntityA = Optional.ofNullable(new HeroEntity());
		heroEntityA.get().setName("Robot");
		HeroResponse heroResponse = HeroMapper.heroEntityToDto(heroEntityA.get());

		when(service.findById(anyInt())).thenReturn(heroResponse);
		Integer id = 1;
		String expected = "Robot";
		assertEquals(heroController.getById(id).getBody().getName(), expected);
	}

	@Test
	void getByName400Test() throws Exception {
		when(service.findById(anyInt())).thenThrow(BadRequestCustomException.class);
		assertThrows(BadRequestCustomException.class, () -> heroController.findByName(""));
	}

	@Test
	void saveTest() throws Exception {
		HeroRequest heroRequest = new HeroRequest();
		heroRequest.setName("Hulk");
		heroRequest.setSaga("Marvel");

		service.saveHero(heroRequest);
		verify(service, times(1)).saveHero(heroRequest);

		String expected = "Created!";
		assertEquals(heroController.saveHero(heroRequest).getBody(), expected);
	}

	@Test
	void updateTest() throws Exception {
		Integer id = 1;
		HeroRequest heroRequest = new HeroRequest();
		heroRequest.setSaga("nuevo");

		HeroResponse heroResponse = new HeroResponse();
		heroRequest.setName("Hulk");
		heroResponse.setSaga("nuevo");

		when(service.updateHero(anyInt(), any())).thenReturn(heroResponse);

		String expected = "nuevo";
		assertEquals(heroController.updateHero(id, heroRequest).getBody().getSaga(), expected);
	}

	@Test
	void deleteTest() throws Exception {
		Integer id = 1;
		service.deleteHeroById(id);
		verify(service, times(1)).deleteHeroById(id);
		String expected = "Deleted";
		assertEquals(heroController.deleteById(id).getBody(), expected);

	}
}
