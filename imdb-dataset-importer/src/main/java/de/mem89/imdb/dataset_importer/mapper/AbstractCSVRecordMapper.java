package de.mem89.imdb.dataset_importer.mapper;

import de.mem89.imdb.dataset_importer.mapper.interfaces.CSVRecordMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVRecord;

@Slf4j
public abstract class AbstractCSVRecordMapper<TO> implements CSVRecordMapper<TO> {
    protected static final String EMPTY_CELL = "\\N";

    @Override
    public final TO map(CSVRecord csvRecord) {
        if (csvRecord == null) {
            log.warn("Object to map is null");
            return null;
        }
        return mapNonNull(csvRecord);
    }


    abstract TO mapNonNull(CSVRecord csvRecord);

}
