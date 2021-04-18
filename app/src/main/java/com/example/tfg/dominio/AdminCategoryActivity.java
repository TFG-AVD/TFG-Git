package com.example.tfg.dominio;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tfg.R;

public class AdminCategoryActivity extends AppCompatActivity {

    private ImageView tShirt, sportShirts, femaleDresses, sweathers;
    private ImageView glasses, hatCaps, walletsBagsPurses, shoes;
    private ImageView headPhonesHandFree, laptops, watches, mobilePhones;

    @Override
    protected void onCreate(Bundle saveInstanceState) {

        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_admin_category);

        tShirt = (ImageView) findViewById(R.id.t_shirts);
        sportShirts = (ImageView) findViewById(R.id.sport_t_shirts);
        femaleDresses = (ImageView) findViewById(R.id.female_dresses);
        sweathers = (ImageView) findViewById(R.id.sweathers);

        glasses = (ImageView) findViewById(R.id.glasses);
        hatCaps = (ImageView) findViewById(R.id.hats_caps);
        walletsBagsPurses = (ImageView) findViewById(R.id.purses_bags_wallets);
        shoes = (ImageView) findViewById(R.id.shoes);

        headPhonesHandFree = (ImageView) findViewById(R.id.headphones_handfree);
        laptops = (ImageView) findViewById(R.id.laptop_pc);
        watches = (ImageView) findViewById(R.id.watches);
        mobilePhones = (ImageView) findViewById(R.id.mobilephones);

        tShirt.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(AdminCategoryActivity.this, AdminAddNewProductActivity.class);
                intent.putExtra("category", "Camisetas");
                startActivity(intent);
            }

        });

        sportShirts.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(AdminCategoryActivity.this, AdminAddNewProductActivity.class);
                intent.putExtra("category", "Camisetas de deporte");
                startActivity(intent);
            }

        });

        femaleDresses.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(AdminCategoryActivity.this, AdminAddNewProductActivity.class);
                intent.putExtra("category", "Vestidos");
                startActivity(intent);
            }

        });

        sweathers.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(AdminCategoryActivity.this, AdminAddNewProductActivity.class);
                intent.putExtra("category", "Sudaderas");
                startActivity(intent);
            }

        });

        glasses.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(AdminCategoryActivity.this, AdminAddNewProductActivity.class);
                intent.putExtra("category", "Lentes");
                startActivity(intent);
            }

        });

        hatCaps.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(AdminCategoryActivity.this, AdminAddNewProductActivity.class);
                intent.putExtra("category", "Gorras");
                startActivity(intent);
            }

        });

        walletsBagsPurses.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(AdminCategoryActivity.this, AdminAddNewProductActivity.class);
                intent.putExtra("category", "Carteras Mochilas Bolsos");
                startActivity(intent);
            }

        });

        shoes.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(AdminCategoryActivity.this, AdminAddNewProductActivity.class);
                intent.putExtra("category", "Calzado");
                startActivity(intent);
            }

        });

        headPhonesHandFree.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(AdminCategoryActivity.this, AdminAddNewProductActivity.class);
                intent.putExtra("category", "Auriculares inalámbricos");
                startActivity(intent);
            }

        });

        laptops.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(AdminCategoryActivity.this, AdminAddNewProductActivity.class);
                intent.putExtra("category", "Portátiles");
                startActivity(intent);
            }

        });

        watches.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(AdminCategoryActivity.this, AdminAddNewProductActivity.class);
                intent.putExtra("category", "Relojes");
                startActivity(intent);
            }

        });

        mobilePhones.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(AdminCategoryActivity.this, AdminAddNewProductActivity.class);
                intent.putExtra("category", "Teléfonos móviles");
                startActivity(intent);
            }

        });
    }
}
