package de.mem89.imdb.dataset_importer.reader;

import de.mem89.imdb.dataset_importer.mapper.interfaces.Mapper;
import jakarta.annotation.Nonnull;
import jakarta.validation.constraints.NotEmpty;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.batch.item.ItemReader;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.zip.GZIPInputStream;

@Slf4j
@Component
public class DatasetReaderFactory {

    public <T> DatasetReader<T> create(@NonNull String filename, @Nonnull Mapper<CSVRecord, T> mapper, @NotEmpty List<String> headers) {
        ItemReader<CSVRecord> csvRecordItemReader = createCSVItemReader(filename, headers);

        return new DatasetReader<>(csvRecordItemReader, mapper);
    }

    private ItemReader<CSVRecord> createCSVItemReader(@NonNull String filename, @NotEmpty Collection<String> headers) {
        if (log.isDebugEnabled()) {
            String headersString = headers.stream().collect(Collectors.joining(", "));
            log.debug("Creating {} from file {} with headers {}", CSVRecordReader.class, filename, headersString);
        }
        Resource resource = new ClassPathResource(filename);
        GZIPInputStream inputStream;
        try {
            inputStream = new GZIPInputStream(resource.getInputStream());
        } catch (IOException e) {
            log.error("There was a problem reading file '{}'", filename);
            throw new RuntimeException("Could not read dataset file", e);
        }

        CSVFormat csvFormat = getDefaultCSVFormat(headers);

        return CSVRecordReader.builder(inputStream).csvFormat(csvFormat).build();
    }

    private <T extends Enum<T>> CSVFormat getDefaultCSVFormat(Collection<String> headers) {
        return CSVFormat.DEFAULT.builder()
                .setDelimiter('\t')
                .setQuote(null)
                .setSkipHeaderRecord(true)
                .setHeader(headers.toArray(new String[headers.size()]))
                .build();
    }
}
