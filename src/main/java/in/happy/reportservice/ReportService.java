package in.happy.reportservice;

import java.util.List;

import javax.servlet.http.HttpServletResponse;



import in.happy.reportrequest.SearchRequest;
import in.happy.reportresponse.SearchResponse;

public interface ReportService {

	 public List<String> getUniquePlans();
	 public List<String> getUniquePlanStaus();
	 public List<SearchResponse> search(SearchRequest request);
	 public void generateExcel(HttpServletResponse response)throws Exception;
	 public void generatePdf(HttpServletResponse reponse) throws Exception;

}
