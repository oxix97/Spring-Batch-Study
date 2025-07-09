package study.batch.config;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@RequiredArgsConstructor
@Configuration
public class HelloJobConfig {

    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;

    @Bean
    public Job helloJob(Step helloStep1, Step helloStep2) {
        return new JobBuilder("helloJob", jobRepository)
                .incrementer(new RunIdIncrementer())
                .start(helloStep1)
                .next(helloStep2)
                .build();
    }

    @Bean
    public Step helloStep1(Tasklet helloTasklet1) {
        return new StepBuilder("helloStep1", jobRepository)
                .tasklet(helloTasklet1, transactionManager)
                .build();
    }

    @Bean
    public Step helloStep2(Tasklet helloTasklet2) {
        return new StepBuilder("helloStep2", jobRepository)
                .tasklet(helloTasklet2, transactionManager)
                .build();
    }

    @Bean
    public Tasklet helloTasklet1() {
        return (contribution, chunkContext) -> {
            System.out.println("Hello Step 1");
            return RepeatStatus.FINISHED;
        };
    }

    @Bean
    public Tasklet helloTasklet2() {
        return (contribution, chunkContext) -> {
            System.out.println("Hello Step 2");
            return RepeatStatus.FINISHED;
        };
    }
}