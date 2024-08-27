package pliki;

import java.util.ArrayList;

public class Playlist extends ArrayList<Song> {

    //1f
    public Song atSecond(int time){
        int sum = 0;
        for(Song song : this){
            if((time >= sum) && (time < (sum + song.duration()))){
                return song;
            }
            sum += song.duration();
        }
        if(time > sum){
            throw new IndexOutOfBoundsException("Duration too long");
        }

        if(time <= 0){
            throw new IndexOutOfBoundsException("Negative duration");
        }
        return null;
    }
}
