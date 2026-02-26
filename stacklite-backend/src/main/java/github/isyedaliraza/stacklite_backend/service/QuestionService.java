package github.isyedaliraza.stacklite_backend.service;

import github.isyedaliraza.stacklite_backend.domain.entity.Question;
import github.isyedaliraza.stacklite_backend.domain.entity.Tag;
import github.isyedaliraza.stacklite_backend.domain.model.QuestionDTO;
import github.isyedaliraza.stacklite_backend.domain.model.WriteQuestionDTO;

import java.util.List;
import java.util.Optional;

public interface QuestionService {
    Iterable<Question> findAll();
    Question save(WriteQuestionDTO writeQuestionDTO);
    Optional<Question> findById(String id);
    Question update(String id, WriteQuestionDTO writeQuestionDTO);
    void deleteById(String id);

    List<Question> findAllByTagsName(String name);

    List<Question> findAllByTitleContainingIgnoreCase(String keyword);

    List<Question> findAllByAnswersIsEmpty();

    Question assignTagToQuestion(String questionId, String tagId);

    Question resolveQuestion(String questionId);
}
