package com.example.myapplication;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    //게임시작버튼 선언
    private Button Start;
    //게임정보버튼 선언
    private Button Info;
    //전적기록 버튼 선언
    private Button Record;
    //단어장 버튼 선언
    private Button Dictionary;
    //게임종료버튼 선언
    private Button Exit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //게임시작버튼 활성화
        Start = findViewById(R.id.Start);
        //게임정보버튼 활성화
        Info = findViewById(R.id.Explain);
        //전적기록버튼 활성화
        Record = findViewById(R.id.Record);
        //단어장버튼 활성화
        Dictionary = findViewById(R.id.WordNote);
        //횟수설정화면으로 이동
        Start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(MainActivity.this , SelectRepetition.class);
                startActivity(intent1);
            }
        });
        //게임정보화면으로 이동
        Info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(MainActivity.this , game_info.class);
                startActivity(intent1);
            }
        });
        //전적기록화면으로 이동
        Record.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(MainActivity.this , game_record.class);
                startActivity(intent1);
            }
        });
        //단어장화면으로 이동
        Dictionary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(MainActivity.this , word_dictionary.class);
                startActivity(intent1);
            }
        });

        //게임종료버튼 활성화
        Exit = findViewById(R.id.Exit);
        Exit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                //게임종료 확인창
                builder.setMessage("정말로 종료하시겠습니까?");
                builder.setTitle("종료 알림창")
                        .setCancelable(false)
                        //Yes버튼 -> 어플종료
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int i) {
                                ActivityCompat.finishAffinity(MainActivity.this);
                                android.os.Process.killProcess(android.os.Process.myPid());
                            }
                        })
                        //No버튼 -> 게임종료 버튼 액션 취소
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int i) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alert = builder.create();
                alert.setTitle("종료 알림창");
                alert.show();
            }
        });
    }
}