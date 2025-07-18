package study.batch.config;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.boot.autoconfigure.batch.BatchProperties;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@RequiredArgsConstructor
@Component
public class CustomJobLauncher {

    private final JobLauncher jobLauncher;
    private final Job job;
    private final Job flowJob;

    @PostConstruct
    public void launch() throws Exception {
        JobParameters jobParameters = new JobParametersBuilder()
                .addLocalDate("date", LocalDate.now())
                .toJobParameters();

        jobLauncher.run(job, jobParameters);
        jobLauncher.run(flowJob, jobParameters);
    }
}
