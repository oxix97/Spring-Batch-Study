package study.batch.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;
import study.batch.incrementer.CustomJobIncrementer;
import study.batch.validator.CustomJobParametersValidator;
import study.batch.validator.SimpleJobValidator;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class SimpleJobBatchConfig {

    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;
    private final CustomJobParametersValidator validator;

    @Bean
    public Job simpleJob() {
        return new JobBuilder("simpleJob", jobRepository)
                .incrementer(new CustomJobIncrementer())
                .validator(validator)
                .start(step1())
                .on("COMPLETED").to(step2())
                .from(step1())
                .on("FAILED").to(step3())
                .end()
                .build();
    }

    @Bean
    public Step step1() {
        return new StepBuilder("step1", jobRepository)
                .tasklet((contribution, chunkContext) -> {
                    log.info("step1");
                    chunkContext.getStepContext().getStepExecution().setStatus(BatchStatus.FAILED);
                    contribution.setExitStatus(ExitStatus.FAILED);
                    return RepeatStatus.FINISHED;
                }, transactionManager)
                .build();
    }

    @Bean
    public Step step2() {
        return new StepBuilder("step2", jobRepository)
                .tasklet((contribution, chunkContext) -> {
                    log.info("step2");
                    return RepeatStatus.FINISHED;
                }, transactionManager)
                .build();
    }

    @Bean
    public Step step3() {
        return new StepBuilder("step3", jobRepository)
                .tasklet((contribution, chunkContext) -> {
                    log.info("step3");
                    return RepeatStatus.FINISHED;
                }, transactionManager)
                .build();
    }
}
