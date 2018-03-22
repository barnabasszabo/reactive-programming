package eu.loxon.reactiveSpringMeetup.service.asyncCalc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eu.loxon.reactiveSpringMeetup.dto.Operation;
import eu.loxon.reactiveSpringMeetup.service.GeneralCalculator;
import eu.loxon.reactiveSpringMeetup.service.syncCalc.FileSyncCalculator;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class FileAsyncCalculator extends GeneralCalculator implements AsyncCalculator {

	@Autowired
	private FileSyncCalculator fileSyncCalc;

	@Override
	public Operation operationCode() {
		return Operation.READFILE;
	}

	@Override
	public Mono<Double> calculate(Mono<Double> left, Mono<Double> right) {
		return Flux.combineLatest(left, right, (leftValue, rightValue) -> {
			return fileSyncCalc.calculate(leftValue, rightValue);
		}).next();
	}

}
