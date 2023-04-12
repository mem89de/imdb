package de.mem89.imdb.dataset_importer.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Singular;
import lombok.ToString;

import java.util.List;

@Builder
@Getter
@ToString
public class TitleCrew {
    private String tconst;
    @Singular
    private List<String> directors;
    @Singular
    private List<String> writers;
}
