package sth.core;

import sth.core.exception.DuplicateProjectIdException;
import sth.core.exception.NoSuchProjectIdException;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public class Discipline implements Comparable<Discipline>, java.io.Serializable {

    /** Serial number for serialization */
    private static final long serialVersionUID = 201811152205L;

    private static final int MAX_STUDENTS_DISCIPLINE = 200;
    private Course _course;
    private String _name;

    private Set<Student> _students;
    private Set<Teacher> _teachers;
    private Map<String, Project> _projects;

    Discipline(String name, Course course) {
        _name = name;
        _course = course;

        _students = new TreeSet<>();
        _teachers = new HashSet<>();
        _projects = new HashMap<>();
    }

    boolean enrollStudent(Student student) {
        if (_students.size() > MAX_STUDENTS_DISCIPLINE)
            return false;

        return _students.add(student);
    }

    boolean addTeacher(Teacher teacher) {
        return _teachers.add(teacher);
    }

    void createProject(String projName) throws DuplicateProjectIdException {
        if(_projects.containsKey(projName))
            throw new DuplicateProjectIdException(_name, projName);

        _projects.put(projName,new Project(projName));
    }

    Project getProject(String projName) throws NoSuchProjectIdException {
        if(_projects.containsKey(projName) == false)
            throw new NoSuchProjectIdException(projName);

        return _projects.get(projName);
    }

    String getName() {
        return _name;
    }

    Course getCourse() {
        return _course;
    }

   	String showStudents() {
        String info = "";

        for (Student student : _students) {
            info += student.toString();
        }

        return info;
    }

    @Override
    public boolean equals(Object obj) {
        return obj != null &&
               obj instanceof Discipline &&
               ((Discipline) obj)._name.equals(_name) &&
               ((Discipline) obj)._course.equals(_course);
    }

    @Override
    public int compareTo(Discipline d) {
        int equal = _course.compareTo(d._course);

        return equal == 0 ? _name.compareTo(d._name) : equal;
    }


}
