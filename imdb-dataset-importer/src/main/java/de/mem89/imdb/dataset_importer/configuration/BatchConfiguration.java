package de.mem89.imdb.dataset_importer.configuration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.job.builder.SimpleJobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

import java.util.List;

@Configuration
@Slf4j
public class BatchConfiguration {

    // Todo: Should only be a workaround
    @Autowired
    @Lazy
    List<Step> steps;

    @Bean
    public Job importDatasetsJob(JobRepository jobRepository, JobExecutionListener listener) {
        log.debug("Configuring job 'importDatasetsJob");

        JobBuilder jobBuilder = new JobBuilder("importDatasetsJob", jobRepository).incrementer(new RunIdIncrementer())
                                                                                  .listener(listener);

        // TODO: Ugly!
        Step firstStep = steps.get(0);
        SimpleJobBuilder simpleJobBuilder = jobBuilder.start(firstStep);
        if (steps.size() > 1) {
            for (Step step : steps.subList(1, steps.size())) {
                simpleJobBuilder.next(step);
            }
        }

        return simpleJobBuilder.build();
    }

}
