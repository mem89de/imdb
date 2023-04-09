package de.mem89.imdb.dataset_importer.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@Getter
@ToString
public class TitlePrincipals {
    private String tconst;
    private Integer ordering;
    private String nconst;
    private String category;
    private String job;
    private String characters;
}
