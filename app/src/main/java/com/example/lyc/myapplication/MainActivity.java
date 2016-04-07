package com.example.lyc.myapplication;
import android.app.Activity;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.File;

public class MainActivity extends Activity {

    private EditText et_path;

    private MediaPlayer mediaPlayer;

    private Button bt_play,bt_pause,bt_stop,bt_replay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        et_path = (EditText) findViewById(R.id.et_path);
        bt_play = (Button) findViewById(R.id.bt_play);
        bt_pause = (Button) findViewById(R.id.bt_pause);
        bt_stop = (Button) findViewById(R.id.bt_stop);
        bt_replay = (Button) findViewById(R.id.bt_replay);
    }

    public void play(View view) {
        String filepath = et_path.getText().toString().trim();
        File file = new File(filepath);
        if(file.exists()){
            try {
                mediaPlayer = new MediaPlayer();
                mediaPlayer.setDataSource(filepath);
                mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                mediaPlayer.prepare();
                mediaPlayer.start();
                bt_play.setEnabled(false);
                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        bt_play.setEnabled(true);
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
                //Toast.makeText(this,"播放失败",0).show();
            }
        }else{
           // Toast.makeText(this, "文件不存在,请检查文件路径", 0).show();
        }
    }

    public void pause(View view) {
        if("继续".equals(bt_pause.getText().toString())){
            mediaPlayer.start();
            bt_pause.setText("暂停");
            return;
        }
        if(mediaPlayer!=null&&mediaPlayer.isPlaying()){
            mediaPlayer.pause();
            bt_pause.setText("继续");
        }
    }

    public void stop(View view) {
        if(mediaPlayer!=null&&mediaPlayer.isPlaying()){
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
        bt_pause.setText("暂停");
        bt_play.setEnabled(true);
    }

    public void replay(View view) {
        if(mediaPlayer!=null&&mediaPlayer.isPlaying()){
            mediaPlayer.seekTo(0);
        }else{
            play(view);
        }
        bt_pause.setText("继续");
    }
}
