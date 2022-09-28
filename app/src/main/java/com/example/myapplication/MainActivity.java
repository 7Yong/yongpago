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
    //    1. 게임시작버튼 선언
    private Button Start;
    private Button Info;
    private Button Record;
    private Button Dictionary;
    //    2. 게임종료버튼 선언
    private Button Exit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//    3. 게임시작버튼 활성화
        Start = findViewById(R.id.Start);
        Info = findViewById(R.id.Explain);
        Record = findViewById(R.id.Record);
        Dictionary = findViewById(R.id.WordNote);
        Start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(MainActivity.this , SelectRepetition.class);
                startActivity(intent1);//4. 횟수설정화면으로 이동
            }
        });
        Info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(MainActivity.this , game_info.class);
                startActivity(intent1);//4. 횟수설정화면으로 이동
            }
        });
        Record.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(MainActivity.this , game_record.class);
                startActivity(intent1);//4. 횟수설정화면으로 이동
            }
        });
        Dictionary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(MainActivity.this , word_dictionary.class);
                startActivity(intent1);//4. 횟수설정화면으로 이동
            }
        });

//    5. 게임종료버튼 활성화
        Exit = findViewById(R.id.Exit);
        Exit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
//    6. 게임종료 확인창
                builder.setMessage("정말로 종료하시겠습니까?");
                builder.setTitle("종료 알림창")
                        .setCancelable(false)
//    7. Yes버튼 -> 어플종료
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int i) {
                                ActivityCompat.finishAffinity(MainActivity.this);
                                android.os.Process.killProcess(android.os.Process.myPid());
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
                alert.setTitle("종료 알림창");
                alert.show();
            }
        });
    }
}