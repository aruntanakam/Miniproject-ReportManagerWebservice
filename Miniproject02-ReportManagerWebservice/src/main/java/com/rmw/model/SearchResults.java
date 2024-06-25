package com.rmw.model;

import java.time.LocalDate;
import java.time.LocalTime;

import lombok.Data;

@Data
public class SearchResults {

	private Integer courseId;

	private String courseName;

	private String courseCategory;

	private String courseMode;

	private String facultyName;

	private String adminName;

	private Long adminContact;

	private String location;

	private LocalDate startDate;

	private LocalTime timings;

	private Long fee;

	private String status;

}
