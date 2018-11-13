package sth.core;

import java.io.IOException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import sth.core.exception.BadEntryException;
import sth.core.exception.NoSuchPersonIdException;

/**
 * 
 * @author Miguel Levezinho,  No 90756
 * @author Rafael Figueiredo, No 90770
 */
public class School implements java.io.Serializable {
	
	/** Serial number for serialization */
	private static final long serialVersionUID = 201810051538L;

	/** Name of the school */
	private String _name;

	/** Stores a collection of courses that the school has */
	private List<Course> _courses;

	/** Stores all the people the related to the school by their IDs */
	private Map<Integer, Person> _people;

	/**
	 *
	 * @param name - Name of the school
	 */
	public School(String name) {
		_name = name;
		_courses = new ArrayList<>();
		_people = new HashMap<>();
	}

	/**
	 * 
	 * @param id - Person identifier number
	 *
	 * @return The <code>Person</code> whose ID is passed to the method
	 * @throws NoSuchPersonIdException When no one with the passed id exists in the school 
	 */
	Person getPerson(int id) throws NoSuchPersonIdException {
		if (!_people.containsKey(id))
			throw new NoSuchPersonIdException(id);
		return _people.get(id);
	}

	/**
	 * 
	 * @return Formatted information of all the people in the school
	 */
	String showAllPersons() {
		String info = "";

		List<Person> people = new ArrayList<>(_people.values());
		Collections.sort(people);

		for (Person p : people)
			info += p.toString();

		return info;
	}

	/**
	 * 
	 * @param name - Name of the person to search for
	 *
	 * @return Formatted information about the person(s) after the serach
	 */
	String searchPerson(String name) {
		String info = "";

		List<Person> people = new ArrayList<>(_people.values());
		Collections.sort(people, Person.getComparatorByName());

		for (Person p : people)
			if (p.getName().contains(name))
				info += p.toString();

		return info;
	}

	/**
	 *
	 * @param fileName - Name of the file with information of the school dominion
	 *
	 * @throws BadEntryException When the contents of the file are not formatted correctly
	 * @throws IOException When the file with name <code>fileName</code> could not be read 
	 */
	void importFile(String fileName) throws IOException, BadEntryException {
		Parser schoolParser = new Parser(this);
		schoolParser.parseFile(fileName);
	}

	/**
	 * 
	 * @param name - Name of the course to search for
	 *
	 * @return The <code>Course</code> whose name matches the one passed to the method
	 */
	Course parseCourse(String name) {
		Course course;
		
		for (Course c : _courses)
			if (c.getName().equals(name))
				return c;

		course = new Course(name);
		_courses.add(course);
		return course;
	}

	/**
	 * 
	 * @param type     - String type of the person to create (Must be ALUNO, DELEGADO, DOCENTE or FUNCIONÁRIO)
	 * @param id       - ID to give the person to be created
	 * @param phoneNum - Telephone number of the person to be created
	 * @param name     - Name of the person being created
	 *
	 * @return A new <code>Person</code> to be added to the school
	 * @throws BadEntryException When the type of the person is not recognized
	 */
	Person parsePerson(String type, int id, int phoneNum, String name) throws BadEntryException {
		Person person;

		switch (type) {
			case "ALUNO":
				person = new Student(id, phoneNum, name, false);
				break;
			case "DELEGADO":
				person = new Student(id, phoneNum, name, true);
				break;
			case "DOCENTE":
				person = new Teacher(id, phoneNum, name);
				break;
			case "FUNCIONÁRIO":
				person = new Employee(id, phoneNum, name);
				break;
			default:
				throw new BadEntryException("Invalid token " + type + "in line describing a person");
		}

		_people.put(id, person);
		return person;
	}	
}