package sth.app.main;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.Input;
import sth.core.SchoolManager;

//FIXME import other classes if needed

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
    _fileName = _form.addStringInput(Message.saveAs());
  }

  /** @see pt.tecnico.po.ui.Command#execute() */
  @Override
  public final void execute() {
    ObjectOutputStream objOut = null;
    if(_fileName.value() == null)
        _form.parse();

    try {
      FileOutputStream fpout = new FileOutputStream(_fileName.value());
      objOut = new ObjectOutputStream(fpout);
      objOut.writeObject(_receiver.saveFile());

    } catch (FileNotFoundException fnfe) {
      _display.popup(Message.fileNotFound());
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      try {
        if (objOut != null)
          objOut.close();
      } catch(IOException x){
        x.printStackTrace();
      }
    }
  }
}

