package study.batch.tasklet;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ExecutionContextTasklet2 implements Tasklet {

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        log.info("step2 execute");

        ExecutionContext jobExecutionContext = contribution.getStepExecution().getJobExecution().getExecutionContext();
        ExecutionContext stepExecutionContext = contribution.getStepExecution().getExecutionContext();

        log.info("jobName: {}", jobExecutionContext.get("jobName"));
        log.info("stepName: {}", stepExecutionContext.get("stepName"));

        String stepName = chunkContext.getStepContext().getStepExecution().getStepName();

        if (stepExecutionContext.get("stepName") == null) {
            stepExecutionContext.put("stepName", stepName);
        }

        log.info("step2 jobName: {}, stepName: {}", jobExecutionContext.get("jobName"), stepExecutionContext.get("stepName"));
        return RepeatStatus.FINISHED;
    }
}

