package sth.core;

import sth.core.exception.BadEntryException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Student extends Person implements java.io.Serializable {

    private static final int MAX_NUM_DISCIPLINES = 6;

    private List<Discipline> _disciplines;
    private int _numDisciplines;
    private Course _course;
    private boolean _representative;


    public Student(int id, int phoneNum, String name, boolean representative){
        super(id, phoneNum, name);

        _disciplines = new ArrayList<>();
        _numDisciplines = 0;
        _representative = representative;
        _course = null;
    }

    @Override
    void parseContext(String context, School school) throws BadEntryException {
        String components[] =  context.split("\\|");

        if (components.length != 2)
            throw new BadEntryException("Invalid line context " + context);

        if(_course == null){
            _course = school.parseCourse(components[0]);
            _course.addStudent(this);
        }

        Discipline dis = _course.parseDiscipline(components[1]);
        dis.enrollStudent(this);
        _disciplines.add(dis);

    }

    void makeStudent(){
        _representative = false;
    }

    void makeRepresentaive(){
        _representative = true;
    }

    boolean isRepresentative(){
        return _representative;
    }
    @Override
    public boolean equals(Object obj){
        return obj!=null && obj instanceof Student && ((Student)obj).getId() == getId();
    }

    @Override
    public String toString() {

        String info = _representative ? "DELEGADO|" + super.toString() + "\n" : "ALUNO|" + super.toString() + "\n";
        Collections.sort(_disciplines);

        for(Discipline d : _disciplines)
            info += "* " + _course.getName() + " - " + d.getName() + "\n";

        return info;
    }







}
