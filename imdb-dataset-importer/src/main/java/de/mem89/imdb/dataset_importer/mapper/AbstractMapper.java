package de.mem89.imdb.dataset_importer.mapper;

import de.mem89.imdb.dataset_importer.dto.TitleBasics;
import de.mem89.imdb.dataset_importer.mapper.interfaces.CSVRecordMapper;
import org.apache.commons.csv.CSVRecord;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.Collections;
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
        if("\\N".equals(source)) {
            return Collections.emptyList();
        }
        String[] asArray = StringUtils.tokenizeToStringArray(source, ",");
        return Arrays.stream(asArray).map(this::mapString).toList();
    }

    default boolean mapStringToBoolean(String source) {
        if(!StringUtils.hasText(source)) {
            throw new IllegalArgumentException(String.format("Can not map empty String to boolean: %s", source));
        }

        if (List.of("true", "1").contains(source.toLowerCase())) {
            return true;
        }

        if (List.of("false", "0").contains(source.toLowerCase())) {
            return false;
        }
        throw new IllegalArgumentException(String.format("Can not map String to boolean: %s", source));
    }
}
