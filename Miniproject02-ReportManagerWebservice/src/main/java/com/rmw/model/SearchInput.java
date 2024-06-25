package com.rmw.model;

import java.time.LocalDate;

import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class SearchInput {

	@Size(min=1,max = 50)
	private String courseCategory;

	@Size(min=0,max = 50)
	private String courseMode;

	@Size(min=0,max = 50)
	private String facultyName;
	
	@Size(min=0,max = 50)
	private LocalDate startDate;

}
