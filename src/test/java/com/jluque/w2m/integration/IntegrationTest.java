package com.jluque.w2m.integration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.jluque.w2m.controller.HeroController;
import com.jluque.w2m.dto.HeroResponse;
import com.jluque.w2m.entity.HeroEntity;
import com.jluque.w2m.mapper.HeroMapper;
import com.jluque.w2m.repository.HeroRepository;
import com.jluque.w2m.service.impl.HeroServiceImpl;

class IntegrationTest extends BaseTest {

	@InjectMocks
	private HeroController controller; // = new HeroController();

	@MockBean
	private HeroServiceImpl service;
	@MockBean
	private HeroRepository heroRepository;

	@BeforeEach
	void init() {
//	void init() throws Exception {
		restTemplateBuilder = new RestTemplateBuilder().rootUri("http://localhost:8080");
		testRestTemplate = new TestRestTemplate(restTemplateBuilder);

//		Optional<HeroEntity> heroEntityA = Optional.ofNullable(new HeroEntity());
//		heroEntityA.get().setName("Robot");
//		List<HeroEntity> heroEntityList = new ArrayList<>();
//		heroEntityList.add(heroEntityA.get());
//		List<HeroResponse> responseList = heroEntityList.stream().map(HeroMapper::heroEntityToDto)
//				.collect(Collectors.toList());
//		when(service.findAll()).thenReturn(responseList);

		MockitoAnnotations.openMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(controller).build();

//		MockitoAnnotations.openMocks(this);
//		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).dispatchOptions(true).build();
//	    this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
	}

	@Test
	void findByIdRestTemplate() throws Exception {

		ResponseEntity<String> responseEntity = testRestTemplate.getForEntity("/superheros/1", String.class);
		String heros = responseEntity.getBody();
		System.out.println(heros);
		assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);

	}

	@Test
	void findById() throws Exception {

		Optional<HeroEntity> heroEntityA = Optional.ofNullable(new HeroEntity());
		heroEntityA.get().setName("Robot");
		HeroResponse heroResponse = HeroMapper.heroEntityToDto(heroEntityA.get());

		when(service.findById(anyInt())).thenReturn(heroResponse);

		Integer id = 5;
		mockMvc.perform(get("/superheros/{id}", id).contentType(MediaType.APPLICATION_JSON_VALUE)
				.accept(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isOk());

	}

}
