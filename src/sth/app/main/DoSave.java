package sth.app.main;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.Input;
import sth.core.SchoolManager;


/**
 * 4.1.1. Save to file under current name (if unnamed, query for name).
 */
public class DoSave extends Command<SchoolManager> {

  Input<String> _fileName = null;

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

    if(_fileName.value() == null)
      _form.parse();

    try (ObjectOutputStream objOut = new ObjectOutputStream(new FileOutputStream(_fileName.value()))) {
      objOut.writeObject(_receiver.saveState());
    } catch (FileNotFoundException fnfe) {
      _display.popup(Message.fileNotFound());
    } catch (IOException e) {
        e.printStackTrace();
    }
  }
}

