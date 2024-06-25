package com.rmw.service;

import static com.rmw.commons.RmwConstants.HEADERS;
import static com.rmw.commons.RmwConstants.WIDTHS;

import java.util.List;

import org.springframework.stereotype.Service;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.rmw.model.SearchResults;

import jakarta.servlet.http.HttpServletResponse;

@Service("pdfService")
public class PdfGenerationService implements ReportGenerationService {

	public void addHeader(PdfPTable table) {
		Font hFont = FontFactory.getFont(FontFactory.TIMES_BOLD, 10, BaseColor.BLACK);
		for (String header : HEADERS) {
			PdfPCell cell = new PdfPCell();
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_CENTER);
			cell.setBackgroundColor(BaseColor.RED);
			cell.setBorderWidth(2);
			cell.setPhrase(new Phrase(header, hFont));
			cell.setPadding(5.0f);
			table.addCell(cell);

		}
	}

	public void addDatacell(PdfPTable table, String phrase, Font f) {
		PdfPCell cell = new PdfPCell();
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setVerticalAlignment(Element.ALIGN_CENTER);
		cell.setBackgroundColor(BaseColor.YELLOW);
		cell.setPhrase(new Phrase(phrase, f));
		cell.setBorderWidth(1);
		cell.setPadding(5.0f);
		table.addCell(cell);
	}

	@Override
	public void generateReport(List<SearchResults> results, HttpServletResponse response) {

		try {
			Document document = new Document();
			PdfWriter.getInstance(document, response.getOutputStream());
			document.open();
			Font pFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16, BaseColor.RED);
			Paragraph p = new Paragraph("Search Results of Course Details", pFont);
			p.setAlignment(Element.ALIGN_CENTER);
			document.add(p);

			PdfPTable table = new PdfPTable(HEADERS.length);
			table.setWidthPercentage(100.0f);
			table.setWidths(WIDTHS);
			table.setSpacingBefore(10.0f);

			addHeader(table);
			Font f = FontFactory.getFont(FontFactory.HELVETICA, 8, BaseColor.BLACK);
			results.forEach(result -> {
				addDatacell(table, result.getCourseId().toString(), f);
				addDatacell(table, result.getCourseName(), f);
				addDatacell(table, result.getCourseCategory(), f);
				addDatacell(table, result.getCourseMode(), f);
				addDatacell(table, result.getFacultyName(), f);
				addDatacell(table, result.getAdminName(), f);
				addDatacell(table, result.getAdminContact().toString(), f);
				addDatacell(table, result.getLocation(), f);
				addDatacell(table, result.getStartDate().toString(), f);
				addDatacell(table, result.getTimings().toString(), f);
				addDatacell(table, result.getFee().toString(), f);
				addDatacell(table, result.getStatus(), f);
				

			});
			document.add(table);
			document.close();

		} catch (Exception e) {
			e.printStackTrace();

		}

	}

}
