package sth.core;

import sth.core.exception.BadEntryException;

import java.util.Arrays;
import java.util.Collections;

public class Student extends Person {
    private static final int MAX_NUM_DISCIPLINES = 6;

    private Discipline[] _disciplines;
    private int _numDisciplines;
    private Course _course;
    private boolean _representative;

    public Student(int id, int numTel, String name, boolean representative){
        super(id, numTel, name);

        _disciplines = new Discipline[MAX_NUM_DISCIPLINES];
        _numDisciplines = 0;
        _representative = representative;

    }

    @Override
    void addContext(Course course, Discipline discipline) {
        _course = course;
        for(Discipline disc : _disciplines){
            if (_disciplines.equals(discipline) |_numDisciplines == 6)
                System.out.println("Erro"); //Retornar False =??
        }

        _disciplines[_numDisciplines] = discipline;
        _numDisciplines++;
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

        String info = _representative ? "DELEGADO|" + getId() + "|" + getPhoneNum() + "|" + getName() + "\n" : super.toString();
        Arrays.sort(_disciplines);
        for(Discipline d : _disciplines)
            info += "* " + _course + " - " + d.getName() + "\n";

        return info;
    }







}
