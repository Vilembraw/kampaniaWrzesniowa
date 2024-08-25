package kol1;

import static kol1.DigitalClock.Type.DWUDZIESTO;
import static kol1.DigitalClock.Type.DWUNASTO;

public class Main {
    public static void main(String[] args) {
        DigitalClock clock24 = new DigitalClock(DWUDZIESTO);
        System.out.println(clock24.toString());
        DigitalClock clock12 = new DigitalClock(DWUNASTO);
        System.out.println(clock12.toString());
    }
}
