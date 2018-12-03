package sth.core;

import sth.core.exception.InvalidSurveyOperationException;

/**
 * 
 * @author Miguel Levezinho,  No 90756
 * @author Rafael Figueiredo, No 90770
 * @version 2.0
 */
public class Created implements SurveyState, java.io.Serializable {

	/** Serial number for serialization */
    private static final long serialVersionUID = 201812022008L;

	@Override
	public void cancel(Survey survey, Notification notifier) {
		survey.getProject().removeSurvey();
	}

	@Override
	public void open(Survey survey, Notification notifier) {
		survey.setState(new Opened());
		notifier.sendAllMessage("Pode preencher inquérito do projecto " + survey.getProject().getName() + " da disciplina " + notifier.getDisName());
	}

	@Override
	public void close(Survey survey) throws InvalidSurveyOperationException {
		throw new InvalidSurveyOperationException(survey.getProject().getName());
	}

	@Override
	public void finish(Survey survey, Notification notifier) throws InvalidSurveyOperationException {
		throw new InvalidSurveyOperationException(survey.getProject().getName());
	}

	@Override
	public void answer(Survey survey, int id, int time, String comment) throws InvalidSurveyOperationException {
		throw new InvalidSurveyOperationException(survey.getProject().getName());
	}

	@Override
	public String showResults(Survey survey, SurveyShowable shower) {
		return "(por abrir)";
	}
}