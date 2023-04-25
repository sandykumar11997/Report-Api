package in.happy.reportrepo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import in.happy.reportentity.Report;


public interface ReportRepo extends JpaRepository<Report,Integer> {
	
	@Query("select distinct(planName) from Report")
	public List<String> findPlanNames();
	
	@Query("select distinct(status) from Report")
	public List<String> findPlanStatus();
}
