package dp.com.nabbtabase.viewmodel;

import dp.com.nabbtabase.servise.model.pojo.Comment;

public class CommentItemViewModel {
    private Comment comment;

    public CommentItemViewModel(Comment comment) {
        this.comment = comment;
    }

    public void setComment(Comment comment) {
        this.comment = comment;
    }

    public String getComment(){
        return comment.getComment();
    }

    public float getRate(){
        return comment.getRate();
    }


}
