package eu.loxon.reactiveSpringMeetup.repo;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import eu.loxon.reactiveSpringMeetup.domain.Data;

@Repository("blockingMongoRepo")
public interface DataCrudRepository extends MongoRepository<Data, Double> {

}
