package sth.core;

import sth.core.exception.InvalidSurveyOperationException;

/**
 * 
 * @author Miguel Levezinho,  No 90756
 * @author Rafael Figueiredo, No 90770
 * @version 2.0
 */
public class Closed implements SurveyState, java.io.Serializable {

	/** Serial number for serialization */
    private static final long serialVersionUID = 201812022009L;

	@Override
	public void cancel(Survey survey, Notification notifier) {
		open(survey, notifier);
	}

	@Override
	public void open(Survey survey, Notification notifier) {
		survey.setState(new Opened());
		notifier.sendAllMessage("Pode preencher inquérito do projecto " + survey.getProject().getName() + " da disciplina " + notifier.getDisName());
	}

	@Override
	public void close(Survey survey) {}

	@Override
	public void finish(Survey survey, Notification notifier) {
		survey.setState(new Finalized());
		notifier.sendAllMessage("Resultados do inquérito do projecto " + survey.getProject().getName() + " da disciplina " + notifier.getDisName());
	}

	@Override
	public void answer(Survey survey, int id, int time, String comment) throws InvalidSurveyOperationException {
		throw new InvalidSurveyOperationException(survey.getProject().getName());
	}

	@Override
	public String showResults(Survey survey, SurveyShowable shower) {
		return "(fechado)";
	}
}