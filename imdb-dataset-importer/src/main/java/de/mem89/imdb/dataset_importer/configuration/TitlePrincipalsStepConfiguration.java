package de.mem89.imdb.dataset_importer.configuration;

import de.mem89.imdb.dataset_importer.dto.TitlePrincipals;
import de.mem89.imdb.dataset_importer.mapper.TitlePrincipalsMapper;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

@Configuration
public class TitlePrincipalsStepConfiguration extends AbstractStepConfiguration {

    @Value("${app.datasets.titlePrincipals.headers}")
    String[] headers;

    @Value("${app.datasets.titlePrincipals.filename}")
    String filename;

    @Autowired
    public TitlePrincipalsMapper mapper;

    @Bean("titlePrincipalsStep")
    @Order(value = 6)
    public Step createStep() {
        return new StepBuilder("titlePrincipalsStep", jobRepository).chunk(chunkSize, transactionManager)
                                                                .transactionManager(transactionManager)
                                                                .reader(reader())
                                                                .writer(writer)
                                                                .build();
    }

    ItemReader<TitlePrincipals> reader() {
        return datasetReaderFactory.create(filename, mapper, headers);
    }
}
