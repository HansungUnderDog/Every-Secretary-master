package cse.underdog.org.underdog_client.schedule;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.CalendarMode;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import java.util.HashMap;
import java.util.Iterator;

import butterknife.BindView;
import butterknife.ButterKnife;
import cse.underdog.org.underdog_client.R;
import cse.underdog.org.underdog_client.application.ApplicationController;
import cse.underdog.org.underdog_client.network.NetworkService;
import cse.underdog.org.underdog_client.recyclerview.schedule.RecyclerAdapterTabSchedule;
import cse.underdog.org.underdog_client.schedule.calendar.OneDayDecorator;
import cse.underdog.org.underdog_client.schedule.calendar.SaturdayDecorator;
import cse.underdog.org.underdog_client.schedule.calendar.SundayDecorator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ScheduleFragment extends Fragment {
    private HashMap<String, ArrayList<ScheduleInfo>> hash;
    int count = 0;
    private NetworkService service;
    String selectedDate;

    Date date;
    SimpleDateFormat tmp;
    String currentDate;

    ArrayList<ScheduleInfo> schedules= new ArrayList<ScheduleInfo>();
    ArrayList<ScheduleInfo> currentArray = new ArrayList<ScheduleInfo>();
    ArrayList<ScheduleInfo> selectedArray = new ArrayList<ScheduleInfo>();
    //ArrayList<ScheduleInfo> emptyArray;

    private RecyclerView sRecyclerView;
    private RecyclerAdapterTabSchedule sAdapter;
    private RecyclerView.LayoutManager sLayoutManager;

    static CalendarDay selectedDay = null;
    static boolean Selected;

    String result = "no";
    Intent i;

    @Nullable
    @BindView(R.id.calendarView)
    MaterialCalendarView calendar;

    private final int REQ_CODE_SPEECH_INPUT = 100;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    } // all method is test

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_schedule, null);
        date = new Date();
        tmp = new SimpleDateFormat("yyyy-MM-dd");
        currentDate = tmp.format(date).toString();
        service = ApplicationController.getInstance().getNetworkService();
        ButterKnife.bind(this, view);
        setCalendar();

        ButterKnife.bind(this, view);

        setCalendar();

        //ScheduleInfo info = new ScheduleInfo(1,1, "asdf", "asdf", "asdf", "asdf", 1, 1);
       // emptyArray = new ArrayList<ScheduleInfo>();
       // emptyArray.add(info);

        HashMap<String, ScheduleInfo> hashMap = new HashMap<>();

        setCalendar();

        sRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);

        //getRecyclerView(emptyArray);

        getSchedule();

        //System.out.println(schedules.indexOf(0));
        return view;
    }

    public void getHash(){
        hash = new HashMap<String, ArrayList<ScheduleInfo>> ();

        for(int i=0; i<schedules.size();i++) {
            String splitDate[] = schedules.get(i).dates.split("T");
            String com = splitDate[0];
            ArrayList<ScheduleInfo> array = new ArrayList<ScheduleInfo>();
            for(int j=0; j<schedules.size(); j++) {
                String splitDate2[] = schedules.get(j).dates.toString().split("T");
                String com2 = splitDate2[0];
                if(com2.equals(com)) {
                    array.add((ScheduleInfo) schedules.get(j));
                }
            }
            hash.put(com, array);
        }

        currentArray = hash.get(currentDate);
        selectedArray = hash.get(selectedDate);

        if(currentArray == null) {
            currentArray = new ArrayList<ScheduleInfo>();
        }
        if(selectedArray == null) {
            selectedArray = new ArrayList<ScheduleInfo>();
        }


        Iterator it = hash.keySet().iterator();
        while(it.hasNext()){
            String key = (String)it.next();

            ArrayList<ScheduleInfo> info = hash.get(key);
        }

        currentArray = hash.get(currentDate);
        selectedArray = hash.get(selectedDate);

    }

    public void getRecyclerView(ArrayList<ScheduleInfo> sArray){


        sRecyclerView.setHasFixedSize(false);
        sLayoutManager = new LinearLayoutManager(getActivity());
        sRecyclerView.setLayoutManager(sLayoutManager);
        sRecyclerView.scrollToPosition(0);

        sAdapter = new RecyclerAdapterTabSchedule(sArray);
        sRecyclerView.setAdapter(sAdapter);
        sRecyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    public void getSchedule(){
        Call<ScheduleResult> getSchedule = service.getSchedule();

        getSchedule.enqueue(new Callback<ScheduleResult>() {
            @Override
            public void onResponse(Call<ScheduleResult> call, Response<ScheduleResult> response) {
                if (response.isSuccessful()) {
                    if (response.body().stat.equals("success")) {
                        System.out.println("리스폰스");
                        for(int i=0; i<response.body().data.size(); i++){
                            schedules.add(response.body().data.get(i));
                        }
                        getHash();
                        getRecyclerView(currentArray);
                    }
                }
            }

            @Override
            public void onFailure(Call<ScheduleResult> call, Throwable t) {
                Log.e("fail", t.getMessage());
                System.out.println("리스폰스 실패");
            }
        });
    }

    public void setCalendar() {
        calendar.state().edit() //materialCalendarView 세팅 : 달력의 시작과 끝을 지정
                .setFirstDayOfWeek(Calendar.SUNDAY)
                .setMinimumDate(CalendarDay.from(2010, 0, 1))
                .setMaximumDate(CalendarDay.from(2050, 11, 31))
                .setCalendarDisplayMode(CalendarMode.MONTHS)
                .commit();

        calendar.addDecorators( // 오늘, 일요일, 토요일 날짜에 데코
                new SundayDecorator(),
                new SaturdayDecorator(),
                new OneDayDecorator());

        calendar.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {

                if(selectedDay == date){
                    selected = false;
                    Selected = selected;
                }
                else{
                    selected = true;
                    Selected = selected;
                }
                selectedDay = date;
                String year = String.valueOf(selectedDay.getYear());
                String month, day;
                if(selectedDay.getMonth() <10) {
                    month = "0"+String.valueOf(selectedDay.getMonth()+1);
                }
                else month = String.valueOf(selectedDay.getMonth());

                if(selectedDay.getDay() < 10) {
                    day = "0" + String.valueOf(selectedDay.getDay());
                }
                else day = String.valueOf(selectedDay.getDay());


                //selectedDate = year+"-"+month+"-"+day;

                selectedDate = year+"-"+month+"-"+day; // yyyy-mm-dd
                System.out.println("아래에서 " + selectedDate);

                getHash();
                getRecyclerView(selectedArray);
            }
        });
    }


}