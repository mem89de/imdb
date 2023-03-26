package de.mem89.imdb.dataset_importer.mapper.interfaces;

import org.apache.commons.csv.CSVRecord;

import java.util.List;

public interface CSVRecordMapper<TO> extends Mapper<CSVRecord, TO> {
}
