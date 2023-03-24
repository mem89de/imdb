package de.mem89.imdb.dataset_importer.mapper;

import de.mem89.imdb.dataset_importer.dto.TitleBasics;
import static de.mem89.imdb.dataset_importer.headers.TitleBasicsHeader.*;
import org.apache.commons.csv.CSVRecord;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.Collection;

public class TitleBasicsMapper implements Mapper<CSVRecord,TitleBasics> {
    private static final String EMPTY_CELL = "\\N";
    public TitleBasics map(CSVRecord csvRecord) {
        if(csvRecord == null) {
            return null;
        }

        return TitleBasics.builder()
                .tconst(csvRecord.get(tconst))
                .titleType(csvRecord.get(titleType))
                .primaryTitle(csvRecord.get(primaryTitle))
                .originalTitle(csvRecord.get(originalTitle))
                .isAdult(Integer.valueOf(csvRecord.get(isAdult)) == 0)
                .startYear(csvRecord.get(startYear))
                .endYear(extractEndYear(csvRecord))
                .runtimeMinutes(extractRuntimeInMinutes(csvRecord))
                .genres(extractGenres(csvRecord))
                .build();
    }

    private String extractEndYear(CSVRecord csvRecord) {
        String endYearValue = csvRecord.get(endYear);
        return isEmptyCell(endYearValue) ? null : endYearValue;
    }

    private String extractRuntimeInMinutes(CSVRecord csvRecord) {
        String runtimeMinutesValue = csvRecord.get(runtimeMinutes);
        return isEmptyCell(runtimeMinutesValue) ? null : runtimeMinutesValue;
    }

    private Collection<String> extractGenres(CSVRecord csvRecord) {
        String[] genresArray = StringUtils.tokenizeToStringArray(csvRecord.get(genres), ",");
        return Arrays.stream(genresArray).toList();
    }

    private boolean isEmptyCell(String cell) {
        if(!StringUtils.hasText(cell)) {
            return false;
        }

        return !EMPTY_CELL.equals(cell);
    }
}
