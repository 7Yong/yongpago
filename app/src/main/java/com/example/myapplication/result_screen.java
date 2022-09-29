package com.example.myapplication;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
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
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class result_screen extends AppCompatActivity {
    //홈버튼 선언
    private Button home;
    //게임 결과 스트링 선언
    private String get_result;
    //게임 결과 텍스트 선언
    private TextView result_text;
    //서버 아이피 선언
    private static String IP_ADDRESS = "192.168.0.11";
    //태그 선언
    private static String TAG = "myapplication";
    //json데이터를 받을 배열 선언
    private ArrayList<WordData> mArrayList;
    //리스트 어댑터 선언
    private WordAdapter mAdapter;
    //리사이클러뷰를 활용해 리스트에 표시
    private RecyclerView mRecyclerView;
    //json 스트링 선언
    private String mJsonString;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_screen);
        //리사이클러뷰 리스트 활성
        mRecyclerView = (RecyclerView) findViewById(R.id.listView_main_list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        //json데이터 받을 배열 활성
        mArrayList = new ArrayList<>();
        //리스트 어댑터 활성
        mAdapter = new WordAdapter(this, mArrayList);
        mRecyclerView.setAdapter(mAdapter);
        //결과보기 버튼 활성
        Button button_all = (Button) findViewById(R.id.show_result);
        button_all.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //리스트 초기화
                mArrayList.clear();
                //어댑터로 받아온 데이터로 변경
                mAdapter.notifyDataSetChanged();
                //json 데이터를 받아올 함수
                GetData task = new GetData();
                task.execute( "http://" + IP_ADDRESS + "/result.php", "");
            }
        });
        //게임 결과를 GameScreen에서 받아옴
        Intent intent = getIntent();
        get_result = intent.getStringExtra("result");
        result_text = findViewById(R.id.game_result);
        result_text.setText(get_result);
        //홈버튼 활성
        home = findViewById(R.id.Home);
        home.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                AlertDialog.Builder builder = new AlertDialog.Builder(result_screen.this);
                builder.setMessage("메인화면으로 가시겠습니까?");
                builder.setTitle("메인화면 알림창")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int i) {
                                initialize();
                                Intent intent5 = new Intent(result_screen.this , MainActivity.class);
                                startActivity(intent5);
                            }
                        })
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
    //메인화면으로 돌아가면 게임데이터 초기화
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

        RequestQueue requestQueue = Volley.newRequestQueue(result_screen.this);
        requestQueue.add(stringRequest);
    }
    //직전에 진행한 게임 데이터를 불러오는 함수
    private class GetData extends AsyncTask<String, Void, String> {

        ProgressDialog progressDialog;
        String errorString = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = ProgressDialog.show(result_screen.this,
                    "Please Wait", null, true, true);
        }


        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            progressDialog.dismiss();
            Log.d(TAG, "response - " + result);

            if (result == null){

            }
            else {

                mJsonString = result;
                showResult();
            }
        }


        @Override
        protected String doInBackground(String... params) {

            String serverURL = params[0];
            String postParameters = params[1];


            try {

                URL url = new URL(serverURL);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();


                httpURLConnection.setReadTimeout(5000);
                httpURLConnection.setConnectTimeout(5000);
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoInput(true);
                httpURLConnection.connect();


                OutputStream outputStream = httpURLConnection.getOutputStream();
                outputStream.write(postParameters.getBytes("UTF-8"));
                outputStream.flush();
                outputStream.close();


                int responseStatusCode = httpURLConnection.getResponseCode();
                Log.d(TAG, "response code - " + responseStatusCode);

                InputStream inputStream;
                if(responseStatusCode == HttpURLConnection.HTTP_OK) {
                    inputStream = httpURLConnection.getInputStream();
                }
                else{
                    inputStream = httpURLConnection.getErrorStream();
                }


                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                StringBuilder sb = new StringBuilder();
                String line;

                while((line = bufferedReader.readLine()) != null){
                    sb.append(line);
                }

                bufferedReader.close();

                return sb.toString().trim();


            } catch (Exception e) {

                Log.d(TAG, "GetData : Error ", e);
                errorString = e.toString();

                return null;
            }

        }
    }

    //게임 데이터를 json 형식으로 받아오는 함수
    private void showResult(){

        String TAG_JSON="root";
        String TAG_ID = "idProgress";
        String TAG_WORD = "Word";
        String TAG_INPUTPLAYER ="Inputplayer";


        try {
            JSONObject jsonObject = new JSONObject(mJsonString);
            JSONArray jsonArray = jsonObject.getJSONArray(TAG_JSON);

            for(int i=0;i<jsonArray.length();i++){

                JSONObject item = jsonArray.getJSONObject(i);

                String idProgress = item.getString(TAG_ID);
                String Word = item.getString(TAG_WORD);
                String Inputplayer = item.getString(TAG_INPUTPLAYER);

                WordData wordData = new WordData();

                wordData.setMember_idProgress(idProgress);
                wordData.setMember_Word(Word);
                wordData.setMember_Inputplayer(Inputplayer);

                mArrayList.add(wordData);
                mAdapter.notifyDataSetChanged();
            }



        } catch (JSONException e) {

            Log.d(TAG, "showResult : ", e);
        }

    }
}