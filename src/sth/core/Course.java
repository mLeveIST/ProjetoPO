package sth.core;

import java.util.ArrayList;
import java.util.List;

public class Course implements Comparable<Course> {

    private static final int Max_Representatives = 7;
    private static int _numRepresentatives;

    private String _name;
    private List<Discipline> _disciplines;
    private List<Student> _students;

    public String getName(){
        return _name;
    }
    public Course(String name){
        _disciplines = new ArrayList<>();
        _students = new ArrayList<>();
        _name = name;
    }

    Discipline parseDiscipline(String name){
        Discipline discipline = new Discipline(name,this);
        if (_disciplines.contains(discipline) == false){
            _disciplines.add(discipline);
            return discipline;
        }
        else
            return _disciplines.get(_disciplines.indexOf(discipline));

    }

    void addStudent(Student student) {
        if (!student.isRepresentative())
            _students.add(student);

        else if (_numRepresentatives != Max_Representatives) {
            _numRepresentatives++;
            _students.add(student);
        } else
            System.out.println(("Error: Add Student"));

    }

    boolean hasStudent(Student student){
        return _students.contains(student);
    }

    void addNumRepresentatives(){
        _numRepresentatives++;
    }

    void subNumRepresentatives(){
        _numRepresentatives--;
    }

    int getNumRepresentatives(){
        return _numRepresentatives;
    }

    Discipline getDiscipline(String name){
        Discipline discipline = new Discipline(name, this);
        if(_disciplines.contains(discipline) == false)
            return null;
        else
            return _disciplines.get(_disciplines.indexOf(discipline));
    }


    @Override
    public boolean equals(Object obj) {
        return obj != null &&
                obj instanceof Course &&
                _name == ((Course) obj).getName();
    }

    @Override
    public int compareTo(Course c) {
        return _name.compareTo(c.getName());
    }

}
