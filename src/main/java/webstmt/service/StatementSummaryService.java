package webstmt.service;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import webstmt.entity.StatementSummary;
import webstmt.repo.CommonRepository;
import webstmt.repo.StatementSummaryRepository;

@Service
public class StatementSummaryService extends CommonService<StatementSummary, StatementSummaryRepository>
{	
	@Autowired
	StatementSummaryRepository concreteRepo;

	@Override
	CommonRepository<StatementSummary, Long> getConcreteRepository() 
	{
		return (CommonRepository<StatementSummary, Long>) concreteRepo;
	}

	@Value("${app.section.template.file.statement.summary}")
	String stmtSummTemplateFile;

	@Override
	String getTemplateFileName() 
	{
		return stmtSummTemplateFile;
	}

	@Override
	String getRecordTypeName() 
	{
		return "Statement Summary";
	}

	@Override
	String getSectionId() 
	{
		return "STMT_SUMM";
	}
	
	@Override
	String render(String template) throws IOException 
	{
		String formattedHtml = ""; 
		for (StatementSummary summ : getRecords()) {
			
			formattedHtml = template
									.replace("{#post-code}", summ.getPostalCode())
									.replace("{#address1}", summ.getAddress1())
									.replace("{#address2}", summ.getAddress2())
									.replace("{#address3}", summ.getAddress3())
									.replace("{#ao-branch-name}", summ.getAoBranchName())
									.replace("{#barcode1}", summ.getBarcode1())
									.replace("{#barcode2}", summ.getBarcode2())
									.replace("{#cust-home-no}", summ.getCustomerHomeNumber())
									.replace("{#cust-office-no}", summ.getCustomerOfficeNumber())
									.replace("{#cust-name}", summ.getCustomerName())
									.replace("{#reln-no}", summ.getRelationshipNo())
									.replace("{#salution}", summ.getSalution());
		}
		
		return formattedHtml;
	}


}
