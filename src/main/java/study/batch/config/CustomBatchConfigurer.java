package study.batch.config;

import org.springframework.batch.core.DefaultJobKeyGenerator;
import org.springframework.batch.core.configuration.support.DefaultBatchConfiguration;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.support.JobRepositoryFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@Configuration
public class CustomBatchConfigurer {

    @Bean
    public JobRepository customJobRepository(DataSource dataSource, PlatformTransactionManager tm) {
        try {
            JobRepositoryFactoryBean factory = new JobRepositoryFactoryBean();
            factory.setDataSource(dataSource);
            factory.setTransactionManager(tm);
            factory.setTablePrefix("SYSTEM_");
            factory.setJobKeyGenerator(new DefaultJobKeyGenerator());
            factory.afterPropertiesSet();
            return factory.getObject();
        } catch (Exception e) {
            throw new RuntimeException("Batch JobRepository 생성에 실패했습니다.", e);
        }
    }
}
