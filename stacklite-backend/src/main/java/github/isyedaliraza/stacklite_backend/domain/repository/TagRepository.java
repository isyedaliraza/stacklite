package github.isyedaliraza.stacklite_backend.domain.repository;

import github.isyedaliraza.stacklite_backend.domain.entity.Tag;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TagRepository extends CrudRepository<Tag, String> {
    List<Tag> findAllByNameContainingIgnoreCase(String keyword);
}
