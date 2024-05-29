package com.backend.request;

import java.util.HashSet;
import java.util.Set;

import com.backend.controller.model.Size;

import lombok.Data;

@Data
public class CreateProductRequest {
	
	private String title;
	
	private String description;
	
	private int price;
	
	private int discountedPrice;
	
	private int discountPersent;
	
	private int quantity;
	
	private String brand;
	
	private String color;
	
	private Set<Size> size= new HashSet<>();
	
	private String imageUrl;
	
//	 Men
	private String topLevelCategory;
	
//	clothing
	private String secondLevelCategory;
	
//	mens_shirt
	private String thirdLevelCategory;
	
	
	
	
	

}
