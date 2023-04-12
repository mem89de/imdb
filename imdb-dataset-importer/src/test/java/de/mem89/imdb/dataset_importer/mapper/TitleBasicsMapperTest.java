package de.mem89.imdb.dataset_importer.mapper;

import com.google.common.collect.ImmutableMap;
import de.mem89.imdb.dataset_importer.dto.TitleBasics;
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
public class TitleBasicsMapperTest {

    TitleBasicsMapper mapper;
    @Mock
    CSVRecord csvRecord;

    ImmutableMap.Builder<String, String> happyPathRecordBuilder = ImmutableMap.<String, String>builder()
                                                                              .put("tconst", "the-tconst")
                                                                              .put("titleType", "the-titleType")
                                                                              .put("primaryTitle", "the-primaryTitle")
                                                                              .put("originalTitle", "the-originalTitle")
                                                                              .put("startYear", "2022")
                                                                              .put("endYear", "2023")
                                                                              .put("runtimeMinutes", "1234")
                                                                              .put("genres", "horror,coding");
    Map<String, String> emptyCellsRecord = ImmutableMap.<String, String>builder()
                                                       .put("tconst", "the-tconst")
                                                       .put("titleType", "the-titleType")
                                                       .put("primaryTitle", "the-primaryTitle")
                                                       .put("originalTitle", "the-originalTitle")
                                                       .put("startYear", "2022")
                                                       .put("endYear", "2023")
                                                       .put("runtimeMinutes", "1234")
                                                       .put("isAdult", "1")
                                                       .put("genres", "\\N")
                                                       .build();


    @BeforeEach
    void beforeEach() {
        mapper = Mockito.spy(new TitleBasicsMapperImpl());
    }

    @ParameterizedTest
    @ValueSource(booleans = {true, false})
    void testMap(boolean isAdult) {
        // given
        when(csvRecord.toMap()).thenReturn(happyPathRecordBuilder.put("isAdult", isAdult ? "1" : "0").build());

        // when
        TitleBasics titleBasics = mapper.map(csvRecord);

        // then
        assertThat(titleBasics, is(not(nullValue())));

        assertThat(titleBasics.getTconst(), is("the-tconst"));
        assertThat(titleBasics.getTitleType(), is("the-titleType"));
        assertThat(titleBasics.getPrimaryTitle(), is("the-primaryTitle"));
        assertThat(titleBasics.getOriginalTitle(), is("the-originalTitle"));
        assertThat(titleBasics.getStartYear(), is("2022"));
        assertThat(titleBasics.getEndYear(), is("2023"));
        assertThat(titleBasics.getRuntimeMinutes(), is("1234"));
        assertThat(titleBasics.getGenres(), contains("horror", "coding"));
        assertThat(titleBasics.isAdult(), is(isAdult));

        verify(csvRecord).toMap();
        verifyNoMoreInteractions(csvRecord);
    }

    @Test
    void testMapEmptyCells() {
        // given
        when(csvRecord.toMap()).thenReturn(emptyCellsRecord);

        // when
        TitleBasics titleBasics = mapper.map(csvRecord);

        // then
        assertThat(titleBasics, is(not(nullValue())));

        assertThat(titleBasics.getGenres(), is(emptyIterable()));

        verify(csvRecord).toMap();
        verifyNoMoreInteractions(csvRecord);
    }

    @Test
    void testMapNullValueCsvRecord() {
        // given
        CSVRecord csvRecord = null;

        // when
        TitleBasics titleBasics = mapper.map(csvRecord);

        // then
        assertThat(titleBasics, is(nullValue()));
    }

    @Test
    void testMapNullValueMap() {
        // given
        Map<String, String> map = null;

        // when
        TitleBasics titleBasics = mapper.map(map);

        // then
        assertThat(titleBasics, is(nullValue()));
    }
}
