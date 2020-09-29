package majed.eddin.shaadoowapp.data.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Post implements Parcelable {

    private String _id;
    private int parent;
    private PostOwner created_by;
    private RecordingDetails recording_details;
    private SongDetails song_details;
    private Artist artist_details;
    private InterActionsCounter interactions_counter;
    private int status;
    private int weight;
    private int published;
    private int views;
    private String updated_at;
    private String created_at;
    private boolean is_liked;

    protected Post(Parcel in) {
        _id = in.readString();
        parent = in.readInt();
        created_by = in.readParcelable(PostOwner.class.getClassLoader());
        recording_details = in.readParcelable(RecordingDetails.class.getClassLoader());
        song_details = in.readParcelable(SongDetails.class.getClassLoader());
        artist_details = in.readParcelable(Artist.class.getClassLoader());
        interactions_counter = in.readParcelable(InterActionsCounter.class.getClassLoader());
        status = in.readInt();
        weight = in.readInt();
        published = in.readInt();
        views = in.readInt();
        updated_at = in.readString();
        created_at = in.readString();
        is_liked = in.readByte() != 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(_id);
        dest.writeInt(parent);
        dest.writeParcelable(created_by, flags);
        dest.writeParcelable(recording_details, flags);
        dest.writeParcelable(song_details, flags);
        dest.writeParcelable(artist_details, flags);
        dest.writeParcelable(interactions_counter, flags);
        dest.writeInt(status);
        dest.writeInt(weight);
        dest.writeInt(published);
        dest.writeInt(views);
        dest.writeString(updated_at);
        dest.writeString(created_at);
        dest.writeByte((byte) (is_liked ? 1 : 0));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Post> CREATOR = new Creator<Post>() {
        @Override
        public Post createFromParcel(Parcel in) {
            return new Post(in);
        }

        @Override
        public Post[] newArray(int size) {
            return new Post[size];
        }
    };

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public int getParent() {
        return parent;
    }

    public void setParent(int parent) {
        this.parent = parent;
    }

    public PostOwner getCreated_by() {
        return created_by;
    }

    public void setCreated_by(PostOwner created_by) {
        this.created_by = created_by;
    }

    public RecordingDetails getRecording_details() {
        return recording_details;
    }

    public void setRecording_details(RecordingDetails recording_details) {
        this.recording_details = recording_details;
    }

    public SongDetails getSong_details() {
        return song_details;
    }

    public void setSong_details(SongDetails song_details) {
        this.song_details = song_details;
    }

    public Artist getArtist_details() {
        return artist_details;
    }

    public void setArtist_details(Artist artist_details) {
        this.artist_details = artist_details;
    }

    public InterActionsCounter getInteractions_counter() {
        return interactions_counter;
    }

    public void setInteractions_counter(InterActionsCounter interactions_counter) {
        this.interactions_counter = interactions_counter;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getPublished() {
        return published;
    }

    public void setPublished(int published) {
        this.published = published;
    }

    public int getViews() {
        return views;
    }

    public void setViews(int views) {
        this.views = views;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public boolean isIs_liked() {
        return is_liked;
    }

    public void setIs_liked(boolean is_liked) {
        this.is_liked = is_liked;
    }
}
