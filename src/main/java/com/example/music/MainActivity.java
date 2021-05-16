package com.example.music;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    Button btn_play;
    Button btn_stop;


    MediaPlayer mediaPlayer;
    //활동 종료 시점일때 여기 실행
    @Override
    protected void onDestroy() {
        super.onDestroy();

        if(mediaPlayer!=null){

            mediaPlayer.release();
            mediaPlayer=null;

        }




    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_play = findViewById(R.id.btn_play);
        btn_stop = findViewById(R.id.btn_stop);

        //재생
        btn_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mediaPlayer = MediaPlayer.create(MainActivity.this,R.raw.m);

                mediaPlayer.start();
            }
        });


        //멈춰!
        btn_stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(mediaPlayer.isPlaying()){
                    mediaPlayer.stop();
                    mediaPlayer.reset();
                }
            }
        });












    }
}


