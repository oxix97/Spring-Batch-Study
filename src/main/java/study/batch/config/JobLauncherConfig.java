package study.batch.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.TaskExecutorJobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.SyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;
import org.springframework.transaction.PlatformTransactionManager;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class JobLauncherConfig {

    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;

    @Bean
    public TaskExecutor asyncTaskExecutor() {
        return new SimpleAsyncTaskExecutor("async-");
    }

    @Bean
    public TaskExecutor syncTaskExecutor() {
        return new SyncTaskExecutor();
    }

    @Bean(name = "asyncJobLauncher")
    public JobLauncher asyncJobLauncher(TaskExecutor asyncTaskExecutor) throws Exception {
        TaskExecutorJobLauncher jobLauncher = new TaskExecutorJobLauncher();
        jobLauncher.setJobRepository(jobRepository);
        jobLauncher.setTaskExecutor(asyncTaskExecutor);
        jobLauncher.afterPropertiesSet();
        return jobLauncher;
    }

    @Primary
    @Bean(name = "syncJobLauncher")
    public JobLauncher syncJobLauncher(TaskExecutor syncTaskExecutor) throws Exception {
        TaskExecutorJobLauncher jobLauncher = new TaskExecutorJobLauncher();
        jobLauncher.setJobRepository(jobRepository);
        jobLauncher.setTaskExecutor(syncTaskExecutor);
        jobLauncher.afterPropertiesSet();
        return jobLauncher;
    }

    @Bean
    public Job job() {
        return new JobBuilder("job", jobRepository)
                .start(step1())
                .next(step2())
                .build();
    }

    @Bean
    public Step step1() {
        return new StepBuilder("step1", jobRepository)
                .tasklet((stepContribution, chunkContext) -> {
                    log.info("step1");
                    Thread.sleep(1000L);
                    return RepeatStatus.FINISHED;
                }, transactionManager)
                .build();
    }

    @Bean
    public Step step2() {
        return new StepBuilder("step2", jobRepository)
                .tasklet((stepContribution, chunkContext) -> {
                    log.info("step2");
                    Thread.sleep(1000L);
                    return RepeatStatus.FINISHED;
                }, transactionManager)
                .build();
    }
}
