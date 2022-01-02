package children.models;

public class CustomTime {
    private int hours, minutes;

    public CustomTime(int hours, int minutes) {
        this.hours = hours;
        this.minutes = minutes;
    }
    public CustomTime(String src) {
        String[] srcs = src.split(":");
        this.hours = Integer.parseInt(srcs[0]);
        this.minutes = Integer.parseInt(srcs[1]);
    }

    public int getHours() {
        return hours;
    }

    public void setHours(int hours) {
        this.hours = hours;
    }

    public int getMinutes() {
        return minutes;
    }

    public void setMinutes(int minutes) {
        this.minutes = minutes;
    }
}
