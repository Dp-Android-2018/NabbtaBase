package dp.com.nabbtabase.view.viewholder;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jakewharton.rxbinding.widget.RxTextView;

import java.util.concurrent.TimeUnit;

import dp.com.nabbtabase.databinding.CardListItemBinding;
import dp.com.nabbtabase.servise.model.pojo.CartProduct;
import dp.com.nabbtabase.viewmodel.CartListItemViewModel;

public class CartViewHolder extends RecyclerView.ViewHolder {
    CardListItemBinding binding;
    CartListItemViewModel cartListItemViewModel;

    public CartViewHolder(@NonNull CardListItemBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    public void bindCartItem(CartProduct cartProduct) {
        if (binding.getViewModel() == null) {
            cartListItemViewModel = new CartListItemViewModel(cartProduct, itemView.getContext());
            binding.setViewModel(cartListItemViewModel);
        } else {
            binding.getViewModel().setCartProduct(cartProduct);

        }

        RxTextView.textChanges(binding.etQuentanaty)
                .debounce(3000, TimeUnit.MILLISECONDS)
                .subscribe(charSequence -> {
                    System.out.println("in text value : " + charSequence);
                    //cartListItemViewModel.updateQuantanty(charSequence);
                });
    }
}
