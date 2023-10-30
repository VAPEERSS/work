package test.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import test.web.auth.PrincipalDetails;
import test.web.dto.GroupMembers;
import test.web.entity.Group_Entity;
import test.web.entity.Role_Entity;
import test.web.entity.User_Entity;
import test.web.repository.UserRepository;
import test.web.service.GroupService;
import test.web.service.RoleService;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("group/{groupId}")
@RequiredArgsConstructor
public class GroupSettingsController {

    private final GroupService groupService;
    private final UserRepository userRepository;
    private final RoleService roleService;

    @GetMapping("/settings")
    public String adminView(@PathVariable Long groupId, @AuthenticationPrincipal PrincipalDetails principalDetails,
                            Model model){
        Group_Entity groupEntity = groupService.Group_Find_GroupId(groupId);
        List<Role_Entity> roleEntity = groupEntity.getRoleEntity();

        for(Role_Entity role : roleEntity){
            if(role.getRole().equals("master")){
                if(role.getUserid().equals(principalDetails.getUser().getId())){
                    model.addAttribute("group", groupEntity);
                    return "/group/group_manager";
                }
            }
        }
        return "/group/group_manager_x";
    }

    @GetMapping("/members")
    public String members(@PathVariable Long groupId, @AuthenticationPrincipal PrincipalDetails principalDetails,
                          Model model){
        Group_Entity groupEntity = groupService.Group_Find_GroupId(groupId);
        List<Role_Entity> roleEntity = groupEntity.getRoleEntity();
        List<GroupMembers> members = new ArrayList<>();
        for (Role_Entity re : roleEntity){
            GroupMembers member = new GroupMembers();
            Integer userId = re.getUserid();
            User_Entity byId = userRepository.findById(userId).get();
            member.setId(userId);
            member.setUsername(byId.getUsername());
            member.setRole(re.getRole());
            members.add(member);
        }
        model.addAttribute("group", groupEntity);
        model.addAttribute("members", members);

        return "/group/group_members";
    }

    @PostMapping("/title")
    public String updateGroupTitle(@PathVariable Long groupId, String newGroupName,
                                   Model model) {
        Group_Entity group_entity = groupService.Group_Find_GroupId(groupId);
        groupService.updateGroupName(group_entity, newGroupName);
        return "redirect:/group/" + groupId + "/settings";
    }

    @PostMapping("/introduction")
    public String updateGroupIntroduction(@PathVariable Long groupId, String newIntroduction,
                                   Model model) {
        Group_Entity group_entity = groupService.Group_Find_GroupId(groupId);
        groupService.updateGroupIntroduction(group_entity, newIntroduction);
        return "redirect:/group/" + groupId + "/settings";
    }

    @PostMapping("/remove")
    public String removeGroup(@PathVariable Long groupId, Model model) {
        Group_Entity group_entity = groupService.Group_Find_GroupId(groupId);

        groupService.remove(group_entity);
        return "redirect:/main2";
    }

    @PostMapping("/delete_member")
    public String deleteMember(@PathVariable Long groupId, Integer id){
        Group_Entity group_entity = groupService.Group_Find_GroupId(groupId);
        List<Role_Entity> roleEntities = group_entity.getRoleEntity();
        for(Role_Entity re : roleEntities){
            if(re.getUserid().equals(id)){
                roleService.remove(re);
            }
        }

        return "redirect:/group/"+groupId+"/members";
    }
}
