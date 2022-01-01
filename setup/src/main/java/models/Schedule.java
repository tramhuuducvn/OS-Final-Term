package models;

public class Schedule {
    String F; // from
    String T; // to
    String D; // distance
    String S; // sum
    String I; // interrupt time

    public Schedule(String f, String t, String d, String s, String i) {
        F = f;
        T = t;
        D = d;
        S = s;
        I = i;
    }

    public String getF() {
        return F;
    }

    public void setF(String f) {
        F = f;
    }

    public String getT() {
        return T;
    }

    public void setT(String t) {
        T = t;
    }

    public String getD() {
        return D;
    }

    public void setD(String d) {
        D = d;
    }

    public String getS() {
        return S;
    }

    public void setS(String s) {
        S = s;
    }

    public String getI() {
        return I;
    }

    public void setI(String i) {
        I = i;
    }
}
