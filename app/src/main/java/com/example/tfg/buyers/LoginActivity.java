package com.example.tfg.buyers;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.tfg.admin.AdminHomeActivity;
import com.example.tfg.models.Users;
import com.example.tfg.R;
import com.example.tfg.sellers.SellerProductCategoryActivity;
import com.example.tfg.prevalent.Prevalent;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import io.paperdb.Paper;

public class LoginActivity extends AppCompatActivity {

    private EditText InputPhoneNumber, InputPassword;
    private Button LoginButton;
    private AlertDialog loadingBar;
    private CheckBox checkBoxRememberMe;
    private TextView AdminLink,NotAdmin, ForgetPass;

    private String parentDbName = "Users";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        LoginButton = (Button) findViewById(R.id.login_btn);
        InputPassword = (EditText) findViewById(R.id.login_password);
        InputPhoneNumber = (EditText) findViewById(R.id.login_telefono);
        checkBoxRememberMe = (CheckBox) findViewById(R.id.remember_me);
        ForgetPass = findViewById(R.id.forgetPass);
        AdminLink = (TextView) findViewById(R.id.admin);
        NotAdmin = (TextView) findViewById(R.id.no_admin);
        //loadingBar = new AlertDialog(this);

        Paper.init(this);

        LoginButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                LoginUser();
            }
        });

        ForgetPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, ResetPasswordActivity.class);
                intent.putExtra("login", "check");
                startActivity(intent);
            }
        });

        AdminLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               LoginButton.setText("Login Admin");
               AdminLink.setVisibility(View.INVISIBLE);
               NotAdmin.setVisibility(View.VISIBLE);
               parentDbName = "Admins";
            }
        });
        NotAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginButton.setText("Login");
                AdminLink.setVisibility(View.VISIBLE);
                NotAdmin.setVisibility(View.INVISIBLE);
                parentDbName = "Users";
            }
        });

    }

    private void LoginUser(){
        String phone = InputPhoneNumber.getText().toString();
        String password = InputPassword.getText().toString();

        if(TextUtils.isEmpty(phone)){
            Toast.makeText(this, "escriba su número de teléfono", Toast.LENGTH_LONG).show();

        }else if (TextUtils.isEmpty(password)){
            Toast.makeText(this, "escriba su contraseña", Toast.LENGTH_LONG).show();

        }else{
//            loadingBar.setTitle("Login account");
  //          loadingBar.setMessage("Please wait, checking credentials");
     //       loadingBar.setCanceledOnTouchOutside(false);
       //     loadingBar.show();

            AllowAccessToAccount(phone, password);
        }
    }

    private void AllowAccessToAccount(final String phone, final String password){

        if(checkBoxRememberMe.isChecked()){
            Paper.book().write(Prevalent.userKey, phone);
            Paper.book().write(Prevalent.passKey, password);
        }
        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();

        RootRef.addListenerForSingleValueEvent(new ValueEventListener(){

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.child(parentDbName).child(phone).exists()) {
                    Users userData = dataSnapshot.child(parentDbName).child(phone).getValue(Users.class);
                    if (userData.getPhone().equals(phone)) {
                        if (userData.getPassword().equals(password)) {
                           if (parentDbName.equals("Admins")){
                               Toast.makeText(LoginActivity.this, "¡BIENVENIDO!", Toast.LENGTH_SHORT).show();

                               Intent intent = new Intent(LoginActivity.this, AdminHomeActivity.class);

                               startActivity(intent);

                           }else if (parentDbName.equals("Users")){
                               Toast.makeText(LoginActivity.this, "¡BIENVENIDO!", Toast.LENGTH_SHORT).show();

                               Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                               Prevalent.usuarioOnline = userData;
                               startActivity(intent);
                           }
                        }else{
                            //loadingBar.dismiss();
                            Toast.makeText(LoginActivity.this, "Error", Toast.LENGTH_SHORT).show();
                        }
                    }
                } else {
                    Toast.makeText(LoginActivity.this, "La cuenta con este número:  " + phone + " , no existe", Toast.LENGTH_SHORT).show();
                    //loadingBar.dismiss();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError){

            }
        });
    }
}