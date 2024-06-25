package com.rmw.service;

import java.util.List;

import com.rmw.model.SearchResults;

import jakarta.servlet.http.HttpServletResponse;

public interface ReportGenerationService {
	
	public void generateReport(List<SearchResults> results,HttpServletResponse respone);
	

}
