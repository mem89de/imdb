package de.mem89.imdb.dataset_importer.configuration;

import de.mem89.imdb.dataset_importer.dto.TitleRatings;
import de.mem89.imdb.dataset_importer.mapper.TitleRatingsMapper;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

@Configuration
public class TitleRatingsStepConfiguration extends AbstractStepConfiguration {

    @Value("${app.datasets.titleRatings.headers}")
    String[] headers;

    @Value("${app.datasets.titleRatings.filename}")
    String filename;

    @Autowired
    public TitleRatingsMapper mapper;

    @Bean("titleRatingsStep")
    @Order(value = 7)
    public Step createStep() {
        return new StepBuilder("titleRatingsStep", jobRepository).chunk(chunkSize, transactionManager)
                                                                .transactionManager(transactionManager)
                                                                .reader(reader())
                                                                .writer(writer)
                                                                .build();
    }

    ItemReader<TitleRatings> reader() {
        return datasetReaderFactory.create(filename, mapper, headers);
    }
}
