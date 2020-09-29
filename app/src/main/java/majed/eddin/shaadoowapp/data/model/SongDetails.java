package majed.eddin.shaadoowapp.data.model;

import android.os.Parcel;
import android.os.Parcelable;

public class SongDetails implements Parcelable {

    private int id;
    private String title;
    private int published;
    private String cover_img_url;

    protected SongDetails(Parcel in) {
        id = in.readInt();
        title = in.readString();
        published = in.readInt();
        cover_img_url = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(title);
        dest.writeInt(published);
        dest.writeString(cover_img_url);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<SongDetails> CREATOR = new Creator<SongDetails>() {
        @Override
        public SongDetails createFromParcel(Parcel in) {
            return new SongDetails(in);
        }

        @Override
        public SongDetails[] newArray(int size) {
            return new SongDetails[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getPublished() {
        return published;
    }

    public void setPublished(int published) {
        this.published = published;
    }

    public String getCover_img_url() {
        return cover_img_url;
    }

    public void setCover_img_url(String cover_img_url) {
        this.cover_img_url = cover_img_url;
    }
}
