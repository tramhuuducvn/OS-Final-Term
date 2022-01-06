package children.models;

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

    @Override
    public String toString(){
        return "F: " + f + "\n" +
                "T: " + t + "\n" +
                "D: " + d + "\n" +
                "I: " + i + "\n" +
                "S: " + s;
    }



    public String getF() {
        return f;
    }

    public String getT() {
        return t;
    }

    public int getD() {
        return d;
    }

    public int getS() {
        return s;
    }

    public int getI() {
        return i;
    }
}
