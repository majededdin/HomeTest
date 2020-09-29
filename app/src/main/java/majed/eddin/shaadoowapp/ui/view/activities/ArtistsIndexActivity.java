package majed.eddin.shaadoowapp.ui.view.activities;

import android.os.Bundle;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import majed.eddin.shaadoowapp.R;
import majed.eddin.shaadoowapp.data.model.Artist;
import majed.eddin.shaadoowapp.data.remote.ApiResponse;
import majed.eddin.shaadoowapp.data.remote.ApiStatus;
import majed.eddin.shaadoowapp.ui.base.BaseActivity;
import majed.eddin.shaadoowapp.ui.view.adapters.ArtistsAdapter;
import majed.eddin.shaadoowapp.ui.viewModel.HomeVM;
import majed.eddin.shaadoowapp.utils.recyclerUtils.OnEndless;

public class ArtistsIndexActivity extends BaseActivity<HomeVM> implements ArtistsAdapter.ArtistCallback {

    private HomeVM homeVM;

    private SwipeRefreshLayout swipeRefresh;
    private RecyclerView recycler_artists;
    private RecyclerView.LayoutManager artistsLayoutManager;
    private ArtistsAdapter artistsAdapter;

    private ApiResponse<Artist> apiResponse = new ApiResponse<>();


    @Override
    public Class<HomeVM> getBaseViewModel() {
        return HomeVM.class;
    }

    @Override
    protected void baseViewModelInstance(HomeVM viewModel) {
        homeVM = viewModel;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artists_index);
        showLoading(this);
        viewInit();
        initSwipeRefresh(swipeRefresh);

        updateView();

        homeVM.getArtistsApiResponse().observe(this, this::artistsResult);

    }

    private void artistsResult(ApiResponse<Artist> apiResponse) {
        handleApiResponse(apiResponse, v -> updateView());
        swipeRefresh.setRefreshing(false);
        if (apiResponse.getApiStatus() == ApiStatus.OnSuccess) {
            this.apiResponse = apiResponse;
            if (apiResponse.getResponseList().size() > 0)
                artistsAdapter.addAll(apiResponse.getResponseList());
        }
    }


    @Override
    public void updateView() {
        apiResponse.getResponseList().clear();
        artistsAdapter.clear();

        OnEndless scrollListener = new OnEndless((LinearLayoutManager) artistsLayoutManager, 1) {
            @Override
            public void onLoadMore(int current_page) {
                if (apiResponse.getResponseList().size() != 0)
                    getArtists(current_page);
            }
        };

        recycler_artists.addOnScrollListener(scrollListener);

        if (apiResponse.getResponseList().size() == 0)
            homeVM.getArtists(1);
    }

    private void getArtists(int page) {
        homeVM.getArtists(page);
    }

    @Override
    public void viewInit() {
        swipeRefresh = findViewById(R.id.swipeRefresh);
        recycler_artists = getCustomView().findViewById(R.id.recycler_artists);

        artistsAdapter = new ArtistsAdapter(this, this, false);
        recycler_artists.setAdapter(artistsAdapter);
        artistsLayoutManager = new GridLayoutManager(this, 2);
        recycler_artists.setLayoutManager(artistsLayoutManager);

    }


    @Override
    public void onArtistClicked(Artist artist) {

    }
}