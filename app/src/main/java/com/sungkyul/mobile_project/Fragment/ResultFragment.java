package com.sungkyul.mobile_project.Fragment;


import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.sungkyul.mobile_project.R;
import com.sungkyul.mobile_project.Result.ResultActivity;
import com.sungkyul.mobile_project.Result.ResultItem;
import com.sungkyul.mobile_project.Result.ResultItemView;
import com.sungkyul.mobile_project.MyDBHelper;

import java.util.ArrayList;
import java.util.Calendar;

public class ResultFragment extends Fragment {

    //날짜txt값
    TextView txtDate;
    //이미지 버튼 날짜 +,-
    ImageView ImgDatePlus, ImgDateMinus;
    //날짜선택 데이트 피커
    DatePicker Dpicker;
    final Calendar c = Calendar.getInstance();
    //ListView
    ListView listresult;

    //DB
    public static MyDBHelper myDBHelper;
    public static SQLiteDatabase db;

    //날짜값
    int dyear = c.get(Calendar.YEAR);
    int dmonth = c.get(Calendar.MONTH)+1;
    int ddayofmonth = c.get(Calendar.DAY_OF_MONTH);
    static String[] stractname = new String[31];



    public ResultFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        ViewGroup rootview = (ViewGroup)inflater.inflate(R.layout.fragment_result, container, false);
        //메뉴
        setHasOptionsMenu(true);
        //id값 가져오기
        txtDate = (TextView)rootview.findViewById(R.id.txtDate);
        ImgDatePlus = (ImageView)rootview.findViewById(R.id.ImgDatePlus);
        ImgDateMinus = (ImageView)rootview.findViewById(R.id.ImgDateMinus);
        Dpicker = (DatePicker)rootview.findViewById(R.id.DPicker);
        listresult = (ListView)rootview.findViewById(R.id.listresult);


        //리스트뷰 연결
        final ResultAdapter adapter = new ResultAdapter();
        myDBHelper = new MyDBHelper(getActivity());
        db = myDBHelper.getWritableDatabase();
        String selectAll = "Select * FROM time_db";
        String strdmonth10 = dmonth +"";
        if(Integer.parseInt(strdmonth10) < 10) strdmonth10 = "0"+strdmonth10;
        String strdayofMonth10 = ddayofmonth +"";
        if(Integer.parseInt(strdayofMonth10) < 10) strdayofMonth10 = "0"+strdayofMonth10;



        String timeHMS = dyear + "/" + strdmonth10 + "/" + strdayofMonth10 ;
        String selectDateAll = "Select * FROM time_db WHERE timestart LIKE '" + timeHMS + "%'" ;

        Log.i(this.getClass().getName(), "Select * FROM time_db WHERE timestart LIKE '" + timeHMS + "%'");
        Log.i(this.getClass().getName(), "dyear --> " + dyear);
        Log.i(this.getClass().getName(), "strdmonth10 --> " + strdmonth10);
        Log.i(this.getClass().getName(), "strddayofmonth --> " + strdayofMonth10);

        Cursor cursor = db.rawQuery(selectDateAll,null);
        int resource = 0;
        String getImgsrc = "";

        //아이템 객체 배열
        ResultItem[] resultItems = new ResultItem[cursor.getCount()];


        while(cursor.moveToNext()){
            Log.i(this.getClass().getName(), "Cursor안에 들어옴");
            String activityname = cursor.getString(1);
            String starttime = cursor.getString(2);
            starttime = starttime.split(" ")[1].split(":")[0] +
                        ":" + starttime.split(" ")[1].split(":")[1];
            String endtime = cursor.getString(3);
            endtime = endtime.split(" ")[1].split(":")[0] +
                    ":" + endtime.split(" ")[1].split(":")[1];
            String timedata = cursor.getString(4);
//            timedata = timedata.split(":")[0] + ":" + timedata.split(":")[1];
            String timeHour = timedata.split(":")[0];
            String timeMinute = timedata.split(":")[1];

            if(Integer.parseInt(timeHour) < 10) timeHour = "0" + timeHour;
            if(Integer.parseInt(timeMinute) < 10) timeMinute = "0" + timeMinute;
            timedata = timeHour + " : " + timeMinute;


            //이미지 리소스를 가져오기위한 쿼리문
            getImgsrc = "Select * FROM user_activity WHERE activityname = " + "'" +activityname +"'";
            Log.i(this.getClass().getName() , "getImgsr --> " + getImgsrc );
            Cursor cursor1 = db.rawQuery(getImgsrc,null);
            cursor1.moveToNext();
            resource = cursor1.getInt(2);


            resultItems[cursor.getPosition()] = new ResultItem(activityname, starttime, endtime, cursor.getString(4), resource);

            adapter.addItem(new ResultItem(activityname, starttime, endtime, timedata, resource));
        }

        //어댑터 연결
        listresult.setAdapter(adapter);


        //현재 날짜로 설정하기
        txtDate.setText(dyear + "-" + dmonth + "-" + ddayofmonth);

        //날짜를 클릭 했을때
        txtDate.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {


                if(listresult.getVisibility() == View.VISIBLE){
                    listresult.setVisibility(View.INVISIBLE);
                }

                if(Dpicker.getVisibility() == View.VISIBLE){
                    Dpicker.setVisibility(View.INVISIBLE);
                    listresult.setVisibility(View.VISIBLE);
                }else{
                    Dpicker.setVisibility(View.VISIBLE);
                }
            }
        });

        Dpicker.getCalendarView().setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                Log.i("Test", "선택 년도 : " +year + " 월 : " + (month+1) + " 일 : " + dayOfMonth);
                txtDate.setText(year + "-" + (month+1) + "-" + dayOfMonth);
                c.set(year, month, dayOfMonth);

                refresh();

                dyear = year;
                dmonth = month+1;
                ddayofmonth = dayOfMonth;

                Dpicker.setVisibility(View.INVISIBLE);
                listresult.setVisibility(View.VISIBLE);
            }
        });

        ImgDatePlus.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //일에 +1
                c.add(Calendar.DATE, 1);
                dyear = c.get(Calendar.YEAR);
                dmonth = c.get(Calendar.MONTH)+1;
                ddayofmonth = c.get(Calendar.DAY_OF_MONTH);
                txtDate.setText(dyear + "-" + (dmonth) + "-" + ddayofmonth);
                refresh();
            }
        });

        ImgDateMinus.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                c.add(Calendar.DATE, -1);
                dyear = c.get(Calendar.YEAR);
                dmonth = c.get(Calendar.MONTH)+1;
                ddayofmonth = c.get(Calendar.DAY_OF_MONTH);
                txtDate.setText(dyear + "-" + (dmonth) + "-" + ddayofmonth);
                refresh();
            }
        });

        db.close();
        return rootview;
    }

    //메뉴바
    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_result,menu);

    }

    //메뉴액션
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int curid = item.getItemId();

        switch (curid){
            case R.id.nav_refresh:
                Log.i(this.getClass().getName(), "새로고침 클릭");
                refresh();
                break;

            default:
                break;

        }

        return super.onOptionsItemSelected(item);
    }

    //리스트뷰 어댑터
    class ResultAdapter extends BaseAdapter{
        ArrayList<ResultItem> items = new ArrayList<ResultItem>();

        @Override
        public int getCount() {
            return items.size();
        }

        public void addItem(ResultItem item){
            items.add(item);
        }


        @Override
        public Object getItem(int position) {
            return items.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }


        //어댑터가 데이터를 관리하고 뷰를 만듦
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ResultItemView resultItemView = null;

            if(convertView == null){
                resultItemView = new ResultItemView(getActivity());
            }else{
                resultItemView = (ResultItemView)convertView;
            }

            ResultItem item = items.get(position);
            resultItemView.setName(item.getName());
            resultItemView.setstartTime(item.getStarttime());
            resultItemView.setendTime(item.getEndtime());
            resultItemView.setTime(item.getTime());
            resultItemView.setImgae(item.getResid());


            return resultItemView;
        }
    }

    public void refresh(){
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.detach(this);
        ft.attach(this);
        ft.commit();
    }

}