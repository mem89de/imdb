package de.mem89.imdb.dataset_importer.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Singular;
import lombok.ToString;

import java.util.List;

@Builder
@Getter
@ToString
public class NameBasics {
    private String nconst;
    private String primaryName;
    private Integer birthYear;
    private Integer deathYear;
    private List<String> primaryProfession;
    @Singular
    private List<String> knownForTitles;
}
