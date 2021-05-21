package com.example.tfg.viewholders;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tfg.Interface.ItemClickListener;
import com.example.tfg.R;

public class CartViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView txtProductoNombre;
    public TextView txtProductoPrecio;
    public TextView txtProductoCantidad;
    private ItemClickListener itemClickListener;

    public CartViewHolder(@NonNull View itemView) {
        super(itemView);

        txtProductoNombre = itemView.findViewById(R.id.cart_product_name);
        txtProductoPrecio = itemView.findViewById(R.id.cart_product_price);
        txtProductoCantidad = itemView.findViewById(R.id.cart_product_quantity);

    }

    @Override
    public void onClick(View v) {
        itemClickListener.onClick(v, getAdapterPosition(), false);
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }
}
