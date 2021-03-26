package webstmt.repo.sys;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import webstmt.entity.sys.SysUser;

@Repository
public interface SysUserRepository extends JpaRepository<SysUser, Long> 
{
	@Query("select s from SysUser s where s.username = :username")
	SysUser getUserByUsername(@Param("username")String username);
}
