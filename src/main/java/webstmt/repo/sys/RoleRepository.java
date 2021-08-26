package webstmt.repo.sys;

import org.springframework.stereotype.Repository;

import webstmt.entity.sys.Role;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

	@Query("select r from Role r, Route rt where rt.id = :routeId")
	List<Role> getBindingRolesByRoute(@Param("routeId") long routeId);
}
