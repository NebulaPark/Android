package kr.ac.induk.park.audiorecord2;

import android.app.Activity;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import java.io.IOException;

public class MainActivity extends Activity {

    private static final String LOG_TAG = "AudioRecorderTest";
    private static String filename = null;

    Button play, record;
    private MediaRecorder recorder =null;
    private MediaPlayer player =null;

    @Override
    protected void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.activity_main);
        filename = Environment.getExternalStorageDirectory().getAbsolutePath();
        filename += "/test.3gp";
        play = (Button) findViewById(R.id.play);
        record = (Button) findViewById(R.id.record);

        play.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View view) {
                if(player == null){
                    try {
                        player.setDataSource(filename);
                        player.prepare();
                        player.start();
                    }catch (IOException e){
                        Log.e(LOG_TAG, "prepare() failed");

                    }play.setText("재생 중지");
                }else {
                    player.release();
                    player =null;
                    play.setText("재생 시작");
                }
            }
        });

        record.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (recorder == null) {
                    recorder = new MediaRecorder();
                    recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
                    recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
                    recorder.setOutputFile(filename);
                    recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
                    try {
                        recorder.prepare();
                    } catch (IOException e) {
                        Log.e(LOG_TAG, "prepare() failed");
                    }
                    recorder.start();
                    record.setText("녹음 중지");
                } else {
                    recorder.stop();
                    recorder.release();
                    recorder = null;
                    record.setText("녹음 시작");
                }
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(recorder != null){
            recorder.release();
            recorder = null;
        }
        if(player != null){
            player.release();
            player =null;
        }
    }


}
