package models;

public class Schedule {
    String f; // from
    String t; // to
    int d; // duration
    int s; // sum
    int i; // interrupt time

    public Schedule(String f, String t, int d, int s, int i) {
        this.f = f;
        this.t = t;
        this.d = d;
        this.s = s;
        this.i = i;
    }

    public boolean isChange(Schedule schedule){
        if(f.equals(schedule.getF()) && t.equals(schedule.getT()) && d == schedule.getD() && s == schedule.getS() && i == schedule.getI()){
            return true;
        }
        return false;
    }

    public String getF() {
        return f;
    }

    public void setF(String f) {
        this.f = f;
    }

    public String getT() {
        return t;
    }

    public void setT(String t) {
        this.t = t;
    }

    public int getD() {
        return d;
    }

    public void setD(int d) {
        this.d = d;
    }

    public int getS() {
        return s;
    }

    public void setS(int s) {
        this.s = s;
    }

    public int getI() {
        return i;
    }

    public void setI(int i) {
        this.i = i;
    }
}
