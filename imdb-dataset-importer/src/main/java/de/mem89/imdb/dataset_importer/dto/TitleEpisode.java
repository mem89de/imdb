package de.mem89.imdb.dataset_importer.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@Getter
@ToString
public class TitleEpisode {
    private String tconst;
    private String parentTconst;
    private int seasonNumber;
    private int episodeNumber;
}
