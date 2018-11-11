package sth.core;

import java.util.List;
import java.util.ArrayList;

public class Teacher extends Person {

	private List<Course> _courses;
	private List<Discipline> _disciplines;

	Teacher(int id, int phoneNum, String name) {
		super(id, phoneNum, name);

		_courses = new ArrayList<>();
		_disciplines = new ArrayList<>();
	}

	@Override
	void addContext(Course course, Discipline discipline) {

		_courses.add(course);
		_disciplines.add(discipline);

		// -!-!-!-!-!- //
		//discipline.addTeacher(this);
	}
}
