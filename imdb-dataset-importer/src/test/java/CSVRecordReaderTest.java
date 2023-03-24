import de.mem89.imdb.dataset_importer.reader.CSVRecordReader;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static de.mem89.imdb.test.matcher.HasRowValues.*;

public class CSVRecordReaderTest {


    @Test
    void readCSVDefaultTest() {
        CSVRecordReader csvRecordReader = CSVRecordReader
                .builder(getTestFile("csvRecordReaderExample.csv"))
                .build();

        CSVRecord line1 = csvRecordReader.read();
        assertThat(line1, hasRowValues("foo", "bar", "baz"));

        CSVRecord line2 = csvRecordReader.read();
        assertThat(line2, hasRowValues("gak", "ork", "123"));

        CSVRecord line3 = csvRecordReader.read();
        assertThat(line3, is(nullValue()));
    }

    @Test
    void readCSVCustomizedTest() {
        CSVRecordReader csvRecordReader = CSVRecordReader
                .builder(getTestFile("csvRecordReaderExample.tsv"))
                .csvFormat(CSVFormat.DEFAULT.builder()
                        .setDelimiter('\t')
                        .setSkipHeaderRecord(true)
                        .setHeader(new String[]{"header_1", "header_2", "header_3"})
                        .build())
                .build();

        CSVRecord line1 = csvRecordReader.read();
        assertThat(line1, hasRowValues("foo", "bar", "baz"));

        CSVRecord line2 = csvRecordReader.read();
        assertThat(line2, hasRowValues("gak", "ork", "123"));

        CSVRecord line3 = csvRecordReader.read();
        assertThat(line3, is(nullValue()));
    }

    private InputStream getTestFile(String filename) {
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource(filename).getFile());
        try {
            return new FileInputStream(file);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
