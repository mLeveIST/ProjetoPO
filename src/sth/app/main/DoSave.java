package sth.app.main;

import java.io.IOException;

import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.Input;
import sth.core.SchoolManager;


/**
 * 4.1.1. Save to file under current name (if unnamed, query for name).
 */
public class DoSave extends Command<SchoolManager> {

	Input<String> _fileName;
	
	/**
	 * @param receiver
	 */
	public DoSave(SchoolManager receiver) {
		super(Label.SAVE, receiver);
		_fileName = _form.addStringInput(Message.newSaveAs());
	}
	
	/** @see pt.tecnico.po.ui.Command#execute() */
	@Override
	public final void execute() {
		String fileName = _receiver.getFileName();

		if (_receiver.getFileName() == null) {
			_form.parse();
			fileName = _fileName.value();
		}

		try {
			_receiver.saveState(fileName);
		} catch (IOException e) {
			_display.popup(Message.fileNotFound());
		}
	}
}