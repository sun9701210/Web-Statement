package webstmt.service.sys;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;

import webstmt.entity.sys.Template;
import webstmt.repo.sys.TemplateQueryCriteria;
import webstmt.repo.sys.TemplateRepository;

@Service
public class TemplateService 
{
	@Autowired
	private TemplateRepository repo;
	
	public long count()
	{
		return repo.count();
	}
	
	public long count(TemplateQueryCriteria templateCriteria) {
		
		return repo.count(new Specification<Template>() {

			private static final long serialVersionUID = -2332363994744353782L;
			private List<Predicate> predicates = new ArrayList<Predicate>();

			@Override
			public Predicate toPredicate(Root<Template> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

				if(templateCriteria.getNameWildcard()!=null) {
					
					predicates.add(cb.like(cb.lower(root.get("name")), "%"+templateCriteria.getNameWildcard().toLowerCase() + "%"));
					
				}
				
				if(templateCriteria.getMarket()!=null) {
					
					predicates.add(cb.equal(root.get("market"), templateCriteria.getMarket()));
				}
				
				if(templateCriteria.getPriority()!=null) {
					
					predicates.add(cb.equal(root.get("priority"), templateCriteria.getPriority()));
				}
				
				if(templateCriteria.getTypeId()!=null) {
					
					predicates.add(cb.equal(root.get("folder").get("id"), templateCriteria.getTypeId()));
				}
				
				return cb.and(predicates.toArray(new Predicate[0]));
			}
		});
	}
	
	public List<Template> readAll()
	{
		return repo.findAll();
	}
	
	public List<Template> readPage(int page, int limit) {
		
		return repo.findAll(PageRequest.of(page, limit)).toList();
	}
	
	public List<Template> readPage(TemplateQueryCriteria templateCriteria, int page, int limit) {
		
		Pageable pageable = PageRequest.of(page, limit);
		
		return repo.findAll(new Specification<Template>() {

			private static final long serialVersionUID = -2332363994744353782L;
			private List<Predicate> predicates = new ArrayList<Predicate>();
			
			@Override
			public Predicate toPredicate(Root<Template> root, CriteriaQuery<?> query,CriteriaBuilder cb) {
				
				if(templateCriteria.getNameWildcard()!=null) {
					
					predicates.add(cb.like(cb.lower(root.get("name")), "%"+templateCriteria.getNameWildcard().toLowerCase() + "%"));
					
				}
				
				if(templateCriteria.getMarket()!=null) {
					
					predicates.add(cb.equal(root.get("market"), templateCriteria.getMarket()));
				}
				
				if(templateCriteria.getPriority()!=null) {
					
					predicates.add(cb.equal(root.get("priority"), templateCriteria.getPriority()));
				}
				
				if(templateCriteria.getTypeId()!=null) {
					
					predicates.add(cb.equal(root.get("folder").get("id"), templateCriteria.getTypeId()));
				}
				
				if("-id".equalsIgnoreCase(templateCriteria.getIdSort())) {
					
					query.orderBy(cb.desc(root.get("id")));
					
				} else {
					
					query.orderBy(cb.asc(root.get("id")));
				}
				
				return cb.and(predicates.toArray(new Predicate[0]));
			}
			
		}, pageable).toList();
	}
	
	public List<Template> readPageByIdDescending(int page, int limit) {
		
		return repo.getPageByIdDescending(PageRequest.of(page, limit));
	}
	
	public List<Template> readAllByFolder(long folderId) {
	
		return repo.getByFolderId(folderId);
	}
	
	public List<Template> readPageByFolder(long folderId, int page, int size) {
		
		return repo.getPageByFolderId(folderId, PageRequest.of(page, size));
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
