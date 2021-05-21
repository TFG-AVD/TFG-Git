package com.example.tfg.admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tfg.modelos.Cart;
import com.example.tfg.R;
import com.example.tfg.viewholders.CartViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AdminUserProductsActivity extends AppCompatActivity {

    RecyclerView.LayoutManager layoutManager;
    private String userID = "";
    private RecyclerView listaProductos;
    private DatabaseReference listaCarritoRef;

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerOptions<Cart> options = new FirebaseRecyclerOptions.Builder<Cart>().setQuery(listaCarritoRef, Cart.class).build();

        FirebaseRecyclerAdapter<Cart, CartViewHolder> adapter = new FirebaseRecyclerAdapter<Cart, CartViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull CartViewHolder holder, int position, @NonNull Cart model) {
                holder.txtProductoCantidad.setText("Cantidad = " + model.getQuantity());
                holder.txtProductoPrecio.setText("Precio " + model.getPrice() + "€");
                holder.txtProductoNombre.setText(model.getPname());
            }

            @NonNull
            @Override
            public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_items_layout, parent, false);
                CartViewHolder holder = new CartViewHolder(view);
                return holder;
            }
        };
        listaProductos.setAdapter(adapter);
        adapter.startListening();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_user_products);

        userID = getIntent().getStringExtra("uid");
        listaProductos = findViewById(R.id.admin_lista_productos);
        listaProductos.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        listaProductos.setLayoutManager(layoutManager);
        listaCarritoRef = FirebaseDatabase.getInstance().getReference().child("Cart list").child("Admin View").child(userID).child("Products");
    }
}