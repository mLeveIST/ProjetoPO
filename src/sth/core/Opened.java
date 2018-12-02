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
		if(survey.hasAnswers())
			throw new NonEmptyAssociatedSurveyException(survey.getProject().getName());
		survey.getProject().removeSurvey();
	}

	@Override
	public void open(Survey survey) throws InvalidSurveyOperationException {
		throw new InvalidSurveyOperationException(survey.getProject().getName());
	}

	@Override
	public void close(Survey survey) {
		survey.setState(new Closed());
	}

	@Override
	public void finish(Survey survey) throws InvalidSurveyOperationException {
		throw new InvalidSurveyOperationException(survey.getProject().getName());
	}

	@Override
	public void answer(Survey survey, int id, int time, String comment) {
		survey.addId(id);
		survey.addAnswer(time,comment);
	}

	@Override
	public String showResults(Survey survey, SurveyAccess person) {
		return "(aberto)\n";
	}
}