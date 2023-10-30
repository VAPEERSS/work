package test.web.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import test.web.dto.SearchResult;
import test.web.entity.Calendar_Entity;
import test.web.entity.Group_Entity;
import test.web.entity.Question_Entity;
import test.web.repository.CalendarRepository;
import test.web.repository.GroupRepository;
import test.web.repository.UserRepository;

import javax.management.relation.Role;

@Service
@RequiredArgsConstructor
public class GroupService {

	private final GroupRepository groupRepository;
	private final CalendarRepository calendarRepository;
	private final RoleService roleService;
	
	
	public void save(Group_Entity entity) {
		
		groupRepository.save(entity);
		
	}
	
	
	public List<Group_Entity> findAll() {
		
		return groupRepository.findAll();
		
	}
	
	public Group_Entity find_GroupName(String name) {
		
		return groupRepository.findByGroupName(name);
	}
	
	
	public List<Question_Entity> Group_Find_List(String Group_name) {
	    	
	    	Group_Entity a = groupRepository.findByGroupName(Group_name);
			
	    	return a.getQuestionentity();
			 
	    }
	public Group_Entity Group_Find_GroupId(Long groupid) {
	    	return groupRepository.findByGroupId(groupid);
		}
	 
	public List<Question_Entity> Group_Find_Question_List(Long groupid) {
	    	Group_Entity a = groupRepository.findByGroupId(groupid);
			return a.getQuestionentity();
	}

	public void updateGroupName(Group_Entity groupEntity, String newGroupName) {
		groupEntity.updateTitle(newGroupName);
		groupRepository.save(groupEntity);
	}

	public void updateGroupIntroduction(Group_Entity groupEntity, String newIntroduction) {
		groupEntity.updateIntroduction(newIntroduction);
		groupRepository.save(groupEntity);
	}

	@Transactional
	public void remove(Group_Entity group_entity) {
		calendarRepository.deleteByGroupEntity(group_entity);
		groupRepository.delete(group_entity);
	}

	public List<Group_Entity> searchGroup(String keyword){
		return groupRepository.findByGroupNameContaining(keyword);
	}

	public List<SearchResult> toSearchForm(List<Group_Entity> groupEntities, Integer userid){
		List<SearchResult> searchResults = new ArrayList<>();

		for(Group_Entity ge : groupEntities){
			SearchResult searchResult = new SearchResult();
			searchResult.setGroupId(ge.getGroupId());
			searchResult.setGroupName(ge.getGroupName());
			searchResult.setIntroduction(ge.getIntroduction());
			searchResult.setTime(ge.getTime());
			searchResult.setOpenNotOpen(ge.getOpenNotOpen());
			searchResult.setJoin(roleService.selectGroupRole(userid, ge));
			searchResults.add(searchResult);
		}
		return searchResults;
	}
	
}
