package eu.loxon.reactiveSpringMeetup.service;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eu.loxon.reactiveSpringMeetup.dto.Operation;
import eu.loxon.reactiveSpringMeetup.dto.OperationDto;
import eu.loxon.reactiveSpringMeetup.service.asyncCalc.AsyncCalculator;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Service
public class CalculationASyncService extends GeneralCalculator {

	@Autowired
	private Collection<AsyncCalculator> asyncCalculators;

	private Map<Operation, AsyncCalculator> mappedCalculators = new HashMap<>();

	@PostConstruct
	public void init() {
		asyncCalculators.forEach(c -> {
			log.info("Registering ASYNC calculator: {} to operation: {}", c.getClass(), c.operationCode());
			mappedCalculators.put(c.operationCode(), c);
		});
	}

	public Flux<Double> calculate(final Flux<OperationDto> calcObj) {
		return calcObj.flatMap(e -> doCalculate(e));
	}

	private Mono<Double> doCalculate(final OperationDto dto) {
		Mono<Double> leftValue = getValue(dto.getLeftValue(), dto.getLeftOperation());
		Mono<Double> rightValue = getValue(dto.getRightValue(), dto.getRightOperation());
		return mappedCalculators.get(dto.getOperation()).calculate(leftValue, rightValue);
	}

	private Mono<Double> getValue(final Double possibleValue, final OperationDto subOperation) {
		Mono<Double> value = Mono.just(getOrZero(possibleValue));
		if (subOperation != null) {
			value = doCalculate(subOperation);
		}
		return value;
	}

}
