package webstmt.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import webstmt.entity.AccountDetailRecordSaIn;
import webstmt.entity.ForeachReplaceable;
import webstmt.repo.AccountDetailRecordSaInRepository;

@Service
public class SavingInvestmentAccountDetailService extends CommonService<AccountDetailRecordSaIn, AccountDetailRecordSaInRepository> 
{
	private static Logger log = LoggerFactory.getLogger(SavingInvestmentAccountDetailService.class);

	@Autowired
	AccountDetailRecordSaInRepository concreteRepo;

	@Override
	AccountDetailRecordSaInRepository getConcreteRepository() 
	{
		return concreteRepo;
	}

	@Value("${app.section.template.file.account.details.sa-in}")
	String acctDtlSaInTemplateFile;
	
	@Override
	String getTemplateFileName() 
	{
		return acctDtlSaInTemplateFile;
	}

	@Override
	String getRecordTypeName() 
	{
		return "Saving and Investment Account Details";
	}

	@Override
	String getSectionId() 
	{
		return "ACCT_DTL_SA_IN";
	}

	@Override
	String render(String template) throws IOException 
	{
		String formattedHtml = "";
		
		List<List<ForeachReplaceable>> acctTables = new ArrayList<>();
		
		String currentAcct = null;
		
		List<ForeachReplaceable> acctTable = new ArrayList<>();
		acctTables.add(acctTable);
		
		for (AccountDetailRecordSaIn record : getRecords()) {
			
			if(currentAcct == null)
			{
				currentAcct = record.getAccountNo();
			}
			
			if(currentAcct!=record.getAccountNo())
			{
				currentAcct = record.getAccountNo();
				acctTable = new ArrayList<>();
				acctTables.add(acctTable);
			}
			
			acctTable.add(record);
		}

		log.info("acct table#"+acctTables.size());
		
		for (List<ForeachReplaceable> table : acctTables) {
			
			String html = template;
			double totalDeposit = 0.0;
			double totalWithdrawl = 0.0;
			String acctDesc = "";
			String acctNo = "";
			for (ForeachReplaceable data : table) {
				
				acctDesc = ((AccountDetailRecordSaIn)data).getAccountDescription();
				acctNo = ((AccountDetailRecordSaIn)data).getAccountNo();
				totalDeposit += ((AccountDetailRecordSaIn)data).getDeposit();
				totalWithdrawl += ((AccountDetailRecordSaIn)data).getWithdrawl();
			}
			

			html = html.replace("{$acct-desc}", acctDesc);
			html = html.replace("{$acct-no}", acctNo);
			
			html = foreachReplace("record", html, table, "{$date}", "{$desc}","{$deposit}","{$withdrawl}","{$balance}");
			
			html = html.replace("{$total-deposit}", totalDeposit+"");
			html = html.replace("{$total-withdrawl}", totalWithdrawl+"");
			
			formattedHtml += html;
		}
		return formattedHtml;
	}
}
