package github.isyedaliraza.stacklite_backend.domain.model;

import github.isyedaliraza.stacklite_backend.domain.entity.Question;
import github.isyedaliraza.stacklite_backend.domain.entity.Tag;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class QuestionDTO {
    private String id;
    private String title;
    private String body;
    private boolean answered;
    private List<TagDTO> tags;

    public QuestionDTO(Question question) {
        this.id = question.getId();
        this.title = question.getTitle();
        this.body = question.getBody();
        this.answered = question.isAnswered();
        List<TagDTO> tags = new ArrayList<>();
        for (Tag tag: question.getTags()) {
            tags.add(new TagDTO(tag));
        }
        this.tags = tags;
    }
}
