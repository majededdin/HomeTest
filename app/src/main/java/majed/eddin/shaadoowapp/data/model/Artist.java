package majed.eddin.shaadoowapp.data.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Artist implements Parcelable {

    private int artist_id;
    private String slug;
    private String name;
    private String names;
    private int category;
    private String description;
    private int region_id;
    private String cover_img_url;
    private String profile_img_url;
    private int published;
    private int rank;
    private String created_at;
    private String updated_at;
    private String deleted_at;
    private int profile_id;
    private int followers;

    protected Artist(Parcel in) {
        artist_id = in.readInt();
        slug = in.readString();
        name = in.readString();
        names = in.readString();
        category = in.readInt();
        description = in.readString();
        region_id = in.readInt();
        cover_img_url = in.readString();
        profile_img_url = in.readString();
        published = in.readInt();
        rank = in.readInt();
        created_at = in.readString();
        updated_at = in.readString();
        deleted_at = in.readString();
        profile_id = in.readInt();
        followers = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(artist_id);
        dest.writeString(slug);
        dest.writeString(name);
        dest.writeString(names);
        dest.writeInt(category);
        dest.writeString(description);
        dest.writeInt(region_id);
        dest.writeString(cover_img_url);
        dest.writeString(profile_img_url);
        dest.writeInt(published);
        dest.writeInt(rank);
        dest.writeString(created_at);
        dest.writeString(updated_at);
        dest.writeString(deleted_at);
        dest.writeInt(profile_id);
        dest.writeInt(followers);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Artist> CREATOR = new Creator<Artist>() {
        @Override
        public Artist createFromParcel(Parcel in) {
            return new Artist(in);
        }

        @Override
        public Artist[] newArray(int size) {
            return new Artist[size];
        }
    };

    public int getArtist_id() {
        return artist_id;
    }

    public void setArtist_id(int artist_id) {
        this.artist_id = artist_id;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNames() {
        return names;
    }

    public void setNames(String names) {
        this.names = names;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getRegion_id() {
        return region_id;
    }

    public void setRegion_id(int region_id) {
        this.region_id = region_id;
    }

    public String getCover_img_url() {
        return cover_img_url;
    }

    public void setCover_img_url(String cover_img_url) {
        this.cover_img_url = cover_img_url;
    }

    public String getProfile_img_url() {
        return profile_img_url;
    }

    public void setProfile_img_url(String profile_img_url) {
        this.profile_img_url = profile_img_url;
    }

    public int getPublished() {
        return published;
    }

    public void setPublished(int published) {
        this.published = published;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public String getDeleted_at() {
        return deleted_at;
    }

    public void setDeleted_at(String deleted_at) {
        this.deleted_at = deleted_at;
    }

    public int getProfile_id() {
        return profile_id;
    }

    public void setProfile_id(int profile_id) {
        this.profile_id = profile_id;
    }

    public int getFollowers() {
        return followers;
    }

    public void setFollowers(int followers) {
        this.followers = followers;
    }
}
