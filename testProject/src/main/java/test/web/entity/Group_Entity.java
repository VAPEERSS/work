
package test.web.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.annotation.CreatedDate;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Group_Entity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long groupId; //그룹번호
	private String groupName; //그룹이름
	private String introduction; //소개글
	private Boolean openNotOpen; //공개 여부 

	@CreatedDate
	private LocalDate time; //그룹생성시간
	
	@OneToMany(mappedBy = "groupEntity", cascade = CascadeType.REMOVE)
	private List<Role_Entity> roleEntity;
	
	@OneToMany(mappedBy = "groupEntity", cascade = CascadeType.REMOVE)
	private List<Question_Entity> questionentity;

	public void updateTitle(String newGroupName) {
		this.groupName = newGroupName;
	}

	public void updateIntroduction(String newIntroduction) {
		this.introduction = newIntroduction;
	}
	
}