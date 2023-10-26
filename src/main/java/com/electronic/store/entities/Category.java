package com.electronic.store.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "categories")
public class Category {
	
	@Id
	@Column(name = "category_id")
	private String categoryId;
	
	@Column(name = "category_title" , length = 80, nullable = false)
	private String title;
	
	@Column(name = "category_description" , length = 500)
	private String description;
	
	@Column(name = "cover_image")
	private String coverImage;

}
