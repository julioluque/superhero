package com.jluque.w2m.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.jluque.w2m.entity.HeroEntity;
import com.jluque.w2m.repository.HeroRepository;

@DataJpaTest
class HeroRepositoryIT {

	@Autowired
	private HeroRepository heroRepository;

	@Autowired
	private EntityManager entityManager;

	@BeforeEach
	void setup() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void findByName() {
		Optional<HeroEntity> heroEntity = Optional.ofNullable(new HeroEntity());
		heroEntity.get().setName("Thanos");
		heroEntity.get().setSaga("Marvel");
		heroEntity.get().setStatus(true);
		List<HeroEntity> heroEntityList = new ArrayList<>();
		heroEntityList.add(heroEntity.get());

		entityManager.persist(heroEntity.get());
		entityManager.flush();

		assertEquals(heroRepository.findByName("Thanos"), heroEntityList.get(0));

	}

	@Test
	void findById() {

		Optional<HeroEntity> heroEntity = Optional.ofNullable(new HeroEntity());
		heroEntity.get().setName("Pepe");
		heroEntity.get().setSaga("DC");
		heroEntity.get().setStatus(true);

		entityManager.persist(heroEntity.get());
		entityManager.flush();

		assertEquals(heroRepository.findById(5), heroEntity);

	}
}
