package eu.loxon.reactiveSpringMeetup.service.asyncCalc;

import org.springframework.stereotype.Service;

import eu.loxon.reactiveSpringMeetup.dto.Operation;
import eu.loxon.reactiveSpringMeetup.service.GeneralCalculator;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class AddAsyncCalculator extends GeneralCalculator implements AsyncCalculator {

	@Override
	public Operation operationCode() {
		return Operation.ADD;
	}

	@Override
	public Mono<Double> calculate(Mono<Double> left, Mono<Double> right) {
		return Flux.combineLatest(left, right, (leftValue, rightValue) -> {
			return leftValue + rightValue;
		}).next();
	}

}
