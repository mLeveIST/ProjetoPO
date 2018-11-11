package sth.app.main;

import java.io.*;

import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.Input;
import sth.core.SchoolManager;

//FIXME import other classes if needed

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
  public final void execute() {
      ObjectInputStream objIn = null;
      _form.parse();
    try {

      FileInputStream fpIn = new FileInputStream(_fileName.value());
      objIn = new ObjectInputStream(fpIn);
      Object anObject = objIn.readObject();
      _receiver.openFile(anObject);

      // Colocar LogIn --
    } catch (FileNotFoundException fnfe) {
      _display.popup(Message.fileNotFound());
    } catch (ClassNotFoundException | IOException e) {
      e.printStackTrace();
    } finally {
        try {
            if(objIn != null)
                objIn.close();
        } catch(IOException x){
            x.printStackTrace();
        }
    }
  }

}
