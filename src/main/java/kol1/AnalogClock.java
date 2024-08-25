package kol1;

import org.example.Shape;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AnalogClock extends Clock{
    private final List<ClockHand> hands;

    public AnalogClock(LocalTime time, City city) {
        super(time, city);

        // Inicjalizowanie listy wskazówek
        hands = Arrays.asList(
                new HourHand(),
                new MinuteHand(),
                new SecondHand()
        );

        redoHands();
    }

    public void redoHands(){

        // Ustawianie czasu dla każdej wskazówki
        for (ClockHand hand : hands) {
            hand.setTime(time);
        }

    }
    @Override
    public void setCity(City cityT) {

        double temp = cityT.getStrefa() - this.city.getStrefa();
//        System.out.println(cityT.getStolica());
//        System.out.println(this.city.getStolica());
//        System.out.println(String.format("Roznica: %f",temp));
        this.time = time.plusHours((long)temp);
        this.city = cityT;
        redoHands();
    }

    @Override
    public void setCurrentTime(){
        this.time = LocalTime.now();
        redoHands();
    }
    @Override
    public void setTime(int hour, int minutes, int secs){
        this.time = LocalTime.of(hour,minutes,secs);
        redoHands();
    }

    public void toSvg(String path){

        try {
            FileWriter fileWriter = new FileWriter(path);
            fileWriter.write(" <svg width=\"200\" height=\"200\" viewBox=\"-100 -100 200 200\" xmlns=\"http://www.w3.org/2000/svg\">");
            fileWriter.write(" <!-- Tarcza zegara -->");
            fileWriter.write("  <circle cx=\"0\" cy=\"0\" r=\"90\" fill=\"none\" stroke=\"black\" stroke-width=\"2\" />");
            fileWriter.write("  <g text-anchor=\"middle\">");
            fileWriter.write("   <text x=\"0\" y=\"-80\" dy=\"6\">12</text>");
            fileWriter.write(" <text x=\"80\" y=\"0\" dy=\"4\">3</text>");
            fileWriter.write("  <text x=\"0\" y=\"80\" dy=\"6\">6</text>");
            fileWriter.write("  <text x=\"-80\" y=\"0\" dy=\"4\">9</text>");
            fileWriter.write("</g>");

            for(ClockHand c : hands){
                fileWriter.write(c.toSvg());
            }

            fileWriter.write("</svg>");
            fileWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
