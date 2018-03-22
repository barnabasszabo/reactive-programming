package eu.loxon.reactiveSpringMeetup.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eu.loxon.reactiveSpringMeetup.dto.Operation;
import eu.loxon.reactiveSpringMeetup.dto.OperationDto;
import eu.loxon.reactiveSpringMeetup.service.syncCalc.SyncCalculator;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CalculationSyncService extends GeneralCalculator {

	@Autowired
	private Collection<SyncCalculator> syncCalculators;

	private Map<Operation, SyncCalculator> mappedCalculators = new HashMap<>();

	@PostConstruct
	public void init() {
		syncCalculators.forEach(c -> {
			log.info("Registering SYNC calculator: {} to operation: {}", c.getClass(), c.operationCode());
			mappedCalculators.put(c.operationCode(), c);
		});
	}

	public List<Double> calculate(final List<OperationDto> calcArray, final SyncLoopType loopType) {
		switch (loopType) {
		case FOREACH:
			return calculateForeach(calcArray);
		case STREAM:
			return calculateStream(calcArray);
		default:
			return calculateParallelStream(calcArray);
		}
	}

	private double doCalculate(final OperationDto dto) {
		double leftValue = getValue(dto.getLeftValue(), dto.getLeftOperation());
		double rightValue = getValue(dto.getRightValue(), dto.getRightOperation());
		return mappedCalculators.get(dto.getOperation()).calculate(leftValue, rightValue);
	}

	private double getValue(final Double possibleValue, final OperationDto subOperation) {
		double value = getOrZero(possibleValue);
		if (subOperation != null) {
			value = doCalculate(subOperation);
		}
		return value;
	}

	/* Calculation stream implementations */
	public List<Double> calculateParallelStream(final List<OperationDto> calcArray) {
		return calcArray.parallelStream().map(e -> doCalculate(e)).collect(Collectors.toList());
	}

	public List<Double> calculateStream(final List<OperationDto> calcArray) {
		return calcArray.stream().map(e -> doCalculate(e)).collect(Collectors.toList());
	}

	private List<Double> calculateForeach(final List<OperationDto> calcArray) {
		List<Double> result = new ArrayList<>(calcArray.size());
		for (int i = 0; i < calcArray.size(); i++) {
			result.add(doCalculate(calcArray.get(i)));
		}
		return result;
	}

}
