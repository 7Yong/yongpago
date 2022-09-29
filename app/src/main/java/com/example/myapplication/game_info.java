package com.example.myapplication;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class game_info extends AppCompatActivity {
    private Button back;
    private Button home;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_info);
        back = findViewById(R.id.Back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        home = findViewById(R.id.Home);
        home.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                AlertDialog.Builder builder = new AlertDialog.Builder(game_info.this);
                //게임종료 확인창
                builder.setMessage("메인화면으로 가시겠습니까?");
                builder.setTitle("메인화면 알림창")
                        .setCancelable(false)
                        //Yes버튼 -> 홈화면으로 이동
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int i) {
                                Intent intent1 = new Intent(game_info.this , MainActivity.class);
                                startActivity(intent1);
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
                alert.setTitle("메인화면 알림창");
                alert.show();
            }
        });
    }
}