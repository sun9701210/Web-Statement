package webstmt.repo.sys;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import webstmt.entity.sys.TemplateFolder;
import webstmt.entity.sys.datasource.LegalVehicle;

@Repository
public interface TemplateFolderRepository extends JpaRepository<TemplateFolder, Long>{
	
	@Query("select t from TemplateFolder t where t.parent.id = :parentId")
	List<TemplateFolder> findAllByParentId(@Param("parentId")Long parentId);
	
	@Query("select count(tf) from TemplateFolder tf where tf.parent.id = :folderId")
	Integer countByParentFolderId(@Param("folderId")Long folderId);
	
	@Query("select f from TemplateFolder f where f.active = true")
	List<TemplateFolder> findAllActiveFolder();

	@Query("select f.id from TemplateFolder f where f.name = :folderName")
	Long getIdByName(@Param("folderName")String folderName);
	
	@Query("select f from TemplateFolder f where f.legalVehicle = :legalVehicle and f.active = true")
	List<TemplateFolder> findAllActiveFolderByLegalVehicle(@Param("legalVehicle")String legalVehicle);
}
