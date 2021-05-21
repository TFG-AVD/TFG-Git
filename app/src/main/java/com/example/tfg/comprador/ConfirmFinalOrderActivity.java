package com.example.tfg.comprador;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.tfg.R;
import com.example.tfg.prevalent.Prevalent;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class ConfirmFinalOrderActivity extends AppCompatActivity {

    private Button confirmarPedidoBtn;
    private EditText nombreEditText;
    private EditText telefonoEditText;
    private EditText direccionEditText;
    private EditText ciudadEditText;

    private String totalAmount = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_final_order);

        totalAmount = getIntent().getStringExtra("Precio Total");
        Toast.makeText(this, "Precio Total: " + totalAmount + "€", Toast.LENGTH_LONG).show();

        confirmarPedidoBtn = (Button) findViewById(R.id.confirmar_pedido_btn);
        nombreEditText = (EditText) findViewById(R.id.envio_nombre);
        telefonoEditText = (EditText) findViewById(R.id.envio_telefono);
        direccionEditText = (EditText) findViewById(R.id.envio_direccion);
        ciudadEditText = (EditText) findViewById(R.id.envio_ciudad);

        confirmarPedidoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Check();
            }
        });
    }
    private void Check() {
        if (TextUtils.isEmpty(nombreEditText.getText().toString())){
            Toast.makeText(this, "por favor, introduzca su nombre completo...", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(telefonoEditText.getText().toString())){
            Toast.makeText(this, "por favor, introduzca su numero de telefono...", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(direccionEditText.getText().toString())){
            Toast.makeText(this, "por favor, introduzca su dirección...", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(ciudadEditText.getText().toString())){
            Toast.makeText(this, "por favor, introduzca su ciudad...", Toast.LENGTH_SHORT).show();
        } else {
            ConfirmarPedido();
        }
    }

    private void ConfirmarPedido() {
        final String saveDate, saveTime;

        Calendar calendario = Calendar.getInstance();
        SimpleDateFormat fecha = new SimpleDateFormat("MM dd, yyyy");
        saveDate = fecha.format(calendario.getTime());

        SimpleDateFormat time = new SimpleDateFormat("HH:mm:ss: a");
        saveTime =  fecha.format(calendario.getTime());

        final DatabaseReference ordersRef = FirebaseDatabase.getInstance().getReference().child("Orders").child(Prevalent.usuarioOnline.getPhone());

        HashMap<String, Object> ordersMap = new HashMap<>();
        ordersMap.put("totalAmount", totalAmount);
        ordersMap.put("name", nombreEditText.getText().toString());
        ordersMap.put("phone", telefonoEditText.getText().toString());
        ordersMap.put("address", direccionEditText.getText().toString());
        ordersMap.put("city", ciudadEditText.getText().toString());
        ordersMap.put("date", saveDate);
        ordersMap.put("time", saveTime);
        ordersMap.put("state", "no enviado");

        ordersRef.updateChildren(ordersMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
             if (task.isSuccessful()){
                 FirebaseDatabase.getInstance().getReference().child("Cart List").child("User View").child(Prevalent.usuarioOnline.getPhone()).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                     @Override
                     public void onComplete(@NonNull Task<Void> task) {
                         Toast.makeText(ConfirmFinalOrderActivity.this,"¡Tu pedido ha sido realizado con éxito!",Toast.LENGTH_SHORT).show();
                         Intent intent = new Intent(ConfirmFinalOrderActivity.this, HomeActivity.class);
                         intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                         startActivity(intent);
                         finish();
                     }
                 });
             }
            }
        });
    }
}