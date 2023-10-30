package test.web.entity;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Answer_Entity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(columnDefinition = "TEXT")
    private String content;

    @CreatedDate //해당 필드 즉 컬럼에 날짜값 자동 반영하도록 선언
	@Column(name = "regdate", updatable = false)
    private LocalDateTime regdate;

    @ManyToOne
    private Question_Entity question;
    
    @CreatedDate
	@Column(name="moddate")
    private LocalDateTime moddate;
    
    private Integer userid;
    
    private String username;
    
    private String Author;

}
