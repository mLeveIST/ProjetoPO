package sth.app.main;

import java.io.*;

import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.DialogException;
import pt.tecnico.po.ui.Input;
import sth.core.SchoolManager;

import sth.app.exception.NoSuchPersonException;
import sth.core.exception.NoSuchPersonIdException;

/**
 * 4.1.1. Open existing document.
 */
public class DoOpen extends Command<SchoolManager> {

  Input<String> _fileName;

  /**
   * @param receiver
   */
  public DoOpen(SchoolManager receiver) {
    super(Label.OPEN, receiver);
    _fileName = _form.addStringInput(Message.openFile());
  }

  /** @see pt.tecnico.po.ui.Command#execute() */
  @Override
  public final void execute() throws DialogException {
    _form.parse();
    _receiver.setFileName(_fileName.value());

    try (ObjectInputStream objIn = new ObjectInputStream(new FileInputStream(_fileName.value()))) {
      _receiver.openState(objIn.readObject());
    } catch (FileNotFoundException fnfe) {
      _display.popup(Message.fileNotFound());
    } catch (ClassNotFoundException | IOException e) {
      e.printStackTrace();
    } catch (NoSuchPersonIdException nsp) {
      throw new NoSuchPersonException(nsp.getId());
    }
  }

}
