package sth.core;

import sth.core.exception.BadEntryException;
import sth.core.exception.NoSuchPersonIdException;

import java.io.IOException;

public class School implements java.io.Serializable {
	
	// Serial number for serialization
	private static final long serialVersionUID = 201810051538L;

	private String _name;
	private List<Course> _courses;
	private Map<Integer, Person> _people; // Might change this map

	public School(String name) {
		_name = name;
		_courses = new ArrayList<>();
		_people = new HashMap<>();
	}

	public createCourse(String name) {
		Course course = new Course(name);

		if (course != null)
			_courses.add(course);
	}

	public void createDiscipline(String name, String course, int maxStudents, int teacher) {

	
	}

	/**
	 * @param filename
	 * @throws BadEntryException
	 * @throws IOException
	 */
	void importFile(String filename) throws IOException, BadEntryException {
	
	}	
}