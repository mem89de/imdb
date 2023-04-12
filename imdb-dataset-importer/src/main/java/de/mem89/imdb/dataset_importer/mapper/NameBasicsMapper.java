package de.mem89.imdb.dataset_importer.mapper;

import de.mem89.imdb.dataset_importer.dto.NameBasics;
import de.mem89.imdb.dataset_importer.dto.TitleBasics;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

import java.util.Map;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface NameBasicsMapper extends AbstractMapper<NameBasics> {

    NameBasics map(Map<String, String> source);
}
