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
import dp.com.nabbtabase.databinding.ResetItemBinding;
import dp.com.nabbtabase.servise.model.pojo.ResetProduct;
import dp.com.nabbtabase.view.viewholder.ResetItemViewHolder;

public class ResetAdapter extends RecyclerView.Adapter<ResetItemViewHolder> {
    List<ResetProduct> resetProducts;

    public void setResetProducts(List<ResetProduct> resetProducts) {
        if (this.resetProducts == null) {
            this.resetProducts = resetProducts;
            notifyItemRangeInserted(0, resetProducts.size());
        } else {
            DiffUtil.DiffResult result = DiffUtil.calculateDiff(new DiffUtil.Callback() {
                @Override
                public int getOldListSize() {
                    return ResetAdapter.this.resetProducts.size();
                }

                @Override
                public int getNewListSize() {
                    return resetProducts.size();
                }

                @Override
                public boolean areItemsTheSame(int oldPosition, int newPosition) {
                    return ResetAdapter.this.resetProducts.get(oldPosition).getId() ==
                            resetProducts.get(newPosition).getId();
                }

                @Override
                public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                    ResetProduct newHistoryItem = resetProducts.get(newItemPosition);
                    ResetProduct oldHistoryItem = resetProducts.get(oldItemPosition);

                    return newHistoryItem.getId() == oldHistoryItem.getId() &&
                            Objects.equals(newHistoryItem.getId(), oldHistoryItem.getId());
                }
            });
            this.resetProducts = resetProducts;
            result.dispatchUpdatesTo(this);
        }
    }

    @NonNull
    @Override
    public ResetItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        ResetItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()), R.layout.reset_item, viewGroup, false);
        return new ResetItemViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ResetItemViewHolder resetItemViewHolder, int i) {

        resetItemViewHolder.bindItem(resetProducts.get(i));
    }

    @Override
    public int getItemCount() {
        return resetProducts != null ? resetProducts.size() : 0;
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
