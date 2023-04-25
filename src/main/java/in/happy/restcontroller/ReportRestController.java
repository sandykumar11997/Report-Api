package in.happy.restcontroller;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import in.happy.reportrequest.SearchRequest;
import in.happy.reportresponse.SearchResponse;
import in.happy.reportservice.ReportService;
import in.happy.reportservice.RepostServiceImpl;

@RestController
public class ReportRestController {
	
	@Autowired
	private ReportService service;
	
	@GetMapping("/plans")
	public ResponseEntity<List<String>> getPlanNames(){
		List<String> planNames = service.getUniquePlans();
		return new ResponseEntity<>(planNames , HttpStatus.OK);
		
	}
	
	@GetMapping("/status")
	public ResponseEntity<List<String>> getPlanStatus(){
		List<String> status = service.getUniquePlanStaus();
		return new ResponseEntity<>(status , HttpStatus.OK);
	}
	
	@PostMapping(path="/search")
	public ResponseEntity<List<SearchResponse>> search(@RequestBody SearchRequest request){
		List<SearchResponse> response = service.search(request);
		return new ResponseEntity<>(response,HttpStatus.OK);
	}
	
	@GetMapping("/excel")
	public void excelReport(HttpServletResponse response) throws Exception {
		response.setContentType("aplicatoin/octet-stream");
		
		String headerKey = "content-disposal";
		String headerValue = "attachment;filename = data.xls";
		
		response.setHeader(headerKey, headerValue);
		
		service.generateExcel(response);
	}
	
	@GetMapping("/pdf")
	public void pdfReport(HttpServletResponse response) throws Exception {
		 response.setContentType("aplication/pdf");
		 
		 String headerKey = "content-Disposal";
		 String headerValue = "attachment;filename = data.pdf";
		 
		 response.addHeader(headerKey, headerValue);
		 
		 service.generatePdf(response);
	}

}
