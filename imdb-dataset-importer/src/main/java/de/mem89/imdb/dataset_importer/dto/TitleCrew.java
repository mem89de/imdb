package de.mem89.imdb.dataset_importer.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Builder
@Getter
@ToString
public class TitleCrew {
    private String tconst;
    private List<String> directors;
    private List<String> writers;
}
