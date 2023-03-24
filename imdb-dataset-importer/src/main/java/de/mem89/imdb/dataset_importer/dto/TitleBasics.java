package de.mem89.imdb.dataset_importer.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Singular;

import java.util.List;

@Builder
@Getter
public class TitleBasics {
    private String tconst;
    private String titleType;
    private String primaryTitle;
    private String originalTitle;
    private boolean isAdult;
    private String startYear;
    private String endYear;
    private String runtimeMinutes;
    @Singular
    private List<String> genres;


}
