package springmvc.pojo;

public class TimeSlot {

  private String timeSlotId;
  private String day;
  private int startHr;
  private int startMin;
  private int endHr;
  private int endMin;


  public String getTimeSlotId() {
    return timeSlotId;
  }

  public void setTimeSlotId(String timeSlotId) {
    this.timeSlotId = timeSlotId;
  }


  public String getDay() {
    return day;
  }

  public void setDay(String day) {
    this.day = day;
  }


  public int getStartHr() {
    return startHr;
  }

  public void setStartHr(int startHr) {
    this.startHr = startHr;
  }


  public int getStartMin() {
    return startMin;
  }

  public void setStartMin(int startMin) {
    this.startMin = startMin;
  }


  public int getEndHr() {
    return endHr;
  }

  public void setEndHr(int endHr) {
    this.endHr = endHr;
  }


  public int getEndMin() {
    return endMin;
  }

  public void setEndMin(int endMin) {
    this.endMin = endMin;
  }

}
