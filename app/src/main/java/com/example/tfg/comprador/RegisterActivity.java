package com.example.tfg.comprador;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.tfg.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {

    private EditText inputNombre;
    private EditText inputTelefono;
    private EditText inputContraseña;
    private Button crearCuentaBtn;
    private ProgressDialog loadingbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        crearCuentaBtn = (Button) findViewById(R.id.registro_btn);
        inputNombre = (EditText) findViewById(R.id.registro_username_input);
        inputTelefono = (EditText) findViewById(R.id.registro_telefono);
        inputContraseña = (EditText) findViewById(R.id.registro_contraseña);
        loadingbar = new ProgressDialog(this);

        crearCuentaBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                crearCuenta();
            }
        });
    }

    private void crearCuenta() {
        String nombre = inputNombre.getText().toString();
        String telefono = inputTelefono.getText().toString();
        String password = inputContraseña.getText().toString();

        if (TextUtils.isEmpty(nombre)) {
            Toast.makeText(this, "Por favor escriba su nombre...", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(telefono)) {
            Toast.makeText(this, "Por favor escriba su número de teléfono...", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Por favor escriba su contraseña...", Toast.LENGTH_SHORT).show();
        } else {
            loadingbar.setTitle("Crear cuenta.");
            loadingbar.setMessage("Espere, estamos revisando las credenciales.");
            loadingbar.setCanceledOnTouchOutside(false);
            loadingbar.show();

            validarCuenta(nombre, telefono, password);
        }
    }

    private void validarCuenta(String nombre, String telefono, String password) {
        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();
        RootRef.addListenerForSingleValueEvent(new ValueEventListener(){

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot){

                if (!(dataSnapshot.child("Users").child(telefono).exists())) {
                    HashMap<String, Object> userdataMap = new HashMap<>();
                    userdataMap.put("name", nombre);
                    userdataMap.put("phone", telefono);
                    userdataMap.put("password", password);

                    RootRef.child("Users").child(telefono).updateChildren(userdataMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful() && telefono.length()==9) {
                                        Toast.makeText(RegisterActivity.this, "¡Cuenta creada con éxito!", Toast.LENGTH_SHORT).show();

                                        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                        startActivity(intent);
                                    } else {
                                        loadingbar.dismiss();
                                        Toast.makeText(RegisterActivity.this, "El numero debe tener 9 digitos.", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                } else {
                    Toast.makeText(RegisterActivity.this, "este teléfono: " + telefono + " ya existe...", Toast.LENGTH_SHORT).show();
                    Toast.makeText(RegisterActivity.this, "por favor, inténtelo con otro número de teléfono", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}