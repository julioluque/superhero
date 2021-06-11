package com.jluque.w2m.mapper;

import com.jluque.w2m.dto.HeroRequest;
import com.jluque.w2m.dto.HeroResponse;
import com.jluque.w2m.entity.HeroEntity;

public class HeroMapper {

	private HeroMapper() {
	}

	public static HeroEntity heroDtoToEntity(HeroRequest heroRequest) {
		return HeroEntity.builder().name(heroRequest.getName()).saga(heroRequest.getSaga()).build();
	}

	public static HeroResponse heroEntityToDto(HeroEntity heroEntity) {
		return HeroResponse.builder().id(heroEntity.getId()).name(heroEntity.getName()).saga(heroEntity.getSaga())
				.status(heroEntity.getStatus()).build();
	}
}
