package github.isyedaliraza.stacklite_backend.service;

import github.isyedaliraza.stacklite_backend.domain.entity.Tag;
import github.isyedaliraza.stacklite_backend.domain.model.TagDTO;
import github.isyedaliraza.stacklite_backend.domain.model.WriteTagDTO;

import java.util.List;
import java.util.Optional;

public interface TagService {
    Iterable<Tag> findAll();
    Tag save(WriteTagDTO writeTagDTO);
    Optional<Tag> findById(String id);
    Tag update(String id, WriteTagDTO writeTagDTO);
    void deleteById(String id);

    List<Tag> findAllByNameContainingIgnoreCase(String keyword);
}
