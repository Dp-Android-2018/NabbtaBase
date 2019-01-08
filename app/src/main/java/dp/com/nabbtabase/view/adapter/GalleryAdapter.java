package dp.com.nabbtabase.view.adapter;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import dp.com.nabbtabase.R;
import dp.com.nabbtabase.databinding.RecyclerItemImageViewBinding;
import dp.com.nabbtabase.view.callback.DeleteImageIterface;
import dp.com.nabbtabase.view.viewholder.ImageItemViewHolder;

public class GalleryAdapter extends RecyclerView.Adapter<ImageItemViewHolder> {
    List<String>imageUrls;
    DeleteImageIterface deleteImageIterface;

    public GalleryAdapter(DeleteImageIterface deleteImageIterface) {
        this.deleteImageIterface = deleteImageIterface;
    }

    public void setImageUrls(List<String> imageUrls) {
        this.imageUrls = imageUrls;
    }

    @NonNull
    @Override
    public ImageItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        RecyclerItemImageViewBinding binding=DataBindingUtil.inflate(
                LayoutInflater.from(viewGroup.getContext()),R.layout.recycler_item_image_view,viewGroup,false);

        return new ImageItemViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageItemViewHolder imageItemViewHolder, int i) {
        imageItemViewHolder.bindImage(imageUrls.get(i));
        imageItemViewHolder.imageView.setOnClickListener(v -> {
            deleteImageIterface.deleteImage(i);
        });
    }

    @Override
    public int getItemCount() {
        return imageUrls.size();
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
