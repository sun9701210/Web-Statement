package webstmt.repo.sys;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import webstmt.entity.sys.Template;

@Repository
public interface TemplateRepository extends JpaRepository<Template, Long>, JpaSpecificationExecutor<Template> 
{
	@Query("select t from Template t where t.name like CONCAT('%',:tName,'%')")
	List<Template> getByName(@Param("tName")String templateName);
	
	@Query("select count(t) from Template t where t.folder.id = :folderId")
	Integer countByFolderId(@Param("folderId")Long folderId);
	
	@Query("select t from Template t where t.folder.id = :folderId")
	List<Template> getByFolderId(@Param("folderId")Long folderId);

	@Query("select t from Template t order by id desc")
	List<Template> getPageByIdDescending(Pageable pageable);
	
	@Query("select t from Template t where t.folder.id = :folderId")
	List<Template> getPageByFolderId(@Param("folderId")Long folderId, Pageable pageable);
	
	@Query("select t from Template t where t.oppm = :oppm")
	List<Template> getByOppm(@Param("oppm")Long oppm);
	
}
