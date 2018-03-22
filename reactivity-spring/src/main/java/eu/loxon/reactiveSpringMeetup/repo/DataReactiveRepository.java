package eu.loxon.reactiveSpringMeetup.repo;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import eu.loxon.reactiveSpringMeetup.domain.Data;

@Repository("reactiveMongoRepo")
public interface DataReactiveRepository extends ReactiveMongoRepository<Data, Double> {

}
