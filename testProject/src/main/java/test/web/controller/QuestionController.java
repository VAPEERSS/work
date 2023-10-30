package test.web.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.RequiredArgsConstructor;
import test.web.AnswerForm;
import test.web.auth.PrincipalDetails;
import test.web.dto.EditDTO;
import test.web.dto.QuestionDTO;
import test.web.entity.Answer_Entity;
import test.web.entity.Group_Entity;
import test.web.entity.Question_Entity;
import test.web.entity.Role_Entity;
import test.web.entity.User_Entity;
import test.web.repository.AnswerRepository;
import test.web.repository.GroupRepository;
import test.web.repository.QuestionRepository;
import test.web.repository.RoleRepository;
import test.web.repository.UserRepository;
import test.web.service.AnswerService;
import test.web.service.GroupService;
import test.web.service.QuestionService;

@RequestMapping("/")
@RequiredArgsConstructor
@Controller
public class QuestionController {

	private final QuestionService questionService;	
	private final GroupService groupService;
	private final AnswerService answerService;
	private final AnswerRepository answerRepository;
	private final QuestionRepository questionRepository;	
	private final UserRepository userRepository;
	private final RoleRepository rolereposotory;
	private final GroupRepository groupRepository;
	

	@GetMapping("/board")
    public String board(Model model, @RequestParam(value="page", defaultValue="0") int page, @AuthenticationPrincipal PrincipalDetails principalDetails) {

		Page<Question_Entity> paging = this.questionService.get_Group_List(page, 1L);
		        model.addAttribute("paging", paging);
		        
		       // model.addAttribute("group", "커뮤니티");
		        
		        List<Role_Entity> bb = rolereposotory.findByUserid(principalDetails.getUser().getId());
		        List<Group_Entity> cc = new ArrayList<>();
		        
		        for (int j = 0; j < bb.size(); j++) {
					
		        	cc.add(bb.get(j).getGroupEntity());
		        	
				}  
		        
		        System.out.println(cc.size() + "========");


				model.addAttribute("userid", principalDetails.getUser().getId());
				model.addAttribute("grouplist", cc);
		        model.addAttribute("groupid", 1L);
		        return "board";
	}
    
	
	
	@GetMapping(value = "/board/{id}")
    public String board(Model model, @RequestParam(value="page", defaultValue="0") int page, @PathVariable("id") Long id , @AuthenticationPrincipal PrincipalDetails principalDetails) {
		/*여긴 게시판의 그룹별 페이징 처리*/
		Page<Question_Entity> paging = this.questionService.get_Group_List(page, id);
			
		model.addAttribute("paging", paging);
        
		List<Role_Entity> aa = groupService.Group_Find_GroupId(id).getRoleEntity();

		for (int i = 0; i < aa.size(); i++) {
			if (aa.get(i).getUserid() == principalDetails.getUser().getId() && aa.get(i).getRole().equals("master"))
			{
				model.addAttribute("group", groupService.Group_Find_GroupId(id).getGroupName());
				 
				List<Role_Entity> bb = rolereposotory.findByUserid(principalDetails.getUser().getId());
				List<Group_Entity> cc = new ArrayList<>();

				for (int j = 0; j < bb.size(); j++) {
						
					 cc.add(bb.get(j).getGroupEntity());
			        	
				}
				System.out.println(cc.size() + "========");

				model.addAttribute("role", "master");
				model.addAttribute("userid", principalDetails.getUser().getId());
				model.addAttribute("grouplist", cc);
				model.addAttribute("groupid",id);
			        
				return "board";
			}
			else if(aa.get(i).getUserid() == principalDetails.getUser().getId() && aa.get(i).getRole().equals("user"))
			{
			    List<Role_Entity> bb = rolereposotory.findByUserid(principalDetails.getUser().getId());
		        
		        for (int j = 0; j < bb.size(); j++) {
					
		        	System.err.println(bb.get(j).getRole() + "----------");
		        	
				}
				model.addAttribute("role", "user");
				model.addAttribute("userid", principalDetails.getUser().getId());
				model.addAttribute("group", groupService.Group_Find_GroupId(id).getGroupName());
				model.addAttribute("groupid",id);
				return "board";
			}

		};
	
		//model.addAttribute("grouplist", cc);
        //List<Group_Entity> list = groupService.findAll();

        return "redirect:/main2";
    }
	
	
	 @GetMapping(value = "/view/{id}")
	 public String view(Model model, @PathVariable("id") Integer id, AnswerForm answerForm,
						@RequestParam("groupid") Long groupId, Role_Entity role,
						@AuthenticationPrincipal PrincipalDetails principalDetails) {
    
    	Question_Entity question = this.questionService.getQuestion(id);
    	Optional<Answer_Entity> answer = this.answerRepository.findById(id);
    	Group_Entity group = this.groupRepository.findByGroupId(groupId);

		 for(Role_Entity re : group.getRoleEntity()){
			 if(re.getRole().equals("master") && re.getUserid().equals(principalDetails.getUser().getId())){
				 model.addAttribute("role", "master");
			 }
		 }
    	
    	model.addAttribute("answer", answer);
        model.addAttribute("question", question);
        model.addAttribute("groupid",groupId);
        model.addAttribute("userid",  principalDetails.getUser().getId());
    	
        return "view";
    }
	 
	 
	 @PostMapping("/board")
	 public String view(Model model, @RequestParam(value="page", defaultValue="0") int page) {
		 
	        Page<Question_Entity> paging = this.questionService.getList(page);
	        model.addAttribute("paging", paging);
	        
	        return "board";
	}
	 
	 
	 
	 @PreAuthorize("isAuthenticated()")
	 @GetMapping("/delete/{id}")
	 public String questionDelete(@PathVariable("id") Integer id, @AuthenticationPrincipal PrincipalDetails principalDetails) {
	    Question_Entity question = null;
	      
	       if (principalDetails != null) {
	              // 주어진 ID로 질문을 조회합니다.
	           question = this.questionService.getQuestion(id);
	           Group_Entity groupEntity = groupService.Group_Find_GroupId(question.getGroupEntity().getGroupId());
	           Role_Entity master = null;
	            for(Role_Entity re : groupEntity.getRoleEntity()){
	               if(re.getRole().equals("master")){
	                  master = re;
	               }
	            }
	              // 질문이 존재하고, 현재 사용자의 ID와 질문의 작성자 ID가 일치하는 경우에만 삭제를 허용합니다.
	              if (question != null && (question.getUserid().equals(principalDetails.getUser().getId())
	                  || master.getUserid().equals(principalDetails.getUser().getId()))) {
	                  // 질문 삭제를 서비스에 요청합니다.
	                  this.questionService.delete(question);
	              }
	          }
	          if(question.getGroupEntity().getGroupId() == 1){
	            return "redirect:/board";
	         }
	          return "redirect:/board/"+question.getGroupEntity().getGroupId();
	      
	   }
	 
	 @GetMapping("/write/{id}")
	 public String write(Model model,@AuthenticationPrincipal PrincipalDetails principalDetails,
	 	@PathVariable("id") Long id){
		 	
		 
		 	if(principalDetails != null) {
		 		Optional<User_Entity> a = userRepository.findById(principalDetails.getUser().getId());
		 		
				model.addAttribute("id", a.get().getId());
				model.addAttribute("username", a.get().getUsername());
		        
				List<Group_Entity> list = groupService.findAll();
		        model.addAttribute("grouplist", list);
				model.addAttribute("groupid", id);
				
				return "/write";
			}
			
			else {
							
				return "/main";
			} 
	 }
	 
	 
	 @PostMapping("/write")
	 public String questionCreate(
			 Model model,
			 Question_Entity question,
			 @RequestParam(value="page", defaultValue="0") int page,
			 @AuthenticationPrincipal PrincipalDetails principalDetails,
			 @RequestParam(value="groupName", defaultValue="전체게시판") String name
			 	 
		){
		 
		 Group_Entity group =  groupService.find_GroupName(name);
		 question.setGroupEntity(group);
		 question.setRegdate(LocalDateTime.now());
		 question.setUserid(principalDetails.getUser().getId());
		 question.setUsername(principalDetails.getUser().getUsername());
		 
		 
		 questionRepository.save(question);
		 
		 Page<Question_Entity> paging = this.questionService.getList(page);
	        
	     model.addAttribute("paging", paging);
		 
		 if(group.getGroupId() == 1){
			 return "redirect:/board";
		 }
	     return "redirect:/board/"+group.getGroupId();
	 }
	 
	
	 @GetMapping(value = "/edit/{id}")
	    public String edit(Question_Entity question, Model model){
	    		
	    	Question_Entity questions = this.questionService.getQuestion(question.getId());
	    	model.addAttribute("question", questions);
	
	        return "/edit";
	    }
	 
	    
	    @PostMapping("/edit")
	    public String edit(Model model, EditDTO editDTO) { 
	    	System.out.println("실행됨");
	    	Question_Entity question = this.questionService.getQuestion(editDTO.getId());
	        
	    	question.setSubject(editDTO.getSubject());
	    	question.setContent(editDTO.getContent());
	    	question.setModdate(LocalDateTime.now());
	    
	    	questionRepository.save(question);
	    	
	    	model.addAttribute("question", question);
	    	
	    	return "redirect:/view/"+question.getId()+"?groupid="+question.getGroupEntity().getGroupId();
	    }
	    
	   
	 
}


