package com.example.myapplication;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.speech.RecognizerIntent;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class GameScreen extends AppCompatActivity {
    //타이머 변수 선언
    ProgressBar prog=null;
    Timer timer=null;
    TimerTask timerTask=null;
    //홈 버튼 변수 선언
    private Button home;
    //마이크 버튼 변수 선언
    private Button mic;
    //일시정지 버튼 변수 선언
    private Button pause;
    //게임정보 버튼 변수 선언
    private Button info;
    //단어넘기기 버튼 변수 선언
    private Button pass;
    //남은 횟수 텍스트 변수 선언
    private TextView remain_repeat;
    //남은 시간 텍스트 변수 선언
    private TextView remain_time;
    //횟수 데이터 획득 변수 선언
    public int get_times;
    //어플 점수 변수 선언
    public int app_score = 0;
    //유저 점수 변수 선언
    public int user_score = 0;
    //어플 점수 텍스트 변수
    public TextView app_score_text;
    //유저 점수 텍스트 변수
    public TextView user_score_text;
    //어플이 입력하는 단어 변수 선언
    public String app_word;
    //어플이 입력하는 단어 끝말 변수 선언
    public String app_word_last;
    //어플이 입력하는 단어 텍스트 변수 선언
    public TextView app_word_view;
    //유저가 입력하는 단어 텍스트 변수 선언
    public TextView tv;
    //유저가 입력하는 단어 끝말 텍스트 변수 선언
    public TextView tv_last;
    //유저가 입력하는 단어 변수 선언
    public String mic_str;
    //유저가 입력하는 단어 끝말 변수 선언
    public String mic_str_last;
    public String inputplayer;
    public int get_second;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_screen);
        //repetition액티비티에서 받아온 횟수 데이터 받아오는 Intent
        Intent intent = getIntent();
        //어플이 입력하는 단어 텍스트뷰
        app_word_view = findViewById(R.id.insert_view);
        //유저가 입력하는 단어 텍스트뷰
        tv = findViewById(R.id.insert_text);
        //단어 끝말 텍스트뷰
        tv_last = findViewById(R.id.last_view);
        //마이크 버튼
        mic = findViewById(R.id.Mic);
        //홈 버튼
        home = findViewById(R.id.Home);
        //일시정지 버튼
        pause = findViewById(R.id.pause);
        //게임정보 버튼
        info = findViewById(R.id.go_info);
        //단어 넘기기 버튼
        pass = findViewById(R.id.pass);
        //횟수 데이터
        get_times = intent.getIntExtra("times", 0);
        //남은시간 텍스트뷰
        remain_time = findViewById(R.id.timer);
        //남은 횟수 텍스트뷰
        remain_repeat = findViewById(R.id.remain_repeat);
        //어플 점수 텍스트뷰
        app_score_text = findViewById(R.id.app_point);
        //유저 점수 텍스트뷰
        user_score_text = findViewById(R.id.user_point);
        //남은 횟수 텍스트
        remain_repeat.setText("남은횟수:"+get_times+"");
        //남은시간 프로그레스바
        prog=(ProgressBar) findViewById(R.id.time_bar);
        //타이머 함수
        initProg();
        //타이머 시작
        startTimerThread();
        //첫 단어 불러오기
        GetWord();
        //단어 넘기기 버튼 작동
        pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(GameScreen.this);
                //타이머 멈춤
                stopTimer();
                builder.setMessage("단어를 넘기겠습니까?\n횟수가 소모되고, 점수를 얻지 못합니다.");
                builder.setTitle("단어 넘기기")
                        .setCancelable(false)
                        //Yes버튼을 눌렀을 때 작동
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int i) {
                                //남은 횟수가 0이면 게임결과 화면으로 이동
                                if(get_times == 0){
                                    Intent intent = new Intent(GameScreen.this, result_screen.class);
                                    if(user_score > app_score){
                                        intent.putExtra("result", "승리!!");
                                        insert_record1();
                                    }
                                    else{
                                        intent.putExtra("result", "패배...");
                                        insert_record2();
                                    }
                                    startActivity(intent);
                                }
                                //횟수가 남아있으면 단어를 불러온다
                                else {
                                    //단어 불러오기
                                    GetWord();
                                    app_score = app_score + 100;
                                    app_score_text.setText(app_score+"");
                                    //타이머 시작
                                    initProg();
                                    startTimerThread();
                                    //횟수를 1 차감한다
                                    get_times--;
                                    remain_repeat.setText("남은횟수:" + get_times + "");
                                }
                            }
                        })
                        //No버튼을 눌렀을때 작동
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int i) {
                                //타이머를 다시 시작한다.
                                startTimerThread();
                                dialog.cancel();
                            }
                        });
                AlertDialog alert = builder.create();
                alert.setTitle("단어 넘기기");
                alert.show();
            }
        });
        //일시정지 버튼 작동
        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(GameScreen.this);
                //타이머 정지
                stopTimer();
                builder.setTitle("일시정지")
                        .setCancelable(false)
                        //재개버튼을 눌렀을 때 작동
                        .setPositiveButton("재개", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int i) {
                                //타이머 시작
                                startTimerThread();
                                dialog.cancel();
                            }
                        });
                AlertDialog alert = builder.create();
                alert.setTitle("일시정지");
                alert.show();
            }
        });
        //게임정보 버튼 작동
        info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //게임정보 화면으로 넘어간다
                Intent intent = new Intent(GameScreen.this, game_info.class);
                startActivity(intent);
            }
        });
        //홈 버튼 작동
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(GameScreen.this);
                stopTimer();
                //게임종료 확인창
                builder.setMessage("게임을 중단하겠습니까?");
                builder.setTitle("게임중단 알림창")
                        .setCancelable(false)
                        //Yes버튼 -> 홈화면으로 이동
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int i) {
                                initialize();
                                Intent intent1 = new Intent(GameScreen.this, MainActivity.class);
                                startActivity(intent1);
                            }
                        })
                        //No버튼 -> 게임종료 버튼 액션 취소
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int i) {
                                startTimerThread();
                                dialog.cancel();
                            }
                        });
                AlertDialog alert = builder.create();
                alert.setTitle("게임중단 알림창");
                alert.show();
            }
        });
        //마이크 버튼 작동
        mic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //마이크 함수 호출
                VoiceTask voiceTask = new VoiceTask();
                voiceTask.execute();
            }
        });
    }
    //마이크 함수
    public class VoiceTask extends AsyncTask<String, Integer, String> {
        String str = null;

        @Override
        protected String doInBackground(String... params) {
            // TODO Auto-generated method stub
            try {
                getVoice();
            } catch (Exception e) {
                // TODO: handle exception
            }
            return str;
        }

        @Override
        protected void onPostExecute(String result) {
            try {

            } catch (Exception e) {
                Log.d("onActivityResult", "getImageURL exception");
            }
        }
    }

    private void getVoice() {

        Intent intent = new Intent();
        intent.setAction(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);

        String language = "ko-KR";

        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, language);
        startActivityForResult(intent, 2);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {

            ArrayList<String> results = data
                    .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);

            mic_str = results.get(0);
            boolean bool = mic_str.startsWith(app_word_last);
            mic_str_last = mic_str.substring(mic_str.length() - 1);
            if(mic_str.length()>1){
                if(bool) {
                    pass.setEnabled(false);
                    mic.setEnabled(false);
                    int minimumValue = 1000;
                    int maximumValue = 60000;

                    Random random = new Random();
                    int randomValue = random.nextInt(maximumValue - minimumValue + 1) + minimumValue;
                    tv.setText("유저: " + mic_str);
                    tv_last.setText("끝말: " + mic_str_last);
                    //유저 점수 = (입력한 단어 길이 X 10) + 남은시간
                    user_score = user_score + (mic_str.length() * 10) + get_second;
                    user_score_text.setText(user_score+"");
                    insert_user_data();
                    stopTimer();
                    initProg();
                    startTimerThread();//타이머 시작
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            GetData();
                        }
                    }, randomValue); //딜레이 타임 조절
//                    GetData();
                }
                else {
                    Toast.makeText(this, "끝말이 일치하지 않습니다. 다시 입력해주세요", Toast.LENGTH_SHORT).show();
                }
            }
            else{
                Toast.makeText(this, "두글자 이상의 단어를 입력하세요", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void GetData()
    {
        String url = "http://192.168.0.11/get_word.php?word=" + mic_str_last;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, response -> {
            try
            {
                JSONArray jsonArray = new JSONArray(response);
                for (int i = 0; i < jsonArray.length(); i++)
                {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);

//                    app_word = jsonObject.getString("word");
//                    app_word_last = app_word.substring(app_word.length() - 1);
//                    app_word_view.setText(app_word);
//                    tv_last.setText("끝말: " + app_word_last);
                    if(get_times == 0){
                        Intent intent = new Intent(GameScreen.this, result_screen.class);
                        if(user_score > app_score){
                            intent.putExtra("result", "승리!!");
                            insert_record1();
                        }
                        else{
                            intent.putExtra("result", "패배...");
                            insert_record2();
                        }
                        startActivity(intent);
                    }
                    else {
//                        stopTimer();
//                        initProg();
//                        startTimerThread();//타이머 시작
                        app_word = jsonObject.getString("word");
                        app_word_last = app_word.substring(app_word.length() - 1);
                        app_word_view.setText("어플: " + app_word);
                        tv_last.setText("끝말: " + app_word_last);
                        //어플 점수 = (입력한 단어 길이 X 10) + 남은시간
                        app_score = app_score + (app_word.length() * 10) + get_second;
                        app_score_text.setText(app_score+"");
                        get_times--;
                        remain_repeat.setText("남은횟수:" + get_times + "");
                        insert_app_data();
                        stopTimer();
                        initProg();
                        startTimerThread();//타이머 시작
                        pass.setEnabled(true);
                        mic.setEnabled(true);
                    }
                }
            }
            catch (JSONException e)
            {
                e.printStackTrace();
            }
        }, error -> Toast.makeText(this, "실패함. 인터넷 연결 확인", Toast.LENGTH_SHORT).show());

        RequestQueue requestQueue = Volley.newRequestQueue(GameScreen.this);
        requestQueue.add(stringRequest);
    }
    private void GetWord()
    {
        String url = "http://192.168.0.11/firstword.php?";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, response -> {
            try
            {
                JSONArray jsonArray = new JSONArray(response);
                for (int i = 0; i < jsonArray.length(); i++)
                {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);

                    app_word = jsonObject.getString("word");
                    app_word_last = app_word.substring(app_word.length() - 1);
                    app_word_view.setText("어플: " + app_word);
                    tv_last.setText("끝말: " + app_word_last);
                    insert_app_data();
                }
            }
            catch (JSONException e)
            {
                e.printStackTrace();
            }
        }, error -> Toast.makeText(this, "실패함. 인터넷 연결 확인", Toast.LENGTH_SHORT).show());

        RequestQueue requestQueue = Volley.newRequestQueue(GameScreen.this);
        requestQueue.add(stringRequest);
    }

    private void insert_user_data()
    {
        String url = "http://192.168.0.11/insert.php?word=" + mic_str + "&inputplayer=유저";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, response -> {
            try
            {

            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }, error -> Toast.makeText(this, "실패함. 인터넷 연결 확인", Toast.LENGTH_SHORT).show());

        RequestQueue requestQueue = Volley.newRequestQueue(GameScreen.this);
        requestQueue.add(stringRequest);
    }

    private void insert_app_data()
    {
        String url = "http://192.168.0.11/insert.php?word=" + app_word + "&inputplayer=어플";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, response -> {
            try
            {

            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }, error -> Toast.makeText(this, "실패함. 인터넷 연결 확인", Toast.LENGTH_SHORT).show());

        RequestQueue requestQueue = Volley.newRequestQueue(GameScreen.this);
        requestQueue.add(stringRequest);
    }

    private void insert_record1()
    {
        String url = "http://192.168.0.11/record.php?result=승" + "&score=" + user_score;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, response -> {
            try
            {

            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }, error -> Toast.makeText(this, "실패함. 인터넷 연결 확인", Toast.LENGTH_SHORT).show());

        RequestQueue requestQueue = Volley.newRequestQueue(GameScreen.this);
        requestQueue.add(stringRequest);
    }

    private void insert_record2()
    {
        String url = "http://192.168.0.11/record.php?result=패" + "&score=" + user_score;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, response -> {
            try
            {

            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }, error -> Toast.makeText(this, "실패함. 인터넷 연결 확인", Toast.LENGTH_SHORT).show());

        RequestQueue requestQueue = Volley.newRequestQueue(GameScreen.this);
        requestQueue.add(stringRequest);
    }

    private void initialize()
    {
        String url = "http://192.168.0.11/Initialize.php";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, response -> {
            try
            {

            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }, error -> Toast.makeText(this, "실패함. 인터넷 연결 확인", Toast.LENGTH_SHORT).show());

        RequestQueue requestQueue = Volley.newRequestQueue(GameScreen.this);
        requestQueue.add(stringRequest);
    }

    public void stopTimer(){
        if(timerTask != null){
            timerTask.cancel();//타이머task를 timer큐에서 지워버린다.
            timerTask=null;
        }
        if(timer != null){
            timer.cancel();//스케쥴task와 타머를 취소한다.
            timer.purge();//task큐의 모든 task를 제거한다.
            timer=null;
        }
    }

    //프로그레스바를 초기화하는 함수
    public void initProg(){
        prog.setMax(60);//최대값 60초 지정
        prog.setProgress(60);//현재값 60초 지정
    }
    public void startTimerThread(){
        timerTask = new TimerTask() {//timerTask는 timer가 일할 내용을 기록하는 객체
            @Override
            public void run() {
                //TODO Auto-generated method stub
                //이곳에 timer가 동작할 task를 작성
                decreaseBar();//timer가 동작할 내용을 갖는 함수 호출
            }
        };
        timer=new Timer();//timer 생성
        timer.schedule(timerTask,0,1000);//timerTask라는 일을 갖는 timer를 0초딜레이로 1000ms마다 실행
    }
    public void decreaseBar(){
        runOnUiThread(//프로그레스바는 ui에 해당하므로 runOnUiThread로 컨트롤해야 한다.
                new Runnable() {//thread구동과 마찬가지로 Runnable을 써준다.
                    @Override
                    public void run() {//run을 해준다. 그러나 일반 thread처럼 .start()를 해줄 필요는 없다.
                        //TODO Auto-generated method stub
                        get_second=prog.getProgress();
                        if(get_second>0){
                            get_second=get_second-1;
                            remain_time.setText("남은시간:"+get_second+"초");
                        }
                        else if(get_second==0){
                            if(get_times == 0){
                                Intent intent = new Intent(GameScreen.this, result_screen.class);
                                if(user_score > app_score){
                                    intent.putExtra("result", "승리!!");
                                    insert_record1();
                                }
                                else{
                                    intent.putExtra("result", "패배...");
                                    insert_record2();
                                }
                                startActivity(intent);
                            }
                            else {
                                GetWord();
                                app_score = app_score + (app_word.length() * 10) + get_second;
                                app_score_text.setText(app_score+"");
                                get_times--;
                                remain_repeat.setText("남은횟수:" + get_times + "");
                            }
                            get_second=60;
                            remain_time.setText("남은시간:"+get_second+"");
                        }
                        prog.setProgress(get_second);
                    }
                }
        );
    }
//    private void GetData()
//    {
//        String url = "http://192.168.0.11/count.php?word=" + mic_str_last;
//        app_word.setText(url);
//    }

}
