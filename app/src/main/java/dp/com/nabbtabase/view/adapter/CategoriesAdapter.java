package dp.com.nabbtabase.view.adapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import dp.com.nabbtabase.R;
import dp.com.nabbtabase.servise.model.pojo.Category;
import dp.com.nabbtabase.servise.model.pojo.SubCategory;
import dp.com.nabbtabase.view.callback.CloseCountryDialogInterface;
import dp.com.nabbtabase.view.viewholder.PopupRecyclerViewHolder;

public class CategoriesAdapter extends RecyclerView.Adapter<PopupRecyclerViewHolder> {
    private List<Category> categories;
    private CloseCountryDialogInterface closeDialog;
    private int select;
    private List<SubCategory> subCategories = new ArrayList<>();

    public CategoriesAdapter(CloseCountryDialogInterface closeDialog) {
        this.closeDialog = closeDialog;
        select = 0;
    }

    public void setCategories(List<Category> categories) {
        if (this.categories == null) {
            this.categories = categories;
            notifyItemRangeInserted(0, categories.size());
        } else {
            DiffUtil.DiffResult result = DiffUtil.calculateDiff(new DiffUtil.Callback() {
                @Override
                public int getOldListSize() {
                    return CategoriesAdapter.this.categories.size();
                }

                @Override
                public int getNewListSize() {
                    return categories.size();
                }

                @Override
                public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                    return CategoriesAdapter.this.categories.get(oldItemPosition).getId() ==
                            categories.get(newItemPosition).getId();
                }

                @Override
                public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                    Category newCategory = categories.get(newItemPosition);
                    Category oldCategory = categories.get(oldItemPosition);

                    return newCategory.getId() == oldCategory.getId()
                            && Objects.equals(newCategory.getName(), oldCategory.getName());
                }
            });
            this.categories = categories;
            result.dispatchUpdatesTo(this);
        }

        select = 1;
    }

    @NonNull
    @Override
    public PopupRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View v = inflater.inflate(R.layout.popup_recycler_list_item, viewGroup, false);
        PopupRecyclerViewHolder viewHolder = new PopupRecyclerViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull PopupRecyclerViewHolder popupRecyclerViewHolder, int i) {
        String name;
        if (select == 1) {
            name = categories.get(i).getName();
        } else {
            name = subCategories.get(i).getName();
        }

        popupRecyclerViewHolder.textView.setText(name);
        popupRecyclerViewHolder.itemView.setOnClickListener(v -> {
            closeDialog.onListItemClicked(i);
        });
    }

    @Override
    public int getItemCount() {
        if (select == 1) {
            return categories.size();
        } else {
            return subCategories.size();
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public void setSubCategories(List<SubCategory> subCategories) {
        this.subCategories = (subCategories);
        select = 2;
    }
}
