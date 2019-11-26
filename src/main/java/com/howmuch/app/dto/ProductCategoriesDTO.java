package com.howmuch.app.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;

import org.hibernate.validator.constraints.Length;

import com.howmuch.app.entities.Product;

public class ProductCategoriesDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@NotEmpty
	private String name;
	
	@NotEmpty
	@Length(min = 8)
	private String description;
	
	@Positive
	private Double price;
	
	private String barCode;
	
	private List<CategoryDTO> categories = new ArrayList<>();
	
	public ProductCategoriesDTO() {
	}

	public ProductCategoriesDTO(String name, String description, Double price, String barCode,
			List<CategoryDTO> categories) {
		this.name = name;
		this.description = description;
		this.price = price;
		this.barCode = barCode;
		this.categories = categories;
	}
	
	public ProductCategoriesDTO(Product entity) {
		this.name = entity.getName();
		this.description = entity.getDescription();
		this.price = entity.getPrice();
		this.barCode = entity.getBarCode();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public String getBarCode() {
		return barCode;
	}

	public void setBarCode(String barCode) {
		this.barCode = barCode;
	}

	public List<CategoryDTO> getCategories() {
		return categories;
	}
	
	public Product toEntity() {
		return new Product(null, name, description, price, barCode);
	}

}