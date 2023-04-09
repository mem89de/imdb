package de.mem89.imdb.dataset_importer.mapper;

import de.mem89.imdb.dataset_importer.dto.TitleBasics;
import de.mem89.imdb.dataset_importer.mapper.interfaces.CSVRecordMapper;
import org.apache.commons.csv.CSVRecord;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public interface AbstractMapper<T> extends CSVRecordMapper<T> {
    default T map(CSVRecord source) {
        if (source == null) {
            return null;
        }
        return map(source.toMap());
    }

    T map(Map<String, String> source);

    default Integer mapInteger(String source) {
        if("\\N".equals(source)){
            return null;
        }
        return Integer.valueOf(source);
    }
    default String mapString(String source) {
        if("\\N".equals(source)) {
            return null;
        }
        return source;
    }
    default List<String> mapList(String source) {
        String[] asArray = StringUtils.tokenizeToStringArray(source, ",");
        return Arrays.stream(asArray).map(this::mapString).toList();
    }
}
