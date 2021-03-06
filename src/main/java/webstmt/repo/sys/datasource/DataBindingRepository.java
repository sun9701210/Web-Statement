package webstmt.repo.sys.datasource;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import webstmt.entity.sys.datasource.DataBinding;

@Repository
public interface DataBindingRepository extends JpaRepository<DataBinding, Long> 
{
	@Query("select b from DataBinding b where b.template.id=:templateId")
	List<DataBinding> getAllDataBindingByTemplateId(@Param("templateId") long templateId);
	
	@Query("select b.id from DataBinding b where b.template.id = :templateId")
	List<Long> getAllDataBindingIdsByTemplateId(@Param("templateId") long templateId);
	
	@Query("select max(b.id) from DataBinding b")
	Long findNextId();
}
