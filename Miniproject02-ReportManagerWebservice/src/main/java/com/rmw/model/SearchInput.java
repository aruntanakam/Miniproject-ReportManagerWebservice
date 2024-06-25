package com.rmw.model;

import java.time.LocalDate;

import lombok.Data;

@Data
public class SearchInput {

	private String courseCategory;

	private String courseMode;

	private String facultyName;
	
	private LocalDate startDate;

}
