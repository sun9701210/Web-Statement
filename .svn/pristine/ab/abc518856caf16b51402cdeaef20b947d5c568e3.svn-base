package webstmt.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.util.HtmlUtils;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import webstmt.entity.ForeachReplaceable;
import webstmt.repo.CommonRepository;

public abstract class CommonService<T, R> {
	
	protected static Logger log = LoggerFactory.getLogger(CommonService.class);
	
	protected CommonRepository<T, Long> repo;
//	@Getter
	private List<T> records;
	
	public List<T> getRecords()
	{
		return records;
	}
	
	/**
	 * File Name of Template on file system.
	 * @return
	 */
	abstract String getTemplateFileName();
	/**
	 * Descriptive Name of Record
	 * @return
	 */
	abstract String getRecordTypeName();
	/**
	 * Abbreviation as ID to represent section.
	 * @return
	 */
	abstract String getSectionId();
	abstract CommonRepository<T, Long> getConcreteRepository();
	abstract String render(String template) throws IOException;
	
	@Value("${app.section.template.root}")
	String templateFolder;
	
	@Value("${data.html.cache.root}")
	String cacheRootFolder;
	
	public String generateHtml(String date, String relationshipNo) throws IOException
	{
		readRecords(date, relationshipNo);
		
		//List<T> records = repo.getRecordsByDateRelationshipNo(date, relationshipNo);
		
		String resultMessage = "";
		
		if(records==null||records.size()==0)
		{
			resultMessage = "No "+getRecordTypeName() +" record.";
			log.info(resultMessage);
		}
		else
		{
			String template = loadTemplate(getTemplateFileName());
			String formattedHtml = render(template);
			
			resultMessage = "Result: "+formattedHtml + "<br />";
			
			String fileName = concatenateHtmlFileName(date, relationshipNo);
			
			log.info("Will save formatted html to "+fileName);
			
			saveCacheHtmlFile(fileName, formattedHtml);
		}
		
		return resultMessage;
	}
	

	
	public String buildSectionJsonResponse(String date, String relationshipNo) throws IOException
	{
		Gson gson = new Gson();
		
		String sectionHtmlFile = concatenateHtmlFileName(date, relationshipNo); 
		
		String sectionText = loadCachedHtmlFile(sectionHtmlFile);
		
		JsonObject root = new JsonObject();
		
		JsonElement sections = gson.toJsonTree(HtmlUtils.htmlEscape(sectionText));
		
		root.add("sections", sections);
		
		
		JsonObject resp = new JsonObject();
		
		resp.add("documentFragmentResponse",root);
		
		String jsonRespText = gson.toJson(resp);
		
		return jsonRespText;
	}
	
	private List<T> readRecords(String date, String relationshipNo)
	{
		repo = getConcreteRepository();
		
		records = repo.getRecordsByDateRelationshipNo(date, relationshipNo);
		
		return records;
	}
	
	private String loadTemplate(String templateName) throws IOException {
		
		String template = new String(Files.readAllBytes(Paths.get(templateFolder+templateName)));

		return template;
	}
	
	private void saveCacheHtmlFile(String fileName, String formattedHtml) throws IOException {

		Path parentDir = Paths.get(cacheRootFolder);
		
		if(!Files.exists(parentDir)) Files.createDirectories(parentDir);
		
		Files.write(Paths.get(cacheRootFolder+"/"+fileName), formattedHtml.getBytes());
	}
	
	private String concatenateHtmlFileName(String date, String relationshipNo)
	{
		return date + "_" + relationshipNo + "_" + getSectionId() +".html"; 
	}
	
	private String loadCachedHtmlFile(String fileName) throws IOException
	{
		Path filePath = Paths.get(cacheRootFolder+fileName);
		
		String sectionsText = new String(Files.readAllBytes(filePath));
		
		return sectionsText;
	}
	

	protected String foreachReplace(String foreachId, String template, List<ForeachReplaceable> data, String... placeholders) {

		String foreachStartIndicator = "<%foreach#" + foreachId;
		String foreachEndIndicator ="%>";
		int foreachStartIndex = template.indexOf(foreachStartIndicator);
		int foreachEndIndex = template.indexOf(foreachEndIndicator);
		
		String beforeForeachHtml = template.substring(0,foreachStartIndex);
		String foreachHtmlSegment = template.substring(foreachStartIndex+foreachStartIndicator.length(), foreachEndIndex).trim();
		String afterForeachHtml = template.substring(foreachEndIndex+foreachEndIndicator.length());
		
		String result = beforeForeachHtml;
		
		for (ForeachReplaceable object : data) {
			
			String replacedForeachHtml = foreachHtmlSegment;
			
			for (String placeholder : placeholders) {
				replacedForeachHtml = replacedForeachHtml.replace(placeholder, object.getTextByPlaceholder(placeholder));
			}
			
			result += replacedForeachHtml;
		}
		
		result += afterForeachHtml;
		
		return result;
	}
}
