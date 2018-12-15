package dp.com.nabbtabase.view.viewholder;

import android.support.v7.widget.RecyclerView;
import dp.com.nabbtabase.databinding.CommentListItemBinding;
import dp.com.nabbtabase.servise.model.pojo.Comment;
import dp.com.nabbtabase.viewmodel.CommentItemViewModel;

public class CommentViewHolder extends RecyclerView.ViewHolder {
    CommentListItemBinding binding;

    public CommentViewHolder(CommentListItemBinding binding) {
        super(binding.getRoot());
        this.binding=binding;
    }

    public void bindComment(Comment comment){
        if (binding.getViewModel()==null){
            binding.setViewModel(new CommentItemViewModel(comment));
        }else {
            binding.getViewModel().setComment(comment);
        }
    }
}
