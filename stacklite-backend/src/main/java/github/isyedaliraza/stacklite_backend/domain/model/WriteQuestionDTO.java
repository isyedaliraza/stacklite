package github.isyedaliraza.stacklite_backend.domain.model;

import lombok.Data;

@Data
public class WriteQuestionDTO {
    private String title;
    private String body;
    private String tagId;
}
