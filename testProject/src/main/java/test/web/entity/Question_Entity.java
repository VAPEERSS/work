package test.web.entity;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.annotation.CreatedDate;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Question_Entity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 200)
    private String subject;
  
    @Column(columnDefinition = "TEXT")
    private String content;

    @CreatedDate //해당 필드 즉 컬럼에 날짜값 자동 반영하도록 선언
	@Column(name = "regdate", updatable = false)
    private LocalDateTime regdate;

    @OneToMany(mappedBy = "question")
    private List<Answer_Entity> answerList;
    
    @ManyToOne
    private Group_Entity groupEntity;
    
    @CreatedDate
	@Column(name="moddate")
    private LocalDateTime moddate;

    private Integer userid;
    
    private String username;
    
    private String Author;
    

}