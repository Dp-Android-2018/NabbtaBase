package dp.com.nabbtabase.view.viewholder;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import dp.com.nabbtabase.databinding.OrderListItemBinding;
import dp.com.nabbtabase.servise.model.pojo.OrderItem;
import dp.com.nabbtabase.viewmodel.OrderListItemViewModel;

public class OrderHistoryViewHolder extends RecyclerView.ViewHolder {
    OrderListItemBinding binding;

    public OrderHistoryViewHolder(@NonNull OrderListItemBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    public void bindItem(OrderItem item) {
        if (binding.getViewModel() == null) {
            binding.setViewModel(new OrderListItemViewModel(item, itemView.getContext()));
        } else {
            binding.getViewModel().setOrderItem(item);
        }
    }
}
