package webstmt.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import webstmt.service.AccountSummaryService;
import webstmt.service.OneGlanceSummaryService;
import webstmt.service.ProductTotalSummaryService;
import webstmt.service.SavingInvestmentAccountDetailService;
import webstmt.service.StatementSummaryService;

@Controller
public class WebStatementController 
{
	@Autowired
	StatementSummaryService stmtSummService;
	@Autowired
	ProductTotalSummaryService prodTotalSummService;
	@Autowired
	SavingInvestmentAccountDetailService savingInvestmentAcctDetailService;
	@Autowired
	OneGlanceSummaryService oneGlanceSummService;
	@Autowired
	AccountSummaryService acctSummService;

	@RequestMapping("/ccs-web-stmt")
	public String webStatement()
	{
		return "ccs-web-stmt";
	}
	
	@RequestMapping("/dashboard/gen-stmt/{stmt-date-month}/{reln}")
	public String generateStatement(@PathVariable("stmt-date-month") String dateMonth, @PathVariable("reln") String relationshipNo, Model model) throws IOException
	{
		String messages = "";
		
		String stmtSummaryGenerateResult = stmtSummService.generateHtml(dateMonth, relationshipNo);
		
		messages += stmtSummaryGenerateResult;
		
		String prodTotalSummGenerateResult = prodTotalSummService.generateHtml(dateMonth, relationshipNo);
		
		messages += prodTotalSummGenerateResult;
		
		String savingInvestmentAccountDetailsGenerateResult = savingInvestmentAcctDetailService.generateHtml(dateMonth, relationshipNo);
		
		messages += savingInvestmentAccountDetailsGenerateResult;
		
		String oneGlanceSummaryGenerateResult = oneGlanceSummService.generateHtml(dateMonth, relationshipNo);
		
		messages += oneGlanceSummaryGenerateResult;
		
		String acctSummaryGenerateResult = acctSummService.generateHtml(dateMonth, relationshipNo);
		
		messages += acctSummaryGenerateResult;
		
		model.addAttribute("result", messages);
		
		return "stmt-gen-result";
	}

	@RequestMapping(value="stmt/acct-detail-sa-in/{stmt-date-month}/{reln}", method= RequestMethod.GET, produces= {"application/json;charset=UTF-8"})
	@ResponseBody
	public String accountDetailsSavingInvestment(@PathVariable("stmt-date-month") String dateMonth, @PathVariable("reln") String relationshipNo) throws IOException
	{
		return savingInvestmentAcctDetailService.buildSectionJsonResponse(dateMonth, relationshipNo);
	}
	
	@RequestMapping(value="stmt/prod-summ-total/{stmt-date-month}/{reln}", method= RequestMethod.GET, produces= {"application/json;charset=UTF-8"})
	@ResponseBody
	public String productSummaryTotal(@PathVariable("stmt-date-month") String dateMonth, @PathVariable("reln") String relationshipNo) throws IOException
	{
		return prodTotalSummService.buildSectionJsonResponse(dateMonth, relationshipNo);
	}
	
	@RequestMapping(value="stmt/summ/{stmt-date-month}/{reln}", method= RequestMethod.GET, produces= {"application/json;charset=UTF-8"})
	@ResponseBody
	public String statementSummary(@PathVariable("stmt-date-month") String dateMonth, @PathVariable("reln") String relationshipNo) throws IOException
	{
		return stmtSummService.buildSectionJsonResponse(dateMonth, relationshipNo);
	}
	
	@RequestMapping(value="/stmt/one-glance-summ/{stmt-date-month}/{reln}", method = RequestMethod.GET, produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	public String oneGlanceSummary(@PathVariable("stmt-date-month")String dateMonth, @PathVariable("reln")String relationshipNo) throws IOException
	{
		return oneGlanceSummService.buildSectionJsonResponse(dateMonth, relationshipNo);
	}
	
	@RequestMapping(value="/stmt/acct-summ/{stmt-date-month}/{reln}", method = RequestMethod.GET, produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	public String accountSummary(@PathVariable("stmt-date-month")String date, @PathVariable("reln")String relationshipNo) throws IOException
	{
		return acctSummService.buildSectionJsonResponse(date, relationshipNo);
	}

	//TODO where is sys parms definition?

}
