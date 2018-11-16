package sth.app.common;

import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.DialogException;
import pt.tecnico.po.ui.Input;
import sth.core.SchoolManager;

public abstract class OptionsFileCommand extends Command<SchoolManager> {

    protected static Input<String> _inputFile;

    public OptionsFileCommand(String mess, String label, SchoolManager receiver){
        super(label,receiver);

        _inputFile = _form.addStringInput(mess);
    }

    protected abstract void myExecute() throws DialogException;

    @Override
    public final void execute() throws DialogException {
        if(_inputFile.value() == null){
            _form.parse();
        }

        myExecute();
    }
}
