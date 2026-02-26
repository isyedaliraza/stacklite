package github.isyedaliraza.stacklite_backend.domain.model;

import github.isyedaliraza.stacklite_backend.domain.entity.Tag;
import lombok.Data;

@Data
public class TagDTO {
    private String id;
    private String name;

    public TagDTO(Tag tag) {
        this.id = tag.getId();
        this.name = tag.getName();
    }
}
