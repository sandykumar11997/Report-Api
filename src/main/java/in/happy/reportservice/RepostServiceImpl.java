package in.happy.reportservice;

import java.awt.Color;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfTable;
import com.lowagie.text.pdf.PdfWriter;

import in.happy.reportentity.Report;
import in.happy.reportrepo.ReportRepo;
import in.happy.reportrequest.SearchRequest;
import in.happy.reportresponse.SearchResponse;

@Service
public class RepostServiceImpl implements ReportService {
	
	@Autowired
	private ReportRepo reportRepo;

	@Override
	public List<String> getUniquePlans() {
	return reportRepo.findPlanNames();
	}

	@Override
	public List<String> getUniquePlanStaus() {
		return reportRepo.findPlanStatus();
	}

	@Override
	public List<SearchResponse> search(SearchRequest request) {
	    List<SearchResponse> response = new ArrayList<>();
	    
	    Report queryBuilder = new Report();
	    String planName = request.getPlanName();
	    if(planName != null && planName.equals("")){
	    	queryBuilder.setPlanName(planName);
	    }
	    String status = request.getStatus();
	    if(status != null && status.equals("")) {
	    	queryBuilder.setStatus(status);
	    }
	    LocalDate startDate = request.getStartDate();
	    if(startDate != null && startDate.equals(0)) {
	    	queryBuilder.setStartDate(startDate);
	    }
	    LocalDate endDate = request.getEndDate();
	    if (endDate != null && endDate.equals(0)) {
	    	queryBuilder.setEndDate(endDate);
	    }
	    Example<Report> example = Example.of(queryBuilder);
	    
		List<Report> entities = reportRepo.findAll(example);
		for(Report report : entities) {
			SearchResponse sr = new SearchResponse();
			BeanUtils.copyProperties(report, sr);
			response.add(sr);
		}
		return response;
	}

	@Override
	public void generateExcel(HttpServletResponse response) throws Exception {
		
		List<Report> entities = reportRepo.findAll();
		
		 HSSFWorkbook workbook = new HSSFWorkbook();
		 HSSFSheet createSheet = workbook.createSheet();
		 HSSFRow headerRow = createSheet.createRow(0);
		 
		 headerRow.createCell(0).setCellValue("name");
		 headerRow.createCell(1).setCellValue("mobile");
		 headerRow.createCell(2).setCellValue("email");
		 headerRow.createCell(3).setCellValue("gender");
		 headerRow.createCell(4).setCellValue("ssn");
		 
		 int i = 1;
		 for( Report entity : entities){
			 
			
			 HSSFRow dataRow = createSheet.createRow(i);
			 dataRow.createCell(0).setCellValue(entity.getPlanName());
			 dataRow.createCell(1).setCellValue(entity.getMNumber());
			 dataRow.createCell(2).setCellValue(entity.getEmailId());
			 dataRow.createCell(3).setCellValue(String.valueOf(entity.getGender()));
			 dataRow.createCell(4).setCellValue(entity.getSsn());
			 
			 i++;
			 
		 }
		 
		 ServletOutputStream outputStream = response.getOutputStream();
		 workbook.write(outputStream);
		 workbook.close();
		 outputStream.close();
		
	}

	@Override
	public void generatePdf(HttpServletResponse reponse) throws Exception {
		
		List<Report> entities = reportRepo.findAll();
		
		Document document = new Document(PageSize.A4);
		PdfWriter.getInstance(document, reponse.getOutputStream());
		document.open();
		Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
		font.setSize(12);
		font.setColor(Color.BLUE);
		Paragraph p = new Paragraph("search Report",font);
		p.setAlignment(Paragraph.ALIGN_CENTER);
		
		document.add(p);
		
		PdfPTable table = new PdfPTable(5);
		table.setWidthPercentage(100f);
		table.setWidths(new float[] {1.5f,3.5f,3.0f,3.0f,1.5f});
		table.setSpacingBefore(10);
		
		PdfPCell cell = new PdfPCell();
        cell.setBackgroundColor(Color.BLUE);
        cell.setPadding(5);
         
        font = FontFactory.getFont(FontFactory.HELVETICA);
        font.setColor(Color.WHITE);
         
        cell.setPhrase(new Phrase("NAME", font));
         
        table.addCell(cell);
         
        cell.setPhrase(new Phrase("E-mail", font));
        table.addCell(cell);
         
        cell.setPhrase(new Phrase("MOB NUM", font));
        table.addCell(cell);
         
        cell.setPhrase(new Phrase("GENDER", font));
        table.addCell(cell);
         
        cell.setPhrase(new Phrase("SSN", font));
        table.addCell(cell);      
        
        for (Report entity : entities) {
        	table.addCell(entity.getName());
        	table.addCell(entity.getEmailId());
        	table.addCell(String.valueOf(entity.getMNumber()));
        	table.addCell(String.valueOf(entity.getGender()));
        	table.addCell(String.valueOf(entity.getSsn()));
        }
        
        document.add(table);
        document.close();
    }
		
		
		
	}


