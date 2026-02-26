package github.isyedaliraza.stacklite_backend.domain.repository;

import github.isyedaliraza.stacklite_backend.domain.entity.Question;
import github.isyedaliraza.stacklite_backend.domain.entity.Tag;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface QuestionRepository extends CrudRepository<Question, String> {
    List<Question> findAllByTagsName(String name);

    List<Question> findAllByTitleContainingIgnoreCase(String keyword);

    List<Question> findAllByAnswersIsEmpty();
}
