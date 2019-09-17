/**
 * 
 */
package it.cambi.celum;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.mongodb.MongoClient;

import de.flapdoodle.embed.mongo.MongodExecutable;
import de.flapdoodle.embed.mongo.MongodStarter;
import de.flapdoodle.embed.mongo.config.IMongodConfig;
import de.flapdoodle.embed.mongo.config.MongodConfigBuilder;
import de.flapdoodle.embed.mongo.config.Net;
import de.flapdoodle.embed.mongo.distribution.Version;
import de.flapdoodle.embed.process.runtime.Network;

/**
 * @author luca
 *
 */
@Configuration
@EnableMongoRepositories(basePackages = "it.cambi.celum.mongo.repository")
@ComponentScan(basePackages = { "it.cambi.celum.service" })
public class ApplicationConfiguration {

	@Bean
	public MongoTemplate mongoTemplate() throws Exception {
		String ip = "localhost";
		int port = 27017;

		IMongodConfig mongodConfig = new MongodConfigBuilder().version(Version.Main.PRODUCTION)
				.net(new Net(ip, port, Network.localhostIsIPv6())).build();

		MongodStarter starter = MongodStarter.getDefaultInstance();
		MongodExecutable mongodExecutable = starter.prepare(mongodConfig);
		mongodExecutable.start();
		return new MongoTemplate(new MongoClient(ip, port), "test");
	}

}
