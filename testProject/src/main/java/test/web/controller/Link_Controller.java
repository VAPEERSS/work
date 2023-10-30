package test.web.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import test.web.auth.PrincipalDetails;
import test.web.config.SecurityConfig;
import test.web.entity.Calendar_Entity;
import test.web.entity.Group_Entity;
import test.web.entity.Question_Entity;
import test.web.entity.Role_Entity;
import test.web.entity.User_Entity;
import test.web.repository.CalendarRepository;
import test.web.repository.GroupRepository;
import test.web.repository.QuestionRepository;
import test.web.repository.RoleRepository;
import test.web.repository.UserRepository;
import test.web.service.CalendarService;
import test.web.service.GroupService;
import test.web.service.QuestionService;

@Controller
@RequiredArgsConstructor
public class Link_Controller {
	
	private final UserRepository user_repository;
	private final GroupService groupService;
	private final GroupRepository groupRepository;
	private final QuestionService questionService;
	private final QuestionRepository questionRepository;
	private final UserRepository userRepository;
	private final RoleRepository rolereposotory;
	private final CalendarService calendarService;
	private final CalendarRepository calendarRepository;
	
	@GetMapping("/")
	public String root(Model model){
	
		return "main";
	}

	@GetMapping("/user")
	public @ResponseBody String user(@AuthenticationPrincipal PrincipalDetails principalDetails){
		
		return principalDetails.getUser().toString();
	}
	
	
	@GetMapping("/test/login")
	public @ResponseBody String testlogin(Authentication authentication, @AuthenticationPrincipal PrincipalDetails userDetails){
		
		try {
			PrincipalDetails principalDetails = (PrincipalDetails)authentication.getPrincipal();
			
			System.out.println(principalDetails.getUser());
		
		
		} catch (Exception e) {
			System.out.println(e.getMessage());	
		}
		
		return "세선졍보확인하기";
	}
	
	@GetMapping("/index")
	public String index(){
	
		return "index";
	}
	
	
	@PostMapping("/join")
	public String join(User_Entity user){
		
		User_Entity user_join = user_repository.findByEmailAndProvider(user.getEmail(),"client");
		
		if(user_join == null) {
			user.setProvider("client");
			user.setRole("USER");
			user.setPassword(SecurityConfig.incoding_password().encode(user.getPassword()));
			user.setDatetime(LocalDateTime.now());	
			user_repository.save(user);
		
			return "redirect:/login";
		}
		else {
			return "redirect:/login";
		}		
	}
	

	
	@GetMapping("/main2")
	public String loginCheck(Model model, HttpSession httpSession,  @RequestParam(value="page", defaultValue="0") int page,@AuthenticationPrincipal PrincipalDetails principalDetails) {
		
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
	        	httpSession.setAttribute("grouplist", cc);
			
			
			
			//List<Group_Entity> list = groupService.findAll();
	        //model.addAttribute("grouplist", list);
	        
			return "/main2";
			
		}else {
			return "/login";
		}
	}
	
	@GetMapping("/calendar/{id}")
	   public String Calendar(Model model, @RequestParam(value="page", defaultValue="0") int page, 
			   Calendar_Entity calendar, @PathVariable("id") Long id, 
			   @AuthenticationPrincipal PrincipalDetails principalDetails) {
		
		
	    Page<Calendar_Entity> cpaging = this.calendarService.get_Group_List(page, id);
	    model.addAttribute("cpaging", cpaging);
		
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
				model.addAttribute("listcal", calendarService.getfindByGroup(groupService.Group_Find_GroupId(id),page));
				   
				return "calendar";
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
				model.addAttribute("listcal", calendarService.getfindByGroup(groupService.Group_Find_GroupId(id),page));
				
				return "calendar";
			
			}
			
		};
		
		return "redirect:/main2";
}
	
	@GetMapping("/scheduler/{id}")
	public String gCalendar(Model model, @AuthenticationPrincipal PrincipalDetails principalDetails, 
			@PathVariable("id") Long id) {
		
		
		
		if(principalDetails != null) {
	 		Optional<User_Entity> a = userRepository.findById(principalDetails.getUser().getId());
	 		
			model.addAttribute("id", a.get().getId());
			model.addAttribute("username", a.get().getUsername());
	        
			List<Group_Entity> list = groupService.findAll();
	        model.addAttribute("grouplist", list);
	        model.addAttribute("groupid", id);
	        
			
			
			return "/scheduler";
		}
		
		else {
						
			return "/main";
		} 
	}
	
	@PostMapping("/scheduler")
	public String gScheduler(
			Model model, 
			Question_Entity question,
			Calendar_Entity calendar,
			@AuthenticationPrincipal PrincipalDetails principalDetails,
			@RequestParam(value="page", defaultValue="0") int page,
			@RequestParam(value="groupName", defaultValue="전체게시판") String name,
			@RequestParam String eventContent, 
			@RequestParam LocalDate eventDate, 
			@RequestParam LocalDate endDate,
			@RequestParam Long ids
			) {

		
		 Group_Entity group =  groupService.find_GroupName(name);
//	       question.setGroupEntity(group);
//	       question.setRegdate(LocalDateTime.now());
//	       question.setUserid(principalDetails.getUser().getId());
//	       question.setUsername(principalDetails.getUser().getUsername());
//
//
//	       questionRepository.save(question);
	       
	       Page<Calendar_Entity> paging = this.calendarService.getList(page);
	        
		     model.addAttribute("paging", paging);
	       
		
	       calendarService.schedule(eventContent, eventDate, endDate, principalDetails, group);
	       
	       if(group.getGroupId() == 1){
	    	   model.addAttribute("listcal1", calendarService.getfindByGroup(groupService.Group_Find_GroupId(ids),page));
	    	   
	    	   return "redirect:/calendar";
			 }
	       
	       
	       model.addAttribute("listcal", calendarService.getfindByGroup(groupService.Group_Find_GroupId(ids),page));
	       
	       
	       return "redirect:/calendar/"+group.getGroupId();
	}
	
	@PostMapping("/success")
	public String success(@AuthenticationPrincipal PrincipalDetails principalDetails, Model model, Integer sno) {
		Integer userid = principalDetails.getUser().getId();
		Calendar_Entity calendar = calendarService.getCalendar(sno);
		
		model.addAttribute("완료", calendar.getSuccess());
		
		return "redirect:/calendar";
		
	    
	}
	
	@GetMapping("/map")
	public String map() {
		
		return "map";
	}
	
	
	
	@Secured("ADMIN")
	@GetMapping("/info")
	public String info() {
		
		return "info";
	}
	
}
