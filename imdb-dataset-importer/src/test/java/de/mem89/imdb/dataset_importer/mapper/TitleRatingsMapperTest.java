package de.mem89.imdb.dataset_importer.mapper;

import com.google.common.collect.ImmutableMap;
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
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;

@ExtendWith({MockitoExtension.class})
public class TitleRatingsMapperTest {

    public static final BigDecimal EXPECTED_AVERAGE_RATING = new BigDecimal("6.5");
    public static final BigDecimal ERROR = BigDecimal.ZERO;
    TitleRatingsMapper mapper;
    @Mock
    CSVRecord csvRecord;

    Map<String, String> happyPathRecord = ImmutableMap.<String, String>builder()
                                                      .put("tconst", "the-tconst")
                                                      .put("averageRating", "6.5")
                                                      .put("numVotes", "2604")
                                                      .build();


    @BeforeEach
    void beforeEach() {
        mapper = Mockito.spy(new TitleRatingsMapperImpl());
    }

    @Test
    void testMap() {
        // given
        when(csvRecord.toMap()).thenReturn(happyPathRecord);

        // when
        TitleRatings titleRatings = mapper.map(csvRecord);

        // then
        assertThat(titleRatings, is(not(nullValue())));

        assertThat(titleRatings.getTconst(), is("the-tconst"));
        assertThat(titleRatings.getAverageRating(), is(closeTo(EXPECTED_AVERAGE_RATING, ERROR)));
        assertThat(titleRatings.getNumVotes(), is(2604));


        verify(csvRecord).toMap();
        verifyNoMoreInteractions(csvRecord);
    }

    @Test
    void testMapNullValueCsvRecord() {
        // given
        CSVRecord csvRecord = null;

        // when
        TitleRatings titleRatings = mapper.map(csvRecord);

        // then
        assertThat(titleRatings, is(nullValue()));
    }

    @Test
    void testMapNullValueMap() {
        // given
        Map<String, String> map = null;

        // when
        TitleRatings titleRatings = mapper.map(map);

        // then
        assertThat(titleRatings, is(nullValue()));
    }
}
