package cse.underdog.org.underdog_client.memo;

import java.util.ArrayList;
import java.util.List;

import cse.underdog.org.underdog_client.login.UserInfo;
import cse.underdog.org.underdog_client.schedule.ScheduleInfo;

public class MemoResult {
    List<MemoInfo> data = new ArrayList<MemoInfo>();
    String stat;

    public MemoResult(String stat){
        this.stat = stat;
    }

    public MemoResult(List<MemoInfo> data, String stat){
        this.data = data;
        this.stat = stat;
    }

}
