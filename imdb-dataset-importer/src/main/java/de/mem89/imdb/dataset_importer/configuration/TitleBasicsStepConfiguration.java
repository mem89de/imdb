package de.mem89.imdb.dataset_importer.configuration;

import de.mem89.imdb.dataset_importer.dto.TitleBasics;
import de.mem89.imdb.dataset_importer.mapper.TitleBasicsMapper;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

@Configuration
public class TitleBasicsStepConfiguration extends AbstractStepConfiguration {

    @Value("${app.datasets.titleBasics.headers}")
    String[] headers;

    @Value("${app.datasets.titleBasics.filename}")
    String filename;

    @Autowired
    public TitleBasicsMapper mapper;

    @Bean("titleBasicsStep")
    @Order(value = 1)
    public Step createStep() {
        return new StepBuilder("titleBasicsStep", jobRepository).chunk(chunkSize, transactionManager)
                                                                .transactionManager(transactionManager)
                                                                .reader(reader())
                                                                .writer(writer)
                                                                .build();
    }

    ItemReader<TitleBasics> reader() {
        return datasetReaderFactory.create(filename, mapper, headers);
    }
}
