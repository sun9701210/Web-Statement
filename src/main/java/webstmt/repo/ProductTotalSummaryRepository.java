package webstmt.repo;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import webstmt.entity.ProductTotalSummary;

@Repository
public interface ProductTotalSummaryRepository extends CommonRepository<ProductTotalSummary, Long>
{
	@Query("select s from ProductTotalSummary s where s.relationshipNo = :reln and s.statementDate = :month")
	List<ProductTotalSummary> getRecordsByDateRelationshipNo(@Param("month")String dateMonth, @Param("reln")String relationshipNo);
}
