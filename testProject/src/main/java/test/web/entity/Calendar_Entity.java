package test.web.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import groovy.transform.builder.Builder;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Calendar_Entity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer sno;
	
	private String eventWriter;
	private String eventContent;
	private LocalDate eventDate;
	private LocalDate endDate;
	private String success;
	
	
	
	@ManyToOne
	private Group_Entity groupEntity;
}
