package in.happy.reportrequest;

import java.time.LocalDate;

import lombok.Data;

@Data
public class SearchRequest {
	
	private String planName;
	private String status;
	private LocalDate startDate;
	private LocalDate endDate;

}
