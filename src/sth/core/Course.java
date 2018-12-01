package sth.core;

import java.util.HashSet;
import java.util.Map;
import java.util.HashMap;
import java.util.Set;

public class Course implements Comparable<Course>, java.io.Serializable {

    /** Serial number for serialization */
    private static final long serialVersionUID = 201811152204L;

    private static final int MAX_REPRESENTATIVES = 7;

    private String _name;
    private Map<String, Discipline> _disciplines;
    private Set<Student> _students;
    private Set<Student> _representatives;

    Course(String name) {
        _disciplines = new HashMap<>();
        _students = new HashSet<>();
        _representatives = new HashSet<>();
        _name = name;
    }

    String getName() {
        return _name;
    }

    Discipline parseDiscipline(String name) {
        addDiscipline(name);
        return _disciplines.get(name);
    }

    boolean addDiscipline(String name) {
        if (_disciplines.containsKey(name))
            return false;

        Discipline discipline = new Discipline(name, this);
        _disciplines.put(name, discipline);
        return true;
    }

    boolean addStudent(Student student) {
        return _students.add(student) && (student.isRepresentative() ? addRepresentative(student) : true);
    }
    
    boolean addRepresentative(Student student) {
        return maxNumRepresentatives() ? false : _representatives.add(student);
    }
    
    boolean removeRepresentative(Student student) {
        return _representatives.remove(student);
    }

    boolean maxNumRepresentatives() {
        return getNumRepresentatives() == MAX_REPRESENTATIVES;
    }

    int getNumRepresentatives() {
        return _representatives.size();
    }

    @Override
    public boolean equals(Object obj) {
        return obj != null &&
               obj instanceof Course &&
               ((Course) obj)._name.equals(_name);
    }

    @Override
    public int compareTo(Course c) {
        return _name.compareTo(c._name);
    }
}
