package majed.eddin.shaadoowapp.ui.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import majed.eddin.shaadoowapp.data.model.Artist;
import majed.eddin.shaadoowapp.data.model.Post;
import majed.eddin.shaadoowapp.data.remote.ApiResponse;
import majed.eddin.shaadoowapp.data.repositories.HomeRepository;

public class HomeVM extends BaseViewModel {

    private HomeRepository repository;

    public HomeVM(@NonNull Application application) {
        super(application);
        repository = new HomeRepository(application);
    }

    @Override
    public MutableLiveData<Boolean> showLoadingResponse() {
        return repository.getShowLoading();
    }


    public MutableLiveData<ApiResponse<Artist>> getArtistsApiResponse() {
        return repository.getArtistApiResponse();
    }

    public MutableLiveData<ApiResponse<Post>> getPostsApiResponse() {
        return repository.getPostsApiResponse();
    }


    public void getArtists(int page) {
        repository.getArtists(page);
    }


    public void getPosts(int page) {
        repository.getPosts(page);
    }

}