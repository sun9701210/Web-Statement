package webstmt.repo;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import webstmt.entity.OneGlanceSummary;

@Repository
public interface OneGlanceSummaryRepository extends CommonRepository<OneGlanceSummary, Long>
{
	@Query("select s from OneGlanceSummary s where s.relationshipNo = :reln and s.statementDate = :month")
	List<OneGlanceSummary> getRecordsByDateRelationshipNo(@Param("month")String dateMonth, @Param("reln")String relationshipNo);
}
