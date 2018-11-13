package sth.core;

import java.io.IOException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import sth.core.exception.BadEntryException;
import sth.core.exception.NoSuchPersonIdException;

public class School implements java.io.Serializable {
	
	// Serial number for serialization
	private static final long serialVersionUID = 201810051538L;

	private String _name;
	private List<Course> _courses;
	private Map<Integer, Person> _people;

	public School(String name) {
		_name = name;
		_courses = new ArrayList<>();
		_people = new HashMap<>();
	}

	Person getPerson(int id) throws NoSuchPersonIdException {
		if (!_people.containsKey(id))
			throw new NoSuchPersonIdException(id);
		return _people.get(id);
	}

	String showAllPersons() {
		String info = "";

		List<Person> people = (List<Person>) _people.values();
		Collections.sort(people);

		for (Person p : people)
			info += p.toString();

		return info;
	}

	String searchPerson(String name) {
		String info = "";

		List<Person> people = (List<Person>) _people.values();
		Collections.sort(people, Person.getComparatorByName());

		for (Person p : people)
			if (p.getName().contains(name))
				info += p.toString();

		return info;
	}

	/**
	 * @param fileName
	 * @throws BadEntryException
	 * @throws IOException
	 */
	void importFile(String fileName) throws IOException, BadEntryException {
		Parser schoolParser = new Parser(this);
		schoolParser.parseFile(fileName);
	}

	Course parseCourse(String name) {
		Course course;
		
		for (Course c : _courses)
			if (c.getName().equals(name))
				return c;

		course = new Course(name);
		_courses.add(course);
		return course;
	}

	Person parsePerson(String type, int id, int phoneNum, String name) throws BadEntryException {
		Person person;

		switch (type) {
			case "ALUNO":
				person = new Student(id, phoneNum, name, false);
			case "DELEGADO":
				person = new Student(id, phoneNum, name, true);
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