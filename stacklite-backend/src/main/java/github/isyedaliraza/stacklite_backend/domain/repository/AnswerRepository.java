package github.isyedaliraza.stacklite_backend.domain.repository;

import github.isyedaliraza.stacklite_backend.domain.entity.Answer;
import github.isyedaliraza.stacklite_backend.domain.entity.Question;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AnswerRepository extends CrudRepository<Answer, String> {
    List<Answer> findAllByQuestion_Id(String questionId);
}
