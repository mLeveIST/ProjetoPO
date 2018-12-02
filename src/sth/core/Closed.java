package sth.core;

import sth.core.exception.InvalidSurveyOperationException;

/**
 * 
 * @author Miguel Levezinho,  No 90756
 * @author Rafael Figueiredo, No 90770
 * @version 2.0
 */
public class Closed implements SurveyState {

	@Override
	public void cancel(Survey survey) {
		open(survey);
	}

	@Override
	public void open(Survey survey) {
		survey.setState(new Opened());
	}

	@Override
	public void close(Survey survey) {}

	@Override
	public void finish(Survey survey) {
		survey.setState(new Finalized());
	}

	@Override
	public void answer(Survey survey, int id, int time, String comment) throws InvalidSurveyOperationException {
		throw new InvalidSurveyOperationException(survey.getProject().getName());
	}

	@Override
	public String showResults(Survey survey, SurveyAccess person) {
		return "(fechado)\n";
	}
}