package test.web.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import test.web.entity.Group_Entity;
import test.web.entity.Question_Entity;




public interface QuestionRepository extends JpaRepository<Question_Entity, Integer> {
	
	Optional<Question_Entity> findById(Integer id);
	
	Question_Entity findBySubject(String subject);
	
    Question_Entity findBySubjectAndContent(String subject, String content);
    
    List<Question_Entity> findBySubjectLike(String subject);
    
    Page<Question_Entity> findAll(Pageable pageable);
    
    Page<Question_Entity> findByGroupEntity(Pageable pageable,Group_Entity entity);

   
}