package com.example.myapplication;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SelectRepetition extends AppCompatActivity {
    //    1. 게임시작버튼 선언
    private Button time10;
    //    2. 게임종료버튼 선언
    private Button time30;
    //    1. 게임시작버튼 선언
    private Button time50;
    //    2. 게임종료버튼 선언
    private Button back;
    private Button home;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_repetition);

        time10 = findViewById(R.id.times10);
        time10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(SelectRepetition.this , SelectDifficulty.class);
                intent1.putExtra("times", 10);
                startActivity(intent1);//4. 횟수설정화면으로 이동
            }
        });
        time30 = findViewById(R.id.times30);
        time30.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(SelectRepetition.this , SelectDifficulty.class);
                intent2.putExtra("times", 30);
                startActivity(intent2);//4. 횟수설정화면으로 이동
            }
        });
        time50 = findViewById(R.id.times50);
        time50.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent3 = new Intent(SelectRepetition.this , SelectDifficulty.class);
                intent3.putExtra("times", 50);
                startActivity(intent3);//4. 횟수설정화면으로 이동
            }
        });
        back = findViewById(R.id.Back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent4 = new Intent(SelectRepetition.this , MainActivity.class);
                startActivity(intent4);//4. 횟수설정화면으로 이동
            }
        });
        home = findViewById(R.id.Home);
        home.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                AlertDialog.Builder builder = new AlertDialog.Builder(SelectRepetition.this);
//    6. 게임종료 확인창
                builder.setMessage("메인화면으로 가시겠습니까?");
                builder.setTitle("메인화면 알림창")
                        .setCancelable(false)
//    7. Yes버튼 -> 홈화면으로 이동
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int i) {
                                Intent intent5 = new Intent(SelectRepetition.this , MainActivity.class);
                                startActivity(intent5);
                            }
                        })
//    8. No버튼 -> 게임종료 버튼 액션 취소
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