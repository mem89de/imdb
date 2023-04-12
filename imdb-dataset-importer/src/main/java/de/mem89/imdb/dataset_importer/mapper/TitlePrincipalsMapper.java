package de.mem89.imdb.dataset_importer.mapper;

import de.mem89.imdb.dataset_importer.dto.TitleBasics;
import de.mem89.imdb.dataset_importer.dto.TitlePrincipals;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import java.util.Map;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface TitlePrincipalsMapper extends AbstractMapper<TitlePrincipals> {

    TitlePrincipals map(Map<String, String> source);
}
