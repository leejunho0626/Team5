package com.example.a5teamproject;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

//BGM
public class Bgm extends AppCompatActivity {

    MediaPlayer mediaPlayer;
    Button btn_play, btn_stop;
    Button btn_play2,btn_stop2;
    Button btn_play3,btn_stop3;
    Button btn_play4,btn_stop4;
    Button btn_play5,btn_stop5;
    Button btn_back;

    //종료 될때
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mediaPlayer!= null){
            mediaPlayer.release();
            mediaPlayer=null;
        }
    }
    //버튼할당
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bgm);

        btn_play =(Button)findViewById(R.id.btn_play);
        btn_stop = (Button)findViewById(R.id.btn_stop);

        btn_play2 =(Button)findViewById(R.id.btn_play2);
        btn_stop2 = (Button)findViewById(R.id.btn_stop2);

        btn_play3 =(Button)findViewById(R.id.btn_play3);
        btn_stop3 = (Button)findViewById(R.id.btn_stop3);

        btn_play4 =(Button)findViewById(R.id.btn_play4);
        btn_stop4 = (Button)findViewById(R.id.btn_stop4);

        btn_play5 =(Button)findViewById(R.id.btn_play5);
        btn_stop5 = (Button)findViewById(R.id.btn_stop5);

        btn_back = (Button)findViewById(R.id.btn_back);

        //뒤로가기
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Bgm.this, CalendarActivity.class);
                startActivity(intent);
            }
        });

        //시작버튼
        btn_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mediaPlayer = MediaPlayer.create(Bgm.this,R.raw.ncm1);
                mediaPlayer.start();//BGM재생함수
            }
        });
        btn_play2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mediaPlayer = MediaPlayer.create(Bgm.this,R.raw.ncm2);
                mediaPlayer.start();
            }
        });

        btn_play3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mediaPlayer = MediaPlayer.create(Bgm.this,R.raw.ncm3);
                mediaPlayer.start();
            }
        });

        btn_play4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mediaPlayer = MediaPlayer.create(Bgm.this,R.raw.ncm4);
                mediaPlayer.start();
            }
        });

        btn_play5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mediaPlayer = MediaPlayer.create(Bgm.this,R.raw.ncm5);
                mediaPlayer.start();
            }
        });
        //종료버튼
        btn_stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mediaPlayer.stop();
                mediaPlayer.reset();//음악 정지시 리셋됨
            }
        });

        btn_stop2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mediaPlayer.stop();
                mediaPlayer.reset();
            }
        });

        btn_stop3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mediaPlayer.stop();
                mediaPlayer.reset();
            }
        });

        btn_stop4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mediaPlayer.stop();
                mediaPlayer.reset();
            }
        });
        btn_stop5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mediaPlayer.stop();
                mediaPlayer.reset();
            }
        });
    }
}
