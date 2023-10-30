package test.web.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import test.web.entity.Group_Entity;
import test.web.entity.Role_Entity;

public interface RoleRepository extends JpaRepository<Role_Entity, Long> {
	
	public List<Role_Entity> findByUserid(Integer userid);

	Optional<Role_Entity> findByUseridAndGroupEntity(Integer userid, Group_Entity group_entity);
}
