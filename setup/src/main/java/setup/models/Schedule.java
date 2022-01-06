package setup.models;

public class Schedule {
    String F; // from
    String T; // to
    int D; // duration
    int S; // sum
    int I; // interrupt time

    public Schedule(String F, String T, int D, int S, int I) {
        this.F = F;
        this.T = T;
        this.D = D;
        this.S = S;
        this.I = I;
    }
}
