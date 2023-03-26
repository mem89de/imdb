package de.mem89.imdb.dataset_importer.reader;

import de.mem89.imdb.dataset_importer.mapper.interfaces.Mapper;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.apache.commons.csv.CSVRecord;
import org.springframework.batch.item.ItemReader;

@RequiredArgsConstructor
public class DatasetReader<T> implements ItemReader<T> {

    @NonNull
    private ItemReader<CSVRecord> csvRecordReader;

    @NonNull
    private Mapper<CSVRecord, T> mapper;

    @Override
    public T read() throws Exception {
        CSVRecord csvRecord = csvRecordReader.read();
        return mapper.map(csvRecord);
    }
}
