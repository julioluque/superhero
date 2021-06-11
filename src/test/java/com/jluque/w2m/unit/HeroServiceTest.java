package com.jluque.w2m.unit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.jluque.w2m.entity.HeroEntity;
import com.jluque.w2m.repository.HeroRepository;
import com.jluque.w2m.service.impl.HeroServiceImpl;

class HeroServiceTest {

	@InjectMocks
	private HeroServiceImpl heroService;

	@Mock
	private HeroRepository repository;

	@BeforeEach
	void setup() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void findAllSuccessTest() throws Exception {
		Optional<HeroEntity> heroEntityA = Optional.ofNullable(new HeroEntity());
		heroEntityA.get().setName("Spiderman");
		heroEntityA.get().setSaga("Marvel");
		Optional<HeroEntity> heroEntityB = Optional.ofNullable(new HeroEntity());
		heroEntityB.get().setName("Superman");
		heroEntityB.get().setSaga("DC");
		Optional<HeroEntity> heroEntityC = Optional.ofNullable(new HeroEntity());
		heroEntityC.get().setName("Neo");
		heroEntityC.get().setSaga("Matrix");
		List<HeroEntity> heroEntityList = new ArrayList<>();
		heroEntityList.add(heroEntityA.get());
		heroEntityList.add(heroEntityB.get());
		heroEntityList.add(heroEntityC.get());

		when(repository.findAll()).thenReturn(heroEntityList);
		String expectedHero = "Neo";
		assertEquals(heroService.findAll().get(2).getName(), expectedHero);
	}

	@Test
	void findByIdSuccessTest() throws Exception {

		Integer inputId = 1;

		Optional<HeroEntity> heroEntity = Optional.ofNullable(new HeroEntity());
		heroEntity.get().setName("Spiderman");
		heroEntity.get().setSaga("Marvel");

		when(repository.findById(anyInt())).thenReturn(heroEntity);
		String expectedHero = "Spiderman";
		assertEquals(heroService.findById(inputId).getName(), expectedHero);
	}

	@Test
	void findByIdEmptyTest() throws Exception {
		Integer inputId = 1;
		when(repository.findById(anyInt())).thenReturn(Optional.empty());
		assertEquals(heroService.findById(inputId), null);
	}

}
