package dp.com.nabbtabase.servise.model.response;

import com.google.gson.annotations.SerializedName;

public class CheckMailResponse {

    @SerializedName("exists")
    private String exist;

    public String getExist() {
        return exist;
    }

    public void setExist(String exist) {
        this.exist = exist;
    }
}
