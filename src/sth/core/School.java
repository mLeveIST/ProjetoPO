package sth.core;

import sth.core.exception.BadEntryException;
import sth.core.exception.NoSuchPersonIdException;

import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.Reader;

import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;

public class School implements java.io.Serializable {
	
	// Serial number for serialization
	private static final long serialVersionUID = 201810051538L;

	private String _name;
	private List<Course> _courses;
	private Map<Integer, Person> _people;
	private SchoolParser _schoolParser;

	public School(String name) {
		_name = name;
		_courses = new ArrayList<>();
		_people = new HashMap<>();
		_schoolParser = new SchoolParser();
	}

	public Course parseCourse(String name) {
		Course course;
		
		for (Course c : _courses)
			if (c.getName().equals(name))
				return c;

		course = new Course(name);
		_courses.add(course);
		return course;
	}

	public Person addPerson(String type, int id, int phoneNum, String name) throws BadEntryException {
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

	/**
	 * @param fileName
	 * @throws BadEntryException
	 * @throws IOException
	 */
	void importFile(String fileName) throws IOException, BadEntryException {
		_schoolParser.parseFile(fileName);
	}

	private class SchoolParser {

		private Person _currPerson;

		void parseFile(String fileName) throws IOException, BadEntryException {
			String line;

			try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
				while ((line = reader.readLine()) != null)
					parseLine(line);
			}
		}

		private void parseLine(String line) throws BadEntryException {
			if (line.startsWith("#"))
				parseContext(line);
			else
				parseHeaderPerson(line);
		}

		private void parseHeaderPerson(String header) throws BadEntryException {
			String[] components = header.split("\\|");

			if (components.length != 4)
				throw new BadEntryException("Invalid line: " + header);

			try {
				_currPerson = addPerson(components[0], Integer.parseInt(components[1]), Integer.parseInt(components[2]), components[3]);
			} catch (NumberFormatException nfe) {
				throw new BadEntryException("Invalid line: " + header);
			}
		}

		private void parseContext(String line) throws BadEntryException {
			String[] components = line.substring(2).split("\\|");

			if (components.length != 2)
				throw new BadEntryException("Invalid line: " + line);

			Course course = parseCourse(components[0]);

			Discipline discipline = course.parseDiscipline(components[1]);
			_currPerson.addContext(course, discipline);
		}
	}	
}