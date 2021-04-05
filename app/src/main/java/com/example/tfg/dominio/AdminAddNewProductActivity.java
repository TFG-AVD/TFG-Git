package com.example.tfg.dominio;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.example.tfg.R;

public class AdminAddNewProductActivity extends AppCompatActivity {

    private String CategoryName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_add_new_product);

        CategoryName = getIntent().getExtras().get("category").toString();

        Toast.makeText(this, CategoryName, Toast.LENGTH_SHORT).show();
    }
}