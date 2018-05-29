package cse.underdog.org.underdog_client;

import java.sql.Time;

public class TTSData implements  Comparable<TTSData>{
    String name;
    String time;
    String person;
    String place;

    public TTSData(String name, String time, String person, String place){
        this.name = name;
        this.time = time;
        this.person = person;
        this.place = place;
    }

    public String getName(){
        return name;
    }

    public String getTime(){
        return time;
    }

    public String getPerson(){
        return person;
    }

    public String getPlace(){
        return place;
    }

    public int compareTo(TTSData tts){
        Time t1 = Time.valueOf(this.time);
        long l1 = t1.getTime();
        Time t2 = Time.valueOf(tts.time);
        long l2 = t2.getTime();

        if(l1 > l2){
            return 1;
        }else if (l1<l2){
            return -1;
        }else{
            return 0;
        }
    }
}
