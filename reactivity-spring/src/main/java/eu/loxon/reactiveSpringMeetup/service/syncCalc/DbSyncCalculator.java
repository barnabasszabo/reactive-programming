package eu.loxon.reactiveSpringMeetup.service.syncCalc;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import eu.loxon.reactiveSpringMeetup.domain.Data;
import eu.loxon.reactiveSpringMeetup.dto.Operation;
import eu.loxon.reactiveSpringMeetup.repo.DataCrudRepository;
import eu.loxon.reactiveSpringMeetup.service.GeneralCalculator;

@Service
public class DbSyncCalculator extends GeneralCalculator implements SyncCalculator {

	@Autowired
	@Qualifier("blockingMongoRepo")
	private DataCrudRepository repo;

	@Override
	public Operation operationCode() {
		return Operation.READDB;
	}

	@Override
	public double calculate(final Double leftValue, final Double rightValue) {
		double result = 0d;
		Optional<Data> maybeDbResult = repo.findById(leftValue);
		if (maybeDbResult.isPresent()) {
			result = getOrZero(maybeDbResult.get().getValue());
		}
		return result;
	}

}
