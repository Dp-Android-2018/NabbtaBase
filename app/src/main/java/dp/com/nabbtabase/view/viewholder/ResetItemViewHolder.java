package dp.com.nabbtabase.view.viewholder;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import dp.com.nabbtabase.databinding.ResetItemBinding;
import dp.com.nabbtabase.servise.model.pojo.ResetProduct;
import dp.com.nabbtabase.viewmodel.ResetItemViewModel;

public class ResetItemViewHolder extends RecyclerView.ViewHolder {
    ResetItemBinding binding;

    public ResetItemViewHolder(@NonNull ResetItemBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    public void bindItem(ResetProduct resetProduct) {
        if (binding.getViewModel() == null) {
            binding.setViewModel(new ResetItemViewModel(resetProduct));
        } else {
            binding.getViewModel().setProduct(resetProduct);
        }
    }
}
