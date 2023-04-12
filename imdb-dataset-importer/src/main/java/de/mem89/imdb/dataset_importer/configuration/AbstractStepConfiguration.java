package de.mem89.imdb.dataset_importer.configuration;

import de.mem89.imdb.dataset_importer.reader.DatasetReaderFactory;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.PlatformTransactionManager;

public abstract class AbstractStepConfiguration {
    @Autowired
    DatasetReaderFactory datasetReaderFactory;

    @Autowired
    JobRepository jobRepository;

    @Autowired
    PlatformTransactionManager transactionManager;

    @Autowired
    public ItemWriter<Object> writer;

    @Value("${app.chunk_size}")
    int chunkSize;

}
