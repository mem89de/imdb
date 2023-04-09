package de.mem89.imdb.dataset_importer.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.math.BigDecimal;

@Builder
@Getter
@ToString
public class TitleRatings {
    private String tconst;
    private BigDecimal averageRating;
    private Integer numVotes;
}
