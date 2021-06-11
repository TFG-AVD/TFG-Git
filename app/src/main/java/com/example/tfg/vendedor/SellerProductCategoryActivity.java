package com.example.tfg.vendedor;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tfg.R;

public class SellerProductCategoryActivity extends AppCompatActivity {

    private ImageView botanica, bricolaje, comida, deportes;
    private ImageView drogueria, electrodomesticos, electronica, ferreteria;
    private ImageView hogar, mascotas, ocio, papeleria;

    private Button LogoutBtn, CheckOrdersBtn;

    @Override
    protected void onCreate(Bundle saveInstanceState) {

        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_seller_add_category);



/*        LogoutBtn = (Button) findViewById(R.id.admin_logout_btn);
        CheckOrdersBtn = (Button) findViewById(R.id.check_orders_btn);

        LogoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminCategoryActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }
        });

        CheckOrdersBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminCategoryActivity.this, AdminNewOrderActivity.class);
                startActivity(intent);

            }
        });*/

        botanica = (ImageView) findViewById(R.id.botanica_foto);
        bricolaje = (ImageView) findViewById(R.id.bricolaje_foto);
        comida = (ImageView) findViewById(R.id.comida_foto);
        deportes = (ImageView) findViewById(R.id.deporte_foto);

        drogueria = (ImageView) findViewById(R.id.drogueria_foto);
        electrodomesticos = (ImageView) findViewById(R.id.electrodomesticos_foto);
        electronica = (ImageView) findViewById(R.id.electronica_foto);
        ferreteria = (ImageView) findViewById(R.id.ferreteria_foto);

        hogar = (ImageView) findViewById(R.id.hogar_foto);
        mascotas = (ImageView) findViewById(R.id.mascota_foto);
        ocio = (ImageView) findViewById(R.id.ocio_foto);
        papeleria = (ImageView) findViewById(R.id.papeleria_foto);

        botanica.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(SellerProductCategoryActivity.this, SellerAddNewProductActivity.class);
                intent.putExtra("category", "Botánica");
                startActivity(intent);
            }

        });

        bricolaje.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(SellerProductCategoryActivity.this, SellerAddNewProductActivity.class);
                intent.putExtra("category", "Bricolaje");
                startActivity(intent);
            }

        });

        comida.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(SellerProductCategoryActivity.this, SellerAddNewProductActivity.class);
                intent.putExtra("category", "Comida");
                startActivity(intent);
            }

        });

        deportes.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(SellerProductCategoryActivity.this, SellerAddNewProductActivity.class);
                intent.putExtra("category", "Deportes");
                startActivity(intent);
            }

        });

        drogueria.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(SellerProductCategoryActivity.this, SellerAddNewProductActivity.class);
                intent.putExtra("category", "Drogueria");
                startActivity(intent);
            }

        });

        electrodomesticos.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(SellerProductCategoryActivity.this, SellerAddNewProductActivity.class);
                intent.putExtra("category", "Electrodomésticos");
                startActivity(intent);
            }

        });

        electronica.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(SellerProductCategoryActivity.this, SellerAddNewProductActivity.class);
                intent.putExtra("category", "Electrónica");
                startActivity(intent);
            }

        });

        ferreteria.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(SellerProductCategoryActivity.this, SellerAddNewProductActivity.class);
                intent.putExtra("category", "Ferretería");
                startActivity(intent);
            }

        });

        hogar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(SellerProductCategoryActivity.this, SellerAddNewProductActivity.class);
                intent.putExtra("category", "Hogar");
                startActivity(intent);
            }

        });

        mascotas.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(SellerProductCategoryActivity.this, SellerAddNewProductActivity.class);
                intent.putExtra("category", "Mascotas");
                startActivity(intent);
            }

        });

        ocio.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(SellerProductCategoryActivity.this, SellerAddNewProductActivity.class);
                intent.putExtra("category", "Ocio");
                startActivity(intent);
            }

        });

        papeleria.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(SellerProductCategoryActivity.this, SellerAddNewProductActivity.class);
                intent.putExtra("category", "Papeleria");
                startActivity(intent);
            }

        });
    }
}
