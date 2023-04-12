package de.mem89.imdb.dataset_importer.mapper;

import de.mem89.imdb.dataset_importer.dto.TitleAkas;
import de.mem89.imdb.dataset_importer.dto.TitleBasics;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import java.util.Map;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface TitleAkasMapper extends AbstractMapper<TitleAkas> {

    TitleAkas map(Map<String, String> source);
}
