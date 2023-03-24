package de.mem89.imdb.dataset_importer.reader;

import lombok.Builder;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemReader;

import java.io.*;
import java.util.Iterator;

@Builder(builderMethodName = "internalBuilder")
@Slf4j
public class CSVRecordReader implements ItemReader<CSVRecord> {
    @Builder.Default
    private CSVFormat csvFormat = CSVFormat.DEFAULT;
    @NonNull
    private InputStream inputStream;
    private Iterator<CSVRecord> records;

    @Override
    public CSVRecord read() {
        log.trace("Reading Item");
        if (records == null) {
            initialize();
        }

        if (records.hasNext()) {
            CSVRecord record = records.next();
            log.trace("Found item {}", record);
            return record;
        }
        log.debug("No items left");
        return null;
    }

    private void initialize() {
        log.debug("Initializing");
        Reader reader = new InputStreamReader(inputStream);
        reader = new BufferedReader(reader);

        try {
            records = csvFormat.parse(reader).iterator();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static CSVRecordReaderBuilder builder(@NonNull InputStream inputStream) {
        return internalBuilder().inputStream(inputStream);
    }

    public static class CSVRecordReaderBuilder {
        private CSVRecordReaderBuilder records(Iterator<CSVRecord> records) {
            return this;
        }
    }
}
