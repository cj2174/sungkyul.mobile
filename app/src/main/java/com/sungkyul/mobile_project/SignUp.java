package com.sungkyul.mobile_project;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;

import com.sungkyul.mobile_project.httpServlet.phpApi;

import org.json.JSONException;
import org.json.JSONObject;

public class SignUp extends Activity {
    EditText edtFullName, edtUserName, edtEmail, password, password_again;
    // 회원가입 클래스
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //  set ProgressDialog..
        Button btnSubmit = (Button)findViewById(R.id.btnSubmit);
        edtFullName = (EditText)findViewById(R.id.edtFullName);
        edtUserName = (EditText)findViewById(R.id.edtUserName);
        edtEmail = (EditText)findViewById(R.id.edtEmail);
        password = (EditText)findViewById(R.id.password);
        password_again = (EditText)findViewById(R.id.password_again);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Progressbar start
                ProgressDialog progressDialog;
                progressDialog = ProgressDialog.show(SignUp.this, "", "Loading..");

                // data set
                String serviceName = "회원가입";
                String Servicedata;
                Servicedata = "func=" + serviceName;
                // editText data set
                if ( edtFullName.getText().toString().equals("") || edtUserName.getText().toString().equals("") ||
                        edtEmail.getText().toString().equals("") || password.getText().toString().equals("") || password_again.getText().toString().equals("")){
                    progressDialog.dismiss();
                    AlertDialog alertDialog = new AlertDialog.Builder(SignUp.this).create();
                    alertDialog.setTitle("Error");
                    alertDialog.setMessage("정보를 모두 입력해주세요.");
                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "확인",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                    alertDialog.show();
                    return;
                }
                Servicedata += "&fullname=" + edtFullName.getText().toString();
                Servicedata += "&username=" + edtUserName.getText().toString();
                Servicedata += "&email=" + edtEmail.getText().toString();
                Servicedata += "&password=" + password.getText().toString();
                Servicedata += "&passagain=" + password_again.getText().toString();

                // return json data
                JSONObject data = phpApi.POSTsend(Servicedata);
                System.err.println("Data : " + data);

                // prev process
                try {
                    if ( data.toString().equals("") ||  data.getString("status") == "error" ){
                        progressDialog.dismiss();
                       return;
                    }
                    System.err.println("datagetString : " + data.getString("data"));
                    if ( data.getString("data").toString().equals("password is wrong") ){
                        progressDialog.dismiss();
                        AlertDialog alertDialog = new AlertDialog.Builder(SignUp.this).create();
                        alertDialog.setTitle("Error");
                        alertDialog.setMessage("비밀번호를 다시 입력해주세요.");
                        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "확인",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });
                        alertDialog.show();
                        System.err.println("비밀번호를 다시 입력해주세요.");
                        return;
                    }
                    if ( data.getString("data").toString().equals("existsUser") ){
                        progressDialog.dismiss();
                        AlertDialog alertDialog = new AlertDialog.Builder(SignUp.this).create();
                        alertDialog.setTitle("Error");
                        alertDialog.setMessage("이미 가입된 회원입니다.");
                        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "확인",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });
                        alertDialog.show();
                        System.err.println("이미 가입된 회원.");
                        return;
                    }
                    // 아이디 중복 막기
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                // center process
                System.err.println("아이디 생성 완료");

                // Progressbar exit
                progressDialog.dismiss();

                // LoginActivity 로 이동
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);

                // 현재 엑티비티 종료
                finish();
            }
        });
    }
}
