package springmvc.pojo;

public class Teaches {

  private String id;
  private String courseId;
  private String secId;
  private String semester;
  private int year;


  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }


  public String getCourseId() {
    return courseId;
  }

  public void setCourseId(String courseId) {
    this.courseId = courseId;
  }


  public String getSecId() {
    return secId;
  }

  public void setSecId(String secId) {
    this.secId = secId;
  }


  public String getSemester() {
    return semester;
  }

  public void setSemester(String semester) {
    this.semester = semester;
  }


  public int getYear() {
    return year;
  }

  public void setYear(int year) {
    this.year = year;
  }

}
