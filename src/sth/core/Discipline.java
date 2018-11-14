package sth.core;

import sth.core.exception.NoSuchProjectIdException;


import java.util.*;

public class Discipline implements  Comparable<Discipline>, java.io.Serializable {

    private static final int MAX_STUDENTS_DISCIPLINE = 200;
    private Course _course;
    private String _name;

    private Set<Student> _students;
    private Set<Teacher> _teachers;
    private Map<String,Project> _projects;

    private Notification _notifications;

    Discipline(String name, Course course) {
        _name = name;
        _course = course;

        _students = new HashSet<>();
        _teachers = new TreeSet<>();
        _projects = new HashMap<>();
        _notifications = new Notification();
    }

    boolean enrollStudent(Student student) {
        if(_students.size() > MAX_STUDENTS_DISCIPLINE)
            return false;

        return _students.add(student);
    }

    boolean addTeacher(Teacher teacher) {
        return _teachers.add(teacher);
    }

    void createProject(String projName) throws NoSuchProjectIdException {
        if(_projects.containsKey(projName))
            throw new NoSuchProjectIdException(projName);

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

    public String showStudents() {
        String info = "";
        for (Student a : _students) {
            info += a.toString();
        }

        return info;
    }

    @Override
    public boolean equals(Object obj) {
        return obj != null &&
                obj instanceof Discipline &&
                _name.compareTo(((Discipline)obj).getName()) == 0 &&
                _course.equals(((Discipline) obj).getCourse());
    }

    @Override
    public int compareTo(Discipline d) {
        int equal = _course.compareTo(d.getCourse());

        if(equal == 0)
            return _name.compareTo(d.getName());

        return equal;
    }


}
