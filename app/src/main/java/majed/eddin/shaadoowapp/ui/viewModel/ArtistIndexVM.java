package majed.eddin.shaadoowapp.ui.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import io.reactivex.functions.Action;
import majed.eddin.shaadoowapp.data.model.Artist;
import majed.eddin.shaadoowapp.data.remote.ApiResponse;
import majed.eddin.shaadoowapp.data.repositories.BaseRepository;

public class ArtistIndexVM extends BaseViewModel {

    private BaseRepository repository;

    public ArtistIndexVM(@NonNull Application application) {
        super(application);
        repository = new BaseRepository(application);
    }

    @Override
    public MutableLiveData<Boolean> showLoadingResponse() {
        return repository.getShowLoading();
    }


    public MutableLiveData<ApiResponse<Artist>> getArtistsApiResponse() {
        return repository.getArtistApiResponse();
    }


    public void getArtists(int page, Action action) {
        repository.getArtists(page, action);
    }

}