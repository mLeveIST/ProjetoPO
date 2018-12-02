package sth.app.teaching;

import pt.tecnico.po.ui.DialogException;;
import sth.core.SchoolManager;

import sth.app.exception.DuplicateProjectException;

import sth.core.exception.DuplicateProjectIdException;
import sth.core.exception.NoSuchDisciplineIdException;

/**
 * 4.4.1. Create project.
 */
public class DoCreateProject extends sth.app.common.ProjectCommand {

	/**
	 * 
	 * @param receiver
	 */
	public DoCreateProject(SchoolManager receiver) {
		super(Label.CREATE_PROJECT, receiver);
	}
	
	/** @see pt.tecnico.po.ui.Command#execute() */
	@Override
	public final void myExecute() throws DialogException, NoSuchDisciplineIdException {
		try {
			_receiver.createProject(_discipline.value(), _project.value());
		} catch (DuplicateProjectIdException dpe) {
			throw new DuplicateProjectException(_discipline.value(), _project.value());
		}
	}
}
