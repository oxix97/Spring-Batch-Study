package study.batch.listener;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.*;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class JobRepositoryListener implements JobExecutionListener {

    private final JobRepository jobRepository;

    @Override
    public void afterJob(JobExecution jobExecution) {
        String jobName = jobExecution.getJobInstance().getJobName();
        JobParameters jobParameters = new JobParametersBuilder()
                .addString("requestDate", "20250715")
                .toJobParameters();

        // jobName과 jobParameters로 마지막 Job 실행 정보를 조회합니다.
        JobExecution lastExecution = jobRepository.getLastJobExecution(jobName, jobParameters);

        // 조회된 실행 정보가 null이 아닌 경우에만 로직을 수행합니다.
        if (lastExecution != null) {
            log.info("조회된 Job Execution ID: {}", lastExecution.getId());
            lastExecution.getStepExecutions().forEach(stepExecution -> {
                BatchStatus status = stepExecution.getStatus();
                ExitStatus exitStatus = stepExecution.getExitStatus();
                log.info("Step Name: {}, Status: {}, Exit Status: {}", stepExecution.getStepName(), status, exitStatus.getExitCode());
            });
        } else {
            log.warn("{} Job에 대한 실행 기록(requestDate={})을 찾을 수 없습니다.", jobName, "20250715");
        }
    }
}