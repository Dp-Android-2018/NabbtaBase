package dp.com.nabbtabase.view.viewholder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import dp.com.nabbtabase.databinding.ProductItemGridLayoutBinding;
import dp.com.nabbtabase.servise.model.pojo.Product;
import dp.com.nabbtabase.viewmodel.ProductItemViewModel;

public class ProductViewHolder extends RecyclerView.ViewHolder {
    public ProductItemGridLayoutBinding binding;
    public ProductViewHolder(@NonNull ProductItemGridLayoutBinding binding) {
        super(binding.getRoot());
        this.binding=binding;
    }
    public void bindProduct(Product product){
        if (binding.getViewModel()==null){
            binding.setViewModel(new ProductItemViewModel(product,itemView.getContext()));
        }else {
            binding.getViewModel().product=product;
        }
    }
}


