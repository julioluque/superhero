package com.jluque.w2m.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.jluque.w2m.entity.HeroEntity;

@Repository
public interface HeroRepository extends JpaRepository<HeroEntity, Integer> {

	@Query(value = "select * from HERO H where LOWER (H.nombre) like %:name%", nativeQuery = true)
	public List<HeroEntity> findContainByName(@Param("name") String name);

	public HeroEntity findByName(String name);
}
