package sth.core;

import java.io.FileNotFoundException;
import java.io.IOException;

import sth.core.exception.BadEntryException;
import sth.core.exception.ImportFileException;
import sth.core.exception.NoSuchDisciplineIdException;
import sth.core.exception.NoSuchPersonIdException;
import sth.core.exception.NoSuchProjectIdException;

public class SchoolManager {

	School _school;
	private Person _user;

	public SchoolManager() {
		_school = new School("School");
	}

	public SchoolManager(String schoolName) {
		_school = new School(schoolName);
	}

	public void importFile(String dataFile) throws ImportFileException {
		try {
			_school.importFile(dataFile);
		} catch (IOException | BadEntryException e) {
			throw new ImportFileException(e);
		}
	}

	public void login(int id) throws NoSuchPersonIdException {
		_user = _school.getPerson(id);
	}

	public boolean isLoggedUserAdministrative() {
		return _user instanceof Employee;
	}

	public boolean isLoggedUserProfessor() {
		return _user instanceof Teacher;
	}

	public boolean isLoggedUserStudent() {
		return _user instanceof Student;
	}

	public boolean isLoggedUserRepresentative() {
		return _user instanceof Student && ((Student) _user).isRepresentative();
	}

	// Portal Principal

	public void openState(Object obj) throws NoSuchPersonIdException {
		if (obj instanceof School) {
			School newSchool = (School) obj;
			newSchool.getPerson(_user.getId());
			_school = newSchool;
		}
	}

	public School saveState() {
		return _school;
	}

	// Portal Pessoal

	public String showPerson() {
		return _user.toString();
	}

	public String showAllPersons() {
		return _school.showAllPersons();
	}

	public String searchPerson(String name) {
		return _school.searchPerson(name);
	}

	public void changePhoneNumber(int phoneNum) {
		_user.setPhoneNumber(phoneNum);
	}

	// Portal Docente

	public void createProject(String disName, String projName) throws NoSuchDisciplineIdException, NoSuchProjectIdException {
		((Teacher)_user).createProject(disName, projName);
	}

	public void closeProject(String disName, String projName) throws NoSuchDisciplineIdException, NoSuchProjectIdException {
		((Teacher)_user).closeProject(disName, projName);
	}

	public void showSubmissions(String disName, String projName) {
		// TODO Entrega final
	}

	public String showStudents(String disName) throws NoSuchDisciplineIdException {
		return ((Teacher)_user).showStudents(disName);
	}

	// Portal Estudante

	public void submitProject(String disName, String projName, String submission) {
		// TODO Entrega final
	}

	public void fillSurvey(String disName, String projName, int time, String comment) {
		// TODO Entrega final
	}

	public void showSurveyResults(String disName, String projName) {
		// TODO Entrega final
	}

	// Portal Delegado

	public void createSurvey(String disName, String projName) {
		// TODO Entrega final
	}

	public void cancelSurvey(String disName, String projName) {
		// TODO Entrega final
	}

	public void openSurvey(String disName, String projName) {
		// TODO Entrega final
	}

	public void closeSurvey(String disName, String projName) {
		// TODO Entrega final
	}

	public void finalizeSurvey(String disName, String projName) {
		// TODO Entrega final
	}

	public void showSurveyResults(String disName) {
		// TODO Entrega final
	}
}