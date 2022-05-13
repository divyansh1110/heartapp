package com.rcoemen.heartbeatmonitoring;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class PlayActivity extends AppCompatActivity {
    String fileName;
    TextView currentTime, totalTime, fName;
    MediaPlayer mediaPlayer;
    SeekBar seekBar;
    Handler handler;
    ImageView playPause, pause;
    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.play_activity);
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
       // Objects.requireNonNull(getSupportActionBar()).hide();
        dialog = new ProgressDialog(PlayActivity.this);
        dialog.setTitle("Please wait");
        dialog.show();
        currentTime = findViewById(R.id.ct);
        totalTime = findViewById(R.id.tt);
        playPause = findViewById(R.id.play);
        seekBar = findViewById(R.id.seekBar);
        fName = findViewById(R.id.fileName);


        seekBar.setMax(100);
        fileName = getIntent().getStringExtra("name");

        fName.setText(fileName);
        mediaPlayer = new MediaPlayer();
        handler = new Handler();
        playPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mediaPlayer.isPlaying()) {
                    handler.removeCallbacks(updater);
                    mediaPlayer.pause();
                    playPause.setImageResource(R.drawable.ic_play);
                } else {
                    mediaPlayer.start();
                    playPause.setImageResource(R.drawable.ic_pause);
                    updateSeekbar();
                }
            }
        });

        prepareMediaPlayer();
        //Toast.makeText(PlayActivity.this, fileName, Toast.LENGTH_SHORT).show();


    }


    private void prepareMediaPlayer() {


        FirebaseDatabase.getInstance().getReference("AudioFiles").child(fileName).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot file_snap) {
                if (file_snap.exists()) {
                    try {
                        //Toast.makeText(PlayActivity.this, file_snap.getValue(String.class), Toast.LENGTH_SHORT).show();
                        mediaPlayer.setDataSource(file_snap.getValue(String.class));
                        mediaPlayer.prepare();
                        totalTime.setText(milliSecondToTime(mediaPlayer.getDuration()));
                        dialog.dismiss();
                    } catch (Exception e) {
                        Toast.makeText(PlayActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(PlayActivity.this, "Some error occurred", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    private Runnable updater = new Runnable() {
        @Override

        public void run() {
            updateSeekbar();
            long currentDuration = mediaPlayer.getCurrentPosition();
            currentTime.setText(milliSecondToTime(currentDuration));
        }

    };


    private void updateSeekbar() {
        if (mediaPlayer.isPlaying()) {
            seekBar.setProgress((int) (((float) mediaPlayer.getCurrentPosition() / mediaPlayer.getDuration()) * 100));
            handler.postDelayed(updater, 1000);
        }
    }


    private String milliSecondToTime(long milliSecond) {
        String timerString = "";
        String secondString;


        int hours = (int) (milliSecond / (1000 * 60 * 60));
        int minute = (int) (milliSecond % (1000 * 60 * 60)) / (1000 * 60);
        int seconds = (int) (milliSecond % (1000 * 60 * 60)) % (1000 * 60) / 1000;


        if (hours > 0) {
            timerString = hours + ":";
        }


        if (seconds < 10) {
            secondString = "0" + seconds;
        } else {
            secondString = "" + seconds;
        }

        timerString = timerString + minute + ":" + secondString;
        return timerString;


    }


}