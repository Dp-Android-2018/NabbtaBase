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
import dp.com.nabbtabase.databinding.OrderListItemBinding;
import dp.com.nabbtabase.servise.model.pojo.OrderItem;
import dp.com.nabbtabase.view.viewholder.OrderHistoryViewHolder;

public class OrderHistoryAdapter extends RecyclerView.Adapter<OrderHistoryViewHolder> {

    List<OrderItem> historyItems;

    public void setHistoryItems(List<OrderItem> historyItems) {
        if (this.historyItems == null) {
            this.historyItems = historyItems;
            notifyItemRangeInserted(0, historyItems.size());
        } else {
            DiffUtil.DiffResult result = DiffUtil.calculateDiff(new DiffUtil.Callback() {
                @Override
                public int getOldListSize() {
                    return OrderHistoryAdapter.this.historyItems.size();
                }

                @Override
                public int getNewListSize() {
                    return historyItems.size();
                }

                @Override
                public boolean areItemsTheSame(int oldPosition, int newPosition) {
                    return OrderHistoryAdapter.this.historyItems.get(oldPosition).getId() ==
                            historyItems.get(newPosition).getId();
                }

                @Override
                public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                    OrderItem newHistoryItem = historyItems.get(newItemPosition);
                    OrderItem oldHistoryItem = historyItems.get(oldItemPosition);

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
    public OrderHistoryViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        OrderListItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()), R.layout.order_list_item, viewGroup, false);
        return new OrderHistoryViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderHistoryViewHolder orderHistoryViewHolder, int i) {
        orderHistoryViewHolder.bindItem(historyItems.get(i));
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
