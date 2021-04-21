package com.example.tfg;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.tfg.dominio.HomeActivity;
import com.example.tfg.dominio.Prevalent;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class ConfirmFinalOrderActivity extends AppCompatActivity {

    private EditText nameEditText, phoneEditText, addressEditText, cityEditText;
    private Button confirmOrderBtn;

    private String totalAmount = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_final_order);

        totalAmount = getIntent().getStringExtra("Precio Total");
        Toast.makeText(this, "Precio Total = " + totalAmount, Toast.LENGTH_SHORT).show();

        confirmOrderBtn = (Button) findViewById(R.id.confirm_final_order_btn);
        nameEditText = (EditText) findViewById(R.id.shippment_name);
        phoneEditText = (EditText) findViewById(R.id.shippment_phone_number);
        addressEditText = (EditText) findViewById(R.id.shippment_address);
        cityEditText = (EditText) findViewById(R.id.shippment_city);

        confirmOrderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Check();
            }
        });
    }
    private void Check() {
        if (TextUtils.isEmpty(nameEditText.getText().toString())){
            Toast.makeText(this, "Por favor, introduca su nombre completo", Toast.LENGTH_SHORT).show();
        }else if(TextUtils.isEmpty(phoneEditText.getText().toString())){
            Toast.makeText(this, "Por favor, introduca su numero de telefono", Toast.LENGTH_SHORT).show();
        }else if(TextUtils.isEmpty(addressEditText.getText().toString())){
            Toast.makeText(this, "Por favor, introduca su dirección", Toast.LENGTH_SHORT).show();
        }else if(TextUtils.isEmpty(cityEditText.getText().toString())){
            Toast.makeText(this, "Por favor, introduca su ciudad", Toast.LENGTH_SHORT).show();
        }else{
            ConfirmOrder();
        }
    }

    private void ConfirmOrder() {
        final String saveDate, saveTime;

        Calendar calForDate = Calendar.getInstance();
        SimpleDateFormat date = new SimpleDateFormat("MM dd, yyyy");
        saveDate = date.format(calForDate.getTime());

        SimpleDateFormat time = new SimpleDateFormat("HH:mm:ss: a");
        saveTime =  date.format(calForDate.getTime());

        final DatabaseReference ordersRef = FirebaseDatabase.getInstance().getReference().child("Orders").child(Prevalent.usuarioOnline.getPhone());

        HashMap<String, Object> ordersMap = new HashMap<>();
        ordersMap.put("totalAmount", totalAmount);
        ordersMap.put("name", nameEditText.getText().toString());
        ordersMap.put("phone", phoneEditText.getText().toString());
        ordersMap.put("address", addressEditText.getText().toString());
        ordersMap.put("city", cityEditText.getText().toString());
        ordersMap.put("date", saveDate);
        ordersMap.put("time", saveTime);
        ordersMap.put("state", "no enviado");

        ordersRef.updateChildren(ordersMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
             if (task.isSuccessful()){
                 FirebaseDatabase.getInstance().getReference()
                         .child("Card List")
                         .child("user View")
                         .child(Prevalent.usuarioOnline.getPhone()).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                     @Override
                     public void onComplete(@NonNull Task<Void> task) {
                         Toast.makeText(ConfirmFinalOrderActivity.this,"Tu pedido ha sido completado correctamente.",Toast.LENGTH_SHORT).show();
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