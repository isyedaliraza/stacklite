package github.isyedaliraza.stacklite_backend.service;

import github.isyedaliraza.stacklite_backend.domain.entity.Answer;
import github.isyedaliraza.stacklite_backend.domain.model.AnswerDTO;
import github.isyedaliraza.stacklite_backend.domain.model.WriteAnswerDTO;

import java.util.List;
import java.util.Optional;

public interface AnswerService {
    Iterable<Answer> findAll();
    Answer save(WriteAnswerDTO writeAnswerDTO);
    Optional<Answer> findById(String id);
    Answer update(String id, WriteAnswerDTO writeAnswerDTO);
    void deleteById(String id);

    List<Answer> findAllByQuestion_Id(String questionId);
}
