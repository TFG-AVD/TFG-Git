package com.example.tfg.viewholders;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tfg.Interface.ItemClickListener;
import com.example.tfg.R;

public class ProductViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
{
    public TextView txtProductoNombre;
    public TextView txtProductoDescripcion;
    public TextView txtProductoPrecio;
    public TextView txtNombreTienda;
    public TextView txtCategoria;

    public ImageView imageView;
    public ItemClickListener listener;

    public ProductViewHolder(@NonNull View itemView) {
        super(itemView);

        imageView = (ImageView) itemView.findViewById(R.id.producto_imagen);
        txtProductoNombre = (TextView) itemView.findViewById(R.id.producto_nombre);
        txtProductoDescripcion = (TextView) itemView.findViewById(R.id.producto_descripcion);
        txtProductoPrecio = (TextView) itemView.findViewById(R.id.producto_precio);
        txtNombreTienda = (TextView) itemView.findViewById(R.id.tienda_nombre);
        txtCategoria = (TextView) itemView.findViewById(R.id.categoria_nombre);

    }

    public void setItemClickListener(ItemClickListener listener){
        this.listener = listener;
    }

    @Override
    public void onClick(View view)
    {
        listener.onClick(view, getAdapterPosition(), false);
    }
}
