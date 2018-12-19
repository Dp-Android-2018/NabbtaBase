package dp.com.nabbtabase.servise.model.pojo;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

public class ServiceContent implements Serializable {
    @SerializedName("id")
    private int id;

    @SerializedName("name")
    private String name;

    @SerializedName("description")
    private String description;


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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }



//    @Override
//    public void writeToParcel(Parcel dest, int flags) {
//        dest.writeString(this.name);
//        dest.writeString(this.description);
//        dest.writeInt(this.id);
//    }
//
//    public ServiceContent(Parcel in) {
//        id = in.readInt();
//        name = in.readString();
//        description = in.readString();
//    }
//
//    public static final Creator<ServiceContent> CREATOR = new Creator<ServiceContent>() {
//        @Override
//        public ServiceContent createFromParcel(Parcel in) {
//            return new ServiceContent(in);
//        }
//
//        @Override
//        public ServiceContent[] newArray(int size) {
//            return new ServiceContent[size];
//        }
//    };
//
//    @Override
//    public int describeContents() {
//        return hashCode();
//    }
}
