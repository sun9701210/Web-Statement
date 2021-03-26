package webstmt.service;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import webstmt.entity.OneGlanceSummary;
import webstmt.repo.CommonRepository;
import webstmt.repo.OneGlanceSummaryRepository;

@Service
public class OneGlanceSummaryService extends CommonService<OneGlanceSummary, OneGlanceSummaryRepository>
{
	@Value("${app.section.template.file.one-glance.summary}")
	String template;
	
	@Autowired
	OneGlanceSummaryRepository concreateRepo;
	
	@Override
	CommonRepository<OneGlanceSummary, Long> getConcreteRepository() 
	{
		return (CommonRepository<OneGlanceSummary, Long>) concreateRepo;
	}
	
	@Override
	String getTemplateFileName() 
	{
		return template;
	}

	@Override
	String getRecordTypeName() 
	{
		return "One Glance Summary";
	}

	@Override
	String getSectionId() 
	{
		return "ONE_GLANCE_SUMM";
	}

	@Override
	String render(String template) throws IOException 
	{
		String formattedHtml = "";
		
		for (OneGlanceSummary entity : getRecords()) 
		{
			String caTotalAmount = entity.isHasCheckingAccount() ? entity.getTotalAmountCheckingAccount().toPlainString() : "尚未申请";
			String saInTotalAmount = entity.isHasSavingInvestmentAccount() ? entity.getTotalAmountSavingInvestment().toPlainString() : "尚未申请";
			String loanTotalAmount = entity.isHasLoanAccount() ? entity.getTotalAmountLoan().toPlainString() : "尚未申请";
			String creditCardTotalAmount = entity.isHasCreditCard() ? entity.getTotalAmountCreditCard().toPlainString() : "尚未申请";
			String insuranceTotalAmount = entity.isHasInsurance() ? entity.getTotalAmountInsurance().toPlainString() : "尚未申请";
			
			formattedHtml = template.replace("{$due-date}", entity.getDueDate())
					.replace("{$ca-total-amount}", caTotalAmount)
					.replace("{$sa-in-total-amount}", saInTotalAmount)
					.replace("{$loan-total-amount}", loanTotalAmount)
					.replace("{$credit-card-total-amount}", creditCardTotalAmount)
					.replace("{$insurance-total-amount}", insuranceTotalAmount);
		}
		
		return formattedHtml;
	}
}
