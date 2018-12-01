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

/**
 * @author Miguel Levezinho,  No 90756
 * @author Rafael Figueiredo, No 90770
 * @version 2.0
 */
public class Teacher extends Person implements java.io.Serializable {

	/** Serial number for serialization */
    private static final long serialVersionUID = 201811152208L;

    /** Disciplines the teacher gives */
	private Map<String, Discipline> _disciplines;

	/**
	 * Creates a new teacher.
	 *
	 * @param id       - Teacher ID number
     * @param phoneNum - Teacher phone number
     * @param name     - Teacher name
	 */
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

	/**
	 * Adds a new discipline to the teacher, representing a new dicipline the teacher will give.
	 *
	 * @param discipline Discipline to add
	 * @return true if the discipline was added, false otherwise
	 */
	boolean addDiscipline(Discipline discipline) {
		if (_disciplines.containsValue(discipline))
			return false;

		_disciplines.put(discipline.getName(), discipline);
		discipline.addTeacher(this);
		return true;
	}

	/**
	 * Opens a project in a given discipline.
	 *
	 * @param disName  - Name ID of the discipline
	 * @param projName - Name ID of the project
	 *
	 * @throws NoSuchDisciplineIdException When the passed discipline ID is not found in the discilines given by the teacher
	 * @throws DuplicateProjectIdException When the passed project ID is already in use in the discipline
	 */
	void createProject(String disName, String projName) throws NoSuchDisciplineIdException, DuplicateProjectIdException {
		Discipline discipline = getDiscipline(disName);
		discipline.createProject(projName);
	}

	/**
	 * Closes a project in a given discipline.
	 *
	 * @param disName  - Name ID of the discipline
	 * @param projName - Name ID of the project
	 *
	 * @throws NoSuchDisciplineIdException When the passed discipline ID is not found in the discilines given by the teacher
	 * @throws NoSuchProjectIdException When the passed project ID is not found in the disciline given by the teacher
	 */
	void closeProject(String disName, String projName) throws NoSuchDisciplineIdException, NoSuchProjectIdException {
		Discipline discipline = getDiscipline(disName);
		Project project = discipline.getProject(projName);
		project.close();
	}

	/**
	 * Gets a formatted <code>String</code> with the information of a project submissions in a discipline of the teacher.
	 *
	 * @param disName  - Name ID of the discipline
	 * @param projName - Name ID of the project
	 * @return Project info of the given discipline.
	 *
	 * @throws NoSuchDisciplineIdException When the passed discipline ID is not found in the discilines given by the teacher
	 * @throws NoSuchProjectIdException When the passed project ID is not found in the disciline given by the teacher
	 */
	String showSubmissions(String disName, String projName) throws NoSuchDisciplineIdException, NoSuchProjectIdException {
		Discipline discipline = getDiscipline(disName);
		Project project = discipline.getProject(projName);
		
		return disName + " - " + projName + "\n" + project.showSubmissions();
	}

	/**
	 * Gets a formatted <code>String</code> containing the information of all the students in a given discipline of the teacher.
	 *
	 * @param disName - Name ID of the discipline
	 * @return Student info of the given discipline
	 *
	 * @throws NoSuchDisciplineIdException When the passed discipline ID is not found in the discilines given by the teacher
	 */
	String showStudents(String disName) throws NoSuchDisciplineIdException {
		Discipline discipline = getDiscipline(disName);
		return discipline.showStudents();
	}

	/**
	 * Gets a discipline given by the teacher.
	 * 
	 * @param disName - Name ID of the discipline
	 * @return The dicipline which corresponds to the passed name
	 *
	 * @throws NoSuchDisciplineIdException When the passed discipline ID is not found in the discilines given by the teacher
	 */
	private Discipline getDiscipline(String disName) throws NoSuchDisciplineIdException {
		if (!_disciplines.containsKey(disName))
			throw new NoSuchDisciplineIdException(disName);
		return _disciplines.get(disName);
	}

	/**
	 * Makes a certain discipline notify the teacher every time a survey is opened or closed in a project of the discipline.
	 * 
	 * @param disName - Name ID of the discipline
	 *
	 * @throws NoSuchDisciplineIdException When the passed discipline ID is not found in the discilines given by the teacher
	 */
	void enableNotifications(String disName) throws NoSuchDisciplineIdException {
        Discipline discipline = getDiscipline(disName);	
		discipline.giveNotifications(this);
    }
    
    /**
	 * Makes a certain discipline stop notifying the teacher every time a survey is opened or closed in a project of the discipline.
	 * 
	 * @param disName - Name ID of the discipline
	 *
	 * @throws NoSuchDisciplineIdException When the passed discipline ID is not found in the discilines given by the teacher
	 */
    void disableNotifications(String disName) throws NoSuchDisciplineIdException {
        Discipline discipline = getDiscipline(disName);
		discipline.stopNotifications(this);
    }

	@Override
    String getType() {
        return "DOCENTE";
    }

    @Override
    String getInfo() {
        String info = "";
		
		List<Discipline> disciplines = new ArrayList<>(_disciplines.values());
		Collections.sort(disciplines);
		
		for (Discipline d : disciplines)
			info += "* " + d.getCourse().getName() + " - " + d.getName() + "\n";
		
		return info;
    }

    String showSurveyResults(String disName, String projName) {
    	return null;
    	// NOT TODO
    }
}
