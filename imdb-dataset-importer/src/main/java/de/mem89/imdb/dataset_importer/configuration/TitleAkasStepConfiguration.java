package de.mem89.imdb.dataset_importer.configuration;

import de.mem89.imdb.dataset_importer.dto.TitleAkas;
import de.mem89.imdb.dataset_importer.mapper.TitleAkasMapper;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

@Configuration
public class TitleAkasStepConfiguration extends AbstractStepConfiguration {

    @Value("${app.datasets.titleAkas.headers}")
    String[] headers;

    @Value("${app.datasets.titleAkas.filename}")
    String filename;

    @Autowired
    public TitleAkasMapper mapper;

    @Bean("titleAkasStep")
    @Order(value = 3)
    public Step createStep() {
        return new StepBuilder("titleAkasStep", jobRepository).chunk(chunkSize, transactionManager)
                                                              .transactionManager(transactionManager)
                                                              .reader(reader())
                                                              .writer(writer)
                                                              .build();
    }

    ItemReader<TitleAkas> reader() {
        return datasetReaderFactory.create(filename, mapper, headers);
    }
}
