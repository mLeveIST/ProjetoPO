package sth.app.representative;

import pt.tecnico.po.ui.DialogException;
import sth.app.exception.ClosingSurveyException;
import sth.core.SchoolManager;

import sth.core.exception.InvalidSurveyOperationException;
import sth.core.exception.NoAssociatedSurveyException;
import sth.core.exception.NoSuchProjectIdException;
import sth.core.exception.NoSuchDisciplineIdException;

/**
 * 4.5.4. Close survey.
 */
public class DoCloseSurvey extends sth.app.common.ProjectCommand {

	public DoCloseSurvey(SchoolManager receiver) { super(Label.CLOSE_SURVEY, receiver); }
	
	/** @see sth.app.common.ProjectCommand#myExecute() */
	@Override
	public final void myExecute() throws NoSuchProjectIdException, NoSuchDisciplineIdException, NoAssociatedSurveyException, DialogException {
		try {
			_receiver.closeSurvey(_discipline.value(), _project.value());
		} catch(InvalidSurveyOperationException x) {
			throw new ClosingSurveyException(_discipline.value(), _project.value());
		}
	}
}
