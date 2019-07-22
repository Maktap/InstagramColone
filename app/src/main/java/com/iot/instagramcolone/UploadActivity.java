package com.iot.instagramcolone;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class UploadActivity extends AppCompatActivity {
    // izin isteme --->requestCode = 1000
    // resim seçme --->requestCode = 2000

    Bitmap secilenResim;
    ImageView imageViewP;
    EditText editTextComm;
    ParseObject parseObject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);

        imageViewP = findViewById(R.id.imageViewPICK);
        editTextComm  = findViewById(R.id.editText_Comment);

        parseObject = new ParseObject("POSTS");
    }


    public void izinIste(){
        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},1000);
    }

    public void resimSec(){
        Intent intent =new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI); // URI --> seçilen resmim nerede kaynağı (Seçilen Verinin YOLU)
        startActivityForResult(intent,2000);
    }

    public void imageSec(View view){
        if(ContextCompat.checkSelfPermission(this,Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            izinIste();
        }
        else{
            resimSec();
        }
    }


        @Override
        public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
            if(requestCode ==1000){
                if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){

                    resimSec();
                }
            }


            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }

        @Override//izin seçme onaylandı+resim seçme bitti--->bunun sonucunda yapılacaklar
        protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
            if(requestCode ==2000 && resultCode == RESULT_OK && data != null){

                Uri uri = data.getData();

                try {
                    secilenResim = MediaStore.Images.Media.getBitmap(this.getContentResolver(),uri);
                        imageViewP.setImageBitmap(secilenResim);


                } catch (IOException e) {
                    Toast.makeText(this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }

            }

            super.onActivityResult(requestCode, resultCode, data);
        }


    public void uploadIslemi(View view){

        String userName = ParseUser.getCurrentUser().getUsername();
        String userComment = editTextComm.getText().toString();

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        secilenResim.compress(Bitmap.CompressFormat.PNG,100,byteArrayOutputStream);
        byte[] bytesOfImage = byteArrayOutputStream.toByteArray();

        ParseFile parseFile = new ParseFile("userImage",bytesOfImage);

        parseObject.put("name",userName);
        parseObject.put("comment",userComment);
        parseObject.put("Image",parseFile);
        parseObject.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if(e != null){
                    Toast.makeText(UploadActivity.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(UploadActivity.this, "Upload Successful", Toast.LENGTH_SHORT).show();

                }
            }
        });


    }
}
