package com.example.music;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

import androidx.annotation.Nullable;

public class MusicService extends Service {

    MediaPlayer mediaPlayer;
    int[] playList;
    int[] musicList;
    int play=0;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    //초기화
    @Override
    public void onCreate() {
        super.onCreate();

        playList= new int[3];
        playList[0]= R.raw.m;
        playList[1]= R.raw.p;
        playList[2]=R.raw.g;

        for(int i=0;i<playList.length;i++)
        {
            mediaPlayer = MediaPlayer.create(this, playList[i]);
        }
        mediaPlayer.setLooping(true);//반복재생
    }
    //시작
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        mediaPlayer.start();

        return super.onStartCommand(intent, flags, startId);

    }

    //종료
    @Override
    public void onDestroy() {
        super.onDestroy();

        mediaPlayer.stop();
    }
}
