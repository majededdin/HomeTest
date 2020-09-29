package majed.eddin.shaadoowapp.data.model;

import android.os.Parcel;
import android.os.Parcelable;

public class InterActionsCounter implements Parcelable {

    private int comments;
    private int likes;
    private int points;

    protected InterActionsCounter(Parcel in) {
        comments = in.readInt();
        likes = in.readInt();
        points = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(comments);
        dest.writeInt(likes);
        dest.writeInt(points);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<InterActionsCounter> CREATOR = new Creator<InterActionsCounter>() {
        @Override
        public InterActionsCounter createFromParcel(Parcel in) {
            return new InterActionsCounter(in);
        }

        @Override
        public InterActionsCounter[] newArray(int size) {
            return new InterActionsCounter[size];
        }
    };

    public int getComments() {
        return comments;
    }

    public void setComments(int comments) {
        this.comments = comments;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }
}
