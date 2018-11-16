package sth.core;

import sth.core.exception.BadEntryException;

import java.util.Set;
import java.util.TreeSet;

public class Student extends Person implements java.io.Serializable {

    /** Serial number for serialization */
    private static final long serialVersionUID = 201811152207L;

    private static final int MAX_NUM_DISCIPLINES = 6;

    private Set<Discipline> _disciplines;
    private int _numDisciplines;
    private Course _course;
    private boolean _representative;

	Student(int id, int phoneNum, String name, boolean representative) {
        super(id, phoneNum, name);

        _disciplines = new TreeSet<>();
        _numDisciplines = 0;
        _representative = representative;
        _course = null;
    }

    @Override
    void parseContext(String context, School school) throws BadEntryException {
        String components[] =  context.split("\\|");

        if (components.length != 2)
            throw new BadEntryException("Invalid line context " + context);

        if (_course == null) { 
            addCourse(school.parseCourse(components[0]));
            _course.addStudent(this);
        }

        Discipline discipline = _course.parseDiscipline(components[1]);
        addDiscipline(discipline);
    }

    boolean addCourse(Course course) {
        if (_course != null)
            return false;

        _course = course;
        return true;
    }

    boolean addDiscipline(Discipline discipline) {
        if (_disciplines.size() == MAX_NUM_DISCIPLINES || _disciplines.contains(discipline))
            return false;

        _numDisciplines++;
        discipline.enrollStudent(this);
        return _disciplines.add(discipline);
    }

    boolean unmakeRepresentaive() {
        if (!isRepresentative())
            return false;

        _course.subNumRepresentatives();
        _representative = false;

        return true;
    }

    boolean makeRepresentaive() {
        if (isRepresentative() && !_course.addNumRepresentatives())
            return false;

        _representative = true;
        return true;
    }

    boolean isRepresentative() {
        return _representative;
    }

    @Override
    public boolean equals(Object obj) {
        return obj != null &&
               obj instanceof Student &&
               ((Student)obj).getId() == getId();
    }

    @Override
    public String toString() {
        String info = _representative ? "DELEGADO" + super.toString() : "ALUNO" + super.toString() ;

        for (Discipline d : _disciplines)
            info += "* " + _course.getName() + " - " + d.getName() + "\n";

        return info;
    }
}
