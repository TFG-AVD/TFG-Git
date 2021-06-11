package com.example.tfg.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.tfg.R;
import com.example.tfg.comprador.HomeActivity;
import com.example.tfg.comprador.MainActivity;

public class AdminHomeActivity extends AppCompatActivity {

    private Button logoutBtn;
    private Button administrarProductosBtn;
    private Button revisarPedidosBtn;
    private Button productosAceptadosBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);


        logoutBtn = (Button) findViewById(R.id.admin_logout_btn);
        revisarPedidosBtn = (Button) findViewById(R.id.revisar_pedidos_btn);
        administrarProductosBtn = (Button) findViewById(R.id.maintain_btn);
        productosAceptadosBtn = (Button) findViewById(R.id.revisar_aceptar_productos_btn);

        administrarProductosBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminHomeActivity.this, HomeActivity.class);
                intent.putExtra("Admin", "Admin");
                startActivity(intent);
            }
        });

        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminHomeActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }
        });

        revisarPedidosBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminHomeActivity.this, AdminNewOrderActivity.class);
                intent.putExtra("Admin", "Admin");
                startActivity(intent);
            }
        });

        productosAceptadosBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminHomeActivity.this, AdminCheckNewProductsActivity.class);
                intent.putExtra("Admin", "Admin");
                startActivity(intent);
            }
        });
    }
}