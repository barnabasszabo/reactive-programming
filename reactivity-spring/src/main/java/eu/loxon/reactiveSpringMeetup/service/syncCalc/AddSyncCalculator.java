package eu.loxon.reactiveSpringMeetup.service.syncCalc;

import org.springframework.stereotype.Service;

import eu.loxon.reactiveSpringMeetup.dto.Operation;
import eu.loxon.reactiveSpringMeetup.service.GeneralCalculator;

@Service
public class AddSyncCalculator extends GeneralCalculator implements SyncCalculator {

	@Override
	public Operation operationCode() {
		return Operation.ADD;
	}

	@Override
	public double calculate(final Double leftValue, final Double rightValue) {
		return leftValue + rightValue;
	}

}
