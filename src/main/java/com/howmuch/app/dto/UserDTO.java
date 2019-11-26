package com.howmuch.app.dto;

import java.io.Serializable;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

import com.howmuch.app.entities.User;
import com.howmuch.app.services.validation.UserUpdateValid;

@UserUpdateValid
public class UserDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Long id;

	@NotEmpty(message = "can't be empty")
	@Length(min = 5, max = 80, message = "length must be between 5 and 80")
	private String name;
	
	@NotEmpty(message = "can't be empty")
	@Email(message = "invalid e-mail")
	private String email;
	
	@NotEmpty(message = "can't be empty")
	@Length(min = 5, max = 20, message = "length must be between 5 and 20")
	private String phone;
	
	public UserDTO() {
	}

	public UserDTO(Long id, String name, String email, String phone) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.phone = phone;
	}
	
	public UserDTO(User entity) {
		this.id = entity.getId();
		this.name = entity.getName();
		this.email = entity.getEmail();
		this.phone = entity.getPhone();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String nome) {
		this.name = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	public User toEntity() {
		return new User(id, name, email, phone, null);
	}

}