package test.web.service;

import java.time.LocalDate;
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
import test.web.auth.PrincipalDetails;
import test.web.entity.Answer_Entity;
import test.web.entity.Calendar_Entity;
import test.web.entity.Group_Entity;
import test.web.entity.Question_Entity;
import test.web.repository.AnswerRepository;
import test.web.repository.CalendarRepository;
import test.web.repository.GroupRepository;
import test.web.repository.QuestionRepository;

@Service
@RequiredArgsConstructor
public class CalendarService {

	private final CalendarRepository calendarRepository;
	private final GroupRepository groupRepository;
	private final QuestionRepository questionRepository;
	private final AnswerRepository answerRepository;
	
	
	 public Page<Calendar_Entity> get_Group_List(int page, Long number) {
	    	
	    	List<Sort.Order> sorts = new ArrayList<>();
	        sorts.add(Sort.Order.asc("eventDate"));
	        Pageable pageable = PageRequest.of(page, 10, Sort.by("sno").descending());
	        
	        return this.calendarRepository.findByGroupEntity(pageable, groupRepository.findByGroupId(number));
	    }
	
	public void schedule(String eventContent, LocalDate eventDate, LocalDate endDate, PrincipalDetails principalDetails, Group_Entity group) {
		
		Calendar_Entity cal = new Calendar_Entity();
		
		
		cal.setEventWriter(principalDetails.getUser().getUsername());
		cal.setEventContent(eventContent);
		cal.setEventDate(eventDate);
		cal.setEndDate(endDate);
		cal.setGroupEntity(group);
		
		calendarRepository.save(cal);
		
		
	}
	
	public Calendar_Entity getCalendar(Integer sno) {
		
		Optional<Calendar_Entity> calendar = this.calendarRepository.findById(sno);
		
		if(calendar.isPresent()) {
			return calendar.get();
		}else {
			throw new DataNotFoundException("calendar not found");
		}
	}
	
    public Page<Calendar_Entity> getList(int page) {
    	List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.asc("eventDate"));
        
        Pageable pageable = PageRequest.of(page, 10, Sort.by("sno").descending());
        return this.calendarRepository.findAll(pageable);
    }
    

	public Page<Calendar_Entity> getfindByGroup(Group_Entity groupEntity, Integer pass){
		
		Pageable page = PageRequest.of(pass, 10);
		
		return calendarRepository.findByGroupEntity(page,groupEntity);
	}
	
    
}
