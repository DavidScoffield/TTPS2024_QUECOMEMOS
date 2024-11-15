package com.ttps.quecomemos.dto;

import java.util.List;

import com.ttps.quecomemos.model.Food;

import jakarta.validation.constraints.NotNull;
import lombok.Data;


@Data
public class MenuRegisterDTO {
	
	@NotNull
	private String name;

    @NotNull
    private Float price;
    
    @NotNull
    private List<Long> foodsIds;
    
    private List<Food> foods;

    private String picture;
}
