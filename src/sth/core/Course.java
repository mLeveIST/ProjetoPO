package sth.core;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Course implements Comparable<Course> , java.io.Serializable{

    private static final int MAX_REPRESENTATIVES = 7;
    private static int _numRepresentatives;

    private String _name;
    private List<Discipline> _disciplines;
    private Set<Student> _students;

    public String getName(){
        return _name;
    }
    public Course(String name){
        _disciplines = new ArrayList<>();
        _students = new HashSet<>();
        _name = name;
    }

    Discipline parseDiscipline(String name){
        Discipline discipline = new Discipline(name,this);

        if (!_disciplines.contains(discipline)){
            _disciplines.add(discipline);
            return discipline;
        }

        return _disciplines.get(_disciplines.indexOf(discipline));
    }

    boolean addStudent(Student student) {
        if (!student.isRepresentative())
            return _students.add(student);

        else if (addNumRepresentatives())
            return _students.add(student);

        return false;
    }

    boolean addNumRepresentatives(){
        if(_numRepresentatives == MAX_REPRESENTATIVES)
            return false;

        _numRepresentatives++;
        return true;
    }

    void subNumRepresentatives(){
        _numRepresentatives--;
    }

    int getNumRepresentatives(){
        return _numRepresentatives;
    }

    Discipline getDiscipline(String name){
        Discipline discipline = new Discipline(name, this);

        if(!_disciplines.contains(discipline))
            return null;

        else
            return _disciplines.get(_disciplines.indexOf(discipline));
    }

    @Override
    public boolean equals(Object obj) {
        return obj != null &&
                obj instanceof Course &&
                _name.compareTo(((Course) obj).getName()) == 0;
    }

    @Override
    public int compareTo(Course c) {
        return _name.compareTo(c.getName());
    }

}
