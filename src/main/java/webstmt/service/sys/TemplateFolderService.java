package webstmt.service.sys;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import webstmt.entity.sys.Template;
import webstmt.entity.sys.TemplateFolder;
import webstmt.entity.sys.datasource.LegalVehicle;
import webstmt.repo.sys.TemplateFolderRepository;
import webstmt.repo.sys.TemplateRepository;

@Service
public class TemplateFolderService {
	
	@Autowired
	private TemplateFolderRepository frepo;
	@Autowired
	private TemplateRepository trepo;
	
	private List<TemplateFolder> menu;
	
	
	public TemplateFolder findById(long id)
	{
		return frepo.getOne(id);
	}
	
	public Long getByName(String folderName){
		
		return frepo.getIdByName(folderName);
		
	}
	
	
	public List<TemplateFolder> findAllActive()
	{
		return frepo.findAllActiveFolder();
	}
	
	public List<TemplateFolder> findAllActiveByLegalVehicle(LegalVehicle legalVehicle)
	{
		return frepo.findAllActiveFolderByLegalVehicle(legalVehicle.toString());
	}
	
	public List<TemplateFolder> findAllActiveByMarket(String market) {
		return frepo.findAllActiveFolderByMarket(market);
	}
	
	public List<Object> load(){
		
		List<TemplateFolder> allFolders=frepo.findAll();

		for (TemplateFolder templateFolder : allFolders) {
			
			int folderCount = frepo.countByParentFolderId(templateFolder.getId());
			int fileCount = trepo.countByFolderId(templateFolder.getId());
			templateFolder.setFileAmount(folderCount+fileCount);
		}
		
		List<Object> menuAll=new ArrayList<Object>();
		
		this.menu=allFolders;
		
		for(TemplateFolder root:allFolders){			
							
			if(root.getParent()==null){
				
				Map<Object,Object> mapArr=new HashMap<Object,Object>();
				
				mapArr.put("id", root.getId());
				mapArr.put("name", root.getName());
				mapArr.put("description", root.getDescription());
				mapArr.put("fileAmount", root.getFileAmount());
				mapArr.put("active", root.isActive());
				
				mapArr.put("child", childList(root.getId()));
				
				menuAll.add(mapArr);
			}	
		}
		
		return menuAll;
		
	}

	private List<Object> childList(Long id) {
		
		List<Object> lists=new ArrayList<Object>();
		List<Template> allTemplates=trepo.findAll();
		Long[] parr=new Long[menu.size()+1];
		
		for(TemplateFolder tf:menu){
			
			if(tf.getParent()!=null){
				
				for(int j=0;j<menu.size();j++){
					parr[j]=tf.getParent().getId();
				}
			}
			
			Map<Object,Object> childArr=new LinkedHashMap<Object,Object>();
			
			if(tf.getParent()!=null&&tf.getParent().getId().equals(id)){
					
				childArr.put("id", tf.getId());
				childArr.put("name", tf.getName());
				childArr.put("description", tf.getDescription());
				childArr.put("active", tf.isActive());
				childArr.put("fileAmount", tf.getFileAmount());	
				childArr.put("parent", tf.getParent());
				childArr.put("parentId", tf.getParent().getId());
				for(int k=1;k<parr.length;k++){
					parr[k-1]=parr[k];
					childArr.put("lastId", parr[k-1]);
				}
				
				childArr.put("child", childList(tf.getId()));
				lists.add(childArr);
				
			}
			
	
			List<String> childrenList=new ArrayList<String>();
			
			for(Template all:allTemplates){
				
				if(tf.getName()==all.getFolder().getName()){ 
					
					childrenList.add(all.getOppm());
					
					for(int i=0;i<allTemplates.size();i++){
						
						//System.out.println(childrenList);
						
						childArr.put("childrenList", childrenList);
						
					}
					
					
				}	
				
				
			}
			
		}

		
		return lists;
	}
	
	
	public TemplateFolder update(TemplateFolder folder) {
	
		return frepo.save(folder);
	}
	
	public TemplateFolder create(TemplateFolder folder) {
		
		return frepo.save(folder);
	}
	
	public void remove(long id) {
		
		frepo.deleteById(id);
	}
}
