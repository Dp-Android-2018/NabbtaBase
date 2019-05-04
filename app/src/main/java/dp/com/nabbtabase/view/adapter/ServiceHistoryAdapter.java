package dp.com.nabbtabase.view.adapter;

import androidx.databinding.DataBindingUtil;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.List;
import java.util.Objects;

import dp.com.nabbtabase.R;
import dp.com.nabbtabase.databinding.ListItemServiceHistoryBinding;
import dp.com.nabbtabase.servise.model.pojo.ServiceHistoryItem;
import dp.com.nabbtabase.view.viewholder.ServiceHistoryViewHolder;

public class ServiceHistoryAdapter extends RecyclerView.Adapter<ServiceHistoryViewHolder> {

    List<ServiceHistoryItem> historyItems;

    public void setHistoryItems(List<ServiceHistoryItem> historyItems) {
        if (this.historyItems == null) {
            this.historyItems = historyItems;
            notifyItemRangeInserted(0, historyItems.size());
        } else {
            DiffUtil.DiffResult result = DiffUtil.calculateDiff(new DiffUtil.Callback() {
                @Override
                public int getOldListSize() {
                    return ServiceHistoryAdapter.this.historyItems.size();
                }

                @Override
                public int getNewListSize() {
                    return historyItems.size();
                }

                @Override
                public boolean areItemsTheSame(int oldPosition, int newPosition) {
                    return ServiceHistoryAdapter.this.historyItems.get(oldPosition).getId() ==
                            historyItems.get(newPosition).getId();
                }

                @Override
                public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                    ServiceHistoryItem newHistoryItem = historyItems.get(newItemPosition);
                    ServiceHistoryItem oldHistoryItem = historyItems.get(oldItemPosition);

                    return newHistoryItem.getId() == oldHistoryItem.getId() &&
                            Objects.equals(newHistoryItem.getId(), oldHistoryItem.getId());
                }
            });
            this.historyItems = historyItems;
            result.dispatchUpdatesTo(this);
        }
    }

    @NonNull
    @Override
    public ServiceHistoryViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        ListItemServiceHistoryBinding binding = DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()), R.layout.list_item_service_history, viewGroup, false);
        return new ServiceHistoryViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ServiceHistoryViewHolder serviceHistoryViewHolder, int i) {
        serviceHistoryViewHolder.bindItem(historyItems.get(i));
    }

    @Override
    public int getItemCount() {
        return historyItems != null ? historyItems.size() : 0;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
}
