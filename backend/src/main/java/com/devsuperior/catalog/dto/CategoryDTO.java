package com.devsuperior.catalog.dto;

import java.io.Serializable;

import com.devsuperior.catalog.entities.Category;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Long id;
	
	private String name;
	
	public CategoryDTO (Category category) {
		this.id = category.getId();
		this.name = category.getName();
	}
}
