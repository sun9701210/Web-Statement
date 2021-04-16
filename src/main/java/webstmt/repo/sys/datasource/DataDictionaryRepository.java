package webstmt.repo.sys.datasource;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import webstmt.entity.sys.datasource.DataDictionary;
import webstmt.entity.sys.datasource.SystemID;

@Repository
public interface DataDictionaryRepository extends JpaRepository<DataDictionary, Long> {

	@Query("select d from DataDictionary d where d.sysId = :sysId")
	List<DataDictionary> findAllBySystemId(@Param("sysId")SystemID sysId);
	
	@Query("select d from DataDictionary d where d.name like CONCAT('%',:name,'%')")
	List<DataDictionary> findByName(@Param("name")String name);
	
	
}
