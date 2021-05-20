package com.example.tfg.admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.tfg.modelos.AdminOrders;
import com.example.tfg.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AdminNewOrderActivity extends AppCompatActivity {

    private RecyclerView pedidosLista;
    private DatabaseReference pedidosRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_new_order);

        pedidosRef = FirebaseDatabase.getInstance().getReference().child("Orders");

        pedidosLista = findViewById(R.id.orders_list);
        pedidosLista.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerOptions<AdminOrders> options = new FirebaseRecyclerOptions.Builder<AdminOrders>()
                        .setQuery(pedidosRef, AdminOrders.class).build();

        FirebaseRecyclerAdapter<AdminOrders, AdminOrdersViewHolder> adapter = new FirebaseRecyclerAdapter<AdminOrders, AdminOrdersViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull AdminOrdersViewHolder holder, final int position, @NonNull final AdminOrders model) {
                        holder.usuarioNombre.setText("Nombre: " + model.getName());
                        holder.usuarioTelefono.setText("Teléfono: " + model.getPhone());
                        holder.usuarioPrecioTotal.setText("Cantidad Total =  €" + model.getTotalAmount());
                        holder.usuarioFechaPedido.setText("Fecha de pedido: " + model.getDate() + "  " + model.getTime());
                        holder.usuarioDireccion.setText("Dirección del envio: " + model.getAddress() + ", " + model.getCity());

                        holder.mostrarPedidosBtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                String uID = getRef(position).getKey();

                                Intent intent = new Intent(AdminNewOrderActivity.this, AdminUserProductsActivity.class);
                                intent.putExtra("uid", uID);
                                startActivity(intent);
                            }
                        });

                        holder.itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                CharSequence options[] = new CharSequence[]{"Si", "No"};

                                AlertDialog.Builder builder = new AlertDialog.Builder(AdminNewOrderActivity.this);
                                builder.setTitle("¿Se ha enviado este pedido?");

                                builder.setItems(options, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        if (i == 0) {
                                            String uID = getRef(position).getKey();
                                            borrarPedido(uID);
                                        }
                                        else {
                                            finish();
                                        }
                                    }
                                });
                                builder.show();
                            }
                        });
                    }

                    @NonNull
                    @Override
                    public AdminOrdersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.orders_layout, parent, false);
                        return new AdminOrdersViewHolder(view);
                    }
                };
        pedidosLista.setAdapter(adapter);
        adapter.startListening();
    }

    public static class AdminOrdersViewHolder extends RecyclerView.ViewHolder {
        public TextView usuarioNombre, usuarioTelefono, usuarioPrecioTotal, usuarioFechaPedido, usuarioDireccion;
        public Button mostrarPedidosBtn;

        public AdminOrdersViewHolder(View itemView) {
            super(itemView);

            usuarioNombre = itemView.findViewById(R.id.order_user_name);
            usuarioTelefono = itemView.findViewById(R.id.order_phone_number);
            usuarioPrecioTotal = itemView.findViewById(R.id.order_total_price);
            usuarioFechaPedido = itemView.findViewById(R.id.order_date_time);
            usuarioDireccion = itemView.findViewById(R.id.order_address_city);
            mostrarPedidosBtn = itemView.findViewById(R.id.show_all_products_btn);
        }
    }

    private void borrarPedido(String uID) {
        pedidosRef.child(uID).removeValue();
    }
}

