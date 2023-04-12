package de.mem89.imdb.dataset_importer.writer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

@Component("logWriter")
public class LogWriter<T> implements ItemWriter<T> {
    Logger logger = LoggerFactory.getLogger(LogWriter.class);
    @Override
    public void write(Chunk<? extends T> chunk) throws Exception {
        logger.trace("Writing chunk with {} items", chunk.size());
        for(Object item : chunk){
            logger.info("{}", item.toString());
        }
    }
}
