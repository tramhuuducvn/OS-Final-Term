package models;

public class Schedule {
    String F; // from
    String T; // to
    int D; // duration
    int S; // sum
    int I; // interrupt time

    public Schedule(String f, String t, int d, int s, int i) {
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

    public int getD() {
        return D;
    }

    public void setD(int d) {
        D = d;
    }

    public int getS() {
        return S;
    }

    public void setS(int s) {
        S = s;
    }

    public int getI() {
        return I;
    }

    public void setI(int i) {
        I = i;
    }
}
