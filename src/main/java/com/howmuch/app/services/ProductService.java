package com.howmuch.app.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.howmuch.app.dto.CategoryDTO;
import com.howmuch.app.dto.ProductCategoriesDTO;
import com.howmuch.app.dto.ProductDTO;
import com.howmuch.app.entities.Category;
import com.howmuch.app.entities.Product;
import com.howmuch.app.repositories.CategoryRepository;
import com.howmuch.app.repositories.ProductRepository;
import com.howmuch.app.services.exceptions.DatabaseException;
import com.howmuch.app.services.exceptions.ParamFormatException;
import com.howmuch.app.services.exceptions.ResourceNotFoundException;

@Service
public class ProductService {
	
	@Autowired
	private ProductRepository repository;
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	public Page<ProductDTO> findByNameCategoryPaged(String name, String categoriesStr, Pageable pageable) {
		Page<Product> list;
		
		if (categoriesStr.equals("")) {
			list = repository.findByNameContainingIgnoreCase(name, pageable);
		} else {
			List<Long> ids = parseIds(categoriesStr);
			List<Category> categories = ids.stream().map(id -> categoryRepository.getOne(id)).collect(Collectors.toList());
			list = repository.findByNameContainingIgnoreCaseAndCategoriesIn(name, categories, pageable);
		}
			
		return list.map(e -> new ProductDTO(e));
	}
	
	private List<Long> parseIds(String categoriesStr) {
		String[] idsArray = categoriesStr.split(",");
		List<Long> list = new ArrayList<>();
		
		for (String idStr : idsArray) {
			try {
				list.add(Long.parseLong(idStr));
			} catch (NumberFormatException e) {
				throw new ParamFormatException("Invalid categories format");
			}
		}
		
		return list;
	}

	public ProductDTO findById(Long id) {
		Optional<Product> obj = repository.findById(id);
		Product entity = obj.orElseThrow(() -> new ResourceNotFoundException(id));
		
		return new ProductDTO(entity);
	}
	
	public ProductDTO findByCodBar(String codBar) {
		Optional<Product> obj = repository.findByCodBar(codBar);
		Product entity = obj.orElseThrow(() -> new ResourceNotFoundException(codBar));
		
		return new ProductDTO(entity);
	}
	
	@Transactional
	public ProductDTO insert(ProductCategoriesDTO dto) {
		Product entity = dto.toEntity();
		setProductCategories(entity, dto.getCategories());
		entity = repository.save(entity);
		return new ProductDTO(entity);
	}
	
	@Transactional
	public ProductDTO update(Long id, ProductCategoriesDTO dto) {
		try {
			Product entity = repository.getOne(id);
			updateData(entity, dto);
			entity = repository.save(entity);
			return new ProductDTO(entity);
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException(id);
		}
	}
	
	public void delete(Long id) {
		try {
			repository.deleteById(id);
		} catch (EmptyResultDataAccessException e) {
			throw new ResourceNotFoundException(id);
		} catch (DataIntegrityViolationException e) {
			new DatabaseException(e.getMessage());
		}
	}
	
	private void setProductCategories(Product entity, List<CategoryDTO> categories) {
		entity.getCategories().clear();
		for (CategoryDTO dto : categories) {
			Category category = categoryRepository.getOne(dto.getId());
			entity.getCategories().add(category);
		}
	}

	private void updateData(Product entity, ProductCategoriesDTO dto) {
		entity.setName(dto.getName());
		entity.setDescription(dto.getDescription());
		entity.setPrice(dto.getPrice());
		entity.setBarCode(dto.getBarCode());
		
		if (dto.getCategories() != null && dto.getCategories().size() > 0) {
			setProductCategories(entity, dto.getCategories());
		}
	}

	@Transactional(readOnly = true)
	public Page<ProductDTO> findByCategoryPaged(Long categoryId, Pageable pageable) {
		Category category = categoryRepository.getOne(categoryId);
		Page<Product> products = repository.findByCategory(category, pageable);
		return products.map(e -> new ProductDTO(e));
	}
	
	@Transactional
	public void addCategory(Long id, CategoryDTO dto) {
		Product product = repository.getOne(id);
		Category category = categoryRepository.getOne(dto.getId());
		product.getCategories().add(category);
		repository.save(product);
	}
	
	@Transactional
	public void removeCategory(Long id, CategoryDTO dto) {
		Product product = repository.getOne(id);
		Category category = categoryRepository.getOne(dto.getId());
		product.getCategories().remove(category);
		repository.save(product);
	}
	
	@Transactional
	public void setCategories(Long id, List<CategoryDTO> dto) {
		Product product = repository.getOne(id);
		setProductCategories(product, dto);
		repository.save(product);
	}

}