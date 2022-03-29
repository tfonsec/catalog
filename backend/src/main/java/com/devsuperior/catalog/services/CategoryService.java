package com.devsuperior.catalog.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.catalog.dto.CategoryDTO;
import com.devsuperior.catalog.entities.Category;
import com.devsuperior.catalog.repositories.CategoryRepository;
import com.devsuperior.catalog.services.exceptions.EntityNotFoundException;

@Service
public class CategoryService {
	
	@Autowired
	private CategoryRepository repository;
	
	@Transactional(readOnly = true)
	public List<CategoryDTO> findAll(){
	List<Category>list  = repository.findAll();
	return list.stream()
	.map(x -> new CategoryDTO(x))
	.collect(Collectors.toList());
		
	}
	
    @Transactional(readOnly = true)
	public CategoryDTO findById(Long id) {
	Optional <Category> obj= repository.findById(id);
	Category category = obj.orElseThrow(() -> new EntityNotFoundException("Entity not found"));
	return new CategoryDTO(category);
	
	}
    
    @Transactional
	public CategoryDTO insert(CategoryDTO catDto) {
		Category category = new Category();
		category.setName(catDto.getName());
		category = repository.save(category);
		return new CategoryDTO(category);
	}
    
    

}
