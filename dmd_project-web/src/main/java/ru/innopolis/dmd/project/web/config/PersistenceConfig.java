package ru.innopolis.dmd.project.web.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;

/**
 * @author Timur Kasatkin
 * @date 23.10.15.
 * @email aronwest001@gmail.com
 */
@Configuration
@Import(DataSourceConfig.class)
@ImportResource("classpath:spring-service-context.xml")
public class PersistenceConfig {
}
