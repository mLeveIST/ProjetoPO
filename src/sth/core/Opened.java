package sth.core;

import sth.core.exception.NonEmptyAssociatedSurveyException;
import sth.core.exception.InvalidSurveyOperationException;

/**
 * 
 * @author Miguel Levezinho,  No 90756
 * @author Rafael Figueiredo, No 90770
 * @version 2.0
 */
public class Opened implements SurveyState {

	@Override
	public void cancel(Survey survey) throws NonEmptyAssociatedSurveyException {
		// TODO
	}

	@Override
	public void open(Survey survey) throws InvalidSurveyOperationException {
		// TODO
	}

	@Override
	public void close(Survey survey) {
		// TODO
	}

	@Override
	public void finish(Survey survey) throws InvalidSurveyOperationException {
		// TODO
	}

	@Override
	public void answer(Survey survey) {
		// TODO
	}

	@Override
	public void showResults(Survey survey) {
		// TODO
	}
}