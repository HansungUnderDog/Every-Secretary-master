package cse.underdog.org.underdog_client.schedule;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ScheduleInfo {
    public Data data;
    public int schedule_id;
    public int types;
    public String app_person;
    public String place;
    public String content;
    public String dates;
    public int cycle;
    public int user_id;

    public ScheduleInfo() {

    }

    public class Data{

    }

    public ScheduleInfo(int schedule_id, int types, String app_person, String place, String content,
                            String dates, int cycle, int user_id) {
       this.schedule_id = schedule_id;
       this.types = types;
       this.app_person = app_person;
       this.place = place;
       this.content = content;
       this.dates = dates;
       this.cycle = cycle;
       this.user_id = user_id;
    }

}
