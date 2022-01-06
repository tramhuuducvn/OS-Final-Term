package children.models;

public class CustomTime {
    private int hours, minutes;

    public CustomTime(String timeString) {
        String[] timeTokens = timeString.split(":");
        this.hours = Integer.parseInt(timeTokens[0]);
        this.minutes = Integer.parseInt(timeTokens[1]);
    }

    public int getHours() {
        return hours;
    }

    public int getMinutes() {
        return minutes;
    }
}
