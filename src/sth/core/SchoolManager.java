package sth.core;

import sth.core.exception.BadEntryException;
import sth.core.exception.ImportFileException;
import sth.core.exception.NoSuchPersonIdException;

import java.io.IOException;
import java.io.FileNotFoundException;


//FIXME import other classes if needed

/**
 * The façade class.
 */
public class SchoolManager {

  //FIXME add object attributes if needed
  School _school;
  private Person _user;

  public SchoolManager(String schoolName){
    _school = new School(schoolName);
  }

  public SchoolManager(){
      _school = new School("School");
  }
  
  /**
   * @param datafile
   * @throws ImportFileException
   * @throws InvalidCourseSelectionException
   */
  public void importFile(String datafile) throws ImportFileException {
    try {
      _school.importFile(datafile);
    } catch (IOException | BadEntryException e) {
      throw new ImportFileException(e);
    }
  }

  /**
   * Do the login of the user with the given identifier.
   * @param id identifier of the user to login
   * @throws NoSuchPersonIdException if there is no uers with the given identifier
   */
  public void login(int id) throws NoSuchPersonIdException {
    /*_user = _school.getPerson(id);*/

  }

  /**
   * @return true when the currently logged in person is an administrative
   */
  public boolean isLoggedUserAdministrative() {
    return _user instanceof Employee;
  }

  /**
   * @return true when the currently logged in person is a professor
   */
  public boolean isLoggedUserProfessor() {
    return _user instanceof Teacher;
  }

  /**
   * @return true when the currently logged in person is a student
   */
  public boolean isLoggedUserStudent() {

    return _user instanceof Student && ((Student)_user).isRepresentative();
  }

  /**
   * @return true when the currently logged in person is a representative
   */
  public boolean isLoggedUserRepresentative() {
    return _user instanceof Student && ((Student)_user).isRepresentative();
  }
  
  public void changeTeleNum(int num){
    _user.setPhoneNum(num);
  }

  public String showPerson(){
     return _user.toString();

  }

  public String showAllPersons(){
      return "";
  }

  public String searchPerson(String name){
      return "";
  }

  public void openFile(Object obj){
    if (obj instanceof  School )
      _school = (School) obj;
  }

  public School saveFile(){
    return _school;
  }


}
