package de.mem89.imdb.dataset_importer.configuration;

import de.mem89.imdb.dataset_importer.dto.TitleCrew;
import de.mem89.imdb.dataset_importer.mapper.TitleCrewMapper;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

@Configuration
public class TitleCrewStepConfiguration extends AbstractStepConfiguration {

    @Value("${app.datasets.titleCrew.headers}")
    String[] headers;

    @Value("${app.datasets.titleCrew.filename}")
    String filename;

    @Autowired
    public TitleCrewMapper mapper;

    @Bean("titleCrewStep")
    @Order(value = 4)
    public Step createStep() {
        return new StepBuilder("titleCrewStep", jobRepository).chunk(chunkSize, transactionManager)
                                                              .transactionManager(transactionManager)
                                                              .reader(reader())
                                                              .writer(writer)
                                                              .build();
    }

    ItemReader<TitleCrew> reader() {
        return datasetReaderFactory.create(filename, mapper, headers);
    }
}
