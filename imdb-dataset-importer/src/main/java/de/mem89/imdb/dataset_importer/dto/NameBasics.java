package de.mem89.imdb.dataset_importer.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Builder
@Getter
@ToString
public class NameBasics {
    private String ncsonst;
    private String primaryName;
    private int birthYear;
    private int deathYear;
    private List<String> primaryProfession;
    private List<String> knownForTitles;
}
