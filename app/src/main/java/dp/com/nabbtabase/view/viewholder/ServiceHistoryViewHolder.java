package dp.com.nabbtabase.view.viewholder;

import androidx.recyclerview.widget.RecyclerView;

import dp.com.nabbtabase.databinding.ListItemServiceHistoryBinding;
import dp.com.nabbtabase.servise.model.pojo.ServiceHistoryItem;
import dp.com.nabbtabase.viewmodel.ListItemServiceHistoryViewModel;

public class ServiceHistoryViewHolder extends RecyclerView.ViewHolder {
    ListItemServiceHistoryBinding binding;

    public ServiceHistoryViewHolder(ListItemServiceHistoryBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    public void bindItem(ServiceHistoryItem historyItem) {
        if (binding.getViewModel() == null) {
            binding.setViewModel(new ListItemServiceHistoryViewModel(historyItem));
        } else {
            binding.getViewModel().setServiceHistoryItem(historyItem);
        }
    }
}
