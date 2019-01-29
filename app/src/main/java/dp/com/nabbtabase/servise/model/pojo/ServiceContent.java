package dp.com.nabbtabase.servise.model.pojo;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;


public class ServiceContent implements Parcelable {
    @SerializedName("id")
    private int id;

    @SerializedName("name")
    private String name;

    @SerializedName("image")
    private String imageUrl;


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

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.name);
        dest.writeString(this.imageUrl);
        dest.writeString(this.description);
    }

    public ServiceContent(Parcel in) {
        id = in.readInt();
        name = in.readString();
        imageUrl = in.readString();
        description = in.readString();
    }

    public static final Creator<ServiceContent> CREATOR = new Creator<ServiceContent>() {
        @Override
        public ServiceContent createFromParcel(Parcel in) {
            return new ServiceContent(in);
        }

        @Override
        public ServiceContent[] newArray(int size) {
            return new ServiceContent[size];
        }
    };
}
