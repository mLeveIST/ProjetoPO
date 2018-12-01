package sth.core.exception;

/** Exception thrown when the requested project does not exist. */
public class NoSubmissionsMadeException extends Exception {

  /** Serial number for serialization. */
  private static final long serialVersionUID = 201809021324L;

  /** Student id */
  private int _studentId;

  /** Project id. */
  private String _projectName;

  /**
   * @param id
   */
  public NoSubmissionsMadeException(int sId, String pId) {
    _studentId = sId;
    _projectName = pId;
  }

  /** @return sId */
  public int getStudentId() {
    return _studentId;
  }

  /** @return pId */
  public String getProjectId() {
    return _projectName;
  }

}
