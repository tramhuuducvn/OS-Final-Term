package children.models;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

public class AppStatus implements Serializable {
    private int timeused;
    private Date timeShutdown;
    private int breaktime;

    public AppStatus() {
        this.timeused = 0;
        timeShutdown = null;
        breaktime = 0;
    }

    public AppStatus(int timeused, Date timeShutdown, int breaktime) {
        this.timeused = timeused;
        this.timeShutdown = timeShutdown;
        this.breaktime = breaktime;
    }
    public boolean isSameDate(Date date){
        if(timeShutdown == null){
            return false;
        }
        if(timeShutdown.getDate() != date.getDate() || timeShutdown.getMonth() != date.getMonth() || timeShutdown.getYear() != date.getYear()){
            return false;
        }
        return true;
    }

    public void increaseTimeUsed(){
        timeused++;
    }

    public int getTimeused() {
        return timeused;
    }

    public void setTimeused(int timeused) {
        this.timeused = timeused;
    }

    public Date getTimeShutdown() {
        return timeShutdown;
    }

    public void setTimeShutdown(Date timeShutdown) {
        this.timeShutdown = timeShutdown;
    }

    public int getBreaktime() {
        return breaktime;
    }

    public void setBreaktime(int breaktime) {
        this.breaktime = breaktime;
    }

    public String toString(){
        return "Time shutdown: " + timeShutdown + "\nTime used: " + timeused + "\nBreak: " + breaktime;
    }
}
