package com.example.a5teamproject;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

//날씨
public class Weather extends AppCompatActivity {

    TextView dateView;
    TextView cityView;
    ImageView weatherView;
    TextView tempView, tempView2;
    Button site;
    String city; //도시
    static RequestQueue requestQueue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        dateView = findViewById(R.id.dateView);
        cityView = findViewById(R.id.cityView);
        weatherView = findViewById(R.id.weatherView);
        tempView = findViewById(R.id.tempView);
        tempView2 = findViewById(R.id.tempView2);
        site=findViewById(R.id.btn_show);
        //다이얼로그 메뉴
        AlertDialog.Builder dialogM = new AlertDialog.Builder(this, android.R.style.Theme_DeviceDefault_Light_Dialog_Alert);
        //플로팅 메뉴
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CharSequence menu[] = new CharSequence[] {"서울", "천안", "부산", "인천", "대전", "대구", "아산"}; //메뉴 목록
                dialogM.setTitle("메뉴선택").setSingleChoiceItems(menu,-1,new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        switch (i) {
                            case 0:
                                city = "Seoul";
                                break;
                            case 1:
                                city = "Cheonan";
                                break;
                            case 2:
                                city = "Busan";
                                break;
                            case 3:
                                city = "Incheon";
                                break;
                            case 4:
                                city = "Daejeon";
                                break;
                            case 5:
                                city = "Daegu";
                                break;
                            case 6:
                                city = "Asan";
                                break;
                        }
                    }
                })
                        .show();
            }
        });
        //이미지 버튼 클릭 시
        ImageButton button = findViewById(R.id.imageButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //시간데이터와 날씨데이터 활용
                CurrentCall(city);
            }
        });
        //기상청 사이트 버튼 클릭 시
        site.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.kma.go.kr/kma/"));
                startActivity(intent);
            }
        });
        //volley를 쓸 때 큐가 비어있으면 새로운 큐 생성하기
        if(requestQueue == null){
            requestQueue = Volley.newRequestQueue(getApplicationContext());
        }
    }
    //시간데이터와 날씨데이터 활용
    private void CurrentCall(String city){
        //메뉴에 있는 도시 활용
        String url = "http://api.openweathermap.org/data/2.5/weather?q="+city+"&appid=062ed53941f97b4bc2b417f9476297ee";
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(String response) {
                try {
                    //System의 현재 시간(년,월,일,시,분,초까지)가져오고 Date로 객체화함
                    long now = System.currentTimeMillis();
                    Date date = new Date(now);
                    //년, 월, 일 형식으로. 시,분,초 형식으로 객체화하여 String에 형식대로 넣음
                    SimpleDateFormat simpleDateFormatDay = new SimpleDateFormat("yyyy-MM-dd");
                    SimpleDateFormat simpleDateFormatTime = new SimpleDateFormat("HH:mm:ss");
                    String getDay = simpleDateFormatDay.format(date);
                    String getTime = simpleDateFormatTime.format(date);

                    //getDate에 개행을 포함한 형식들을 넣은 후 dateView에 text설정
                    String getDate = getDay + "\n" + getTime;
                    dateView.setText(getDate);

                    //api로 받은 파일 jsonobject로 새로운 객체 선언
                    JSONObject jsonObject = new JSONObject(response);

                    //도시 키값 받기
                    String city = jsonObject.getString("name");
                    cityView.setText(city);


                    //날씨 키값 받기
                    JSONArray weatherJson = jsonObject.getJSONArray("weather");
                    JSONObject weatherObj = weatherJson.getJSONObject(0);

                    String weather = weatherObj.getString("description");

                    //키값을 이미지로 변경
                    if(weather.equals("clear sky")){
                        weatherView.setImageResource(R.drawable.sunny);
                    }
                   else if(weather.equals("few clouds")){
                        weatherView.setImageResource(R.drawable.few_clouds);
                    }
                    else if(weather.equals("scattered clouds")){
                        weatherView.setImageResource(R.drawable.scatter_clouds);
                    }
                    else if(weather.equals("broken clouds")){
                        weatherView.setImageResource(R.drawable.broken_clouds);
                    }
                    else if(weather.equals("shower rain")){
                        weatherView.setImageResource(R.drawable.shower_rain);
                    }
                    else if(weather.equals("rain")){
                        weatherView.setImageResource(R.drawable.rain);
                    }
                    else if(weather.equals("thunderstorm")){
                        weatherView.setImageResource(R.drawable.thunderstorm);
                    }
                    else if(weather.equals("snow")){
                        weatherView.setImageResource(R.drawable.snow);
                    }
                    else if(weather.equals("mist")){
                        weatherView.setImageResource(R.drawable.mist);
                    }
                    else if(weather.equals("overcast clouds")){
                        weatherView.setImageResource(R.drawable.broken_clouds);
                    }
                    else{
                        //API에서 제공하지 않는 날씨 그림이 있을 경우
                        weatherView.setImageResource(R.drawable.baseline_build_black_24dp);
                    }

                    //기온 키값 받기
                    JSONObject tempK = new JSONObject(jsonObject.getString("main"));

                    //기온 받고 켈빈 온도를 섭씨 온도로 변경
                    //최저, 최고 기온
                    double tempDo = (Math.round((tempK.getDouble("temp_min")-273.15)*100)/100.0); //최저
                    double tempDo2 = (Math.round((tempK.getDouble("temp_max")-273.15)*100)/100.0); //최고
                    tempView.setText(tempDo +  "°C");
                    tempView2.setText(tempDo2 +  "°C");

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();

                return params;
            }
        };

        request.setShouldCache(false);
        requestQueue.add(request);
    }
}