package webstmt.service.sys;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import webstmt.entity.sys.TemplateVersion;
import webstmt.repo.sys.TemplateVersionRepository;

@Service
public class TemplateVersionService {

	@Autowired
	private TemplateVersionRepository repo;
	
	public TemplateVersion findVersionByUUIDAndTemplateId(String uuid, long templateId)
	{
		return repo.findByUUIDAndTemplateId(uuid, templateId);
	}
	
	public TemplateVersion save(TemplateVersion version)
	{
		return repo.save(version);
	}
	
	public static String generateUUID()
	{
		UUID uuid = UUID.randomUUID();
		return uuid.toString();
	}
}
