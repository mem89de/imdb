package de.mem89.imdb.dataset_importer.mapper;

import de.mem89.imdb.dataset_importer.dto.TitleBasics;
import de.mem89.imdb.dataset_importer.headers.TitleBasicsHeader;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.Collection;


@Slf4j
@Component("titleBasicsMapper")
public class TitleBasicsMapper extends AbstractCSVRecordMapper {
    TitleBasics mapNonNull(CSVRecord csvRecord) {
        return TitleBasics.builder()
                .tconst(csvRecord.get(TitleBasicsHeader.tconst))
                .titleType(csvRecord.get(TitleBasicsHeader.titleType))
                .primaryTitle(csvRecord.get(TitleBasicsHeader.primaryTitle))
                .originalTitle(csvRecord.get(TitleBasicsHeader.originalTitle))
                .isAdult(Integer.valueOf(csvRecord.get(TitleBasicsHeader.isAdult)) == 0)
                .startYear(csvRecord.get(TitleBasicsHeader.startYear))
                .endYear(extractEndYear(csvRecord))
                .runtimeMinutes(extractRuntimeInMinutes(csvRecord))
                .genres(extractGenres(csvRecord))
                .build();
    }

    private String extractEndYear(CSVRecord csvRecord) {
        String endYearValue = csvRecord.get(TitleBasicsHeader.endYear);
        return isEmptyCell(endYearValue) ? null : endYearValue;
    }

    private String extractRuntimeInMinutes(CSVRecord csvRecord) {
        String runtimeMinutesValue = csvRecord.get(TitleBasicsHeader.runtimeMinutes);
        return isEmptyCell(runtimeMinutesValue) ? null : runtimeMinutesValue;
    }

    private Collection<String> extractGenres(CSVRecord csvRecord) {
        String[] genresArray = StringUtils.tokenizeToStringArray(csvRecord.get(TitleBasicsHeader.genres), ",");
        return Arrays.stream(genresArray).toList();
    }

    private boolean isEmptyCell(String cell) {
        if (!StringUtils.hasText(cell)) {
            return false;
        }

        return !EMPTY_CELL.equals(cell);
    }


}
