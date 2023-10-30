package test.web.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import test.web.AnswerForm;
import test.web.auth.PrincipalDetails;
import test.web.entity.Answer_Entity;
import test.web.entity.Group_Entity;
import test.web.entity.Question_Entity;
import test.web.entity.Role_Entity;
import test.web.entity.User_Entity;
import test.web.repository.AnswerRepository;
import test.web.repository.QuestionRepository;
import test.web.repository.UserRepository;
import test.web.service.AnswerService;
import test.web.service.GroupService;
import test.web.service.QuestionService;

@RequestMapping("/")
@RequiredArgsConstructor
@Controller
public class AnswerController {

    private final QuestionService questionService;
    private final QuestionRepository questionRepository;
    private final AnswerService answerService;
    private final AnswerRepository answerRepository;
    private final GroupService groupService;
    private final UserRepository userRepository;

    @PostMapping("/view/{id}")
    public String createAnswer(@AuthenticationPrincipal PrincipalDetails principalDetails, Model model, @PathVariable("id") Integer id, 
            @Valid AnswerForm answerForm, BindingResult bindingResult) {
    	Question_Entity question = this.questionService.getQuestion(id);
        if (bindingResult.hasErrors()) {
            model.addAttribute("question", question);
            return "redirect:/view/"+question.getId()+"?groupid="+question.getGroupEntity().getGroupId();
        }
        
        this.answerService.create(question, answerForm.getContent(), principalDetails);
        question = this.questionService.getQuestion(id);
        model.addAttribute("question", question);
        return "redirect:/view/"+question.getId()+"?groupid="+question.getGroupEntity().getGroupId();
    }
    
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/remove/{id}")
    public String removeAnswer(@PathVariable("id") Integer id,  
    		@AuthenticationPrincipal PrincipalDetails principalDetails) {
    	

    	Answer_Entity answer = null;
    	
    	if (principalDetails != null) {
         
    	 answer = this.answerService.getAnswer(id);

         Group_Entity groupEntity = groupService.Group_Find_GroupId(answer.getQuestion().getGroupEntity().getGroupId());
         Role_Entity master = null;
          for(Role_Entity re : groupEntity.getRoleEntity()){
             if(re.getRole().equals("master") && re.getUserid().equals(principalDetails.getUser().getId())){
                master = re;
             }
          }
    		if (answer != null && (answer.getUserid().equals(principalDetails.getUser().getId()) || master != null)) {
		            
		           this.answerService.delete(answer);
		    }
    	}

          return "redirect:/view/"+answer.getQuestion().getId()+"?groupid="+answer.getQuestion().getGroupEntity().getGroupId();
    }
	
}