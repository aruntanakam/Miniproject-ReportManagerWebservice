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

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/rmw/api")
@OpenAPIDefinition(
        info = @Info(
                title = "Report Manager Webservice",
                version = "1.0",
                description = "Api for  downloading reports in pdf and excel format",
                license = @License(name = "Apache 2.0", url = "http://foo.bar"),
                contact = @Contact(url = "http://gigantic-server.com", name = "Fred", email = "Fred@gigagantic-server.com")
        ))
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
	@Operation(summary = "Get course categories")
	@ApiResponses(value = { 
	  @ApiResponse(responseCode = "200", description = "Found the courses", 
	    content = { @Content(mediaType = "application/json", 
	    array = @ArraySchema(schema = @Schema(implementation = String.class)) )}),
	  @ApiResponse(responseCode = "500", description = "Some exception occured", 
	    content = @Content(mediaType="text/plain",schema=@Schema(implementation=String.class)))})
	public ResponseEntity<Set<String>> getCategories() {
		return new ResponseEntity<>(courseService.getCouseCategories(), HttpStatus.OK);
	}

	@GetMapping("/course-modes")
	@Operation(summary = "Get course modes")
	@ApiResponses(value = { 
	  @ApiResponse(responseCode = "200", description = "Found the course modes", 
	    content = { @Content(mediaType = "application/json", 
	      array = @ArraySchema(schema = @Schema(implementation = String.class)) )}),
	  @ApiResponse(responseCode = "500", description = "Some exception occured", 
	    content = @Content(mediaType="text/plain",schema=@Schema(implementation=String.class)))})
	public ResponseEntity<Set<String>> getTrainingModes() {
		return new ResponseEntity<>(courseService.getCourseModeDetails(), HttpStatus.OK);
	}

	@GetMapping("/faculty-details")
	@Operation(summary = "Get faculty names")
	@ApiResponses(value = { 
	  @ApiResponse(responseCode = "200", description = "Found the faculty names", 
	    content = { @Content(mediaType = "application/json", 
	    array = @ArraySchema(schema = @Schema(implementation = String.class)) )}),
	  @ApiResponse(responseCode = "500", description = "Some exception occured", 
	    content = @Content(mediaType="text/plain",schema=@Schema(implementation=String.class)))})
	public ResponseEntity<Set<String>> getFacultyDetails() {
		return new ResponseEntity<>(courseService.getFacultyDetails(), HttpStatus.OK);
	}
	
	@PostMapping("/get-results")
	@Operation(summary = "Get Search results based on filters")
	@ApiResponses(value = { 
	  @ApiResponse(responseCode = "200", description = "Found the results", 
	    content = { @Content(mediaType = "application/json", 
	    array = @ArraySchema(schema = @Schema(implementation = SearchResults.class)) )}),
	  @ApiResponse(responseCode = "500", description = "Some exception occured", 
	    content = @Content(mediaType="text/plain",schema=@Schema(implementation=String.class)))})
	public ResponseEntity<List<SearchResults>> getSearchResults(@Parameter(description = "Serach Input for filtering data" ,schema = @Schema(implementation = SearchInput.class))  @RequestBody SearchInput input)
	{
		return new ResponseEntity<>(courseService.getSearchResults(input),HttpStatus.OK);
	}
	
	@PostMapping("/excel-report")
	@Operation(summary = "Get Search results  based on filters in the form of excel sheet ")
	@ApiResponses(value = { 
	  @ApiResponse(responseCode = "200", description = "Report fetched successfully", 
	    content =  @Content(mediaType = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")),
	  @ApiResponse(responseCode = "500", description = "Some exception occured", 
	    content = @Content(mediaType="text/plain",schema=@Schema(implementation=String.class)))})
	public void getExcelReport(@RequestBody SearchInput input,HttpServletResponse response )
	{
		List<SearchResults> results=courseService.getSearchResults(input);
		response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
		response.setHeader("Content-Disposition", "attachment;fileName=courseReport.xlsx");
		excelService.generateReport(results, response);
		
	}
	@PostMapping("/pdf-report")
	@Operation(summary = "Get Search results  based on filters in the form of pdf document ")
	@ApiResponses(value = { 
	  @ApiResponse(responseCode = "200", description = "Report fetched successfully", 
	    content =  @Content(mediaType = "application/pdf")),
	  @ApiResponse(responseCode = "500", description = "Some exception occured", 
	    content = @Content(mediaType="text/plain",schema=@Schema(implementation=String.class)))})
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
