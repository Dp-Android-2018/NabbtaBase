package dp.com.nabbtabase.servise.model.pojo;

import com.google.gson.annotations.SerializedName;

public class Comment {

    @SerializedName("id")
    private int id;

    @SerializedName("rate")
    private float rate;

    @SerializedName("comment")
    private String comment;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getRate() {
        return rate;
    }

    public void setRate(float rate) {
        this.rate = rate;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
