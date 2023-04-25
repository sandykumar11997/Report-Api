package in.happy.reportentity;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Data
@Table(name = "Report_Table")
public class Report {
	
	@Id
	private Integer planId;
	private String planName;
	private String status;
	private String name;
	private String emailId;
	private Long mNumber;
	private Character gender;
	private Long ssn;
	private LocalDate startDate;
	private LocalDate endDate;
	
	

}
