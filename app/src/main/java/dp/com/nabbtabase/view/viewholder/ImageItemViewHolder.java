package dp.com.nabbtabase.view.viewholder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import dp.com.nabbtabase.databinding.RecyclerItemImageViewBinding;
import dp.com.nabbtabase.viewmodel.RecyclerItemImageView;

public class ImageItemViewHolder extends RecyclerView.ViewHolder {
    RecyclerItemImageViewBinding binding;
    public ImageView  imageView;

    public ImageItemViewHolder(@NonNull RecyclerItemImageViewBinding binding) {
        super(binding.getRoot());
        this.binding=binding;
        imageView=binding.deleteImage;
    }

    public void bindImage(String imageUrl){
        if(binding.getViewModel()!=null){
            binding.getViewModel().setImageUrl(imageUrl);
        }else {
            binding.setViewModel(new RecyclerItemImageView(imageUrl));
        }
    }
}
