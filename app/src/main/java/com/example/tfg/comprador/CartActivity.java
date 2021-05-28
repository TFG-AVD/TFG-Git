 package com.example.tfg.comprador;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tfg.modelos.Cart;
import com.example.tfg.R;
import com.example.tfg.modelos.Products;
import com.example.tfg.prevalent.Prevalent;
import com.example.tfg.viewholders.CartViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

 public class CartActivity extends AppCompatActivity {

    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView recyclerView;
    private Button nextBtn;
    private TextView txtTotal;
    private TextView txtMsg1;
    private ImageView imgCarrito;

    private int precioTotal = 0;

    @Override
    protected void onStart() {
        super.onStart();

        txtTotal.setText("Precio Total: " + String.valueOf(precioTotal) + "€");

        comprobarEstadoPedido();

        final DatabaseReference cartListRef = FirebaseDatabase.getInstance().getReference().child("Cart list");

        FirebaseRecyclerOptions<Cart> options = new FirebaseRecyclerOptions.Builder<Cart>().setQuery(cartListRef.child("User View").child(Prevalent.usuarioOnline.getPhone()).child("Products"), Cart.class).build();

        FirebaseRecyclerAdapter<Cart, CartViewHolder> adapter = new FirebaseRecyclerAdapter<Cart, CartViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull CartViewHolder cartViewHolder, int i, @NonNull Cart cart ) {
                Products products = null;
                cartViewHolder.txtProductoCantidad.setText("Cantidad: " + cart.getQuantity());
                cartViewHolder.txtProductoPrecio.setText("Precio: " + cart.getPrice()+" €");
                cartViewHolder.txtProductoNombre.setText(cart.getPname());
                cartViewHolder.txtNombreTienda.setText(cart.getSellerName());

                int precioTotalDeUnProducto = ((Integer.valueOf(cart.getPrice()))) * Integer.valueOf(cart.getQuantity());
                precioTotal = precioTotal + precioTotalDeUnProducto;
                txtTotal.setText("Precio Total: " + String.valueOf(precioTotal) + "€");

                cartViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        CharSequence options[] = new CharSequence[]{"Editar", "Borrar"};

                        AlertDialog.Builder builder = new AlertDialog.Builder(CartActivity.this);
                        builder.setTitle("Opciones de carrito");

                        builder.setItems(options, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                if (i == 0){
                                    Intent intent = new Intent(CartActivity.this, ProductDetailsActivity.class);
                                    intent.putExtra("pid", cart.getPid());
                                    startActivity(intent);
                                }
                                if (i == 1){
                                    cartListRef.child("User View").child(Prevalent.usuarioOnline.getPhone()).child("Products").child(cart.getPid()).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if (task.isSuccessful()){
                                                        Toast.makeText(CartActivity.this, "Producto eliminado con éxito.", Toast.LENGTH_SHORT);
                                                        Intent intent = new Intent(CartActivity.this, HomeActivity.class);
                                                        startActivity(intent);
                                                    }
                                                }
                                            });
                                }
                            }
                        });
                        builder.show();
                    }
                });
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        getSupportActionBar().hide();

        recyclerView = findViewById(R.id.lista_carrito);
        recyclerView.setHasFixedSize(true);
        layoutManager =  new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        nextBtn = (Button) findViewById(R.id.next_btn);
        txtTotal = (TextView) findViewById(R.id.precio_total);
        txtMsg1 = (TextView) findViewById(R.id.msg1);

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtTotal.setText("Precio Total: " + String.valueOf(precioTotal) + " €");

                Intent intent= new Intent(CartActivity.this, ConfirmFinalOrderActivity.class);
                intent.putExtra("Precio Total:", String.valueOf(precioTotal));
                startActivity(intent);
                finish();
            }
        });

    }

    private void comprobarEstadoPedido(){
        DatabaseReference ordersRef;
        ordersRef = FirebaseDatabase.getInstance().getReference().child("Orders").child(Prevalent.usuarioOnline.getPhone());
        ordersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    String shippingState = snapshot.child("state").getValue().toString();
                    String userName = snapshot.child("name").getValue().toString();

                    if (shippingState.equals("shipped")){
                        txtTotal.setText("Querido " + userName + "\n pedido enviado con éxito,");
                        recyclerView.setVisibility(View.GONE);

                        txtMsg1.setVisibility(View.VISIBLE);
                        txtMsg1.setText("Felicidades, tu pedido ha sido enviado con éxito. Dentro de poco recibirás el pedido. ");
                        nextBtn.setVisibility(View.GONE);

                        Toast.makeText(CartActivity.this, "puedes adquirir más productos en cuanto hayas recibido tu pedido", Toast.LENGTH_SHORT);

                    } else if (shippingState.equals("No Enviado")){
                        txtTotal.setText("Estado de pedido = No Enviado");
                        recyclerView.setVisibility(View.GONE);

                        txtMsg1.setVisibility(View.VISIBLE);
                        nextBtn.setVisibility(View.GONE);

                        Toast.makeText(CartActivity.this, "puedes adquirir más productos en cuanto hayas recibido tu pedido", Toast.LENGTH_SHORT);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}