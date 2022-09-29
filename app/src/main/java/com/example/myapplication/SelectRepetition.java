package com.example.myapplication;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SelectRepetition extends AppCompatActivity {
    //10회버튼 선언
    private Button time10;
    //30회버튼 선언
    private Button time30;
    //50회버튼 선언
    private Button time50;
    //뒤로가기버튼 선언
    private Button back;
    //홈버튼 선언
    private Button home;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_repetition);
        //10회버튼 활성
        time10 = findViewById(R.id.times10);
        time10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(SelectRepetition.this , SelectDifficulty.class);
                //설정한 횟수 데이터를 전송
                intent1.putExtra("times", 10);
                startActivity(intent1);
            }
        });
        //30회버튼 활성
        time30 = findViewById(R.id.times30);
        time30.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(SelectRepetition.this , SelectDifficulty.class);
                //설정한 횟수 데이터를 전송
                intent2.putExtra("times", 30);
                startActivity(intent2);
            }
        });
        //50회버튼 활성
        time50 = findViewById(R.id.times50);
        time50.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent3 = new Intent(SelectRepetition.this , SelectDifficulty.class);
                //설정한 횟수 데이터를 전송
                intent3.putExtra("times", 50);
                startActivity(intent3);
            }
        });
        //뒤로가기버튼 활성
        back = findViewById(R.id.Back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //이전화면으로 이동
                Intent intent4 = new Intent(SelectRepetition.this , MainActivity.class);
                startActivity(intent4);
            }
        });
        home = findViewById(R.id.Home);
        home.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                AlertDialog.Builder builder = new AlertDialog.Builder(SelectRepetition.this);
                //홈버튼 확인창
                builder.setMessage("메인화면으로 가시겠습니까?");
                builder.setTitle("메인화면 알림창")
                        .setCancelable(false)
                        //Yes버튼 -> 홈화면으로 이동
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int i) {
                                Intent intent5 = new Intent(SelectRepetition.this , MainActivity.class);
                                startActivity(intent5);
                            }
                        })
                        //No버튼 -> 홈버튼 액션 취소
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int i) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alert = builder.create();
                alert.setTitle("메인화면 알림창");
                alert.show();
            }
        });
    }
}