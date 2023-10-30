package test.web.dto;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class EditDTO {

	private Integer id;
	private String subject;
	private String content;
	private LocalDateTime regdate;
	
	@CreatedDate
	@Column(name="moddate")
	private LocalDateTime moddate;
	
	
}
