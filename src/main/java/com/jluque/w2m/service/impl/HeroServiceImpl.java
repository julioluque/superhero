package com.jluque.w2m.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jluque.w2m.dto.HeroRequest;
import com.jluque.w2m.dto.HeroResponse;
import com.jluque.w2m.entity.HeroEntity;
import com.jluque.w2m.mapper.HeroMapper;
import com.jluque.w2m.repository.HeroRepository;
import com.jluque.w2m.service.HeroService;

@Service
public class HeroServiceImpl implements HeroService {

	private HeroRepository repository;

	@Autowired
	public void setRepository(HeroRepository repository) {
		this.repository = repository;
	}

	@Override
	public List<HeroResponse> findAll() throws Exception {
		return repository.findAll().stream().map(HeroMapper::heroEntityToDto).collect(Collectors.toList());
	}

	@Override
	public HeroResponse findById(Integer id) throws Exception {
		HeroResponse response = null;
		Optional<HeroEntity> heroEntity = repository.findById(id);
		if (heroEntity.isPresent()) {
			response = HeroMapper.heroEntityToDto(heroEntity.get());
		}
		return response;
	}

	@Override
	public List<HeroResponse> findByName(String name) throws Exception {
		return repository.findByName(name).stream().map(HeroMapper::heroEntityToDto).collect(Collectors.toList());

	}

	@Override
	public void saveHero(HeroRequest heroRequest) throws Exception {
		HeroEntity heroEntity = HeroMapper.heroDtoToEntity(heroRequest);
		heroEntity.setStatus(true);
		repository.save(heroEntity);
	}

	@Override
	public HeroResponse updateHero(Integer id, HeroRequest heroRequest) {
		Optional<HeroEntity> heroEntity = repository.findById(id);
		if (heroEntity.isPresent()) {
			heroEntity.get().setName(heroRequest.getName());
			heroEntity.get().setSaga(heroRequest.getSaga());
			heroEntity.get().setStatus(true);
			repository.save(heroEntity.get());
		}
		return HeroMapper.heroEntityToDto(heroEntity.get());
	}

	@Override
	public void deleteHeroById(Integer id) {
		repository.deleteById(id);
	}

}
