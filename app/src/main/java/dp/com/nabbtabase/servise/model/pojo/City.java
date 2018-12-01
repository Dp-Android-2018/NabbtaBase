package dp.com.nabbtabase.servise.model.pojo;

import com.google.gson.annotations.SerializedName;

public class City {
    @SerializedName("id")
    private int id;

    @SerializedName("name")
    private String name;

    @SerializedName("shipping_cost")
    private int shipping_cost;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getShipping_cost() {
        return shipping_cost;
    }

    public void setShipping_cost(int shipping_cost) {
        this.shipping_cost = shipping_cost;
    }
}
