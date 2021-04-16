package webstmt.service.sys;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;

import webstmt.entity.sys.Template;
import webstmt.repo.sys.TemplateRepository;

@Service
public class TemplateService 
{
	@Autowired
	private TemplateRepository repo;
	
	public List<Template> readAll()
	{
		return repo.findAll();
	}
	
	public void TemplateCount(){
		
		List<Template> allTemplates=repo.findAll();
		
		int templateCount=0;
		
		for(Template all:allTemplates){
			
			templateCount=repo.countByFolderId(all.getFolder().getId());			

		}
		
		System.out.println(templateCount);
		
	}
	
	public Long save(Template template)
	{
		String escapedHtml = HtmlUtils.htmlEscape(template.getContent());
		
		template.setContent(escapedHtml);
		
		repo.save(template);
		
		return template.getId();
	}
	
	public Template read(Long id)
	{
		Template template = repo.getOne(id);
		
		String unescapedHtml = HtmlUtils.htmlUnescape(template.getContent());
		
		template.setContent(unescapedHtml);
		
		return template;
	}
	
	
	
	public void remove(Long id)
	{
		repo.deleteById(id);
		
	}
	
	public List<Template> search(String templateName){
		
		List<Template> template=repo.getByName(templateName);		
		
		return template;
	}
	
	public List<Template> find(Long folderId){
		
		List<Template> template=repo.getByFolderId(folderId);
		
		return template;
	}
	
	
	public List<Template> oppmSearch(Long oppm){
		
		List<Template> template=repo.getByOppm(oppm);
		
		return template;
	}
}
