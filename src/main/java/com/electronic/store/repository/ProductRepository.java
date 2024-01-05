package com.electronic.store.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.electronic.store.dtos.ProductDto;
import com.electronic.store.entities.Product;

public interface ProductRepository extends JpaRepository<Product, String>{
	
	//search methods
	Page<Product> findBytitleContaining(String subTitle , Pageable pageable);
	Page<Product> findByLiveTrue(Pageable pageable);
	
	//other custom query methods
	

}
