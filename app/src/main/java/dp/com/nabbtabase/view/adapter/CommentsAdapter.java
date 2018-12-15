package dp.com.nabbtabase.view.adapter;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.List;
import java.util.Objects;

import dp.com.nabbtabase.R;
import dp.com.nabbtabase.databinding.CommentListItemBinding;
import dp.com.nabbtabase.servise.model.pojo.Comment;
import dp.com.nabbtabase.view.viewholder.CommentViewHolder;

public class CommentsAdapter extends RecyclerView.Adapter<CommentViewHolder> {

    List<?extends Comment>comments;


    public void setComments(List<? extends Comment> comments) {
        if (this.comments==null){
            this.comments = comments;
            notifyItemRangeInserted(0,comments.size());
        }else{
            DiffUtil.DiffResult result=DiffUtil.calculateDiff(new DiffUtil.Callback() {
                @Override
                public int getOldListSize() {
                    return CommentsAdapter.this.comments.size();
                }

                @Override
                public int getNewListSize() {
                    return comments.size();
                }

                @Override
                public boolean areItemsTheSame(int oldPosition , int newPosition) {
                    return CommentsAdapter.this.comments.get(oldPosition).getId()==
                            comments.get(newPosition).getId();
                }

                @Override
                public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                    Comment newComment=comments.get(newItemPosition);
                    Comment oldComment=comments.get(oldItemPosition);

                    return newComment.getId()==oldComment.getId() &&
                            Objects.equals(newComment.getId(),oldComment.getId());
                }
            });
            this.comments=comments;
            result.dispatchUpdatesTo(this);
        }

        System.out.println("Comments size adapter : "+this.comments.size());
    }

    @NonNull
    @Override
    public CommentViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        CommentListItemBinding binding=DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()), R.layout.comment_list_item,viewGroup,false);
        return new CommentViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentViewHolder commentViewHolder, int i) {
        commentViewHolder.bindComment(comments.get(i));

    }

    @Override
    public int getItemCount() {
        return comments==null?0:comments.size();
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
