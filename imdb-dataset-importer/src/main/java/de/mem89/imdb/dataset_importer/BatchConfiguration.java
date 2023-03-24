package de.mem89.imdb.dataset_importer;

import de.mem89.imdb.dataset_importer.dto.TitleBasics;
import de.mem89.imdb.dataset_importer.mapper.TitleBasicsMapper;
import de.mem89.imdb.dataset_importer.reader.CSVRecordReader;
import de.mem89.imdb.dataset_importer.writer.LogWriter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.transaction.PlatformTransactionManager;

import java.io.IOException;
import java.util.zip.GZIPInputStream;

@Configuration
@Slf4j
public class BatchConfiguration {

    @Value("${app.datasets.titleBasics.file}")
    private String fileInput;

    @Value("${app.datasets.titleBasics.headers}")
    private String[] headers;

    @Value("${app.datasets.titleBasics.dto_class}")
    private Class titleBasicsClass;

    @Value("${app.chunk_size}")
    private int chunkSize;

    @Bean
    public ItemWriter<TitleBasics> writer() {
        return new LogWriter<>();
    }

    @Bean
    public Job importDatasetsJob(JobRepository jobRepository, JobExecutionListener listener, Step step1) {
        log.debug("Configuring job 'importDatasetsJob");
        return new JobBuilder("importDatasetsJob", jobRepository)
                .incrementer(new RunIdIncrementer())
                .listener(listener)
                .flow(step1)
                .end()
                .build();
    }

    @Bean
    public Step step1(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        log.debug("Configuring step 'step1'");
        return new StepBuilder("step1", jobRepository)
                .<CSVRecord, TitleBasics>chunk(chunkSize)
                .transactionManager(transactionManager)
                .reader(createReader(fileInput))
                .processor(new MapperProcessor<CSVRecord, TitleBasics>(new TitleBasicsMapper()))
                .writer(writer())
                .build();
    }

    private <T extends Enum<T>> ItemReader<CSVRecord> createReader(String filename) {
        log.debug("Create Reader from file {}", filename);
        Resource resource = new ClassPathResource(filename);
        try {
            CSVRecordReader itemReader = CSVRecordReader.builder(new GZIPInputStream(resource.getInputStream())).csvFormat(getDefaultCSVFormat()).build();
            return itemReader;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private <T extends Enum<T>> CSVFormat getDefaultCSVFormat() {
        return CSVFormat.DEFAULT.builder()
                .setDelimiter('\t')
                .setQuote(null)
                .setSkipHeaderRecord(true)
                .setHeader(headers)
                .build();
    }
}
