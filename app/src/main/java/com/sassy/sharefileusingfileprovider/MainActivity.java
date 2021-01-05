package com.sassy.sharefileusingfileprovider;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.core.content.res.ResourcesCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.FileUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private String img1,img2,img3;
    //private File audio1 = null;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);

        /*img1 = createImageOnSDCard(R.drawable.aa);
        img2 = createImageOnSDCard(R.drawable.bb);
        img3 = createImageOnSDCard(R.drawable.cc);*/

        String filename = "aa";
        img1 = createImageOnSDCard(getResources().getIdentifier(filename , "drawable", getPackageName()));
        String filename1 = "bb";
        img2 = createImageOnSDCard(getResources().getIdentifier(filename1 , "drawable", getPackageName()));
        String filename2 = "cc";
        img3 = createImageOnSDCard(getResources().getIdentifier(filename2 , "drawable", getPackageName()));

       // saveAudiosToPhone();

        Button btnText = findViewById(R.id.btnText);
        Button btnImage = findViewById(R.id.btnImage);
        Button btnMultiImage = findViewById(R.id.btnMultiImage);
        Button btnOneAudio = findViewById(R.id.btnOneAudio);
        Button btnMultiAudio = findViewById(R.id.btnMultiAudio);

        btnText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onShareText();
            }
        });

        btnImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onShareImage();
            }
        });

        btnMultiImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onShareMultiImage();
            }
        });

        btnOneAudio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onShareOneAudio();
            }
        });

        btnMultiAudio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onShareMultiAudio();
            }
        });

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void saveAudiosToPhone() {
       /* String downloadpath = Environment.
                getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath()+"/";
        InputStream is = getResources().openRawResource(R.raw.playdate);
        audio1 = new File(downloadpath + "first_audio.mp3");
        try {
            Files.copy(is, audio1.toPath(), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }*/
    }


    private String createImageOnSDCard(int resID){

        Toast.makeText(MainActivity.this, "Hello - "+resID , Toast.LENGTH_SHORT).show();

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),resID);
        String path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) +
                "/" + resID + ".jpg" ;
        File file = new File(path);
        OutputStream out = null;
        try {
            out = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG,100,out);
            out.flush();
            out.close();
        }
         catch (IOException e) {
            e.printStackTrace();
        }

        return file.getPath();
    }

    private void onShareText() {
        Intent i = new Intent();
        i.setAction(Intent.ACTION_SEND);
        i.setType("text/plain");
        i.putExtra(Intent.EXTRA_TEXT,"I am sending text...");
        startActivity( Intent.createChooser(i,"Sharing....!!!"));
    }

    private void onShareImage() {
        Uri path = FileProvider.getUriForFile(this,
                "com.sassy.sharefileusingfileprovider",
                new File(img1));
       /* Uri path = FileProvider.getUriForFile(Objects.requireNonNull(getApplicationContext()),
                BuildConfig.APPLICATION_ID + ".provider", new File(img2));*/
        Intent i = new Intent();
        i.setAction(Intent.ACTION_SEND);
        i.setType("image/*");
        i.putExtra(Intent.EXTRA_TEXT,"I am sending an image...");
        i.putExtra(Intent.EXTRA_STREAM,path);
        i.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        startActivity(Intent.createChooser(i,"Sharing....!!!"));
    }

    private void onShareMultiImage() {
        ArrayList<Uri> al = new ArrayList<>();
        al.add(FileProvider.getUriForFile(this,
                "com.sassy.sharefileusingfileprovider",new File(img1)));
        al.add(FileProvider.getUriForFile(this,
                "com.sassy.sharefileusingfileprovider",new File(img2)));
        al.add(FileProvider.getUriForFile(this,
                "com.sassy.sharefileusingfileprovider",new File(img3)));
        Intent i = new Intent();
        i.setAction(Intent.ACTION_SEND_MULTIPLE);
        i.setType("image/*");
        i.putExtra(Intent.EXTRA_TEXT,"I am sending multiple images...");
        i.putParcelableArrayListExtra(Intent.EXTRA_STREAM,al);
        i.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        startActivity(Intent.createChooser(i,"Sharing....!!!"));
    }


    private void onShareOneAudio() {
       /* Uri path = FileProvider.getUriForFile(this,
                "com.sassy.sharefileusingfileprovider",
                audio1);*/

        /*Uri uricik = null;
        File soundPath = new File(getApplicationContext().getExternalFilesDir
                (Environment.DIRECTORY_DOWNLOADS).getPath());
        File newFile = new File(soundPath,"Audio1");
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
            uricik = FileProvider.getUriForFile(Objects.requireNonNull(getApplicationContext()),
                    "com.sassy.sharefileusingfileprovider", (newFile));
        }*/


       /* InputStream inputStream;
        FileOutputStream fileOutputStream;
        try {
            inputStream = getResources().openRawResource(R.raw.playdate);
            fileOutputStream = new FileOutputStream(
                    new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),
                            "sound.mp3"));

            byte[] buffer = new byte[1024];
            int length;
            while ((length = inputStream.read(buffer)) > 0) {
                fileOutputStream.write(buffer, 0, length);
            }

            inputStream.close();
            fileOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


        Intent i = new Intent();
        i.setAction(Intent.ACTION_SEND);
        i.setType("audio/*");
        i.putExtra(Intent.EXTRA_TEXT,"I am sending an audio...");
        //i.putExtra(Intent.EXTRA_STREAM,uricik);
        i.putExtra(Intent.EXTRA_STREAM,
                Uri.parse("file://" + Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
                        + "/sound.mp3" ));
        i.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        startActivity(Intent.createChooser(i,"Sharing....!!!"));*/
    }


    private void onShareMultiAudio() {
    }



}
