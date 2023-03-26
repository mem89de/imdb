package de.mem89.imdb.dataset_importer;

import de.mem89.imdb.dataset_importer.headers.NameBasicsHeader;
import de.mem89.imdb.dataset_importer.headers.TitleBasicsHeader;
import de.mem89.imdb.dataset_importer.mapper.NameBasicsMapper;
import de.mem89.imdb.dataset_importer.mapper.TitleBasicsMapper;
import de.mem89.imdb.dataset_importer.reader.DatasetReaderFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.job.builder.SimpleJobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Configuration
@Slf4j
public class BatchConfiguration {
    @Autowired
    public TitleBasicsMapper titleBasicsMapper;

    @Autowired
    public NameBasicsMapper nameBasicsMapper;

    @Bean("titleBasicsStep")
    public Step createTitleBasicsStep() {
        List<String> headers = Arrays.stream(TitleBasicsHeader.values()).map(Objects::toString).toList();
        return new StepBuilder("titleBasicsStep", jobRepository).chunk(chunkSize, transactionManager).transactionManager(transactionManager).reader(datasetReaderFactory.create("title.basics.tsv.gz", titleBasicsMapper, headers)).writer(writer).build();
    }

    @Bean("nameBasicsStep")
    public Step createNameBasicsStep() {
        List<String> headers = Arrays.stream(NameBasicsHeader.values()).map(Objects::toString).toList();
        return new StepBuilder("nameBasicsStep", jobRepository).chunk(chunkSize, transactionManager).transactionManager(transactionManager).reader(datasetReaderFactory.create("name.basics.tsv.gz", nameBasicsMapper, headers)).writer(writer).build();
    }

    // Todo: Should only be a workaround
    @Autowired
    @Lazy
    List<Step> steps;

    @Autowired
    JobRepository jobRepository;

    @Autowired
    PlatformTransactionManager transactionManager;

    @Autowired
    DatasetReaderFactory datasetReaderFactory;

    @Value("${app.chunk_size}")
    private int chunkSize;

    @Autowired
    public ItemWriter<Object> writer;

    @Bean
    public Job importDatasetsJob(JobRepository jobRepository, JobExecutionListener listener) {
        log.debug("Configuring job 'importDatasetsJob");

        JobBuilder jobBuilder = new JobBuilder("importDatasetsJob", jobRepository).incrementer(new RunIdIncrementer()).listener(listener);


        // TODO: Ugly!
        Step firstStep = steps.get(0);
        SimpleJobBuilder simpleJobBuilder = jobBuilder.start(firstStep);
        if (steps.size() > 1) {
            for (Step step : steps.subList(1, steps.size())) {
                simpleJobBuilder.next(step);
            }
        }

        return simpleJobBuilder.build();
    }

}
