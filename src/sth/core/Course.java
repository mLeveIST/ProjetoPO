package sth.core;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;
import java.util.HashMap;
import java.util.Set;

public class Course implements Comparable<Course> , java.io.Serializable{

    private static final int MAX_REPRESENTATIVES = 7;
    private static int _numRepresentatives;

    private String _name;
    private Map<String, Discipline> _disciplines;
    private Set<Student> _students;

    public String getName(){
        return _name;
    }
    public Course(String name){
        _disciplines = new HashMap<>();
        _students = new HashSet<>();
        _name = name;
    }

    Discipline parseDiscipline(String name){
        addDiscipline(name);
        return _disciplines.get(name);
    }

    boolean addDiscipline(String name){
        Discipline discipline = new Discipline(name,this);
        if(_disciplines.containsKey(name))
            return false;

        _disciplines.put(name,discipline );
        return true;
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
