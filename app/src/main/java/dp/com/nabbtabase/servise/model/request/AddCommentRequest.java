package dp.com.nabbtabase.servise.model.request;

import com.google.gson.annotations.SerializedName;

public class AddCommentRequest {

    @SerializedName("comment")
    private String comment;

    @SerializedName("rate")
    private float rate;

    @SerializedName("product_id")
    private int product_id;

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public float getRate() {
        return rate;
    }

    public void setRate(float rate) {
        this.rate = rate;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }
}
