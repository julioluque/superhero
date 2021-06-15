package com.jluque.w2m.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class HeroResponse implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;
	private String name;
	private String saga;
	private Boolean status;
}
