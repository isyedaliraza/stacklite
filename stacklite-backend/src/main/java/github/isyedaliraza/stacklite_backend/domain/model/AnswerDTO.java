package github.isyedaliraza.stacklite_backend.domain.model;

import github.isyedaliraza.stacklite_backend.domain.entity.Answer;
import lombok.Data;

@Data
public class AnswerDTO {
    private String id;
    private String body;
    private String questionId;

    public AnswerDTO(Answer answer) {
        this.id = answer.getId();
        this.body = answer.getBody();
        this.questionId = answer.getQuestion().getId();
    }
}
