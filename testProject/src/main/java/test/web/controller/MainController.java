
package test.web.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.management.loading.PrivateClassLoader;

import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.RequiredArgsConstructor;
import test.web.auth.PrincipalDetails;
import test.web.dto.SearchResult;
import test.web.entity.Group_Entity;
import test.web.entity.Question_Entity;
import test.web.entity.Role_Entity;
import test.web.entity.User_Entity;
import test.web.repository.GroupRepository;
import test.web.repository.QuestionRepository;
import test.web.repository.RoleRepository;
import test.web.repository.UserRepository;
import test.web.service.GroupService;
import test.web.service.QuestionService;
import test.web.service.RoleService;

@Controller
@RequiredArgsConstructor
public class MainController {
	
	
	private final UserRepository user_repository;
	private final QuestionService questionService;
	private final QuestionRepository questionRepository;
	private final UserRepository userRepository;
	private final RoleRepository rolereposotory;
	private final GroupService groupService;
	private final GroupRepository groupRepository;
	private final RoleService roleservice;
	

	
	@GetMapping("/group_make")
	public String viewGroup_make(@AuthenticationPrincipal PrincipalDetails principalDetails) {
		
		if (principalDetails != null) {
			
			return "/group/group_make"; 
		}
		else {
			return "/main"; 
		}
	}
	
	@PostMapping("/group_make")
	public String viewGroup_make(Group_Entity group_entity, @AuthenticationPrincipal PrincipalDetails principalDetails) {
		group_entity.setTime(LocalDate.now());
		groupService.save(group_entity);
		
		Group_Entity cc = groupService.find_GroupName(group_entity.getGroupName());
		
		Role_Entity aa = new Role_Entity();
		aa.setRole("master");
		aa.setUserid(principalDetails.getUser().getId());
		aa.setGroupEntity(cc);
		roleservice.save(aa);


		return "redirect:/main2";
	}
	
	@GetMapping("/group_notice")
    public String viewGroup_Notice(Model model,@RequestParam(value="page", defaultValue="0") int page,@AuthenticationPrincipal PrincipalDetails principalDetails) {
		
		if (principalDetails != null) {
			
			
			Page<Question_Entity> paging = this.questionService.getList(page);
	        model.addAttribute("paging", paging);
	        model.addAttribute("group", "커뮤니티");
	        		 
		    List<Role_Entity> bb = rolereposotory.findByUserid(principalDetails.getUser().getId());
	        List<Group_Entity> cc = new ArrayList<>();
	        
	        for (int j = 0; j < bb.size(); j++) {
				
	        	cc.add(bb.get(j).getGroupEntity());
	        	
			}  
	  
	        System.out.println(bb.size() + "-----");
	        
	        	model.addAttribute("grouplist", cc);
			
			
			
			//List<Group_Entity> list = groupService.findAll();
	        //model.addAttribute("grouplist", list);
	        
	        	return "group/group_notice";
			
		}else {
			return "/login";
		}
		
		
    }
    
    @GetMapping("/group_notice_view")
    public String viewGroup_NoticeView(Model model,@RequestParam(value="page", defaultValue="0") int page,@AuthenticationPrincipal PrincipalDetails principalDetails) {
		
		if (principalDetails != null) {
			
			
			Page<Question_Entity> paging = this.questionService.getList(page);
	        model.addAttribute("paging", paging);
	        model.addAttribute("group", "커뮤니티");
	        		 
		    List<Role_Entity> bb = rolereposotory.findByUserid(principalDetails.getUser().getId());
	        List<Group_Entity> cc = new ArrayList<>();
	        
	        for (int j = 0; j < bb.size(); j++) {
				
	        	cc.add(bb.get(j).getGroupEntity());
	        	
			}  
	  
	        System.out.println(bb.size() + "-----");
	        
	        	model.addAttribute("grouplist", cc);
			
			
			
			//List<Group_Entity> list = groupService.findAll();
	        //model.addAttribute("grouplist", list);
	        
	        	
	        	return "group/group_notice_view"; 
			
		}else {
			return "/login";
		}
    }
    
    @GetMapping("/group_manager")
    public String viewGroup_manager(Model model,@RequestParam(value="page", defaultValue="0") int page,@AuthenticationPrincipal PrincipalDetails principalDetails) {
		
		if (principalDetails != null) {
			
			
			Page<Question_Entity> paging = this.questionService.getList(page);
	        model.addAttribute("paging", paging);
	        model.addAttribute("group", "커뮤니티");
	        		 
		    List<Role_Entity> bb = rolereposotory.findByUserid(principalDetails.getUser().getId());
	        List<Group_Entity> cc = new ArrayList<>();
	        
	        for (int j = 0; j < bb.size(); j++) {
				
	        	cc.add(bb.get(j).getGroupEntity());
	        	
			}  
	  
	        System.out.println(bb.size() + "-----");
	        
	        	model.addAttribute("grouplist", cc);
			
			
			
			//List<Group_Entity> list = groupService.findAll();
	        //model.addAttribute("grouplist", list);
	        
	        	return "group/group_manager"; 
			
		}else {
			return "/login";
		}
    	
    }
	
	
	@GetMapping("/main")
    public String viewMain(@AuthenticationPrincipal PrincipalDetails principalDetails) {
       
		if (principalDetails != null) {
			return "/main2";
		}else {
			return "/main";
		}
    }

    
    @GetMapping("/login")
    public String viewLogin() {
    	return "login"; 
    }
    
    @GetMapping("/join")
    public String viewJoin() {
    	return "join"; 
    }
    
    @GetMapping("/notice")
    public String viewNotice(Model model,@RequestParam(value="page", defaultValue="0") int page,@AuthenticationPrincipal PrincipalDetails principalDetails) {
        
        if (principalDetails != null) {
           
           
           Page<Question_Entity> paging = this.questionService.getList(page);
             model.addAttribute("paging", paging);
             model.addAttribute("group", "커뮤니티");
                    
            List<Role_Entity> bb = rolereposotory.findByUserid(principalDetails.getUser().getId());
             List<Group_Entity> cc = new ArrayList<>();
             
             for (int j = 0; j < bb.size(); j++) {
              
                cc.add(bb.get(j).getGroupEntity());
                
           }  
       
             System.out.println(bb.size() + "-----");
             
                model.addAttribute("grouplist", cc);
           
           
           
           //List<Group_Entity> list = groupService.findAll();
             //model.addAttribute("grouplist", list);
             
                return "notice";
             
           
        }else {
           return "/login";
        }
    }
    
    @GetMapping("/notice_view")
    public String viewNoticeView() {
    	return "notice_view"; 
    }
    
    @GetMapping("/QnA")
    public String viewQnA(Model model,@RequestParam(value="page", defaultValue="0") int page,@AuthenticationPrincipal PrincipalDetails principalDetails) {
        
        if (principalDetails != null) {
           
           
           Page<Question_Entity> paging = this.questionService.getList(page);
             model.addAttribute("paging", paging);
             model.addAttribute("group", "커뮤니티");
                    
            List<Role_Entity> bb = rolereposotory.findByUserid(principalDetails.getUser().getId());
             List<Group_Entity> cc = new ArrayList<>();
             
             for (int j = 0; j < bb.size(); j++) {
              
                cc.add(bb.get(j).getGroupEntity());
                
           }  
       
             System.out.println(bb.size() + "-----");
             
                model.addAttribute("grouplist", cc);
           
           
           
           //List<Group_Entity> list = groupService.findAll();
             //model.addAttribute("grouplist", list);
             
                return "QnA"; 

           
        }else {
           return "/login";
        }
    }
    
   
    
    @GetMapping("/group_intro/{groupId}")
    public String viewGroup_intro(@PathVariable("groupId") Long groupId, Model model) {
    	
    	
    	Group_Entity group = this.groupRepository.findByGroupId(groupId);
    	
        model.addAttribute("groupId", groupId);
        model.addAttribute("groupName", group.getGroupName());
        model.addAttribute("groupInc", group.getIntroduction());
        model.addAttribute("time", group.getTime());
        
    	return "group/group_intro"; 
    }

	@GetMapping("/group_notice_write")
	public String viewnoticewrite() {
		return "group/group_notice_write";
	}

	@GetMapping("/group_notice_edit")
	public String viewnoticeedit() {
		return "group/group_notice_edit";
	}

	@PostMapping("/search")
	public String searchGroups(String keyword, @AuthenticationPrincipal PrincipalDetails principalDetails, Model model) {
		Integer userid = principalDetails.getUser().getId();
		List<Group_Entity> groups = groupService.searchGroup(keyword);
		List<SearchResult> searchResults = groupService.toSearchForm(groups, userid);

		model.addAttribute("groups", searchResults);
		return "search_results";
	}

}
