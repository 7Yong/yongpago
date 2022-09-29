package com.example.myapplication;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SelectDifficulty extends AppCompatActivity {
    //난이도 쉬움버튼 선언
    private Button easy;
    //난이도 보통버튼 선언
    private Button normal;
    //난이도 어려움버튼 선언
    private Button hard;
    //뒤로가기버튼 선언
    private Button back;
    //홈버튼 선언
    private Button home;
    //횟수 선언
    public int get_times;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_difficulty);
        //횟수선택화면에서 선택한 횟수 데이터를 가져온다
        Intent intent = getIntent();
        get_times = intent.getIntExtra("times", 0);
        //난이도 쉬움 버튼 활성
        easy = findViewById(R.id.Easy);
        easy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(SelectDifficulty.this , GameScreen.class);
                intent1.putExtra("times", get_times);
                startActivity(intent1);
            }
        });
        //난이도 보통 버튼 활성
        normal = findViewById(R.id.Normal);
        normal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(SelectDifficulty.this , GameScreen.class);
                intent1.putExtra("times", get_times);
                startActivity(intent1);
            }
        });
        //난이도 어려움 버튼 활성
        hard = findViewById(R.id.Hard);
        hard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(SelectDifficulty.this , GameScreen.class);
                intent1.putExtra("times", get_times);
                startActivity(intent1);
            }
        });
        //뒤로가기버튼 활성
        back = findViewById(R.id.Back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(SelectDifficulty.this , SelectRepetition.class);
                startActivity(intent1);
            }
        });
        home = findViewById(R.id.Home);
        home.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                AlertDialog.Builder builder = new AlertDialog.Builder(SelectDifficulty.this);
                //홈버튼 확인창
                builder.setMessage("메인화면으로 가시겠습니까?");
                builder.setTitle("메인화면 알림창")
                        .setCancelable(false)
                        //Yes버튼 -> 홈화면으로 이동
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int i) {
                                Intent intent1 = new Intent(SelectDifficulty.this , MainActivity.class);
                                startActivity(intent1);
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