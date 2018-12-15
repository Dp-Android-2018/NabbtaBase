package dp.com.nabbtabase.servise.model.request;

import com.google.gson.annotations.SerializedName;

public class CreateCommentRequest {
    @SerializedName("rate")
    private float rate;

    @SerializedName("product_id")
    private int productId;

    @SerializedName("comment")
    private String comment;

    public float getRate() {
        return rate;
    }

    public void setRate(float rate) {
        this.rate = rate;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
