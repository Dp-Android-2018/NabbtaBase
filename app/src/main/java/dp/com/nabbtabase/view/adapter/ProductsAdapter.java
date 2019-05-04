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
import dp.com.nabbtabase.databinding.ProductItemGridLayoutBinding;
import dp.com.nabbtabase.databinding.ProductItemLinearLayoutBinding;
import dp.com.nabbtabase.servise.model.pojo.Product;
import dp.com.nabbtabase.view.viewholder.ProductViewHolder;

public class ProductsAdapter extends RecyclerView.Adapter<ProductViewHolder> {

    List<? extends Product> products;
    private boolean grid;

    public ProductsAdapter(boolean grid) {
        this.grid = grid;
    }

    public void setProducts(List<? extends Product> products) {
        if (this.products == null) {
            this.products = products;
            notifyItemRangeInserted(0, products.size());
        } else {
            DiffUtil.DiffResult result = DiffUtil.calculateDiff(new DiffUtil.Callback() {
                @Override
                public int getOldListSize() {
                    return ProductsAdapter.this.products.size();
                }

                @Override
                public int getNewListSize() {
                    return products.size();
                }

                @Override
                public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                    return ProductsAdapter.this.products.get(oldItemPosition).getId() ==
                            products.get(newItemPosition).getId();
                }

                @Override
                public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                    Product newProduct = products.get(newItemPosition);
                    Product oldProduct = products.get(oldItemPosition);

                    return newProduct.getId() == oldProduct.getId()
                            && Objects.equals(newProduct.getName(), oldProduct.getName());
                }
            });
            this.products = products;
            result.dispatchUpdatesTo(this);
        }
    }

    public void setGrid(boolean grid) {
        this.grid = grid;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {


        if (grid) {
            ProductItemGridLayoutBinding binding;
            binding = DataBindingUtil.inflate
                    (LayoutInflater.from(parent.getContext()), R.layout.product_item_grid_layout, parent, false);
            return new ProductViewHolder(binding);
        } else {
            ProductItemLinearLayoutBinding binding;
            binding = DataBindingUtil.inflate
                    (LayoutInflater.from(parent.getContext()), R.layout.product_item_linear_layout, parent, false);
            return new ProductViewHolder(binding);
        }

    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        if (grid) {
            holder.bindProduct(products.get(position));
        } else {
            holder.bindProductLinear(products.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return products == null ? 0 : products.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }
}
