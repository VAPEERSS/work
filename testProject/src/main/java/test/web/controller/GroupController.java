package test.web.controller;

import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import test.web.auth.PrincipalDetails;
import test.web.entity.Group_Entity;
import test.web.entity.Role_Entity;
import test.web.repository.GroupRepository;
import test.web.service.GroupService;
import test.web.service.RoleService;

@Controller
@RequiredArgsConstructor
public class GroupController {

    private final RoleService roleService;
    private final GroupService groupService;
    private final GroupRepository groupRepository;

    @PostMapping("/group/join")
    public String joinMember(@RequestParam("groupId") Long groupId, @AuthenticationPrincipal PrincipalDetails principalDetails){
        Integer userid = principalDetails.getUser().getId();
        Group_Entity groupEntity = groupService.Group_Find_GroupId(groupId);

        if(roleService.selectGroupRole(userid, groupEntity)){
            return "redirect:/main2";
        }else{
            Role_Entity roleEntity = new Role_Entity();
            roleEntity.setRole("user");
            roleEntity.setUserid(userid);
            roleEntity.setGroupEntity(groupEntity);
            roleService.save(roleEntity);
            Long id = groupId;
            return "redirect:/board/"+id;
        }
    }
    
    @PostMapping("/group_x/{groupId}")
    public String group_x(@PathVariable("groupId") Long groupId, @AuthenticationPrincipal PrincipalDetails principalDetails) {
    	
        Group_Entity group_entity = groupService.Group_Find_GroupId(groupId);
        
        
        List<Role_Entity> roleEntities = group_entity.getRoleEntity();
        for(Role_Entity re : roleEntities) {
        	
            if(re.getUserid().equals(principalDetails.getUser().getId())) {
                roleService.remove(re);
            }
        }

     

        return "redirect:/main2";
    }
}
