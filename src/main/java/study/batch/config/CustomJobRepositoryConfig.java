package study.batch.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;
import study.batch.listener.JobRepositoryListener;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class CustomJobRepositoryConfig {

    private final JobRepository jobRepository;
    private final PlatformTransactionManager tm;
    private final JobRepositoryListener jobRepositoryListener;

    @Bean
    public Job customJob() {
        return new JobBuilder("customJob", jobRepository)
                .listener(jobRepositoryListener)
                .start(step1())
                .next(step2())
                .build();
    }

    @Bean
    public Step step1() {
        return new StepBuilder("step1", jobRepository)
                .tasklet((contribution, chunkContext) -> {
                    log.info("step1");
                    return RepeatStatus.FINISHED;
                }, tm)
                .build();
    }

    @Bean
    public Step step2() {
        return new StepBuilder("step2", jobRepository)
                .tasklet((contribution, chunkContext) -> {
                    log.info("step2");
                    return RepeatStatus.FINISHED;
                }, tm)
                .build();
    }
}
