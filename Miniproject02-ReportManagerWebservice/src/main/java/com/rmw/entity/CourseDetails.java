package com.rmw.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class CourseDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer courseId;

	@Column(length = 50)
	private String courseName;

	@Column(length = 50)
	private String courseCategory;

	@Column(length = 50)
	private String courseMode;

	@Column(length = 50)
	private String facultyName;

	@Column(length = 50)
	private String adminName;

	private Long adminContact;

	@Column(length = 50)
	private String location;

	private LocalDate startDate;
	
	private LocalTime timings;

	private Long fee;

	private String status;


	@CreationTimestamp
	@Column(updatable = false)
	private LocalDateTime createdTimeStamp;

	@UpdateTimestamp
	@Column(updatable = true)
	private LocalDateTime updatedTimeStamp;

	private String createdBy;

	private String updatedBy;

}
