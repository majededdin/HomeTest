package majed.eddin.shaadoowapp.data.repositories;

import android.app.Application;

import androidx.lifecycle.MutableLiveData;
import io.reactivex.disposables.CompositeDisposable;
import majed.eddin.shaadoowapp.data.remote.ApiClient;
import majed.eddin.shaadoowapp.data.remote.ApiService;

public class BaseRepository {

    private ApiService apiService;
    private CompositeDisposable disposable;
    private MutableLiveData<Boolean> showLoading = new MutableLiveData<>();

    public BaseRepository(Application application) {
        apiService = ApiClient.getInstance();
        disposable = new CompositeDisposable();
    }

    public ApiService getApiService() {
        return apiService;
    }

    public CompositeDisposable getDisposable() {
        return disposable;
    }


    public void onDestroy() {
        disposable.dispose();
    }

    public void onDestroyView() {
        disposable.clear();
    }


    //-----------------------------------------Methods----------------------------------------------

    protected void showLoading(boolean status) {
        getShowLoading().setValue(status);
    }

    public MutableLiveData<Boolean> getShowLoading() {
        return showLoading;
    }

}