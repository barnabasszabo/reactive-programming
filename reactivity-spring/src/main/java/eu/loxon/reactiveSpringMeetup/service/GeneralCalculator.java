package eu.loxon.reactiveSpringMeetup.service;

public abstract class GeneralCalculator {

	protected final Double getOrZero(final Double value) {
		return value != null ? value : 0;
	}

}
