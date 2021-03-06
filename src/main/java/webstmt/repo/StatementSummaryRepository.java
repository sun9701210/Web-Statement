package webstmt.repo;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import webstmt.entity.StatementSummary;

@Repository
public interface StatementSummaryRepository extends CommonRepository<StatementSummary, Long> {
	
	@Override
	@Query("select s from StatementSummary s where s.relationshipNo = :reln and s.statementDate = :month")
	List<StatementSummary> getRecordsByDateRelationshipNo(@Param("month")String dateMonth, @Param("reln")String relationshipNo);
}
