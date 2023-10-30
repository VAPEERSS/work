package test.web.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import test.web.DataNotFoundException;
import test.web.entity.Answer_Entity;
import test.web.entity.Question_Entity;
import test.web.repository.AnswerRepository;
import test.web.repository.GroupRepository;
import test.web.repository.QuestionRepository;
import test.web.repository.UserRepository;

@RequiredArgsConstructor
@Service
public class QuestionService {

    private final QuestionRepository questionRepository;
    private final UserRepository userRepository;
    private final GroupRepository groupRepository;
    private final AnswerRepository answerRepository;
    
    public List<Question_Entity> getList() {
        return this.questionRepository.findAll();
    }
    
    public Page<Question_Entity> get_Group_List(int page, Long number) {
    	
    	List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.asc("regDate"));
        Pageable pageable = PageRequest.of(page, 10, Sort.by("id").descending());
        
        return this.questionRepository.findByGroupEntity(pageable, groupRepository.findByGroupId(number));
    }
      
    public Question_Entity getQuestion(Integer id) {  
        Optional<Question_Entity> question = this.questionRepository.findById(id);
        if (question.isPresent()) {
            return question.get();
        } else {
            throw new DataNotFoundException("question not found" + id);
        }
    }
    
    public void create(String subject, String content) {
        Question_Entity q = new Question_Entity();
        q.setSubject(subject);
        q.setContent(content);
        q.setRegdate(LocalDateTime.now());
        this.questionRepository.save(q);
    }
    
    public Page<Question_Entity> getList(int page) {
    	List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.asc("regDate"));
        
        Pageable pageable = PageRequest.of(page, 10, Sort.by("id").descending());
        return this.questionRepository.findAll(pageable);
    }
    
public void delete(Question_Entity question) {
    	
    	Answer_Entity answer = new Answer_Entity();
    	answer.setQuestion(question);
    	
    	answerRepository.delete(answer);
        this.questionRepository.delete(question);
    }
    
    public void modify(Question_Entity question, String subject, String content) {
        question.setSubject(subject);
        question.setContent(content);
        question.setModdate(LocalDateTime.now());
        this.questionRepository.save(question);
        
    }
    
}
