package com.example.myapplication;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class game_record extends AppCompatActivity {
    private Button back;
    private Button home;
    public int defeat;
    private int rate;
    private TextView total_game;
    private TextView game_record;
    private TextView game_rate;
    private TextView Max_score;
    public int number;
    public int win;
    public int score;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_record);

        GetGame();
        GetResult();
        GetScore();

        defeat = number - win;
//        rate = (win / number) * 100;
        total_game = findViewById(R.id.totalGameText);
        game_record = findViewById(R.id.recordText);
        game_rate = findViewById(R.id.winrateText);
        Max_score = findViewById(R.id.maxScoreText);
        total_game.setText("총 " + number + "판 " +"중");
        game_record.setText(win + "승 "  + defeat + "패");
        game_rate.setText("승률: 50%");
        Max_score.setText("최대 점수: " + score + "");
        back = findViewById(R.id.Back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(game_record.this , MainActivity.class);
                startActivity(intent1);//4. 횟수설정화면으로 이동
            }
        });
        home = findViewById(R.id.Home);
        home.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                AlertDialog.Builder builder = new AlertDialog.Builder(game_record.this);
                //게임종료 확인창
                builder.setMessage("메인화면으로 가시겠습니까?");
                builder.setTitle("메인화면 알림창")
                        .setCancelable(false)
                        //Yes버튼 -> 홈화면으로 이동
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int i) {
                                Intent intent1 = new Intent(game_record.this , MainActivity.class);
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
    //전체 게임횟수 받아오기
    private void GetGame()
    {
        String url = "http://192.168.0.11/show_record_game.php";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, response -> {
            try
            {
                JSONArray jsonArray = new JSONArray(response);
                for (int i = 0; i < jsonArray.length(); i++)
                {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);

                    String jnumber = jsonObject.getString("game");
                    number = Integer.parseInt(jnumber);
                    total_game = findViewById(R.id.totalGameText);
                    total_game.setText("총 " + number + "판 " +"중");
                }
            }
            catch (JSONException e)
            {
                e.printStackTrace();
            }
        }, error -> Toast.makeText(this, "실패함. 인터넷 연결 확인", Toast.LENGTH_SHORT).show());

        RequestQueue requestQueue = Volley.newRequestQueue(game_record.this);
        requestQueue.add(stringRequest);
    }
    //승패 표시
    private void GetResult()
    {
        String url = "http://192.168.0.11/show_record_result.php";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, response -> {
            try
            {
                JSONArray jsonArray = new JSONArray(response);
                for (int i = 0; i < jsonArray.length(); i++)
                {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);

                    String jwin = jsonObject.getString("win");
                    win = Integer.parseInt(jwin);
                    game_record = findViewById(R.id.recordText);
                    game_record.setText(win + "승 ");
                }
            }
            catch (JSONException e)
            {
                e.printStackTrace();
            }
        }, error -> Toast.makeText(this, "실패함. 인터넷 연결 확인", Toast.LENGTH_SHORT).show());

        RequestQueue requestQueue = Volley.newRequestQueue(game_record.this);
        requestQueue.add(stringRequest);
    }
    //최대 점수 표시
    private void GetScore()
    {
        String url = "http://192.168.0.11/show_record_score.php";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, response -> {
            try
            {
                JSONArray jsonArray = new JSONArray(response);
                for (int i = 0; i < jsonArray.length(); i++)
                {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);

                    String jscore = jsonObject.getString("score");
                    score = Integer.parseInt(jscore);
                    Max_score.setText("최대 점수: " + score + "점");
                }
            }
            catch (JSONException e)
            {
                e.printStackTrace();
            }
        }, error -> Toast.makeText(this, "실패함. 인터넷 연결 확인", Toast.LENGTH_SHORT).show());

        RequestQueue requestQueue = Volley.newRequestQueue(game_record.this);
        requestQueue.add(stringRequest);
    }

}