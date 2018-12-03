package dp.com.nabbtabase.servise.model.pojo;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Country {
    @SerializedName("id")
    private int id;

    @SerializedName("name")
    private String name;

    @SerializedName("cities")
    private List<City> cities;

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

    public List<City> getCities() {
        return cities;
    }

    public void setCities(List<City> cities) {
        this.cities = cities;
    }
}
