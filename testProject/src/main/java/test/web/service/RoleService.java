package test.web.service;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import test.web.entity.Group_Entity;
import test.web.entity.Role_Entity;
import test.web.repository.GroupRepository;
import test.web.repository.QuestionRepository;
import test.web.repository.RoleRepository;
import test.web.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class RoleService {

    private final QuestionRepository questionRepository;
    private final UserRepository userRepository;
    private final GroupRepository groupRepository;
    private final RoleRepository roleRepository;
    
	public void save(Role_Entity roleEntity) {
		
		roleRepository.save(roleEntity);
	}

    public Boolean selectGroupRole(Integer userid, Group_Entity group_entity) {
        return roleRepository.findByUseridAndGroupEntity(userid, group_entity).isPresent();
    }

    public void remove(Role_Entity roleEntity) {
        roleRepository.delete(roleEntity);
    }
}
