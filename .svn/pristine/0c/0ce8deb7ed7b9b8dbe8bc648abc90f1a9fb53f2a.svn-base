package webstmt.repo.sys;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import webstmt.entity.sys.Template;

@Repository
public interface TemplateRepository extends JpaRepository<Template, Long> 
{
	@Query("select t from Template t where t.name like CONCAT('%',:tName,'%')")
	List<Template> getByName(@Param("tName")String templateName);
	
	@Query("select count(t) from Template t where t.folder.id = :folderId")
	Integer countByFolderId(@Param("folderId")Long folderId);
	
//	@Query("select t from Template where t.folder.id = :folderId")
//	List<Template> getByFolderId(@Param("folderId")Long folderId);
	
	@Query("select t from Template t where t.oppm = :oppm")
	List<Template> getByOppm(@Param("oppm")Long oppm);
	
}
