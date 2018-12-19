package dp.com.nabbtabase.servise.model.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import dp.com.nabbtabase.servise.model.pojo.Category;

public class CategoryResponse {

    @SerializedName("data")
    private List<Category> categories;

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }
}
