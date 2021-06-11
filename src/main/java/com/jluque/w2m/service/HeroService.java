package com.jluque.w2m.service;

import java.util.List;

import com.jluque.w2m.dto.HeroRequest;
import com.jluque.w2m.dto.HeroResponse;

public interface HeroService {

	public List<HeroResponse> findAll() throws Exception;

	public HeroResponse findById(Integer id) throws Exception;

	public List<HeroResponse> findByName(String name) throws Exception;

	public void saveHero(HeroRequest heroRequest) throws Exception;

	public HeroResponse updateHero(Integer id, HeroRequest heroRequest);

	public void deleteHeroById(Integer id);
}
