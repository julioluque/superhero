package com.jluque.w2m.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HeroRequest implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;
	private String name;
	private String saga;
}
