package sth.app.representative;

import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.DialogException;
import pt.tecnico.po.ui.Input;
import sth.app.common.Message;
import sth.app.exception.NoSuchDisciplineException;
import sth.core.SchoolManager;
import sth.core.exception.NoSuchDisciplineIdException;

//FIXME import other classes if needed

/**
 * 4.6.6. Show discipline surveys.
 */
public class DoShowDisciplineSurveys extends Command<SchoolManager> {

	private Input<String> _discipline;

	/**
	 * 
	 * @param receiver
	 */
	public DoShowDisciplineSurveys(SchoolManager receiver) {
		super(Label.SHOW_DISCIPLINE_SURVEYS, receiver);
		_discipline = _form.addStringInput(Message.requestDisciplineName());

	}

	/** @see pt.tecnico.po.ui.Command#execute() */
	@Override
	public final void execute() throws DialogException {
		_form.parse();
		try {
			_receiver.showSurveyResults(_discipline.value());
		} catch(NoSuchDisciplineIdException x) {
			throw new NoSuchDisciplineException(_discipline.value());
		}
	}
}
