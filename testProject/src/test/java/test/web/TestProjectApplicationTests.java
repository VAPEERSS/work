package test.web;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import jakarta.transaction.Transactional;
import test.web.entity.Group_Entity;
import test.web.entity.Question_Entity;
import test.web.repository.GroupRepository;
import test.web.repository.QuestionRepository;

@SpringBootTest
class TestProjectApplicationTests {

	@Autowired
	QuestionRepository questionRepository;
	
	@Autowired
	GroupRepository groupRepository;
	
	@Transactional
	@Test
	void contextLoads() {
		
		Optional<Group_Entity> a = groupRepository.findById(12L);
		List<Question_Entity> b = a.get().getQuestionentity();
			
		for (int i = 0; i < b.size(); i++) {
			System.out.println(b.get(i).getSubject());
		}
		
		}
	}

