package majed.eddin.shaadoowapp.ui.view.adapters;

import android.text.Html;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import am.dateutils.DateUtils;
import am.leon.LeonImageView;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.RecyclerView;
import majed.eddin.shaadoowapp.R;
import majed.eddin.shaadoowapp.data.model.Post;
import majed.eddin.shaadoowapp.ui.base.BaseActivity;
import majed.eddin.shaadoowapp.utils.Utils;
import majed.eddin.shaadoowapp.utils.imageUtils.RoundedImage;

public class PostsAdapter extends RecyclerView.Adapter<PostsAdapter.ViewHolder> {

    private List<Post> items;
    private BaseActivity<?> context;
    private PostsCallback callback;


    public PostsAdapter(BaseActivity<?> context, PostsCallback callback) {
        this.context = context;
        this.callback = callback;
        this.items = new ArrayList<>();
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_post, parent, false);

        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Post post = items.get(position);

        holder.txt_ownerName.setText(post.getCreated_by().getName());
        holder.img_owner.loadImage(Utils.getImagePath(post.getCreated_by().getProfile_image_url()), new RoundedImage());
        holder.img_post.loadImage(Utils.getImagePath(post.getSong_details().getCover_img_url()));
        holder.txt_postDate.setText(new DateUtils(context, post.getCreated_at()).getTimeAgo());

        holder.txt_postDescription.setText(Html.fromHtml(Html.fromHtml(post.getRecording_details().getDescription()).toString()));

        if (post.getInteractions_counter().getLikes() > 0)
            holder.txt_like.setText(String.valueOf(post.getInteractions_counter().getLikes()).concat(" ").concat(context.getString(R.string.like)));
        else
            holder.txt_like.setText(context.getString(R.string.like));

        if (post.getInteractions_counter().getComments() > 0)
            holder.txt_comment.setText(String.valueOf(post.getInteractions_counter().getComments()).concat(" ").concat(context.getString(R.string.comment)));
        else
            holder.txt_comment.setText(context.getString(R.string.comment));

    }


    public void add(Post r) {
        items.add(r);
        notifyItemInserted(items.size() - 1);
    }


    public void addAll(List<Post> items) {
        for (Post result : items) {
            add(result);
        }
    }


    public void clear() {
        items.clear();
        notifyDataSetChanged();
    }


    @Override
    public int getItemCount() {
        return items.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, PopupMenu.OnMenuItemClickListener {

        private PopupMenu popupMenu;
        private LeonImageView img_owner;
        private LeonImageView img_post;
        private AppCompatTextView txt_ownerName, txt_postDate,
                txt_postDescription, txt_like, txt_comment;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            img_owner = itemView.findViewById(R.id.img_owner);
            txt_ownerName = itemView.findViewById(R.id.txt_ownerName);
            txt_postDate = itemView.findViewById(R.id.txt_postDate);
            txt_postDescription = itemView.findViewById(R.id.txt_postDescription);
            img_post = itemView.findViewById(R.id.img_post);

            txt_like = itemView.findViewById(R.id.txt_like);
            txt_comment = itemView.findViewById(R.id.txt_comment);

            txt_like.setOnClickListener(this);
            txt_comment.setOnClickListener(this);
            itemView.findViewById(R.id.txt_share).setOnClickListener(this);
            itemView.findViewById(R.id.layout_ownerSection).setOnClickListener(this);
            itemView.findViewById(R.id.btn_more).setOnClickListener(this);
            itemView.findViewById(R.id.btn_record).setOnClickListener(this);

            popupMenu = new PopupMenu(context, itemView.findViewById(R.id.btn_more));
            popupMenu.getMenuInflater().inflate(R.menu.more_menu, popupMenu.getMenu());
            popupMenu.setOnMenuItemClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Post post = items.get(getAdapterPosition());
            switch (v.getId()) {
                case R.id.layout_ownerSection:
                    callback.onOwnerClicked(post);
                    break;
                case R.id.txt_like:
                    callback.onLikeClicked(post);
                    break;
                case R.id.txt_comment:
                    callback.onCommentClicked(post);
                    break;
                case R.id.txt_share:
                    callback.onShareClicked(post);
                    break;
                case R.id.btn_more:
                    popupMenu.show();
                    break;
                case R.id.btn_record:
                    callback.onRecordClicked(post);
                    break;
            }
        }

        @Override
        public boolean onMenuItemClick(MenuItem item) {
            Post post = items.get(getAdapterPosition());
            switch (item.getItemId()) {
                case R.id.nav_more:
                    callback.onMoreClicked(post);
                    return true;

                case R.id.nav_action:
                    callback.onActionClicked(post);
                    return true;
            }
            return false;
        }
    }


    public interface PostsCallback {
        void onOwnerClicked(Post post);

        void onLikeClicked(Post post);

        void onCommentClicked(Post post);

        void onShareClicked(Post post);

        void onMoreClicked(Post post);

        void onActionClicked(Post post);

        void onRecordClicked(Post post);
    }

}