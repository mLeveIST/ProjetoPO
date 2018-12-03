package sth.core;

import sth.core.exception.InvalidSurveyOperationException;

/**
 * 
 * @author Miguel Levezinho,  No 90756
 * @author Rafael Figueiredo, No 90770
 * @version 2.0
 */
public class Finalized implements SurveyState, java.io.Serializable {

	/** Serial number for serialization */
    private static final long serialVersionUID = 201812022007L;

	@Override
	public void cancel(Survey survey, Notification notifier) throws InvalidSurveyOperationException {
		throw new InvalidSurveyOperationException(survey.getProject().getName());
	}

	@Override
	public void open(Survey survey, Notification notifier) throws InvalidSurveyOperationException {
		throw new InvalidSurveyOperationException(survey.getProject().getName());
	}

	@Override
	public void close(Survey survey) throws InvalidSurveyOperationException {
		throw new InvalidSurveyOperationException(survey.getProject().getName());
	}

	@Override
	public void finish(Survey survey, Notification notifier) {}

	@Override
	public void answer(Survey survey, int id, int time, String comment) throws InvalidSurveyOperationException {
		throw new InvalidSurveyOperationException(survey.getProject().getName());
	}

	@Override
	public String showResults(Survey survey, SurveyShowable shower) {
		return shower.showAnswers(survey);
	}
}