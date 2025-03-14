/**
 *
 */
package it.cambi.celum;

import it.cambi.celum.mongo.repository.UserRepository;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

/**
 * @author luca
 *
 */
@Configuration
@EnableMongoRepositories(basePackageClasses = UserRepository.class)
@ComponentScan(basePackageClasses = UserRepository.class)
public class ApplicationConfiguration {


}
