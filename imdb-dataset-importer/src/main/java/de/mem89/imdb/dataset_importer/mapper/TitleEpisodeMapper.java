package de.mem89.imdb.dataset_importer.mapper;

import de.mem89.imdb.dataset_importer.dto.TitleBasics;
import de.mem89.imdb.dataset_importer.dto.TitleEpisode;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import java.util.Map;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface TitleEpisodeMapper extends AbstractMapper<TitleEpisode> {

    TitleEpisode map(Map<String, String> source);
}
