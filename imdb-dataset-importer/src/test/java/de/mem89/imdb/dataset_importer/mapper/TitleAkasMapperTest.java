package de.mem89.imdb.dataset_importer.mapper;

import com.google.common.collect.ImmutableMap;
import de.mem89.imdb.dataset_importer.dto.TitleAkas;
import org.apache.commons.csv.CSVRecord;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;

@ExtendWith({MockitoExtension.class})
public class TitleAkasMapperTest {

    TitleAkasMapper mapper;
    @Mock
    CSVRecord csvRecord;

    ImmutableMap.Builder<String, String> happyPathRecordBuilder = ImmutableMap.<String, String>builder()
                                                                              .put("titleId", "the-title-id")
                                                                              .put("ordering", "5")
                                                                              .put("title", "the-title")
                                                                              .put("language", "the-language")
                                                                              .put("region", "the-region")
                                                                              .put("types", "the,three,types")
                                                                              .put("attributes", "the,three,attributes");
    Map<String, String> emptyCellsRecord = ImmutableMap.<String, String>builder()
                                                       .put("titleId", "the-title-id")
                                                       .put("ordering", "5")
                                                       .put("title", "the-title")
                                                       .put("language", "the-language")
                                                       .put("region", "the-region")
                                                       .put("types", "\\N")
                                                       .put("attributes", "\\N")
                                                       .put("isOriginalTitle", "1")
                                                       .build();


    @BeforeEach
    void beforeEach() {
        mapper = Mockito.spy(new TitleAkasMapperImpl());
    }

    @ParameterizedTest
    @ValueSource(booleans = {true, false})
    void testMap(boolean isOriginalTitle) {
        // given
        when(csvRecord.toMap()).thenReturn(happyPathRecordBuilder.put("isOriginalTitle", isOriginalTitle ? "1" : "0")
                                                                 .build());

        // when
        TitleAkas titleAkas = mapper.map(csvRecord);

        // then
        assertThat(titleAkas, is(not(nullValue())));

        assertThat(titleAkas.getTitle(), is("the-title"));
        assertThat(titleAkas.getOrdering(), is(5));
        assertThat(titleAkas.getTitle(), is("the-title"));
        assertThat(titleAkas.getRegion(), is("the-region"));
        assertThat(titleAkas.getLanguage(), is("the-language"));
        assertThat(titleAkas.getTypes(), contains("the", "three", "types"));
        assertThat(titleAkas.getAttributes(), contains("the", "three", "attributes"));
        assertThat(titleAkas.isOriginalTitle(), is(isOriginalTitle));

        verify(csvRecord).toMap();
        verifyNoMoreInteractions(csvRecord);
    }

    @Test
    void testMapEmptyCells() {
        // given
        when(csvRecord.toMap()).thenReturn(emptyCellsRecord);

        // when
        TitleAkas titleAkas = mapper.map(csvRecord);

        // then
        assertThat(titleAkas, is(not(nullValue())));

        assertThat(titleAkas.getTypes(), emptyIterable());
        assertThat(titleAkas.getAttributes(), emptyIterable());

        verify(csvRecord).toMap();
        verifyNoMoreInteractions(csvRecord);
    }

    @Test
    void testMapNullValueCsvRecord() {
        // given
        CSVRecord csvRecord = null;

        // when
        TitleAkas titleAkas = mapper.map(csvRecord);

        // then
        assertThat(titleAkas, is(nullValue()));
    }

    @Test
    void testMapNullValueMap() {
        // given
        Map<String, String> map = null;

        // when
        TitleAkas titleAkas = mapper.map(map);

        // then
        assertThat(titleAkas, is(nullValue()));
    }
}
