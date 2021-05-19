package com.example.tfg.sellers;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.tfg.R;
import com.example.tfg.buyers.MainActivity;

public class SellerRegistrationActivity extends AppCompatActivity {

    private TextView sellerLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_registration);

        sellerLogin = (TextView)findViewById(R.id.seller_begin);


    }
}