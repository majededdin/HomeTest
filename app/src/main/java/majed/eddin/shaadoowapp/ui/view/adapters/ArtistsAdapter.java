package majed.eddin.shaadoowapp.ui.view.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import am.leon.LeonImageView;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;
import majed.eddin.shaadoowapp.R;
import majed.eddin.shaadoowapp.data.model.Artist;
import majed.eddin.shaadoowapp.utils.Utils;
import majed.eddin.shaadoowapp.utils.imageUtils.RoundedImage;

public class ArtistsAdapter extends RecyclerView.Adapter<ArtistsAdapter.ViewHolder> {

    private boolean fromExplore;
    private List<Artist> items;
    private ArtistCallback callback;


    public ArtistsAdapter(ArtistCallback callback, boolean fromExplore) {
        this.fromExplore = fromExplore;
        this.callback = callback;
        this.items = new ArrayList<>();
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_artist, parent, false);

        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Artist artist = items.get(position);

        if (fromExplore)
            holder.layout_artist.getLayoutParams().width = ViewGroup.LayoutParams.WRAP_CONTENT;
        else
            holder.layout_artist.getLayoutParams().width = ViewGroup.LayoutParams.MATCH_PARENT;

        holder.txt_artistName.setText(artist.getName());
        holder.img_artist.loadImage(Utils.getImagePath(artist.getProfile_img_url()), new RoundedImage());
        holder.txt_followersCount.setText(Utils.formatNumber(artist.getFollowers()));

    }


    public void add(Artist r) {
        items.add(r);
        notifyItemInserted(items.size() - 1);
    }


    public void addAll(List<Artist> items) {
        for (Artist result : items) {
            add(result);
        }
    }


    public void clear() {
        items.clear();
        notifyDataSetChanged();
    }


    @Override
    public int getItemCount() {
        if (fromExplore) {
            return Math.min(items.size(), 8);
        } else
            return items.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private LeonImageView img_artist;
        private LinearLayout layout_artist;
        private AppCompatTextView txt_artistName, txt_followersCount;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            layout_artist = itemView.findViewById(R.id.layout_artist);
            img_artist = itemView.findViewById(R.id.img_artist);
            txt_artistName = itemView.findViewById(R.id.txt_artistName);
            txt_followersCount = itemView.findViewById(R.id.txt_followersCount);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Artist artist = items.get(getAdapterPosition());
            callback.onArtistClicked(artist);
        }
    }


    public interface ArtistCallback {
        void onArtistClicked(Artist artist);
    }

}