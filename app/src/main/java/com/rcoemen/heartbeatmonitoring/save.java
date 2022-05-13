package com.rcoemen.heartbeatmonitoring;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContextWrapper;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;
import java.io.File;
import java.util.Locale;

public class save extends AppCompatActivity {


    StorageReference storage ;
    EditText editText;
    TextView status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_save);

        editText=findViewById(R.id.filename);
        status=findViewById(R.id.status);

    }



    @RequiresApi(api = Build.VERSION_CODES.O)
    public  void  upload(View v){

        String name=editText.getText().toString();

        if(name.trim().isEmpty()) {
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
            LocalDateTime now = LocalDateTime.now();
            name=dtf.format(now);
            name=name.replace("/","_");
            name=name.replace(":","_");
            name=name.replace(" ","_");

        }

        storage = FirebaseStorage.getInstance().getReference("AUDIO").child(name);

        ContextWrapper contextWrapper = new ContextWrapper(getApplicationContext());
        File music = contextWrapper.getExternalFilesDir(Environment.DIRECTORY_MUSIC);
        File file = new File(music, "Heartbeat" + ".aac");


        // Create file metadata including the content type
        StorageMetadata metadata = new StorageMetadata.Builder()
                .setContentType("audio/aac")
                .setCustomMetadata("fileName", name)
                .build();


        String finalName = name;
        storage.putFile(Uri.fromFile(file),metadata).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                if(task.isSuccessful()){
                    storage.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            String temp=uri.toString();
                            FirebaseDatabase.getInstance().getReference("AudioFiles").child(finalName).setValue(temp).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        status.setText("File Successfully Uploaded");
                                        Toast.makeText( save.this, "File Successfully Uploaded", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });

                        }
                    });
                }

            }
        });





    }


}