package test.web.service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import test.web.DataNotFoundException;
import test.web.auth.PrincipalDetails;
import test.web.entity.Answer_Entity;
import test.web.entity.Question_Entity;
import test.web.repository.AnswerRepository;

@RequiredArgsConstructor
@Service
public class AnswerService {

    private final AnswerRepository answerRepository;

    
    public void create(Question_Entity question, String content, PrincipalDetails principalDetails) {
    	
        Answer_Entity answer = new Answer_Entity();
        answer.setContent(content);
        answer.setQuestion(question);
        answer.setUserid(principalDetails.getUser().getId());
        answer.setUsername(principalDetails.getUser().getUsername());
        answer.setRegdate(LocalDateTime.now());
        
        this.answerRepository.save(answer);
    }
    
    public Answer_Entity getAnswer(Integer id) {
    	
    	Optional<Answer_Entity> answer = this.answerRepository.findById(id);
    	
    	if (answer.isPresent()) {
            return answer.get();
        } else {
            throw new DataNotFoundException("answer not found" + id);
        }
    	
    }
    
    public void delete(Answer_Entity answer_Entity) {
    	
    	this.answerRepository.delete(answer_Entity);
    }
    

}

