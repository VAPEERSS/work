package test.web.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import test.web.entity.Group_Entity;


@Repository
public interface GroupRepository extends JpaRepository<Group_Entity, Long> {
	
	Group_Entity findByGroupId(Long i);
	Group_Entity findByGroupName(String groupname);

    List<Group_Entity> findByGroupNameContaining(String keyword);
}	
