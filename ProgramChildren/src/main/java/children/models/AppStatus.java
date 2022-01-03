package children.models;

import java.io.Serializable;
import java.util.Date;

public class AppStatus implements Serializable {
    private int usedTime;
    private Date timeShutdown;
    private int breakTime;

    public AppStatus() {
        this.usedTime = 0;
        timeShutdown = null;
        breakTime = 0;
    }

    public AppStatus(int usedTime, Date timeShutdown, int breakTime) {
        this.usedTime = usedTime;
        this.timeShutdown = timeShutdown;
        this.breakTime = breakTime;
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
        usedTime++;
    }

    public int getUsedTime() {
        return usedTime;
    }

    public void setUsedTime(int usedTime) {
        this.usedTime = usedTime;
    }

    public Date getTimeShutdown() {
        return timeShutdown;
    }

    public void setTimeShutdown(Date timeShutdown) {
        this.timeShutdown = timeShutdown;
    }

    public int getBreakTime() {
        return breakTime;
    }

    public void setBreakTime(int breakTime) {
        this.breakTime = breakTime;
    }

    public String toString(){
        return "Time shutdown: " + timeShutdown + "\nTime used: " + usedTime + "\nBreak: " + breakTime;
    }
}
