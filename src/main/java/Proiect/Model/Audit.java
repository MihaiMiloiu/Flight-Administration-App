package Proiect.Model;

public class Audit {
    private String event;
    private String dateAndTime;

    public Audit(String event, String dateAndTime) {
        this.event = event;
        this.dateAndTime = dateAndTime;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public String getDateAndTime() {
        return dateAndTime;
    }

    public void setDateAndTime(String dateAndTime) {
        this.dateAndTime = dateAndTime;
    }

    @Override
    public String toString() {
        return "Audit{" +
                "event='" + event + '\'' +
                ", dateAndTime='" + dateAndTime + '\'' +
                '}';
    }
}
