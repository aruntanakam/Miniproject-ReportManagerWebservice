package com.rmw.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import com.rmw.entity.CourseDetails;
import com.rmw.model.SearchInput;
import com.rmw.model.SearchResults;
import com.rmw.repository.ICourseRepository;

@Service
public class CourseManagementServiceImpl implements ICourseManagementService {

	@Autowired
	private ICourseRepository repo;

	@Override
	public Set<String> getCouseCategories() {

		return repo.getCategories();
	}

	@Override
	public Set<String> getFacultyDetails() {

		return repo.getFacultyDetails();
	}

	@Override
	public Set<String> getCourseModeDetails() {

		return repo.getCourseModeDetails();
	}

	@Override
	public List<SearchResults> getSearchResults(SearchInput input) {

		CourseDetails details = new CourseDetails();

		if (StringUtils.isNotBlank(input.getCourseCategory())) {
			details.setCourseCategory(input.getCourseCategory());
		}

		if (StringUtils.isNotBlank(input.getCourseMode())) {
			details.setCourseMode(input.getCourseMode());

		}

		if (StringUtils.isNotBlank(input.getFacultyName())) {
			details.setFacultyName(input.getFacultyName());
		}

		if (input.getStartDate() != null && StringUtils.isNotBlank(input.getStartDate().toString())) {
			details.setStartDate(input.getStartDate());
		}

		List<CourseDetails> courseDetails = repo.findAll(Example.of(details));
		
		List<SearchResults> results=new ArrayList<SearchResults>();
		
		courseDetails.forEach(detail->{
			SearchResults result=new SearchResults();
			BeanUtils.copyProperties(detail, result);
			results.add(result);
		});

		return results;
	}
	
	public List<SearchResults> getSearchResults()
	{
		List<CourseDetails> courseDetails=repo.findAll();
List<SearchResults> results=new ArrayList<SearchResults>();
		
		courseDetails.forEach(detail->{
			SearchResults result=new SearchResults();
			BeanUtils.copyProperties(detail, result);
			results.add(result);
		});
		return results;
		
	}

}
