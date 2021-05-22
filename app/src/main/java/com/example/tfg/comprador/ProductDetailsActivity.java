package com.example.tfg.comprador;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.example.tfg.R;
import com.example.tfg.modelos.Products;
import com.example.tfg.prevalent.Prevalent;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class ProductDetailsActivity extends AppCompatActivity {

    private TextView productoPrecio;
    private TextView productoDescripcion;
    private TextView productoNombre;
    private ImageView productoImagen;
    private FloatingActionButton añadirCarro;
    private ElegantNumberButton cantidadBtn;

    private String productID = "", state = "Normal";

    @Override
    protected void onStart() {
        super.onStart();
        estadoPedido();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);
        getSupportActionBar().hide();

        productID = getIntent().getStringExtra("pid");

        productoPrecio = (TextView) findViewById(R.id.product_price_details);
        productoDescripcion = (TextView) findViewById(R.id.product_description_details);
        productoNombre = (TextView) findViewById(R.id.product_name_details);
        productoImagen = (ImageView) findViewById(R.id.product_image_details);
        añadirCarro = (FloatingActionButton) findViewById(R.id.add_product_cart_btn);
        cantidadBtn = (ElegantNumberButton) findViewById(R.id.number_btn);

        detallesProductos(productID);

        añadirCarro.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                añadirCarrito();

                if (state.equals("Pedido Colocado.") || state.equals("Pedido Enviado")) {
                    Toast.makeText(ProductDetailsActivity.this, "puede adquirir más productos cuando su pedido haya sido enviado o confirmado.", Toast.LENGTH_LONG).show();
                } else {
                    añadirCarrito();
                }
            }
        });
    }

    private void añadirCarrito() {
        String saveCurrentTime, saveCurrentDate;

        Calendar calForDate = Calendar.getInstance();
        SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd, yyyy");
        saveCurrentDate = currentDate.format(calForDate.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime = currentTime.format(calForDate.getTime());

        final DatabaseReference cartListRef = FirebaseDatabase.getInstance().getReference().child("Cart list");

        final HashMap<String, Object> cartMap = new HashMap<>();
        cartMap.put("pid", productID);
        cartMap.put("pname", productoNombre.getText().toString());
        cartMap.put("price", productoPrecio.getText().toString());
        cartMap.put("date", saveCurrentDate);
        cartMap.put("time", saveCurrentTime);
        cartMap.put("quantity", cantidadBtn.getNumber());
        cartMap.put("discount", "");

        cartListRef.child("User View").child(Prevalent.usuarioOnline.getPhone())
                .child("Products").child(productID)
                .updateChildren(cartMap)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){cartListRef.child("Admin view").child(Prevalent.usuarioOnline.getPhone()).child("Products").child(productID)
                                    .updateChildren(cartMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()){
                                                Toast.makeText(ProductDetailsActivity.this, "Añadido al carrito", Toast.LENGTH_SHORT).show();

                                                Intent intent = new Intent(ProductDetailsActivity.this, HomeActivity.class);
                                                startActivity(intent);
                                            }
                                        }
                                    });
                        }
                    }
                });

    }

    private void estadoPedido(){
        DatabaseReference ordersRef;
        ordersRef = FirebaseDatabase.getInstance().getReference().child("Orders").child(Prevalent.usuarioOnline.getPhone());
        ordersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    String shippingState = snapshot.child("state").getValue().toString();

                    if (shippingState.equals("shipped"))
                    {
                        state = "Pedido Envidado";
                    } else if (shippingState.equals("not shipped")){
                        state = "Pedido Colocado";
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void detallesProductos(String productID) {
        DatabaseReference productRef = FirebaseDatabase.getInstance().getReference().child("Products");
        productRef.child(productID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Products products = dataSnapshot.getValue(Products.class);

                productoNombre.setText(products.getPname());
                productoPrecio.setText(products.getPrice());
                productoDescripcion.setText(products.getDescription());
                Picasso.get().load(products.getImage()).into(productoImagen);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}