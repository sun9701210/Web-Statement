package webstmt.repo;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import webstmt.entity.AccountSummaryRecord;

@Repository
public interface AccountSummaryRecordRepository extends CommonRepository<AccountSummaryRecord, Long>
{
	@Query("select r from AccountSummaryRecord r where r.relationshipNo = :reln and r.date = :month order by r.accountNo")
	List<AccountSummaryRecord> getRecordsByDateRelationshipNo(@Param("month")String dateMonth, @Param("reln")String relationshipNo);
}
