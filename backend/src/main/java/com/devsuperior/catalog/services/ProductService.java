package com.devsuperior.catalog.services;

import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.catalog.dto.CategoryDTO;
import com.devsuperior.catalog.dto.ProductDTO;
import com.devsuperior.catalog.entities.Category;
import com.devsuperior.catalog.entities.Product;
import com.devsuperior.catalog.repositories.CategoryRepository;
import com.devsuperior.catalog.repositories.ProductRepository;
import com.devsuperior.catalog.services.exceptions.DatabaseException;
import com.devsuperior.catalog.services.exceptions.ResourceNotFoundException;

@Service
public class ProductService {

	@Autowired
	private ProductRepository repository;
	
	@Autowired
	private CategoryRepository categoryRepository;

	@Transactional(readOnly = true)
	public Page<ProductDTO> findAllPaged(PageRequest pageRequest) {
		Page<Product> list = repository.findAll(pageRequest);
		return list.map(x -> new ProductDTO(x));

	}

	@Transactional(readOnly = true)
	public ProductDTO findById(Long id) {
		Optional<Product> obj = repository.findById(id);
		Product product = obj.orElseThrow(() -> new ResourceNotFoundException("Entity not found"));
		return new ProductDTO(product, product.getCategories());

	}

	@Transactional
	public ProductDTO insert(ProductDTO prodDto) {
		Product product = new Product();
		copyDtoToProduct(prodDto, product);
		product = repository.save(product);
		return new ProductDTO(product);
	}

	@Transactional
	public ProductDTO update(Long id, ProductDTO prodDto) {
		try {
			Product product = repository.getById(id);
			copyDtoToProduct(prodDto, product);
			product= repository.save(product);
			return new ProductDTO(product);
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException("Id not found" + id);
		}
	}

	public void delete(Long id) {
		try {
		    repository.deleteById(id);
		}
		catch(EmptyResultDataAccessException e) {
			 throw new ResourceNotFoundException("Id not found" + id);
		}
		catch (DataIntegrityViolationException e) {
			throw new DatabaseException("integrity violation");
		}
	}
	
	private void copyDtoToProduct(ProductDTO prodDto, Product product) {
		
		product.setName(prodDto.getName());
		product.setDescription(prodDto.getDescription());
		product.setDate(prodDto.getDate());
		product.setImgUrl(prodDto.getImgUrl());
		product.setPrice(prodDto.getPrice());
		
		product.getCategories().clear();
		for (CategoryDTO catDto : prodDto.getCategories()) {
		    Category category = categoryRepository.getById(catDto.getId());
		    product.getCategories().add(category);
		
		}
	}

}
