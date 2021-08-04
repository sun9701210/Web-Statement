package webstmt.repo.sys;

import org.springframework.stereotype.Repository;

import webstmt.entity.sys.Role;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

}
