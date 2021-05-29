package com.example.tfg.comprador;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tfg.R;
import com.example.tfg.modelos.Products;
import com.example.tfg.viewholders.ProductViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;


public class CategoriesActivity extends AppCompatActivity {

    private Button botanicaBtn;
    private Button bricolajeBtn;
    private Button comidaBtn;
    private Button deporteBtn;
    private Button drogueriaBtn;
    private Button electrodomesticosBtn;
    private Button electronicaBtn;
    private Button ferreteriaBtn;
    private Button hogarBtn;
    private Button mascotasBtn;
    private Button ocioBtn;
    private Button papeleriaBtn;
    private RecyclerView buscarCategorias;

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);

        getSupportActionBar().hide();

        botanicaBtn = (Button) findViewById(R.id.botanica_foto);
        bricolajeBtn = (Button) findViewById(R.id.bricolaje_foto);
        comidaBtn = (Button) findViewById(R.id.comida_foto);
        deporteBtn = (Button) findViewById(R.id.deporte_foto);
        drogueriaBtn = (Button) findViewById(R.id.drogueria_foto);
        electrodomesticosBtn = (Button) findViewById(R.id.electrodomesticos_foto);
        electronicaBtn = (Button) findViewById(R.id.electronica_foto);
        ferreteriaBtn = (Button) findViewById(R.id.ferreteria_foto);
        hogarBtn = (Button) findViewById(R.id.hogar_foto);
        mascotasBtn = (Button) findViewById(R.id.mascota_foto);
        ocioBtn = (Button) findViewById(R.id.ocio_foto);
        papeleriaBtn = (Button) findViewById(R.id.papeleria_foto);

        buscarCategorias = findViewById(R.id.buscar_categorias);
        buscarCategorias.setLayoutManager(new LinearLayoutManager(CategoriesActivity.this));



        botanicaBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                elegirCategoria("Botánica");
            }
        });

        bricolajeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                elegirCategoria("Bricolaje");
            }
        });

        comidaBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                elegirCategoria("Comida");
            }
        });

        deporteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                elegirCategoria("Deporte");
            }
        });

        drogueriaBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                elegirCategoria("Drogería");
            }
        });

        electrodomesticosBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                elegirCategoria("Electrodomésticos");
            }
        });

        electronicaBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                elegirCategoria("Electrónica");
            }
        });

        ferreteriaBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                elegirCategoria("Ferretería");
            }
        });

        hogarBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                elegirCategoria("Hogar");
            }
        });

        mascotasBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               elegirCategoria("Mascotas");
            }
        });

        ocioBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                elegirCategoria("Ocio");
            }
        });
        papeleriaBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                elegirCategoria("Papelería");
            }
        });

    }

    private void elegirCategoria(String caterory) {
        Toast.makeText(CategoriesActivity.this, caterory, Toast.LENGTH_SHORT).show();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Products");

        FirebaseRecyclerOptions<Products> options = new FirebaseRecyclerOptions.Builder<Products>().setQuery(reference.orderByChild("category").equalTo(caterory), Products.class).build();

        FirebaseRecyclerAdapter<Products, ProductViewHolder> adapter = new FirebaseRecyclerAdapter<Products, ProductViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull ProductViewHolder holder, int position, @NonNull final Products model) {
                holder.txtProductoNombre.setText(model.getPname());
                holder.txtProductoDescripcion.setText(model.getDescription());
                holder.txtNombreTienda.setText(model.getSellerName());
                holder.txtCategoria.setText(model.getCategory());
                holder.txtProductoPrecio.setText("Precio: " + model.getPrice() + "€");
                Picasso.get().load(model.getImage()).into(holder.imageView);

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(CategoriesActivity.this, ProductDetailsActivity.class);
                        intent.putExtra("pid", model.getPid());
                        startActivity(intent);
                    }
                });
            }

            @NonNull
            @Override
            public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.products_items_layout, parent, false);
                ProductViewHolder holder = new ProductViewHolder(view);
                return holder;
            }
        };

        buscarCategorias.setAdapter(adapter);
        adapter.startListening();

    }
}