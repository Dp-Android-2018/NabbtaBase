package dp.com.nabbtabase.view.viewholder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;

import dp.com.nabbtabase.databinding.CardListItemBinding;
import dp.com.nabbtabase.servise.model.pojo.CartProduct;
import dp.com.nabbtabase.viewmodel.CartListItemViewModel;

public class CartViewHolder extends RecyclerView.ViewHolder {
    CardListItemBinding binding;
    public CartViewHolder(@NonNull CardListItemBinding binding) {
        super(binding.getRoot());
        this.binding=binding;
    }

    public void bindCartItem(CartProduct cartProduct){
        if(binding.getViewModel()==null){
            binding.setViewModel(new CartListItemViewModel(cartProduct,itemView.getContext()));
        }else {
            binding.getViewModel().setCartProduct(cartProduct);
        }
    }
}
