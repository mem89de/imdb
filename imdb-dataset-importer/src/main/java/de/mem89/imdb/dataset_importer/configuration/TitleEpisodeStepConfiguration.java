package de.mem89.imdb.dataset_importer.configuration;

import de.mem89.imdb.dataset_importer.dto.TitleEpisode;
import de.mem89.imdb.dataset_importer.mapper.TitleEpisodeMapper;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

@Configuration
public class TitleEpisodeStepConfiguration extends AbstractStepConfiguration {

    @Value("${app.datasets.titleEpisode.headers}")
    String[] headers;

    @Value("${app.datasets.titleEpisode.filename}")
    String filename;

    @Autowired
    public TitleEpisodeMapper mapper;

    @Bean("titleEpisodeStep")
    @Order(value = 5)
    public Step createStep() {
        return new StepBuilder("titleEpisodeStep", jobRepository).chunk(chunkSize, transactionManager)
                                                                 .transactionManager(transactionManager)
                                                                 .reader(reader())
                                                                 .writer(writer)
                                                                 .build();
    }

    ItemReader<TitleEpisode> reader() {
        return datasetReaderFactory.create(filename, mapper, headers);
    }
}
