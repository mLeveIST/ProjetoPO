package sth.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import sth.core.exception.BadEntryException;
import sth.core.exception.DuplicateProjectIdException;
import sth.core.exception.NoSuchDisciplineIdException;
import sth.core.exception.NoSuchProjectIdException;

public class Teacher extends Person implements java.io.Serializable {

	/** Serial number for serialization */
    private static final long serialVersionUID = 201811152208L;

	private Map<String, Discipline> _disciplines;

	Teacher(int id, int phoneNum, String name) {
		super(id, phoneNum, name);

		_disciplines = new HashMap<>();
	}

	@Override
	void parseContext(String context, School school) throws BadEntryException {
		String components[] =  context.split("\\|");

    	if (components.length != 2)
      		throw new BadEntryException("Invalid line context " + context);

    	Course course = school.parseCourse(components[0]);
    	Discipline discipline = course.parseDiscipline(components[1]);

    	addDiscipline(discipline);
	}

	boolean addDiscipline(Discipline discipline) {
		if (_disciplines.containsValue(discipline))
			return false;

		_disciplines.put(discipline.getName(), discipline);
		discipline.addTeacher(this);
		return true;
	}

	void createProject(String disName, String projName) throws NoSuchDisciplineIdException, DuplicateProjectIdException {
		Discipline discipline = getDiscipline(disName);
		discipline.createProject(projName);
	}

	void closeProject(String disName, String projName) throws NoSuchDisciplineIdException, NoSuchProjectIdException {
		Discipline discipline = getDiscipline(disName);
		Project project = discipline.getProject(projName);
		project.close();
	}

	String showStudents(String disName) throws NoSuchDisciplineIdException {
		Discipline discipline = getDiscipline(disName);
		return discipline.showStudents();
	}

	private Discipline getDiscipline(String disName) throws NoSuchDisciplineIdException {
		if (!_disciplines.containsKey(disName))
			throw new NoSuchDisciplineIdException(disName);
		return _disciplines.get(disName);
	}

	@Override
	public String toString() {
		String info = "DOCENTE" + super.toString();
		
		List<Discipline> disciplines = new ArrayList<>(_disciplines.values());
		Collections.sort(disciplines);
		
		for (Discipline d : disciplines)
			info += "* " + d.getCourse().getName() + " - " + d.getName() + "\n";
		
		return info;
	}
}
