package eu.loxon.reactiveSpringMeetup.service.syncCalc;

import eu.loxon.reactiveSpringMeetup.dto.Operation;

public interface SyncCalculator {

	Operation operationCode();

	double calculate(Double leftValue, Double rightValue);

}
