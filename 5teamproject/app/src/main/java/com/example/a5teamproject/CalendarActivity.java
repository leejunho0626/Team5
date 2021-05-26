package com.example.a5teamproject;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;


public class CalendarActivity extends AppCompatActivity {

    GridView gridView;
    TextView textView, user_Text;
    GridAdapter adt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calview);


        gridView = findViewById(R.id.gridView); //그리드뷰 객체 참조
        adt = new GridAdapter(this); //어댑터 객체 생성
        gridView.setAdapter(adt); //그리드뷰에 어댑터 설정
        textView = findViewById(R.id.monthText);
        user_Text = findViewById(R.id.userText);
        setMonthText();//년,월 표시

        String getText = user_Text.getText().toString();

        Button previous = findViewById(R.id.previous);
        Button next = findViewById(R.id.next);

        Button monthPrevious = findViewById(R.id.previous);
        Button monthNext = findViewById(R.id.next);

        // 뒤로가기 버튼
        monthPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adt.setPreviousMonth();
                adt.notifyDataSetChanged();
                setMonthText();
            }
        });

        // 앞으로 가기 버튼
        monthNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adt.setNextMonth();
                adt.notifyDataSetChanged();
                setMonthText();
            }
        });

        CharSequence info[] = new CharSequence[] {"사진 추가", "글작성", "★" };
        AlertDialog.Builder dialog = new AlertDialog.Builder(this, android.R.style.Theme_DeviceDefault_Light_Dialog_Alert);
        //날짜 선택
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(CalendarActivity.this,"클릭 성공", Toast.LENGTH_SHORT).show();
                user_Text.setText("테스트입니다.");
                dialog .setTitle("메뉴 선택(글 표시: 뒤로가기버튼)")
                        .setSingleChoiceItems(info, -1, new DialogInterface.OnClickListener(){
                            @Override
                            public void onClick(DialogInterface dialog, int i) {
                                switch (i)
                                {
                                    case 0:
                                        Toast.makeText(getApplicationContext(), "갤러리로 이동", Toast.LENGTH_LONG).show();
                                        Intent intent= new Intent(Intent.ACTION_PICK);
                                        intent.setType("image/*");
                                        startActivityForResult(intent,10);
                                        break;
                                    case 1:
                                        Toast.makeText(getApplicationContext(), "글 작성화면으로 이동", Toast.LENGTH_LONG).show();
                                        Intent intent1 = new Intent(CalendarActivity.this, Write.class);
                                        startActivity(intent1);
                                        break;
                                    case 2:
                                        Toast.makeText(getApplicationContext(), "즐겨찾기 화면으로 이동", Toast.LENGTH_LONG).show();
                                        break;
                                }
                            }
                        })
                        .show();
            }
        });

    }

    //메뉴바 설정
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.favorites:
                Toast.makeText(getApplicationContext(), "즐겨찾기 버튼 클릭됨", Toast.LENGTH_LONG).show();
                return true;
            case R.id.weather:
                Toast.makeText(getApplicationContext(), "날씨 정보 버튼 클릭됨", Toast.LENGTH_LONG).show();
                return true;
            case R.id.bgm:
                Toast.makeText(getApplicationContext(), "BGM 버튼 클릭됨", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(CalendarActivity.this, Bgm.class);
                startActivity(intent);
                return true;
        }
        return true;
    }
    //년,월 표시
    public void setMonthText(){
        int curYear = adt.getCurYear();
        int curMonth = adt.getCurMonth();
        textView.setText(curYear+"년 "+(curMonth+1)+"월");
    }
}