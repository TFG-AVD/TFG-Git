package com.example.tfg;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class RegisterActivity extends AppCompatActivity {

    private Button CreateAccountButton;
    private EditText InputName, InputPhoneNumber, InputPassword;
    private AlertDialog loadingbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        CreateAccountButton = (Button) findViewById(R.id.register_btn);
        InputName = (EditText) findViewById(R.id.register_username_input);
        InputPhoneNumber = (EditText) findViewById(R.id.register_password);
        InputPassword = (EditText) findViewById(R.id.register_telefono);
        loadingbar = new AlertDialog(this);

        CreateAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreateAccount();
            }
        });

    }

    private void CreateAccount()
    {
        String name = InputName.getText().toString();
        String phone = InputPhoneNumber.getText().toString();
        String password = InputPassword.getText().toString();

        if (TextUtils.isEmpty(name))
        {
            Toast.makeText(this, "Por favor escriba su nombre...", Toast.LENGTH_SHORT);
        }

        else if (TextUtils.isEmpty(phone))
        {
            Toast.makeText(this, "Por favor escriba su número de teléfono...", Toast.LENGTH_SHORT);
        }

        else if (TextUtils.isEmpty(password))
        {
            Toast.makeText(this, "Por favor escriba su contraseña...", Toast.LENGTH_SHORT);
        }

        else
        {
            loadingbar.setTitle("Crear cuenta.");
            loadingbar.setMessage("Espere, estamos revisando las credenciales.");
            loadingbar.setCanceledOnTouchOutside(false);
            loadingbar.show();

            ValidatephoneNumber(name, phone, password);
        }
    }

    private void ValidatephoneNumber(String name, String phone, String password)
    {
        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();
        RootRef.addListenerForSingleValueEvent(new ValueEventListener(){

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot){

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError){

            }

        });
    }
}