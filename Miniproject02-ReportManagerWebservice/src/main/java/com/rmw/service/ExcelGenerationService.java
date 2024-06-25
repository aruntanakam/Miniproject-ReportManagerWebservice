package com.rmw.service;

import static com.rmw.commons.RmwConstants.HEADERS;

import java.io.OutputStream;
import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import com.rmw.model.SearchResults;

import jakarta.servlet.http.HttpServletResponse;

@Service("excelService")
public class ExcelGenerationService implements ReportGenerationService {

	public void addHeader(XSSFSheet sheet) {
		XSSFRow header = sheet.createRow(0);

		for (int i = 0; i < HEADERS.length; i++) {
			header.createCell(i).setCellValue(HEADERS[i]);
		}
	}

	@Override
	public void generateReport(List<SearchResults> results, HttpServletResponse response) {

		try (XSSFWorkbook workbook = new XSSFWorkbook(); OutputStream os = response.getOutputStream()) {
			XSSFSheet sheet = workbook.createSheet("CourseReport");
           
			addHeader(sheet);
			int r = 1;

			for (SearchResults result : results) {
				XSSFRow row = sheet.createRow(r);
				row.createCell(0).setCellValue(String.valueOf(result.getCourseId()));
				row.createCell(1).setCellValue(result.getCourseName());
				row.createCell(2).setCellValue(result.getCourseCategory());
				row.createCell(3).setCellValue(result.getCourseMode());
				row.createCell(4).setCellValue(result.getFacultyName());
				row.createCell(5).setCellValue(result.getAdminName());
				row.createCell(6).setCellValue(String.valueOf(result.getAdminContact()));
				row.createCell(7).setCellValue(result.getLocation());
				row.createCell(8).setCellValue(result.getStartDate().toString());
				row.createCell(9).setCellValue(result.getTimings().toString());
				row.createCell(10).setCellValue(String.valueOf(result.getFee()));
				row.createCell(11).setCellValue(result.getStatus());
				r++;

			}

			workbook.write(os);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
