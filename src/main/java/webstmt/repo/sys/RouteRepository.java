package webstmt.repo.sys;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import webstmt.entity.sys.Route;

@Repository
public interface RouteRepository extends JpaRepository<Route, Long> {

	@Query("select r from Route r where r.name = 'Root'")
	Route getRootNode();
	
	@Query("select r from Route r where r.parent.id = :parentId")
	List<Route> getChildren(@Param("parentId") long parentId);
	
	@Query("select r from Route r where r.parent is null")
	List<Route> getTopLevelRoutes();
}
