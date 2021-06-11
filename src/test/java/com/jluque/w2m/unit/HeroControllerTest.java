package com.jluque.w2m.unit;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.jluque.w2m.controller.HeroController;
import com.jluque.w2m.dto.HeroResponse;
import com.jluque.w2m.entity.HeroEntity;
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

	private MockMvc mockMvc;

	@BeforeEach
	void setup() {
		MockitoAnnotations.openMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(heroController).build();
	}

	@Test
	void getAll200Test() throws Exception {
		Optional<HeroEntity> heroEntityA = Optional.ofNullable(new HeroEntity());
		heroEntityA.get().setName("Spiderman");
		List<HeroEntity> heroEntityList = new ArrayList<>();
		heroEntityList.add(heroEntityA.get());

		List<HeroResponse> responseList = heroEntityList.stream().map(HeroMapper::heroEntityToDto)
				.collect(Collectors.toList());

		when(service.findAll()).thenReturn(responseList);
		mockMvc.perform(get("/superheros/").contentType(MediaType.APPLICATION_JSON_VALUE)
				.accept(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isOk());
	}

	@Test
	void getAll404Test() throws Exception {
		List<HeroResponse> responseList = new ArrayList<>();
		when(service.findAll()).thenReturn(responseList);
		mockMvc.perform(get("/superheros/").contentType(MediaType.APPLICATION_JSON_VALUE)
				.accept(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isNotFound());
	}

	@Test
	void getAll500Test() throws Exception {
		when(service.findAll()).thenThrow(Exception.class);
		mockMvc.perform(get("/superheros/").contentType(MediaType.APPLICATION_JSON_VALUE)
				.accept(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isInternalServerError());
	}

}
