package com.example.tfg.admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.tfg.R;
import com.example.tfg.modelos.Products;
import com.example.tfg.viewholders.ProductViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class AdminCheckNewProductsActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    private DatabaseReference unverifiedProductsRef;

    //Según ejecute la aplicación
    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerOptions<Products> options = new FirebaseRecyclerOptions.Builder<Products>().setQuery(unverifiedProductsRef.orderByChild("productState").equalTo("Not Approved"), Products.class).build();

        FirebaseRecyclerAdapter<Products, ProductViewHolder> adapter = new FirebaseRecyclerAdapter<Products, ProductViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull ProductViewHolder holder, int position, @NonNull Products model){
                        holder.txtProductoNombre.setText(model.getPname());
                        holder.txtProductoDescripcion.setText(model.getDescription());
                        holder.txtProductoPrecio.setText("Precio: " + model.getPrice() + "€");
                        holder.txtNombreTienda.setText("Vendedor: " + model.getSellerName());
                        Picasso.get().load(model.getImage()).into(holder.imageView);

                        holder.itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                final String productID = model.getPid();

                                CharSequence options [] = new CharSequence[]{"Si", "No"};

                                AlertDialog.Builder builder = new AlertDialog.Builder(AdminCheckNewProductsActivity.this);
                                builder.setTitle("¿Quieres 'aceptar' este producto?");
                                builder.setItems(options, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int position) {
                                        if (position == 0){
                                            ChangeProductState(productID);
                                        }
                                        if (position == 1){

                                        }
                                    }
                                });
                                builder.show();
                            }
                        });
                    }

                    @NonNull
                    @Override
                    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.products_items_layout, parent, false);
                        ProductViewHolder holder = new ProductViewHolder(view);
                        return holder;
                    }
                };

        recyclerView.setAdapter(adapter);
        adapter.startListening();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_check_new_products);
        getSupportActionBar().hide();

        unverifiedProductsRef = FirebaseDatabase.getInstance().getReference().child("Products");

        recyclerView = findViewById(R.id.admin_products_checklist);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
    }

    private void ChangeProductState(String productID) {
        unverifiedProductsRef.child(productID).child("productState").setValue("Approved").addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(AdminCheckNewProductsActivity.this, "El producto ya ha sido 'aceptado', ya está disponible para su venta", Toast.LENGTH_SHORT).show();
            }
        });
    }
}