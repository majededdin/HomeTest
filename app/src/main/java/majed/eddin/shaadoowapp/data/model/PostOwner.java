package majed.eddin.shaadoowapp.data.model;

import android.os.Parcel;
import android.os.Parcelable;

public class PostOwner implements Parcelable {

    private int id;
    private String name;
    private String slug;
    private String profile_image_url;
    private int artist_id;
    private int verified;
    private boolean vip;

    protected PostOwner(Parcel in) {
        id = in.readInt();
        name = in.readString();
        slug = in.readString();
        profile_image_url = in.readString();
        artist_id = in.readInt();
        verified = in.readInt();
        vip = in.readByte() != 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeString(slug);
        dest.writeString(profile_image_url);
        dest.writeInt(artist_id);
        dest.writeInt(verified);
        dest.writeByte((byte) (vip ? 1 : 0));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<PostOwner> CREATOR = new Creator<PostOwner>() {
        @Override
        public PostOwner createFromParcel(Parcel in) {
            return new PostOwner(in);
        }

        @Override
        public PostOwner[] newArray(int size) {
            return new PostOwner[size];
        }
    };

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

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getProfile_image_url() {
        return profile_image_url;
    }

    public void setProfile_image_url(String profile_image_url) {
        this.profile_image_url = profile_image_url;
    }

    public int getArtist_id() {
        return artist_id;
    }

    public void setArtist_id(int artist_id) {
        this.artist_id = artist_id;
    }

    public int getVerified() {
        return verified;
    }

    public void setVerified(int verified) {
        this.verified = verified;
    }

    public boolean isVip() {
        return vip;
    }

    public void setVip(boolean vip) {
        this.vip = vip;
    }
}
