package study.batch.validator;


import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.JobParametersValidator;
import org.springframework.batch.core.job.DefaultJobParametersValidator;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class SimpleJobValidator implements JobParametersValidator {

    @Override
    public void validate(JobParameters parameters) throws JobParametersInvalidException {
        String fileName = parameters.getString("name");
        if(!StringUtils.hasText(fileName)){
            throw new JobParametersInvalidException("name is required!!");
        }
    }
}
