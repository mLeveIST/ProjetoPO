package sth.app.representative;

import pt.tecnico.po.ui.DialogException;
import sth.app.exception.DuplicateSurveyException;
import sth.core.SchoolManager;

import sth.core.exception.DuplicateAssociatedSurveyException;
import sth.core.exception.NoSuchDisciplineIdException;
import sth.core.exception.NoSuchProjectIdException;

/**
 * 4.5.1. Create survey.
 */
public class DoCreateSurvey extends sth.app.common.ProjectCommand {

	/**
	 * 
	 * @param receiver
	 */
	public DoCreateSurvey(SchoolManager receiver) { super(Label.CREATE_SURVEY, receiver); }
	
	/** @see sth.app.common.ProjectCommand#myExecute() */ 
	@Override
	public final void myExecute() throws DialogException, NoSuchDisciplineIdException, NoSuchProjectIdException {
		try {
			_receiver.createSurvey(_discipline.value(), _project.value());
		} catch(DuplicateAssociatedSurveyException x) {
			throw new DuplicateSurveyException(_discipline.value(), _project.value());
		}
	}
}
