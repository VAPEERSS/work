package test.web.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import test.web.entity.Answer_Entity;
import java.util.List;




public interface AnswerRepository extends JpaRepository<Answer_Entity, Integer> {
	
	
		
}