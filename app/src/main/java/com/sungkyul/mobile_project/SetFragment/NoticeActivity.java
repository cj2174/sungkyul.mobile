package com.sungkyul.mobile_project.SetFragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.sungkyul.mobile_project.R;

public class NoticeActivity extends Activity {

    private View header;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notice);
        setTitle("공지사항");

        //리스트뷰에 들어갈 문자들
        final String[] notice = {"시스템 점검 - 2023.05.26(금)" ,
                "시스템 점검 - 2023.05.27(토)" ,"시스템 점검 - 2023.05.28(일)"};

        header = getLayoutInflater().inflate(R.layout.notice,null,false);


        ListView listnotice = (ListView)findViewById(R.id.Noitcelist);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, notice);

        listnotice.setAdapter(adapter);

        //클릭했을때 이벤트
        listnotice.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Toast.makeText(getApplicationContext(),
//                        notice[position] +"이 클릭되었습니다." +  position, Toast.LENGTH_SHORT).show();
                Toast.makeText(getApplicationContext(),
                        notice[position] +"을 클릭했습니다.", Toast.LENGTH_SHORT).show();
                switch (position) {

                    case 0 :
                        show("홈 화면 레이아웃 생성", "2023.05.26 \n\n 1. 홈 화면 레이아웃을 생성했어요.");
                        break;
                    case 1 :
                        show("로그인 화면 레이아웃 생성", "2023.05.27 \n\n 1. 로그인 화면 레이아웃을 생성했어요.");
                        break;
                    case 2 :
                        show("환경설정 화면 생성", "2023.05.28 \n\n 1. 환경설정 화면을 생성했어요.");
                        break;
                }


            }
        });

    }

    void show(String title, String content)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title);
        builder.setMessage(content);

        builder.setNegativeButton("닫기",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
        builder.show();
    }
}