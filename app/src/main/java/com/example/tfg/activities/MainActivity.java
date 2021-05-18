package com.example.tfg.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.tfg.models.Users;
import com.example.tfg.R;
import com.example.tfg.prevalent.Prevalent;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import io.paperdb.Paper;

public class MainActivity extends AppCompatActivity {

    private ProgressBar loadingBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button joinNowButton = (Button) findViewById(R.id.register_btn);
        Button loginButton = (Button) findViewById(R.id.login_btn);


        Paper.init(this);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        // PARA EL SPLASH SCREEN

//        TimerTask tarea = new TimerTask() {
//            @Override
//            public void run() {
//                Intent intent = new Intent(MainActivity.this, SplashActivity.class);
//                startActivity(intent);
//                finish();
//                }
//        };
//       Timer tiempo = new Timer();
//        tiempo.schedule(tarea,4000);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        joinNowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        String UserKey = Paper.book().read(Prevalent.userKey);
        String PassKey = Paper.book().read(Prevalent.passKey);

        if (UserKey != "" && PassKey !=""){
            if (!TextUtils.isEmpty(UserKey) && !TextUtils.isEmpty(PassKey)){
                AllowAccess(UserKey, PassKey);
            }
        }
        
    }

    private void AllowAccess(final String phone,final String password) {
        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();

        RootRef.addListenerForSingleValueEvent(new ValueEventListener(){

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.child("Users").child(phone).exists()) {
                    Users userData = dataSnapshot.child("Users").child(phone).getValue(Users.class);
                    if (userData.getPhone().equals(phone)) {
                        if (userData.getPassword().equals(password)) {
                            Toast.makeText(MainActivity.this, "Login con éxito", Toast.LENGTH_SHORT).show();
                           // loadingBar.dismiss();

                            Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                            Prevalent.usuarioOnline = userData;
                            startActivity(intent);
                        }else{
                           // loadingBar.dismiss();
                            Toast.makeText(MainActivity.this, "Login con éxito", Toast.LENGTH_SHORT).show();
                        }
                    }
                } else {
                    Toast.makeText(MainActivity.this, "La cuenta con este número: " + phone + " , no existe", Toast.LENGTH_SHORT).show();
                    //loadingBar.dismiss();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError){

            }
        });
    }


}