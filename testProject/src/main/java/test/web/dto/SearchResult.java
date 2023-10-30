package test.web.dto;


import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class SearchResult {
    private Long groupId;
    private String groupName;
    private String introduction;
    private boolean openNotOpen;
    private LocalDate time;

    private boolean isJoin;
}
