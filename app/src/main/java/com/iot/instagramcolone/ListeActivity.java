package com.iot.instagramcolone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.GetDataCallback;
import com.parse.LogOutCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

public class ListeActivity extends AppCompatActivity {

        @Override
        public boolean onCreateOptionsMenu(Menu menu) {
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.menu,menu);

            return super.onCreateOptionsMenu(menu);
        }

        @Override
        public boolean onOptionsItemSelected(@NonNull MenuItem item) {

            switch (item.getItemId()){

                case R.id.listeActivity_Menu_Ekle:

                    Intent intent = new Intent(ListeActivity.this,UploadActivity.class);
                    startActivity(intent);
                    break;

                    case R.id.listeActivity_Menu_LogOut:

                    ParseUser.logOutInBackground(new LogOutCallback() {
                        @Override
                        public void done(ParseException e) {
                            if(e != null){
                                Toast.makeText(ListeActivity.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(ListeActivity.this, "Cıkıs Yapıldı", Toast.LENGTH_SHORT).show();

                                Intent intent = new Intent(ListeActivity.this,MainActivity.class);
                                startActivity(intent);
                            }
                        }
                    });

                    break;

                 default:
                     break;
            }
            return super.onOptionsItemSelected(item);
        }

        ListView listView;
        ArrayList<String> userNameFromParse;
        ArrayList<String> userCommFromParse;
        ArrayList<Bitmap> userImagFromParse;

        ArrayAdapterClass arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liste);

        listView = findViewById(R.id.listView);

        userNameFromParse = new ArrayList<>();
        userCommFromParse = new ArrayList<>();
        userImagFromParse = new ArrayList<>();

        arrayAdapter = new ArrayAdapterClass(userNameFromParse,userCommFromParse,userImagFromParse,ListeActivity.this);

        listView.setAdapter(arrayAdapter); //ArrayAdapterClass'ı,ArrayAdapter sınıfını extend ettiği için burada arrayAdapter kullanabildik

        download();
    }

    public void download(){

        ParseQuery<ParseObject> query =  ParseQuery.getQuery("POSTS");
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if(e != null){
                    Toast.makeText(ListeActivity.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }else{

                    if(objects.size()>0){

                        for(final ParseObject parseObject : objects){


                            ParseFile parseFile = (ParseFile) parseObject.get("Image");

                            parseFile.getDataInBackground(new GetDataCallback() {
                                @Override
                                public void done(byte[] data, ParseException e) {

                                    if(e == null && data != null){

                                        Bitmap bitmap = BitmapFactory.decodeByteArray(data,0,data.length);

                                        userImagFromParse.add(bitmap);
                                        userNameFromParse.add(parseObject.getString("name"));
                                        userCommFromParse.add(parseObject.getString("comment"));

                                        arrayAdapter.notifyDataSetChanged();


                                    }
                                }
                            });
                        }
                    }

                }
            }
        });


    }


}
