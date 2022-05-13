package com.rcoemen.heartbeatmonitoring;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Objects;

public class PlayActivity extends AppCompatActivity {
    String fileName;
    TextView fName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.play_activity);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        Objects.requireNonNull(getSupportActionBar()).hide();

        fileName = getIntent().getStringExtra("name");
        fName = findViewById(R.id.fileName);



        //Toast.makeText(PlayActivity.this, fileName, Toast.LENGTH_SHORT).show();

        fName.setText(fileName);


    }

    


}