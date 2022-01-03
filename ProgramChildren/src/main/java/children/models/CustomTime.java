package children.models;

public class CustomTime {
    private int hours, minutes;

    public CustomTime(int hours, int minutes) {
        this.hours = hours;
        this.minutes = minutes;
    }
    public CustomTime(String timeString) {
        String[] timeTokens = timeString.split(":");
        this.hours = Integer.parseInt(timeTokens[0]);
        this.minutes = Integer.parseInt(timeTokens[1]);
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
