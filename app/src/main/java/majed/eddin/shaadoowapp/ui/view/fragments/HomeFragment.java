package majed.eddin.shaadoowapp.ui.view.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.google.android.material.appbar.AppBarLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import majed.eddin.shaadoowapp.R;
import majed.eddin.shaadoowapp.data.model.Artist;
import majed.eddin.shaadoowapp.data.model.Post;
import majed.eddin.shaadoowapp.data.remote.ApiResponse;
import majed.eddin.shaadoowapp.data.remote.ApiStatus;
import majed.eddin.shaadoowapp.ui.base.BaseFragment;
import majed.eddin.shaadoowapp.ui.view.activities.ArtistsIndexActivity;
import majed.eddin.shaadoowapp.ui.view.adapters.ArtistsAdapter;
import majed.eddin.shaadoowapp.ui.view.adapters.PostsAdapter;
import majed.eddin.shaadoowapp.ui.viewModel.HomeVM;
import majed.eddin.shaadoowapp.utils.recyclerUtils.OnEndless;

public class HomeFragment extends BaseFragment<HomeVM> implements View.OnClickListener, ArtistsAdapter.ArtistCallback, PostsAdapter.PostsCallback, AppBarLayout.OnOffsetChangedListener {

    private HomeVM homeVM;

    private AppBarLayout appBarLayout;
    private LinearLayout layout_artists, layout_posts;
    private RecyclerView recycler_artists, recycler_posts;
    private RecyclerView.LayoutManager artistsLayoutManager, postsLayoutManager;
    private ArtistsAdapter artistsAdapter;
    private PostsAdapter postsAdapter;

    private ApiResponse<Post> apiResponse = new ApiResponse<>();

    @Override
    public int setLayout() {
        return R.layout.fragment_home;
    }

    @Override
    public Class<HomeVM> getViewModel() {
        return HomeVM.class;
    }

    @Override
    public void viewModelInstance(HomeVM viewModel) {
        homeVM = viewModel;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        viewInit();
        showLoading(getViewLifecycleOwner());

        homeVM.getArtists(1, this::updateView);

        homeVM.getArtistsApiResponse().observe(getViewLifecycleOwner(), this::artistsResult);
        homeVM.getPostsApiResponse().observe(getViewLifecycleOwner(), this::postsResult);

        return view;
    }


    private void artistsResult(ApiResponse<Artist> apiResponse) {
        handleApiResponse(apiResponse, v -> updateView());
        if (apiResponse.getApiStatus() == ApiStatus.OnSuccess) {
            if (apiResponse.getResponseList().size() > 0) {
                artistsAdapter.addAll(apiResponse.getResponseList());
                layout_artists.setVisibility(View.VISIBLE);
            } else
                layout_artists.setVisibility(View.GONE);
        }
    }


    private void postsResult(ApiResponse<Post> apiResponse) {
        handleApiResponse(apiResponse, v -> updateView());
        getSwipeRefresh().setRefreshing(false);
        if (apiResponse.getApiStatus() == ApiStatus.OnSuccess) {
            this.apiResponse = apiResponse;
            if (apiResponse.getResponseList().size() > 0) {
                postsAdapter.addAll(apiResponse.getResponseList());
                layout_posts.setVisibility(View.VISIBLE);
            } else
                layout_posts.setVisibility(View.GONE);
        }
    }


    @Override
    public void updateView() {
        apiResponse.getResponseList().clear();
        postsAdapter.clear();

        OnEndless scrollListener = new OnEndless((LinearLayoutManager) postsLayoutManager, 1) {
            @Override
            public void onLoadMore(int current_page) {
                if (apiResponse.getResponseList().size() != 0)
                    getPosts(current_page);
            }
        };

        recycler_posts.addOnScrollListener(scrollListener);

        if (apiResponse.getResponseList().size() == 0)
            getPosts(1);
    }

    private void getPosts(int page) {
        homeVM.getPosts(page);
    }


    @Override
    public void viewInit() {
        appBarLayout = getCustomView().findViewById(R.id.appBarLayout);
        layout_artists = getCustomView().findViewById(R.id.layout_artists);
        layout_posts = getCustomView().findViewById(R.id.layout_posts);
        recycler_artists = getCustomView().findViewById(R.id.recycler_artists);
        recycler_posts = getCustomView().findViewById(R.id.recycler_posts);

        appBarLayout.addOnOffsetChangedListener(this);

        getCustomView().findViewById(R.id.txt_seeAll).setOnClickListener(this);

        artistsAdapter = new ArtistsAdapter(this, true);
        recycler_artists.setAdapter(artistsAdapter);
        artistsLayoutManager = new LinearLayoutManager(getBaseActivity(), LinearLayoutManager.HORIZONTAL, false);
        recycler_artists.setLayoutManager(artistsLayoutManager);

        postsAdapter = new PostsAdapter(getBaseActivity(), this);
        recycler_posts.setAdapter(postsAdapter);
        postsLayoutManager = new LinearLayoutManager(getBaseActivity(), LinearLayoutManager.VERTICAL, false);
        recycler_posts.setLayoutManager(postsLayoutManager);

    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.txt_seeAll) {
            startActivity(new Intent(getBaseActivity(), ArtistsIndexActivity.class));
        }
    }

    @Override
    public void onArtistClicked(Artist artist) {
        showMessage(artist.getNames());
    }

    @Override
    public void onOwnerClicked(Post post) {
        showMessage(post.getCreated_by().getName());
    }

    @Override
    public void onLikeClicked(Post post) {
        showMessage(String.valueOf(post.getInteractions_counter().getLikes()));
    }

    @Override
    public void onCommentClicked(Post post) {
        showMessage(String.valueOf(post.getInteractions_counter().getComments()));
    }

    @Override
    public void onShareClicked(Post post) {
        showMessage(getBaseActivity().getString(R.string.share));
    }

    @Override
    public void onMoreClicked(Post post) {
        showMessage(getBaseActivity().getString(R.string.more));
    }

    @Override
    public void onActionClicked(Post post) {
        showMessage(getBaseActivity().getString(R.string.action));
    }

    @Override
    public void onRecordClicked(Post post) {
        showMessage(getBaseActivity().getString(R.string.start_record));
    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
        setOnSwipeRefreshStatus(verticalOffset == 0);
    }
}