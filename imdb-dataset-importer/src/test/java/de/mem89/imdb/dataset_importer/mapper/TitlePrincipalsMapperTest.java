package de.mem89.imdb.dataset_importer.mapper;

import com.google.common.collect.ImmutableMap;
import de.mem89.imdb.dataset_importer.dto.TitlePrincipals;
import de.mem89.imdb.dataset_importer.dto.TitleRatings;
import org.apache.commons.csv.CSVRecord;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;

@ExtendWith({MockitoExtension.class})
public class TitlePrincipalsMapperTest {

    TitlePrincipalsMapper mapper;
    @Mock
    CSVRecord csvRecord;

    Map<String, String> happyPathRecord = ImmutableMap.<String, String>builder()
                                                      .put("tconst", "the-tconst")
                                                      .put("ordering", "6")
                                                      .put("nconst", "the-nconst")
                                                      .put("category", "the-category")
                                                      .put("job", "the-job")
                                                      .put("characters", "the-characters")
                                                      .build();


    @BeforeEach
    void beforeEach() {
        mapper = Mockito.spy(new TitlePrincipalsMapperImpl());
    }

    @Test
    void testMap() {
        // given
        when(csvRecord.toMap()).thenReturn(happyPathRecord);

        // when
        TitlePrincipals titlePrincipals = mapper.map(csvRecord);

        // then
        assertThat(titlePrincipals, is(not(nullValue())));

        assertThat(titlePrincipals.getTconst(), is("the-tconst"));
        assertThat(titlePrincipals.getOrdering(), is(6));
        assertThat(titlePrincipals.getNconst(), is("the-nconst"));
        assertThat(titlePrincipals.getCategory(), is("the-category"));
        assertThat(titlePrincipals.getJob(), is("the-job"));
        assertThat(titlePrincipals.getCharacters(), is("the-characters"));

        verify(csvRecord).toMap();
        verifyNoMoreInteractions(csvRecord);
    }

    @Test
    void testMapNullValueCsvRecord() {
        // given
        CSVRecord csvRecord = null;

        // when
        TitlePrincipals titlePrincipals = mapper.map(csvRecord);

        // then
        assertThat(titlePrincipals, is(nullValue()));
    }

    @Test
    void testMapNullValueMap() {
        // given
        Map<String, String> map = null;

        // when
        TitlePrincipals titlePrincipals = mapper.map(map);

        // then
        assertThat(titlePrincipals, is(nullValue()));
    }
}
