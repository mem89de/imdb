package de.mem89.imdb.dataset_importer.configuration;

import de.mem89.imdb.dataset_importer.dto.NameBasics;
import de.mem89.imdb.dataset_importer.mapper.NameBasicsMapper;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

@Configuration
public class NameBasicsStepConfiguration extends AbstractStepConfiguration {

    @Value("${app.datasets.nameBasics.headers}")
    String[] headers;

    @Value("${app.datasets.nameBasics.filename}")
    String filename;

    @Autowired
    public NameBasicsMapper mapper;

    @Bean("nameBasicsStep")
    @Order(value = 2)
    public Step createStep() {
        return new StepBuilder("nameBasicsStep", jobRepository).chunk(chunkSize, transactionManager)
                                                                .transactionManager(transactionManager)
                                                                .reader(reader())
                                                                .writer(writer)
                                                                .build();
    }

    ItemReader<NameBasics> reader() {
        return datasetReaderFactory.create(filename, mapper, headers);
    }
}
