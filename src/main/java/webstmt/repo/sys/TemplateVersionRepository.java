package webstmt.repo.sys;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import webstmt.entity.sys.TemplateVersion;

@Repository
public interface TemplateVersionRepository extends JpaRepository<TemplateVersion, Long> {

	@Query("select v from TemplateVersion v where v.UUID = :uuid and v.template.id = :tplId")
	TemplateVersion findByUUIDAndTemplateId(@Param("uuid") String UUID, @Param("tplId") long templateId);
}
