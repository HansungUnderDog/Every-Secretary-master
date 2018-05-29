package cse.underdog.org.underdog_client.schedule;

import java.util.ArrayList;
import java.util.List;

public class ScheduleResult {
    public List<ScheduleInfo> data= new ArrayList<ScheduleInfo>();
    public String stat;

    public ScheduleResult(String stat) {
        this.stat = stat;
    }

    public ScheduleResult(List<ScheduleInfo> data, String stat) {
        this.data = data;
        this.stat = stat;
    }
}
