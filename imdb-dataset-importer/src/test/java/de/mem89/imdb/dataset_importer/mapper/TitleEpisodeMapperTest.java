package de.mem89.imdb.dataset_importer.mapper;

import com.google.common.collect.ImmutableMap;
import de.mem89.imdb.dataset_importer.dto.TitleEpisode;
import de.mem89.imdb.dataset_importer.dto.TitlePrincipals;
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
import static org.mockito.Mockito.*;

@ExtendWith({MockitoExtension.class})
public class TitleEpisodeMapperTest {

    TitleEpisodeMapper mapper;
    @Mock
    CSVRecord csvRecord;

    Map<String, String> happyPathRecord = ImmutableMap.<String, String>builder()
                                                      .put("tconst", "the-tconst")
                                                      .put("parentTconst", "the-parent-tconst")
                                                      .put("seasonNumber", "7")
                                                      .put("episodeNumber", "3")
                                                      .build();


    @BeforeEach
    void beforeEach() {
        mapper = Mockito.spy(new TitleEpisodeMapperImpl());
    }

    @Test
    void testMap() {
        // given
        when(csvRecord.toMap()).thenReturn(happyPathRecord);

        // when
        TitleEpisode titleEpisode = mapper.map(csvRecord);

        // then
        assertThat(titleEpisode, is(not(nullValue())));

        assertThat(titleEpisode.getTconst(), is("the-tconst"));
        assertThat(titleEpisode.getParentTconst(), is("the-parent-tconst"));
        assertThat(titleEpisode.getSeasonNumber(), is(7));
        assertThat(titleEpisode.getEpisodeNumber(), is(3));

        verify(csvRecord).toMap();
        verifyNoMoreInteractions(csvRecord);
    }

    @Test
    void testMapNullValueCsvRecord() {
        // given
        CSVRecord csvRecord = null;

        // when
        TitleEpisode titleEpisode = mapper.map(csvRecord);

        // then
        assertThat(titleEpisode, is(nullValue()));
    }

    @Test
    void testMapNullValueMap() {
        // given
        Map<String, String> map = null;

        // when
        TitleEpisode titleEpisode = mapper.map(map);

        // then
        assertThat(titleEpisode, is(nullValue()));
    }
}
