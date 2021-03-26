package webstmt.repo.sys.datasource;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import webstmt.entity.sys.datasource.DatabaseDataSource;

@Repository
public interface DatabaseDataSourceRepository extends JpaRepository<DatabaseDataSource, Long> {

}
