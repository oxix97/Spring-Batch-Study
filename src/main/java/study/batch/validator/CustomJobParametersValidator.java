package study.batch.validator;

import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.job.DefaultJobParametersValidator;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@Component
public class CustomJobParametersValidator extends DefaultJobParametersValidator {

    public CustomJobParametersValidator() {
        super(RequireKeys.getKeys(), OptionalKeys.getKeys());
    }

    @Override
    public void validate(JobParameters parameters) throws JobParametersInvalidException {
        super.validate(parameters);
        String date = parameters.getString("date");
        try {
            DateTimeFormatter.ISO_LOCAL_DATE.parse(date);
        } catch (DateTimeParseException e) {
            throw new JobParametersInvalidException("date는 'yyyy-MM-dd' 형식이어야 합니다.");
        }
    }
}
