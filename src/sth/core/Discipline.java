package sth.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Discipline implements  Comparable<Discipline>{

    private Course _course;
    private String _name;

    private boolean _sortedStudents;

    private List<Student> _students;
    private List<Teacher> _teachers;
    private List<Project> _projects;

    private Notification _notifications;

    Discipline(String name, Course course){
        _name = name;
        _course = course;
        _sortedStudents = false;

        _students = new ArrayList<>();
        _teachers = new ArrayList<>();
        _projects = new ArrayList<>();
        _notifications = new Notification();

    }

    void enrollStudent(Student student){
        if(_students.contains(student) == true)
            System.out.println("Nao é suposto");
        else
            _students.add(student);
    }

    void addTeacher(Teacher teacher){
        // Falta verificar se o professor ja pertence há disciplina
        _teachers.add(teacher);
    }

    String getName(){
        return _name;
    }

    Course getCourse(){
        return _course;
    }

    public String showStudents() {
        String info = "";

        if (!_sortedStudents) {
            Collections.sort(_students);
            _sortedStudents = true;
        }

        for (Student a : _students)
            info += a.toString();

        return info;
    }

    @Override
    public boolean equals(Object obj) {
        return obj != null &&
                obj instanceof Discipline &&
                _name == ((Discipline) obj).getName() &&
                _course.equals(((Discipline) obj).getCourse());
    }

    @Override
    public int compareTo(Discipline d) {
        return _name.compareTo(d.getName());
    }
}
