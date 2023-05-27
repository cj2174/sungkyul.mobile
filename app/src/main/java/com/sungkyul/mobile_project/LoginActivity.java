package com.sungkyul.mobile_project;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.sungkyul.mobile_project.httpServlet.phpApi;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends Activity {
    Button btnSignUp;
    Button btnLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        btnLogin = (Button)findViewById(R.id.btnlogin);

        // 로그인 버튼
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Progressbar start
                ProgressDialog progressDialog;
                progressDialog = ProgressDialog.show(LoginActivity.this, "", "Loading..");

                // get id and pass text data
                EditText id = (EditText)findViewById(R.id.id);
                EditText pass = (EditText)findViewById(R.id.password);

                // data set
                String serviceName = "signin";
                String Servicedata;
                Servicedata = "func=" + serviceName;

                if ( id.getText().toString().equals("") || pass.getText().toString().equals("") ){
                    progressDialog.dismiss();
                    AlertDialog alertDialog = new AlertDialog.Builder(LoginActivity.this).create();
                    alertDialog.setMessage("아이디와 비밀번호를 입력해주세요.");
                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "확인",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                    alertDialog.show();
                    return;
                }
                Servicedata += "&username=" + id.getText().toString();
                Servicedata += "&password=" + pass.getText().toString();

                // return json data
                JSONObject data = phpApi.POSTsend(Servicedata);
                System.err.println("Data : " + data);
                try {
                    // set alert dialog
                    AlertDialog alertDialog = new AlertDialog.Builder(LoginActivity.this).create();
                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "확인",
                        new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });

                    // 아이디 혹은 비밀번호가 틀림
                    if ( data.getString("data").toString().equals("wronginfo") ){
                        progressDialog.dismiss();
                        alertDialog.setMessage("아이디 혹은 비밀번호 정보가 잘못되었습니다.");
                        alertDialog.show();
                        return;
                    }

                    // 알 수 없는 오류
                    if ( !data.getString("status").toString().equals("success") ){
                        progressDialog.dismiss();
                        alertDialog.setTitle("오류");
                        alertDialog.setMessage("알 수 없는 오류 발생");
                        alertDialog.show();
                        return;
                    }
                    progressDialog.dismiss();

                    // 로그인 정보 기록
                    SharedPreferences sharedpreferences = getSharedPreferences("userData", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedpreferences.edit();
                    editor.putString("아이디", id.getText().toString());
                    editor.putString("비밀번호", id.getText().toString());
                    editor.commit();

                    //alert
                    alertDialog.setMessage("로그인 성공");
                    alertDialog.show();

                    // 메인으로 넘어가기..
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                    finish();
                } catch (JSONException e){
                    e.printStackTrace();
                }
            }
        });


        // 회원가입 버튼 ( 넘어가기 )
        btnSignUp = (Button)findViewById(R.id.btnSignup);
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "회원가입 클릭", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getApplicationContext(), SignUp.class);
                startActivity(intent);
            }
        });
    }
}