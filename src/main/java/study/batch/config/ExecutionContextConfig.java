package study.batch.config;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.support.JobRepositoryFactoryBean;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;
import study.batch.tasklet.ExecutionContextTasklet1;
import study.batch.tasklet.ExecutionContextTasklet2;
import study.batch.tasklet.ExecutionContextTasklet3;
import study.batch.tasklet.ExecutionContextTasklet4;

@RequiredArgsConstructor
@Configuration
public class ExecutionContextConfig {

    private final JobRepository jobRepository;
    private final PlatformTransactionManager tm;

    @Bean
    public Job batchJob() {
        return new JobBuilder("batchJob", jobRepository)
                .start(step1())
                .next(step2())
                .next(step3())
                .next(step4())
                .build();
    }

    @Bean
    public Step step1() {
        return new StepBuilder("step1", jobRepository)
                .tasklet(new ExecutionContextTasklet1(), tm)
                .build();
    }

    @Bean
    public Step step2() {
        return new StepBuilder("step2", jobRepository)
                .tasklet(new ExecutionContextTasklet2(), tm)
                .build();
    }

    @Bean
    public Step step3() {
        return new StepBuilder("step3", jobRepository)
                .tasklet(new ExecutionContextTasklet3(), tm)
                .build();
    }

    @Bean
    public Step step4() {
        return new StepBuilder("step4", jobRepository)
                .tasklet(new ExecutionContextTasklet4(), tm)
                .build();
    }
}
