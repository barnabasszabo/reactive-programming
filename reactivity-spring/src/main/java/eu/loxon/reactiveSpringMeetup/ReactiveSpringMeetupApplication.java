package eu.loxon.reactiveSpringMeetup;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories
@EnableReactiveMongoRepositories
public class ReactiveSpringMeetupApplication {

	public static void main(final String[] args) {
		SpringApplication.run(ReactiveSpringMeetupApplication.class, args);
	}

}
