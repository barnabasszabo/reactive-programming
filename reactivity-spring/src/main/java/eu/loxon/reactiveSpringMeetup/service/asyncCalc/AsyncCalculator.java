package eu.loxon.reactiveSpringMeetup.service.asyncCalc;

import eu.loxon.reactiveSpringMeetup.dto.Operation;
import reactor.core.publisher.Mono;

public interface AsyncCalculator {

	Operation operationCode();

	Mono<Double> calculate(Mono<Double> left, Mono<Double> right);

}
