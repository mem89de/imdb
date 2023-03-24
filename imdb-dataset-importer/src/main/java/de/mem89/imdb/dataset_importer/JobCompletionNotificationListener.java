package de.mem89.imdb.dataset_importer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.StepExecution;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;

@Component
@Slf4j
public class JobCompletionNotificationListener implements JobExecutionListener {
    public JobCompletionNotificationListener() {
        log.info("Importing TitleBasics");
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        JobExecutionListener.super.afterJob(jobExecution);
        log.info("Job: status={}, duration={}", jobExecution.getStatus(), calculateDuration(jobExecution));

        for (StepExecution stepExecution : jobExecution.getStepExecutions()) {
            log.info("Step: {} executed in {}. Summary: {}", stepExecution.getStepName(), calculateDuration(stepExecution), stepExecution.getSummary());
        }
    }

    private String calculateDuration(JobExecution jobExecution) {
        LocalDateTime startTime = jobExecution.getStartTime();
        LocalDateTime endTime = jobExecution.getEndTime();

        return calculateDuration(startTime, endTime);
    }

    private String calculateDuration(StepExecution stepExecutionExecution) {
        LocalDateTime startTime = stepExecutionExecution.getStartTime();
        LocalDateTime endTime = stepExecutionExecution.getEndTime();

        return calculateDuration(startTime, endTime);
    }

    private static String calculateDuration(LocalDateTime startTime, LocalDateTime endTime) {
        Duration duration = Duration.between(startTime, endTime);
        return String.format("%d seconds", duration.getSeconds());
    }
}
