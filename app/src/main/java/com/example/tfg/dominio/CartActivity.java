package com.example.tfg.dominio;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.tfg.Model.Cart;
import com.example.tfg.R;
import com.example.tfg.ViewHolder.CartViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CartActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;

    private Button nextBtn;
    private TextView txtTotal;

    private int overTotalPrice = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        recyclerView = findViewById(R.id.cart_list);
        recyclerView.setHasFixedSize(true);
        layoutManager =  new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        nextBtn = (Button) findViewById(R.id.next_cart_btn);
        txtTotal = (TextView) findViewById(R.id.total_price);
    }

    @Override
    protected void onStart() {
        super.onStart();

        final DatabaseReference cartListRef = FirebaseDatabase.getInstance().getReference().child("Lista de carrito");

        FirebaseRecyclerOptions<Cart> options =
                new FirebaseRecyclerOptions.Builder<Cart>().setQuery(cartListRef.child("User View")
                        .child(Prevalent.usuarioOnline.getPhone())
                        .child("Products"), Cart.class).build();

        FirebaseRecyclerAdapter<Cart, CartViewHolder> adapter = new FirebaseRecyclerAdapter<Cart, CartViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull CartViewHolder cartViewHolder, int i, @NonNull Cart cart) {
                cartViewHolder.txtProductQuantity.setText("Cantidad = " + cart.getQuantity());
                cartViewHolder.txtProductPrice.setText("Precio = " + cart.getPrice());
                cartViewHolder.txtProductName.setText(cart.getPname());

            }

            @NonNull
            @Override
            public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_items_layout, parent, false);
                CartViewHolder holder = new CartViewHolder(view);
                return holder;
            }
        };

        recyclerView.setAdapter(adapter);
        adapter.startListening();

    }
}