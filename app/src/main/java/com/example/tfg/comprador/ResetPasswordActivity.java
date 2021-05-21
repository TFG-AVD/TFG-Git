package com.example.tfg.comprador;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.example.tfg.R;
import com.example.tfg.prevalent.Prevalent;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class ResetPasswordActivity extends AppCompatActivity {

    private TextView tituloPagina;
    private TextView tituloPregunta;
    private EditText telefono;
    private EditText pregunta1;
    private EditText pregunta2;
    private Button aceptarBtn;
    private String check = "";

    @Override
    protected void onStart() {
        super.onStart();

        telefono.setVisibility(View.GONE);

        if (check.equals("settings")){
            tituloPagina.setText("Responda preguntas");
            tituloPregunta.setText("Por favor, responda las preguntas de seguridad");
            aceptarBtn.setText("Aceptar");

            mostrarRespuestas();

            aceptarBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    contestarPregunta();
                }
            });

        } else if (check.equals("login")){
            telefono.setVisibility(View.VISIBLE);

            aceptarBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    verificarUsuario();
                }
            });
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        check = getIntent().getStringExtra("check");
        tituloPagina = (TextView) findViewById(R.id.page_title);
        tituloPregunta = (TextView) findViewById(R.id.title_questions);
        telefono = (EditText) findViewById(R.id.find_phone_number);
        pregunta1 = (EditText) findViewById(R.id.question_1);
        pregunta2 = (EditText) findViewById(R.id.question_2);
        aceptarBtn = (Button) findViewById(R.id.verify_btn);
    }

    private void mostrarRespuestas(){
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Users").child(Prevalent.usuarioOnline.getPhone());

        ref.child("Security Questions").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    String res1 = dataSnapshot.child("answer1").getValue().toString();
                    String res2 = dataSnapshot.child("answer2").getValue().toString();

                    pregunta1.setText(res1);
                    pregunta2.setText(res2);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void contestarPregunta(){
        String respuesta1 = pregunta1.getText().toString().toLowerCase();
        String respuesta2 = pregunta2.getText().toString().toLowerCase();

        if (pregunta1.equals("") && pregunta2.equals("")){
            Toast.makeText(ResetPasswordActivity.this, "Por favor, conteste ambas preguntas", Toast.LENGTH_SHORT).show();
        } else {
            DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Users").child(Prevalent.usuarioOnline.getPhone());

            HashMap<String, Object> userdataMap = new HashMap<>();
            userdataMap.put("answer1", respuesta1);
            userdataMap.put("answer2", respuesta2);

            ref.child("Security Questions").updateChildren(userdataMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()){
                        Toast.makeText(ResetPasswordActivity.this, "Has contestado las preguntas de seguridad con éxito", Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(ResetPasswordActivity.this, HomeActivity.class);
                        startActivity(intent);
                    }
                }
            });
        }
    }

    private void verificarUsuario(){
        final String numeroTel = telefono.getText().toString();
        final String respuesta1 = pregunta1.getText().toString().toLowerCase();
        final String respuesta2 = pregunta2.getText().toString().toLowerCase();

        if (!numeroTel.equals("") && !respuesta1.equals("") && !respuesta2.equals("")){
            final DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Users").child(numeroTel);

            ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()){
                        String mPhone = dataSnapshot.child("phone").getValue().toString();

                        if (dataSnapshot.hasChild("Security Questions")){
                            String res1 = dataSnapshot.child("Security Questions").child("answer1").getValue().toString();
                            String res2 = dataSnapshot.child("Security Questions").child("answer2").getValue().toString();

                            if (!res1.equals(respuesta1)){
                                Toast.makeText(ResetPasswordActivity.this, "La primera respuesta es incorrecta!", Toast.LENGTH_SHORT).show();
                            } else if (!res2.equals(respuesta2)){
                                Toast.makeText(ResetPasswordActivity.this, "La segunda respuesta es incorrecta!", Toast.LENGTH_SHORT).show();
                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(ResetPasswordActivity.this);
                                builder.setTitle("Nueva Contraseña");

                                final EditText newPassword = new EditText(ResetPasswordActivity.this);
                                newPassword.setHint("Escriba la contraseña aquí");
                                newPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                                builder.setView(newPassword);

                                builder.setPositiveButton("Cambiar", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        if (!newPassword.getText().toString().equals("")){ ref.child("password")
                                                    .setValue(newPassword.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<Void> task) {
                                                            if (task.isSuccessful()){
                                                                Toast.makeText(ResetPasswordActivity.this, "Contraseña cambiada con éxito", Toast.LENGTH_SHORT).show();

                                                                Intent intent = new Intent(ResetPasswordActivity.this, HomeActivity.class);
                                                                startActivity(intent);
                                                            }
                                                        }
                                                    });
                                        }
                                    }
                                });
                                builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.cancel();
                                    }
                                });

                                builder.show();
                            }
                        }
                        else {
                            Toast.makeText(ResetPasswordActivity.this, "No has contestado las preguntas de seguridad", Toast.LENGTH_SHORT).show();
                        }
                    }else {
                        Toast.makeText(ResetPasswordActivity.this, "Este número de teléfono no existe", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }else {
            Toast.makeText(this, "Por favor, complete el formulario", Toast.LENGTH_SHORT).show();
        }
    }
}