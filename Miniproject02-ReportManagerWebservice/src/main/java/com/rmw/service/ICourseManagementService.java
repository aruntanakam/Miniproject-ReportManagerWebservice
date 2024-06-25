package com.rmw.service;

import java.util.List;
import java.util.Set;

import com.rmw.model.SearchInput;
import com.rmw.model.SearchResults;

public interface ICourseManagementService {
	
	public Set<String> getCouseCategories();
	
	public Set<String> getFacultyDetails();
	
	public Set<String> getCourseModeDetails();
	
	public List<SearchResults> getSearchResults(SearchInput input);
	
	public List<SearchResults> getSearchResults();
	
}
