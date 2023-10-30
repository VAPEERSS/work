package test.web.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import test.web.entity.Calendar_Entity;
import test.web.entity.Group_Entity;


@Repository
public interface CalendarRepository extends JpaRepository<Calendar_Entity, Integer> {
	
	Page<Calendar_Entity> findAll(Pageable pageable);
	
	Page<Calendar_Entity> findByGroupEntity(Pageable pageable,Group_Entity entity);


    void deleteByGroupEntity(Group_Entity groupEntity);
}
