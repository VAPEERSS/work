package test.web.entity;

import org.hibernate.annotations.ValueGenerationType;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Setter
@Getter
public class Role_Entity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long role_Id;
	private String role;
	private Integer userid;
	
	@ManyToOne
	private Group_Entity groupEntity;
	
}
