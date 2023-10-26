package com.electronic.store.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "users")
public class User {

	@Id
	@Column(name = "user_id")
	private String userId;

	@Column(name = "user_name")
	private String name;

	@Column(name = "user_email", unique = true)
	private String email;

	@Column(name = "user_password")
	private String password;

	@Column(name = "user_gender")
	private String gender;

	@Column(name = "user_about")
	private String about;
	
	@Column(name = "user_image_name")
	private String imageName;

}
