package in.happy.runner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import in.happy.reportentity.Report;
import in.happy.reportrepo.ReportRepo;

@Component
public class AppRunner implements ApplicationRunner{
	
	@Autowired
	private ReportRepo reportRepo;

	@Override
	public void run(ApplicationArguments args) throws Exception {
		 Report report = new Report();
         report.setPlanId(1);
         report.setName("santosh");
         report.setPlanName("ccbp");
         report.setMNumber(12345678l);
         report.setGender('M');
         report.setEmailId("sandykumar11997@gmail.com");
         report.setStatus("Approved");
         report.setSsn(1237648l);
         reportRepo.save(report);
         
         Report report2 = new Report();
         report2.setPlanId(2);
         report2.setName("sandy");
         report2.setPlanName("sbap");
         report2.setMNumber(12389678l);
         report2.setGender('M');
         report2.setEmailId("sandykuma7@gmail.com");
         report2.setStatus("Denied");
         report2.setSsn(12376467l);
         reportRepo.save(report2);
		
	}

}
