package com.example.tfg.admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.tfg.R;
import com.example.tfg.vendedor.SellerProductCategoryActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

public class AdminMaintainProductsActivity extends AppCompatActivity {

    private Button aplicarCambiosBtn;
    private Button borrarBtn;
    private EditText nombre;
    private EditText precio;
    private EditText descripcion;
    private ImageView imagen;
    private String productoID = "";
    private DatabaseReference productoRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_admin_maintain_products);

        productoID = getIntent().getStringExtra("pid");
        productoRef = FirebaseDatabase.getInstance().getReference().child("Products").child(productoID);

        aplicarCambiosBtn = findViewById(R.id.aplicar_cambios_btn);
        borrarBtn = findViewById(R.id.borrar_producto_btn);
        nombre = findViewById(R.id.producto_nombre_maintain);
        precio = findViewById(R.id.producto_precio_maintain);
        descripcion = findViewById(R.id.producto_descripcion_maintain);
        imagen = findViewById(R.id.producto_imagen_maintain);

        mostrarInfoProducto();

        borrarBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                borrarProducto();
            }
        });

        aplicarCambiosBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                aplicarCambios();
            }
        });
    }

    private void mostrarInfoProducto() {
        productoRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String pName = dataSnapshot.child("pname").getValue().toString();
                    String pPrice = dataSnapshot.child("price").getValue().toString();
                    String pDescription = dataSnapshot.child("description").getValue().toString();
                    String pImage = dataSnapshot.child("image").getValue().toString();

                    nombre.setText(pName);
                    precio.setText(pPrice);
                    descripcion.setText(pDescription);
                    Picasso.get().load(pImage).into(imagen);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void aplicarCambios() {
        String pName = nombre.getText().toString();
        String pPrice = precio.getText().toString();
        String pDescription = descripcion.getText().toString();

        if (pName.equals("")) {
            Toast.makeText(this, "Esciba el nombre del producto.", Toast.LENGTH_SHORT).show();
        } else if (pPrice.equals("")) {
            Toast.makeText(this, "Escriba el precio del producto.", Toast.LENGTH_SHORT).show();
        } else if (pDescription.equals("")) {
            Toast.makeText(this, "Escriba la descripción del producto.", Toast.LENGTH_SHORT).show();
        } else {
            HashMap<String, Object> productMap = new HashMap<>();
            productMap.put("pid", productoID);
            productMap.put("description", pDescription);
            productMap.put("price", pPrice);
            productMap.put("pname", pName);

            productoRef.updateChildren(productMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(AdminMaintainProductsActivity.this, "Cambios aplicados con éxito.", Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(AdminMaintainProductsActivity.this, AdminHomeActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }
            });
        }
    }

    private void borrarProducto() {
        productoRef.removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Intent intent = new Intent(AdminMaintainProductsActivity.this, AdminHomeActivity.class);
                startActivity(intent);
                finish();

                Toast.makeText(AdminMaintainProductsActivity.this, "El producto ha sido borrado con éxito", Toast.LENGTH_SHORT).show();
            }
        });
    }
}