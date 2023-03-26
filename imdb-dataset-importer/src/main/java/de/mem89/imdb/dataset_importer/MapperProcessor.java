package de.mem89.imdb.dataset_importer;

import de.mem89.imdb.dataset_importer.mapper.interfaces.Mapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
@Deprecated
public class MapperProcessor<FROM, TO> implements ItemProcessor<FROM, TO> {
    @Autowired
    Mapper<FROM, TO> mapper;

    public MapperProcessor(Mapper<FROM, TO> mapper) {
        log.debug("Creating MapperProcessor");
        this.mapper = mapper;
    }

    @Override
    public TO process(FROM item) throws Exception {
        log.debug("Mapping {}", item);
        return mapper.map(item);
    }
}
