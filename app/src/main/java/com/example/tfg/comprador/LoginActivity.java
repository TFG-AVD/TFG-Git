package com.example.tfg.comprador;

import android.app.AlertDialog;
import android.app.ProgressDialog;
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
import com.example.tfg.modelos.Users;
import com.example.tfg.R;
import com.example.tfg.prevalent.Prevalent;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import io.paperdb.Paper;

public class LoginActivity extends AppCompatActivity {

    private TextView adminLink;
    private TextView adminNotLink;
    private TextView olvidadoContraseña;
    private EditText inputTelefono;
    private EditText inputContraseña;
    private CheckBox checkBoxRecuerdame;
    private Button loginBtn;
    private ProgressDialog loadingBar;

    private String parentDbName = "Users";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();

        adminLink = (TextView) findViewById(R.id.admin);
        adminNotLink = (TextView) findViewById(R.id.no_admin);
        olvidadoContraseña = findViewById(R.id.olvidadoContraseña);
        inputTelefono = (EditText) findViewById(R.id.login_telefono);
        inputContraseña = (EditText) findViewById(R.id.login_contraseña);
        checkBoxRecuerdame = (CheckBox) findViewById(R.id.recuerdame);
        loginBtn = (Button) findViewById(R.id.login_btn);

        loadingBar = new ProgressDialog(this);

        Paper.init(this);

        adminLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginBtn.setText("Login Admin");
                adminLink.setVisibility(View.INVISIBLE);
                adminNotLink.setVisibility(View.VISIBLE);
                parentDbName = "Admins";
            }
        });
        adminNotLink.setVisibility(View.INVISIBLE);
        adminNotLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginBtn.setText("Login");
                adminLink.setVisibility(View.VISIBLE);
                adminNotLink.setVisibility(View.INVISIBLE);
                parentDbName = "Users";
            }
        });

        olvidadoContraseña.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, ResetPasswordActivity.class);
                intent.putExtra("check", "login");
                startActivity(intent);
            }
        });

        loginBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                loginUsuario();
            }
        });
    }

    private void loginUsuario(){
        String phone = inputTelefono.getText().toString();
        String password = inputContraseña.getText().toString();

        if (TextUtils.isEmpty(phone)){
            Toast.makeText(this, "introduzca su número de teléfono...", Toast.LENGTH_LONG).show();

        } else if (TextUtils.isEmpty(password)){
            Toast.makeText(this, "introduzca su contraseña...", Toast.LENGTH_LONG).show();
        } else {
/*            loadingBar.setTitle("Iniciando Sesión");
            loadingBar.setMessage("Espere por favor, revisando credenciales");
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();*/
            permitirAccesoCuenta(phone, password);
        }
    }

    private void permitirAccesoCuenta(final String telefono, final String contraseña){

        if (checkBoxRecuerdame.isChecked()){
            Paper.book().write(Prevalent.userKey, telefono);
            Paper.book().write(Prevalent.passKey, contraseña);
        }

        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();

        RootRef.addListenerForSingleValueEvent(new ValueEventListener(){

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.child(parentDbName).child(telefono).exists()) {
                    Users userData = dataSnapshot.child(parentDbName).child(telefono).getValue(Users.class);

                    if (userData.getPhone().equals(telefono)) {

                        if (userData.getPassword().equals(contraseña)) {

                           if (parentDbName.equals("Admins")){
                               Toast.makeText(LoginActivity.this, "Se ha iniciado sesión", Toast.LENGTH_SHORT).show();

                               Intent intent = new Intent(LoginActivity.this, AdminHomeActivity.class);
                               startActivity(intent);

                           } else if (parentDbName.equals("Users")){
                               Toast.makeText(LoginActivity.this, "Se ha iniciado sesión", Toast.LENGTH_SHORT).show();

                               Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                               Prevalent.usuarioOnline = userData;
                               startActivity(intent);
                           }

                        } else {
                            loadingBar.dismiss();
                            Toast.makeText(LoginActivity.this, "por favor, introduzca de nuevo su contraseña", Toast.LENGTH_SHORT).show();
                        }
                    }
                } else {
                    Toast.makeText(LoginActivity.this, "la cuenta con este teléfono:  " + telefono + " , no existe", Toast.LENGTH_SHORT).show();
                    loadingBar.dismiss();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError){

            }
        });
    }
}