package eu.loxon.reactiveSpringMeetup.service.syncCalc;

import org.springframework.stereotype.Service;

import eu.loxon.reactiveSpringMeetup.dto.Operation;
import eu.loxon.reactiveSpringMeetup.service.GeneralCalculator;

@Service
public class PiSyncCalculator extends GeneralCalculator implements SyncCalculator {

	@Override
	public Operation operationCode() {
		return Operation.PI;
	}

	@Override
	public double calculate(final Double leftValue, final Double rightValue) {
		double result = 0;
		// SUM( +/-1 / 2k+1)
		int multiplier = 1;
		int maxValue = getOrZero(leftValue).intValue();
		for (int i = 0; i < maxValue; i++) {
			double j = (2 * i) + 1;
			result += multiplier / j;
			multiplier = multiplier * -1;
		}
		result = result * 4;
		return result;
	}

}
