package com.jluque.w2m.integration;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jluque.w2m.controller.HeroController;
import com.jluque.w2m.dto.HeroRequest;
import com.jluque.w2m.dto.HeroResponse;
import com.jluque.w2m.entity.HeroEntity;
import com.jluque.w2m.mapper.HeroMapper;
import com.jluque.w2m.repository.HeroRepository;
import com.jluque.w2m.service.impl.HeroServiceImpl;

class SpringContextIT extends BaseIT {

	@InjectMocks
	private HeroController controller;

	@MockBean
	private HeroServiceImpl service;

	@MockBean
	private HeroRepository heroRepository;

	@BeforeEach
	void init() {
		MockitoAnnotations.openMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
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
		mockMvc.perform(get("/superheros/").contentType(MediaType.APPLICATION_JSON_VALUE)
				.accept(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isOk())
				.andExpect(jsonPath("$[0].name", is("Robot")));
	}

	@Test
	void getById200Test() throws Exception {
		Optional<HeroEntity> heroEntityA = Optional.ofNullable(new HeroEntity());
		heroEntityA.get().setName("Robot");
		HeroResponse heroResponse = HeroMapper.heroEntityToDto(heroEntityA.get());

		when(service.findById(anyInt())).thenReturn(heroResponse);

		Integer id = 5;
		mockMvc.perform(get("/superheros/{id}", id).contentType(MediaType.APPLICATION_JSON_VALUE)
				.accept(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isOk());
	}

	@Test
	void getByName200Test() throws Exception {

		Optional<HeroEntity> heroEntityA = Optional.ofNullable(new HeroEntity());
		heroEntityA.get().setName("Robot");
		HeroResponse heroResponse = HeroMapper.heroEntityToDto(heroEntityA.get());

		when(service.findById(anyInt())).thenReturn(heroResponse);
		String name = "Robot";
		mockMvc.perform(get("/superheros?name={name}", name).contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(name)).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}

	@Test
	void saveTest() throws Exception {
		HeroRequest heroRequest = new HeroRequest();
		heroRequest.setName("Hulk");
		heroRequest.setSaga("Marvel");

		service.saveHero(heroRequest);
		verify(service, times(1)).saveHero(heroRequest);

		mockMvc.perform(post("/superheros?", heroRequest).contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(heroRequest)).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated());
	}

	@Test
	void updateTest() throws Exception {
		Integer id = 1;
		HeroResponse heroResponse = new HeroResponse();
		heroResponse.setSaga("nuevo");

		when(service.updateHero(anyInt(), any())).thenReturn(heroResponse);

		mockMvc.perform(put("/superheros/{id}", id, heroResponse).contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(id))
				.content(new ObjectMapper().writeValueAsString(heroResponse)).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}

	@Test
	void deleteTest() throws Exception {
		Integer id = 1;
		service.deleteHeroById(id);
		verify(service, times(1)).deleteHeroById(id);

		mockMvc.perform(delete("/superheros/{id}", id).contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(id)).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}

}
