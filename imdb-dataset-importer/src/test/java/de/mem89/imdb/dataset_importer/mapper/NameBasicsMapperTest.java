package de.mem89.imdb.dataset_importer.mapper;

import com.google.common.collect.ImmutableMap;
import de.mem89.imdb.dataset_importer.dto.NameBasics;
import org.apache.commons.csv.CSVRecord;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.contains;
import static org.mockito.Mockito.*;

@ExtendWith({MockitoExtension.class})
public class NameBasicsMapperTest {

    NameBasicsMapper mapper;
    @Mock
    CSVRecord csvRecord;

    Map<String, String> happyPathRecord = ImmutableMap.<String, String>builder()
                                                      .put("nconst", "the-nconst")
                                                      .put("primaryName", "the-primary-name")
                                                      .put("birthYear", "1950")
                                                      .put("deathYear", "2016")
                                                      .put("primaryProfession", "actor,director")
                                                      .put("knownForTitles", "Great Movie I,Great Movie II")
                                                      .build();

    Map<String, String> emptyCellsPathRecord = ImmutableMap.<String, String>builder()
                                                      .put("nconst", "the-nconst")
                                                      .put("primaryName", "\\N")
                                                      .put("birthYear", "\\N")
                                                      .put("deathYear", "2016")
                                                      .put("primaryProfession", "actor,director")
                                                      .put("knownForTitles", "\\N")
                                                      .build();

    @BeforeEach
    void beforeEach() {
        mapper = Mockito.spy(new NameBasicsMapperImpl());
    }

    @Test
    void testMap() {
        // given
        when(csvRecord.toMap()).thenReturn(happyPathRecord);

        // when
        NameBasics nameBasics = mapper.map(csvRecord);

        // then
        assertThat(nameBasics, is(not(nullValue())));
        assertThat(nameBasics.getNconst(), is("the-nconst"));
        assertThat(nameBasics.getPrimaryName(), is("the-primary-name"));
        assertThat(nameBasics.getBirthYear(), is(1950));
        assertThat(nameBasics.getDeathYear(), is(2016));
        assertThat(nameBasics.getPrimaryProfession(), contains("actor", "director"));
        assertThat(nameBasics.getKnownForTitles(), contains("Great Movie I", "Great Movie II"));

        verify(csvRecord).toMap();
        verifyNoMoreInteractions(csvRecord);
    }

    @Test
    void testMapEmptyCells() {
        // given
        when(csvRecord.toMap()).thenReturn(emptyCellsPathRecord);

        // when
        NameBasics nameBasics = mapper.map(csvRecord);

        // then
        assertThat(nameBasics, is(not(nullValue())));
        assertThat(nameBasics.getNconst(), is("the-nconst"));
        assertThat(nameBasics.getPrimaryName(), is(nullValue()));
        assertThat(nameBasics.getBirthYear(), is(nullValue()));
        assertThat(nameBasics.getDeathYear(), is(2016));
        assertThat(nameBasics.getPrimaryProfession(), contains("actor", "director"));
        assertThat(nameBasics.getKnownForTitles(), is(emptyIterable()));

        verify(csvRecord).toMap();
        verifyNoMoreInteractions(csvRecord);
    }

    @Test
    void testMapNullValueCsvRecord() {
        // given
        CSVRecord csvRecord = null;

        // when
        NameBasics nameBasics = mapper.map(csvRecord);

        // then
        assertThat(nameBasics, is(nullValue()));
    }

    @Test
    void testMapNullValueMap() {
        // given
        Map<String, String> map = null;

        // when
        NameBasics nameBasics = mapper.map(map);

        // then
        assertThat(nameBasics, is(nullValue()));
    }
}
