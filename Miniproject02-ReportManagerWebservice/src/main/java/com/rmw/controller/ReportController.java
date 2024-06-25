package com.rmw.controller;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rmw.model.SearchInput;
import com.rmw.model.SearchResults;
import com.rmw.service.ICourseManagementService;
import com.rmw.service.ReportGenerationService;

import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/rmw/api")
public class ReportController {

	@Autowired
	private ICourseManagementService courseService;

	@Autowired
	@Qualifier("excelService")
	private ReportGenerationService excelService;

	@Autowired
	@Qualifier("pdfService")
	private ReportGenerationService pdfService;

	@GetMapping("/course-categories")
	public ResponseEntity<Set<String>> getCategories() {
		return new ResponseEntity<>(courseService.getCouseCategories(), HttpStatus.OK);
	}

	@GetMapping("/course-modes")
	public ResponseEntity<Set<String>> getTrainingModes() {
		return new ResponseEntity<>(courseService.getCourseModeDetails(), HttpStatus.OK);
	}

	@GetMapping("/faculty-details")
	public ResponseEntity<Set<String>> getFacultyDetails() {
		return new ResponseEntity<>(courseService.getFacultyDetails(), HttpStatus.OK);
	}
	
	@PostMapping("/get-results")
	public ResponseEntity<List<SearchResults>> getSearchResults(@RequestBody SearchInput input)
	{
		return new ResponseEntity<>(courseService.getSearchResults(input),HttpStatus.OK);
	}
	
	@PostMapping("/excel-report")
	public void getExcelReport(@RequestBody SearchInput input,HttpServletResponse response )
	{
		List<SearchResults> results=courseService.getSearchResults(input);
		response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
		response.setHeader("Content-Disposition", "attachment;fileName=courseReport.xlsx");
		excelService.generateReport(results, response);
		
	}
	@PostMapping("/pdf-report")
	public void getPdfReport(@RequestBody SearchInput input,HttpServletResponse response )
	{
		List<SearchResults> results=courseService.getSearchResults(input);
		response.setContentType("application/pdf");
		response.setHeader("Content-Disposition", "attachment;fileName=courseReport.pdf");
		pdfService.generateReport(results, response);
		
	}
	
  @GetMapping("/excel-report")	
  public void getExcelReport(HttpServletResponse response)
  {
	  List<SearchResults> results=courseService.getSearchResults();
		response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
		response.setHeader("Content-Disposition", "attachment;fileName=courseReport.xlsx");
		excelService.generateReport(results, response);
  }
  
  @GetMapping("/pdf-report")
	public void getPdfReport(HttpServletResponse response )
	{
		List<SearchResults> results=courseService.getSearchResults();
		response.setContentType("application/pdf");
		response.setHeader("Content-Disposition", "attachment;fileName=courseReport.pdf");
		pdfService.generateReport(results, response);
		
	}

}
