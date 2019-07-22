package com.iot.instagramcolone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class MainActivity extends AppCompatActivity {

    EditText editTextName,editTextPasw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextName = findViewById(R.id.editTextName);
        editTextPasw = findViewById(R.id.editTextPassw);

        userHatırla();
    }

    public void kayitYap(View view){
        ParseUser user = new ParseUser();
        user.setUsername(editTextName.getText().toString());
        user.setPassword(editTextPasw.getText().toString());
        user.signUpInBackground(new SignUpCallback() {
            @Override
            public void done(ParseException e) {
                if(e != null){
                    Toast.makeText(MainActivity.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(MainActivity.this, "Kayıt Başarılı", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    public void loginOl(View view){
        ParseUser.logInInBackground(editTextName.getText().toString(), editTextPasw.getText().toString(),
                new LogInCallback() {
                    @Override
                    public void done(ParseUser user, ParseException e) {
                        if(e != null){
                            Toast.makeText(MainActivity.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(MainActivity.this, "Giris Basarılı", Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent(MainActivity.this,ListeActivity.class);
                            startActivity(intent);
                        }
                    }
                });

    }
    public void userHatırla(){
        ParseUser user = ParseUser.getCurrentUser();
            if(user != null){
                Intent intent = new Intent(MainActivity.this,ListeActivity.class);
                startActivity(intent);

            }


    }
}
