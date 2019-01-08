package dp.com.nabbtabase.view.adapter;

import android.databinding.DataBindingUtil;
import android.databinding.ObservableInt;
import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.List;
import java.util.Objects;

import dp.com.nabbtabase.R;
import dp.com.nabbtabase.application.MyApp;
import dp.com.nabbtabase.databinding.CardListItemBinding;
import dp.com.nabbtabase.servise.model.pojo.CartProduct;
import dp.com.nabbtabase.view.viewholder.CartViewHolder;

public class CartAdapter extends RecyclerView.Adapter<CartViewHolder> {

    List<CartProduct>cartProducts;

    public void setCartProducts(List<CartProduct> cartProducts) {
        if (this.cartProducts==null){
            this.cartProducts = cartProducts;
            notifyItemRangeInserted(0,cartProducts.size());
        }else{
            DiffUtil.DiffResult result=DiffUtil.calculateDiff(new DiffUtil.Callback() {
                @Override
                public int getOldListSize() {
                    return CartAdapter.this.cartProducts.size();
                }

                @Override
                public int getNewListSize() {
                    return cartProducts.size();
                }

                @Override
                public boolean areItemsTheSame(int oldPosition , int newPosition) {
                    return CartAdapter.this.cartProducts.get(oldPosition).getId()==
                            cartProducts.get(newPosition).getId();
                }

                @Override
                public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                    CartProduct newCartProduct=cartProducts.get(newItemPosition);
                    CartProduct oldCartProduct=cartProducts.get(oldItemPosition);

                    return newCartProduct.getId()==oldCartProduct.getId() &&
                            Objects.equals(newCartProduct.getId(),oldCartProduct.getId());
                }
            });
            this.cartProducts=cartProducts;
            result.dispatchUpdatesTo(this);
        }
    }
    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        CardListItemBinding binding=DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()), R.layout.card_list_item,viewGroup,false);
        return new CartViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder cartViewHolder, int i) {

        cartViewHolder.bindCartItem(cartProducts.get(i));
    }

    @Override
    public int getItemCount() {
        return cartProducts!=null?cartProducts.size():0;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position ;
    }
}
