package study.batch.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@Slf4j
@RequestMapping("/api/job")
@RestController
public class JobLauncherController {
    public JobLauncherController(
            Job job,
            @Qualifier("syncJobLauncher") JobLauncher syncJobLauncher,
            @Qualifier("asyncJobLauncher") JobLauncher asyncJobLauncher
    ) {
        this.job = job;
        this.syncJobLauncher = syncJobLauncher;
        this.asyncJobLauncher = asyncJobLauncher;
    }

    private final Job job;
    private final JobLauncher syncJobLauncher;
    private final JobLauncher asyncJobLauncher;

    @GetMapping("/sync")
    public ResponseEntity<String> launch(
            @RequestParam("member_id") String memberId
    ) throws Exception {
        JobParameters jobParameters = new JobParametersBuilder()
                .addString("member_id", memberId)
                .addDate("date", new Date())
                .toJobParameters();

        syncJobLauncher.run(job, jobParameters);

        return ResponseEntity.ok("sync success : " + memberId);
    }

    @GetMapping("/async")
    public ResponseEntity<String> launchAsync(
            @RequestParam("member_id") String memberId
    ) throws Exception {
        JobParameters jobParameters = new JobParametersBuilder()
                .addString("member_id", memberId)
                .addDate("date", new Date())
                .toJobParameters();
        asyncJobLauncher.run(job, jobParameters);
        return ResponseEntity.ok("async success : " + memberId);
    }
}