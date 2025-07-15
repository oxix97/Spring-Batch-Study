package study.batch.tasklet;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Slf4j
@Component
public class ExecutionContextTasklet3 implements Tasklet {
    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        log.info("step3 execute");

        ExecutionContext context = chunkContext.getStepContext()
                .getStepExecution().getJobExecution()
                .getExecutionContext();

        if (Objects.isNull(context.get("name"))) {
            context.put("name", "user1");
            throw new RuntimeException("step2 was failed");
        }
        return RepeatStatus.FINISHED;
    }
}

