package de.mem89.imdb.dataset_importer.mapper;

import de.mem89.imdb.dataset_importer.dto.NameBasics;
import de.mem89.imdb.dataset_importer.headers.NameBasicsHeader;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Component;

@Component("nameBasicsMapper")
public class NameBasicsMapper extends AbstractCSVRecordMapper<NameBasics> {

    @Override
    NameBasics mapNonNull(CSVRecord csvRecord) {
        return NameBasics.builder().ncsonst(csvRecord.get(NameBasicsHeader.nconst)).build();
    }


}
