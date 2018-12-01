package sth.core;

import sth.core.exception.InvalidSurveyOperationException;

/**
 * 
 * @author Miguel Levezinho,  No 90756
 * @author Rafael Figueiredo, No 90770
 * @version 2.0
 */
public class Finalized implements SurveyState {

	@Override
	public void cancel(Survey survey) throws InvalidSurveyOperationException {
		// TODO
	}

	@Override
	public void open(Survey survey) throws InvalidSurveyOperationException {
		// TODO
	}

	@Override
	public void close(Survey survey) throws InvalidSurveyOperationException {
		// TODO
	}

	@Override
	public void finish(Survey survey) {}

	@Override
	public void answer(Survey survey) throws InvalidSurveyOperationException {
		// TODO
	}

	// WARNING DO NOT DO
	@Override
	public void showResults(Survey survey) {
		// TODO
	}
}