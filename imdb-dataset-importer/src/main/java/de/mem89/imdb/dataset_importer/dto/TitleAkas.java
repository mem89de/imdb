package de.mem89.imdb.dataset_importer.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Builder
@Getter
@ToString
public class TitleAkas {
    private String titleId;
    private Integer ordering;
    private String title;
    private String region;
    private String language;
    private List<String> types;
    private String attributes;
    private boolean isOriginalTitle;
}
