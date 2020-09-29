package majed.eddin.shaadoowapp.data.model;

import android.os.Parcel;
import android.os.Parcelable;

public class RecordingDetails implements Parcelable {

    private float duration;
    private String type;
    private String description;
    private String cover_img_url;
    private String recording_url;
    private int status;
    private int recording_id;
    private String streaming_url;
    private String streaming_hls;

    protected RecordingDetails(Parcel in) {
        duration = in.readFloat();
        type = in.readString();
        description = in.readString();
        cover_img_url = in.readString();
        recording_url = in.readString();
        status = in.readInt();
        recording_id = in.readInt();
        streaming_url = in.readString();
        streaming_hls = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeFloat(duration);
        dest.writeString(type);
        dest.writeString(description);
        dest.writeString(cover_img_url);
        dest.writeString(recording_url);
        dest.writeInt(status);
        dest.writeInt(recording_id);
        dest.writeString(streaming_url);
        dest.writeString(streaming_hls);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<RecordingDetails> CREATOR = new Creator<RecordingDetails>() {
        @Override
        public RecordingDetails createFromParcel(Parcel in) {
            return new RecordingDetails(in);
        }

        @Override
        public RecordingDetails[] newArray(int size) {
            return new RecordingDetails[size];
        }
    };

    public float getDuration() {
        return duration;
    }

    public void setDuration(float duration) {
        this.duration = duration;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCover_img_url() {
        return cover_img_url;
    }

    public void setCover_img_url(String cover_img_url) {
        this.cover_img_url = cover_img_url;
    }

    public String getRecording_url() {
        return recording_url;
    }

    public void setRecording_url(String recording_url) {
        this.recording_url = recording_url;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getRecording_id() {
        return recording_id;
    }

    public void setRecording_id(int recording_id) {
        this.recording_id = recording_id;
    }

    public String getStreaming_url() {
        return streaming_url;
    }

    public void setStreaming_url(String streaming_url) {
        this.streaming_url = streaming_url;
    }

    public String getStreaming_hls() {
        return streaming_hls;
    }

    public void setStreaming_hls(String streaming_hls) {
        this.streaming_hls = streaming_hls;
    }
}
