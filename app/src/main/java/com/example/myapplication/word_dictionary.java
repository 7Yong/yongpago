package com.example.myapplication;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.speech.RecognizerIntent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Random;

public class word_dictionary extends AppCompatActivity {
    //뒤로가기버튼 선언
    private Button back;
    //홈버튼 선언
    private Button home;
    //마이크버튼 선언
    private Button mic;
    //음성입력 단어 텍스트뷰 선언
    private TextView search_text;
    //단어정보 텍스트뷰 선언
    public TextView word_info;
    //음성입력 단어 스트링 선언
    public String mic_str;
    //검색단어 스트링 선언
    public String app_word;
    //검색단어 품사 스트링 선언
    public String app_part;
    //검색단어 뜻 스트링 선언
    public String app_mean;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word_dictionary);
        //음성입력 단어 텍스트뷰 활성
        search_text =findViewById(R.id.search_word);
        //단어정보 텍스트뷰 활성
        word_info = findViewById(R.id.word_info);
        //마이크버튼 활성
        mic = findViewById(R.id.Mic);
        //뒤로가기버튼 활성
        back = findViewById(R.id.Back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //이전화면으로 이동
                Intent intent1 = new Intent(word_dictionary.this , MainActivity.class);
                startActivity(intent1);
            }
        });
        //홈버튼 활성
        home = findViewById(R.id.Home);
        home.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                AlertDialog.Builder builder = new AlertDialog.Builder(word_dictionary.this);
                //홈버튼 확인창
                builder.setMessage("메인화면으로 가시겠습니까?");
                builder.setTitle("메인화면 알림창")
                        .setCancelable(false)
                        //Yes버튼 -> 홈화면으로 이동
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int i) {
                                Intent intent1 = new Intent(word_dictionary.this , MainActivity.class);
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
        //마이크 버튼 작동
        mic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //마이크 함수 호출
                word_dictionary.VoiceTask voiceTask = new word_dictionary.VoiceTask();
                voiceTask.execute();
            }
        });
    }
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
            //마이크로 입력한 단어 데이터
            mic_str = results.get(0);
            //마이크로 입력한 단어 표시
            search_text.setText(mic_str);
            //검색한 단어 정보 불러오기
            GetWord();
        }
    }
    //검색한 단어 정보 불러오는 함수
    private void GetWord()
    {
        String url = "http://192.168.0.11/search_word.php?word=" + mic_str;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, response -> {
            try
            {
                JSONArray jsonArray = new JSONArray(response);
                for (int i = 0; i < jsonArray.length(); i++)
                {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);

                    app_word = jsonObject.getString("word");
                    app_part = jsonObject.getString("part");
                    app_mean = jsonObject.getString("mean");
                    word_info.setText("단어: " + app_word + "\n품사: " + app_part + "\n뜻: " + app_mean);
                }
            }
            catch (JSONException e)
            {
                e.printStackTrace();
            }
        }, error -> Toast.makeText(this, "실패함. 인터넷 연결 확인", Toast.LENGTH_SHORT).show());

        RequestQueue requestQueue = Volley.newRequestQueue(word_dictionary.this);
        requestQueue.add(stringRequest);
    }
}