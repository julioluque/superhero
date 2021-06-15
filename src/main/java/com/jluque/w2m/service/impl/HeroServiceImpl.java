package com.jluque.w2m.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.jluque.w2m.dto.HeroRequest;
import com.jluque.w2m.dto.HeroResponse;
import com.jluque.w2m.entity.HeroEntity;
import com.jluque.w2m.exception.custom.FieldExistCustomException;
import com.jluque.w2m.exception.custom.NotFoundCustomException;
import com.jluque.w2m.mapper.HeroMapper;
import com.jluque.w2m.repository.HeroRepository;
import com.jluque.w2m.service.HeroService;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class HeroServiceImpl implements HeroService {

	private HeroRepository repository;

	@Autowired
	public void setRepository(HeroRepository repository) {
		this.repository = repository;
	}

	@Override
	@Cacheable("herocache")

	public List<HeroResponse> findAll() throws Exception {
		log.info("CACHING ALL");
		List<HeroEntity> heroEntity = repository.findAll();
		if (heroEntity.isEmpty())
			throw new NotFoundCustomException("");
		return heroEntity.stream().map(HeroMapper::heroEntityToDto).collect(Collectors.toList());
	}

	@Override
	@Cacheable(cacheNames = "herocache", key = "#id")
	public HeroResponse findById(Integer id) throws Exception {
		log.info("CACHING ID: " + id);
		Optional<HeroEntity> heroEntity = repository.findById(id);
		if (!heroEntity.isPresent())
			throw new NotFoundCustomException("ID: " + id);
		return HeroMapper.heroEntityToDto(heroEntity.get());
	}

	@Override
	@Cacheable(cacheNames = "herocache", key = "#name")
	public List<HeroResponse> findByName(String name) throws Exception {
		log.info("CACHING NAME: " + name);
		List<HeroEntity> heroEntity = repository.findContainByName(name);
		if (heroEntity.isEmpty())
			throw new NotFoundCustomException("Name: " + name);
		return heroEntity.stream().map(HeroMapper::heroEntityToDto).collect(Collectors.toList());
	}

	@Override
	@CacheEvict(value = "herocache", allEntries = true)
	public void saveHero(HeroRequest heroRequest) throws Exception {
		HeroEntity hero = repository.findByName(heroRequest.getName());
		if (hero != null)
			throw new FieldExistCustomException(heroRequest.getName());
		
		HeroEntity heroEntity = HeroMapper.heroDtoToEntity(heroRequest);
		heroEntity.setStatus(true);
		repository.save(heroEntity);
		log.info("CACHING SAVE: ");
	}

	@Override
	@CacheEvict(value = "herocache", allEntries = true)
	public HeroResponse updateHero(Integer id, HeroRequest heroRequest) {
		Optional<HeroEntity> heroEntity = repository.findById(id);
		if (heroEntity.isPresent()) {
			heroEntity.get().setSaga(heroRequest.getSaga());
			heroEntity.get().setStatus(true);
			repository.save(heroEntity.get());
		} else {
			throw new NotFoundCustomException("ID: " + id);
		}
		log.info("CACHING UPDATE: ");
		return HeroMapper.heroEntityToDto(heroEntity.get());
	}

	@Override
	@CacheEvict(value = "herocache", allEntries = true)
	public void deleteHeroById(Integer id) {
		repository.deleteById(id);
		log.info("CACHING DELETE ID: " + id);
	}

}
