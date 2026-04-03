package github.isyedaliraza.stacklite_backend.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WriteQuestionDTO {
    private String title;
    private String body;
    private ArrayList<String> tags;
}
