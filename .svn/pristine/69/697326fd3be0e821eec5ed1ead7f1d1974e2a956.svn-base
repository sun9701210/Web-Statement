package webstmt.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import webstmt.entity.ForeachReplaceable;
import webstmt.entity.ProductTotalSummary;
import webstmt.repo.CommonRepository;
import webstmt.repo.ProductTotalSummaryRepository;

@Service
public class ProductTotalSummaryService extends CommonService<ProductTotalSummary, ProductTotalSummaryRepository>
{
	private static Logger log = LoggerFactory.getLogger(ProductTotalSummaryService.class);

	@Autowired
	ProductTotalSummaryRepository concreteRepo;

	@Override
	CommonRepository<ProductTotalSummary, Long> getConcreteRepository() 
	{
		return (CommonRepository<ProductTotalSummary, Long>) concreteRepo;
	}

	
	@Value("${app.section.template.file.product.total.summary}")
	String prodSummTemplateFile;
	
	@Override
	String getTemplateFileName() 
	{
		return prodSummTemplateFile;
	}

	@Override
	String getRecordTypeName() 
	{
		return "Product Total Summary";
	}

	@Override
	String getSectionId() 
	{
		return "PROD_SUMM_TOTAL";
	}

	@Override
	String render(String template) throws IOException 
	{
		String formattedHtml = "";
		
		List<ForeachReplaceable> assetList = new ArrayList<>();
		List<ForeachReplaceable> liabList = new ArrayList<>();
		
		for (ProductTotalSummary prodSumm : getRecords()) {
			
			if("A".equalsIgnoreCase(prodSumm.getType()))
			{
				assetList.add(prodSumm);
			}
			else if("L".equalsIgnoreCase(prodSumm.getType()))
			{
				liabList.add(prodSumm);
			}
			else
			{
				log.warn("Unknown type: "+prodSumm.getType());
			}
		}
		
		formattedHtml = foreachReplace("asset", template, assetList, "{$name}","{$amount}");
		formattedHtml = foreachReplace("liab", formattedHtml, liabList, "{$name}","{$amount}");
		
		return formattedHtml;
	}

}
