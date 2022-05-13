package com.rcoemen.heartbeatmonitoring;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        Objects.requireNonNull(getSupportActionBar()).hide();

        setContentView(R.layout.activity_main);

    }

    public void openrecord(View view){
        Intent intent=new Intent(this,record.class);
        startActivity(intent);
    }

    public void openlisten(View view){
        Intent intent=new Intent(this,listen.class);
        startActivity(intent);

    }

    public  void  opencredits(View view){

        Intent intent=new Intent(this,credits.class);
        startActivity(intent);

    }

}