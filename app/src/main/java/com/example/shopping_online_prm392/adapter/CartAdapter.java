package com.example.shopping_online_prm392.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shopping_online_prm392.R;
import com.example.shopping_online_prm392.model.Cart;
import com.example.shopping_online_prm392.model.Product;

import java.util.HashMap;
import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {

    private List<Cart> cartProducts;
    private IClickListener mIClickListener;
    public interface IClickListener {
        void OnClickAddMoreProduct(Cart cart);
        void OnClickRemoveProduct(Cart cart);
    }

    public CartAdapter(List<Cart> cartProducts, IClickListener mIClickListener) {
        this.cartProducts = cartProducts;
        this.mIClickListener = mIClickListener;
    }

    @NonNull
    @Override
    public CartAdapter.CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Bind Item view holder to parent
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cart_layout,parent,false);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartAdapter.CartViewHolder holder, int position) {
    // Bind data to card
    Cart c = cartProducts.get(position);
    holder.productName.setText(c.getProduct().getName());
    holder.addProduct.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mIClickListener.OnClickAddMoreProduct(c);

        }
    });
    holder.removeProduct.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mIClickListener.OnClickRemoveProduct(c);
        }
    });
    }

    @Override
    public int getItemCount() {

        return cartProducts.size();
    }

    public class CartViewHolder extends RecyclerView.ViewHolder {
        private ImageView productImage;
        private Button addProduct;
        private Button removeProduct;
        private TextView quantityProduct;
        private TextView productName;
        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            BindingView();
        }
        private  void BindingView(){
            productImage = itemView.findViewById(R.id.item_image);
            addProduct = itemView.findViewById(R.id.increase_btn);
            removeProduct = itemView.findViewById(R.id.decrease_btn);
            quantityProduct = itemView.findViewById(R.id.quantity_product);
            productName = itemView.findViewById(R.id.product_name);
        }
        public void SetData(Cart cartModel){

            productName.setText(cartModel.getProduct().getName());
            quantityProduct.setText(cartModel.getQuantity());
        }

    }
}
