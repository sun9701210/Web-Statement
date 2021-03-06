package webstmt.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import webstmt.entity.AccountSummaryRecord;
import webstmt.entity.ForeachReplaceable;
import webstmt.repo.AccountSummaryRecordRepository;
import webstmt.repo.CommonRepository;

@Service
public class AccountSummaryService extends CommonService<AccountSummaryRecord, AccountSummaryRecordRepository>
{
	@Autowired
	private AccountSummaryRecordRepository concreteRepo;

	@Override
	CommonRepository<AccountSummaryRecord, Long> getConcreteRepository() 
	{
		return (CommonRepository<AccountSummaryRecord, Long>) concreteRepo;
	}

	@Value("${app.section.template.file.account.summary}")
	String template;
	
	@Override
	String getTemplateFileName() 
	{
		return template;
	}

	@Override
	String getRecordTypeName() 
	{
		return "Account Summary";
	}

	@Override
	String getSectionId() 
	{
		return "ACCT_SUMM";
	}

	@Override
	String render(String template) throws IOException 
	{
		String formattedHtml = "";
		
		List<ForeachReplaceable> acctTable = new ArrayList<ForeachReplaceable>();
		double totalBalance = 0.0;
		for (AccountSummaryRecord record : getRecords()) 
		{
			totalBalance += record.getBalance();
			acctTable.add(record);
		}
		
		formattedHtml = foreachReplace("sa-in-record", template, acctTable, "{$type}", "{$acct-no}", "{$currency}", "{$foreign-balance}", "{$balance}");
		
		formattedHtml = formattedHtml.replace("{$total-balance}", totalBalance+"");
		
		return formattedHtml;
	}

}
