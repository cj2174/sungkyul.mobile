package com.sungkyul.mobile_project;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class AddWorkActivity extends Activity {

    // 활동을 추가할때 사용하는 클래스

    final int[] imgResource = {R.drawable.icon01, R.drawable.icon02,  R.drawable.icon03, R.drawable.icon04, R.drawable.icon05, R.drawable.icon06, R.drawable.icon07,
            R.drawable.icon08, R.drawable.icon09, R.drawable.icon10, R.drawable.icon11, R.drawable.icon12};

    int index;
    Button btnadd;
    EditText edtName;
    ImageButton btnaddImg;

    public static MyDBHelper myDBHelper;
    public static SQLiteDatabase db;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addwork);

        btnaddImg = (ImageButton)findViewById(R.id.btnaddimage);
        btnaddImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"테스트", Toast.LENGTH_LONG).show();
                showAlertDialog();
            }
        });

        myDBHelper = new MyDBHelper(this);
        btnadd = (Button)findViewById(R.id.btnAdd);
        btnadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edtName = (EditText)findViewById(R.id.edtName);
                Option option = new Option(edtName.getText().toString(),"테스트");

                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.putExtra("class" ,option);

                db = myDBHelper.getWritableDatabase();
                myDBHelper.activity_insert(db, edtName.getText().toString(), imgResource[index]);
                db.close();

                startActivity(intent);
                finish();
            }
        });

    }

    private void showAlertDialog() {
        // Prepare grid view
        DialogInterface dia = null;
        GridView gridView = new GridView(this);
        ArrayList<Bitmap> picArr = new ArrayList<Bitmap>();
        // Set grid view to alertDialog
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final AlertDialog ad = builder.create();

        final int[] imgResource = {R.drawable.icon01, R.drawable.icon02,  R.drawable.icon03, R.drawable.icon04, R.drawable.icon05, R.drawable.icon06, R.drawable.icon07,
                R.drawable.icon08, R.drawable.icon09, R.drawable.icon10, R.drawable.icon11, R.drawable.icon12};

        for (int i = 0; i < imgResource.length; i++) {
            picArr.add(BitmapFactory.decodeResource(getResources(), imgResource[i]));
        }

        ImageGridAdapter imageGridAdapter = new ImageGridAdapter(this, imgResource);
        gridView.setAdapter(imageGridAdapter);
        gridView.setNumColumns(4);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // do something here
                Toast.makeText(getApplicationContext(), "테스트" + position, Toast.LENGTH_LONG).show();
                btnaddImg.setBackgroundResource(imgResource[position]);
                ad.dismiss();
                index = position;
            }
        });


        ad.setView(gridView);
        ad.setTitle("이미지 선택");
        gridView.setPadding(20, 20, 20, 20);
        ad.show();

    }


    public class ImageGridAdapter extends BaseAdapter {

        Context context = null;

        int[] imageIDs = null;

        public ImageGridAdapter(Context context, int[] imageIDs) {
            this.context = context;
            this.imageIDs = imageIDs;
        }
        public int getCount() {
            return (null != imageIDs) ? imageIDs.length : 0;
        }
        public Object getItem(int position) {
            return (null != imageIDs) ? imageIDs[position] : 0;
        }
        public long getItemId(int position) {
            return position;
        }
        public View getView(int position, View convertView, ViewGroup parent) {
            ImageView imageView = null;

            if (null != convertView)
                imageView = (ImageView)convertView;
            else {
                // GridView 뷰를 구성할 ImageView 뷰의 비트맵을 정의
                // 크기를 320*240으로 설정

                Bitmap bmp
                        = BitmapFactory.decodeResource(context.getResources(), imageIDs[position]);
                bmp = Bitmap.createScaledBitmap(bmp, 320, 240, false);

                // GridView 뷰를 구성할 ImageView 뷰들을 정의
                // 뷰에 지정할 이미지는 앞에서 정의한 비트맵 객체
                imageView = new ImageView(context);
                imageView.setAdjustViewBounds(true);


                imageView.setImageBitmap(bmp);

            }

            return imageView;
        }

    }



}
