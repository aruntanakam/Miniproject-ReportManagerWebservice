package com.rmw.repository;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.rmw.entity.CourseDetails;

public interface ICourseRepository extends JpaRepository<CourseDetails, Integer> {
	
	@Query("select distinct courseCategory from CourseDetails")
	public Set<String> getCategories();
	
	@Query("select distinct courseMode from CourseDetails")
	public Set<String> getCourseModeDetails();
	
	@Query("select distinct facultyName from CourseDetails")
	public Set<String> getFacultyDetails();

}
