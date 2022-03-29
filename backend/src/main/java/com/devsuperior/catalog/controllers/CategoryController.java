package com.devsuperior.catalog.controllers;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

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
		List<CategoryDTO> list = service.findAll();
		return ResponseEntity.ok().body(list);
	}
	

	@GetMapping(value = "/{id}")
	public ResponseEntity<CategoryDTO>findById(@PathVariable Long id) {
		CategoryDTO catDto = service.findById(id);
		return ResponseEntity.ok().body(catDto);
	}
	
	@PostMapping
	public ResponseEntity<CategoryDTO> insert(@RequestBody CategoryDTO catDto) {
		catDto = service.insert(catDto);
		URI uri = ServletUriComponentsBuilder
				.fromCurrentRequest().path("/{id}")
				.buildAndExpand(catDto.getId())
				.toUri();
		return ResponseEntity.created(uri).body(catDto);
	}
	
}
