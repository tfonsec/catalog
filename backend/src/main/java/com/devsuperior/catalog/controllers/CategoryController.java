package com.devsuperior.catalog.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.devsuperior.catalog.dto.CategoryDTO;
import com.devsuperior.catalog.services.CategoryService;

@RestController
@RequestMapping(value = "/categories") 
public class CategoryController {
	
	@Autowired
	CategoryService service;
	//Response Entity é um objeto do Spring do tipo generic<>que vai encapsular uma resposta Http.
	@GetMapping
	public ResponseEntity<List<CategoryDTO>> findAll() {
		List<CategoryDTO> list = service.findaAll();
		return ResponseEntity.ok().body(list);
	}

}
