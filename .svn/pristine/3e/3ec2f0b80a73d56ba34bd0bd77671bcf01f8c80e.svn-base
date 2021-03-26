package webstmt.repo;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import webstmt.entity.AccountDetailRecordSaIn;

@Repository
public interface AccountDetailRecordSaInRepository extends  CommonRepository<AccountDetailRecordSaIn, Long>
{
	@Query("select r from AccountDetailRecordSaIn r where r.relationshipNo = :reln and r.dateMonth = :month order by r.accountNo, r.transactionDate")
	List<AccountDetailRecordSaIn> getRecordsByDateRelationshipNo(@Param("month")String dateMonth, @Param("reln")String relationshipNo);
}
