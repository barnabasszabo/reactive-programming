package eu.loxon.reactiveSpringMeetup.service.asyncCalc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import eu.loxon.reactiveSpringMeetup.dto.Operation;
import eu.loxon.reactiveSpringMeetup.repo.DataReactiveRepository;
import eu.loxon.reactiveSpringMeetup.service.GeneralCalculator;
import reactor.core.publisher.Mono;

@Service
public class DbAsyncCalculator extends GeneralCalculator implements AsyncCalculator {

	@Autowired
	@Qualifier("reactiveMongoRepo")
	private DataReactiveRepository repo;

	@Override
	public Operation operationCode() {
		return Operation.READDB;
	}

	@Override
	public Mono<Double> calculate(Mono<Double> left, Mono<Double> right) {
		return repo.findById(left).map(d -> d != null ? d.getValue() : 0d);
	}

}
