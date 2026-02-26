package github.isyedaliraza.stacklite_backend.domain.model;

import lombok.Data;

@Data
public class WriteAnswerDTO {
    private String questionId;
    private String body;
}
