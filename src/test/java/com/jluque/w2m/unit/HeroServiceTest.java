package com.jluque.w2m.unit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.jluque.w2m.dto.HeroRequest;
import com.jluque.w2m.entity.HeroEntity;
import com.jluque.w2m.exception.custom.FieldExistCustomException;
import com.jluque.w2m.exception.custom.NotFoundCustomException;
import com.jluque.w2m.mapper.HeroMapper;
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
	void findByAllEmptyTest() throws Exception {
		List<HeroEntity> emptyList = new ArrayList<>();
		when(repository.findAll()).thenReturn(emptyList);
		assertThrows(NotFoundCustomException.class, () -> heroService.findAll());
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
		assertThrows(NotFoundCustomException.class, () -> heroService.findById(inputId));
	}

	// ---------------------
	@Test
	void getByIdSuccessTest() throws Exception {

		Optional<HeroEntity> heroEntity = Optional.ofNullable(new HeroEntity());
		heroEntity.get().setName("Robot");

		when(repository.findById(anyInt())).thenReturn(heroEntity);
		Integer id = 1;
		String expected = "Robot";
		assertEquals(heroService.findById(id).getName(), expected);
	}

	@Test
	void getByNameSuccessTest() throws Exception {
		Optional<HeroEntity> heroEntity = Optional.ofNullable(new HeroEntity());
		heroEntity.get().setName("Robot");

		List<HeroEntity> heroEntityList = new ArrayList<>();
		heroEntityList.add(heroEntity.get());

		when(repository.findContainByName(anyString())).thenReturn(heroEntityList);

		String name = "bot";
		String expected = "Robot";
		assertEquals(heroService.findByName(name).get(0).getName(), expected);

	}

	@Test
	void saveSuccessTest() throws Exception {
		HeroRequest heroRequest = new HeroRequest();
		heroRequest.setName("Hulk");
		heroRequest.setSaga("Marvel");

		HeroEntity heroEntity = HeroMapper.heroDtoToEntity(heroRequest);

		repository.save(heroEntity);
		verify(repository, times(1)).save(heroEntity);

		heroService.saveHero(heroRequest);
	}

	@Test
	void saveExceptionTest() throws Exception {
		HeroRequest heroRequest = new HeroRequest();
		heroRequest.setName("Hulk");
		heroRequest.setSaga("Marvel");

		HeroEntity heroEntityDB = new HeroEntity();
		heroEntityDB.setName("Hulk");
		when(repository.findByName(anyString())).thenReturn(heroEntityDB);

		assertThrows(FieldExistCustomException.class, () -> heroService.saveHero(heroRequest));
	}

	@Test
	void updateSuccessTest() throws Exception {

		HeroRequest heroRequest = new HeroRequest();
		heroRequest.setName("Robot");
		heroRequest.setSaga("nuevo");

		Optional<HeroEntity> heroEntity = Optional.ofNullable(new HeroEntity());
		heroEntity.get().setName("Robot");
		when(repository.findById(anyInt())).thenReturn(heroEntity);

		heroEntity.get().setSaga("nuevo");

		repository.save(heroEntity.get());
		verify(repository, times(1)).save(heroEntity.get());

		Integer id = 1;
		String expected = "nuevo";
		assertEquals(heroService.updateHero(id, heroRequest).getSaga(), expected);
	}

	@Test
	void deleteSuccessTest() throws Exception {
		Integer id = 1;
		repository.deleteById(id);
		verify(repository, times(1)).deleteById(id);
		heroService.deleteHeroById(id);
	}
}
