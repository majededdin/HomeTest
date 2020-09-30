package majed.eddin.shaadoowapp.data.repositories;

import android.app.Application;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.util.List;

import androidx.lifecycle.MutableLiveData;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Action;
import io.reactivex.schedulers.Schedulers;
import majed.eddin.shaadoowapp.data.model.Artist;
import majed.eddin.shaadoowapp.data.remote.ApiClient;
import majed.eddin.shaadoowapp.data.remote.ApiResponse;
import majed.eddin.shaadoowapp.data.remote.ApiService;

import static majed.eddin.shaadoowapp.data.consts.Params.DATA;
import static majed.eddin.shaadoowapp.data.remote.ApiStatus.OnSuccess;

public class BaseRepository {

    private ApiService apiService;
    private CompositeDisposable disposable;
    private MutableLiveData<Boolean> showLoading = new MutableLiveData<>();
    private MutableLiveData<ApiResponse<Artist>> artistApiResponse = new MutableLiveData<>();

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

    public MutableLiveData<ApiResponse<Artist>> getArtistApiResponse() {
        return artistApiResponse;
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

    public void getArtists(int page, Action action) {
        showLoading(true);
        getDisposable().add(getApiService().getArtists(page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnComplete(action)
                .subscribe(responseBody -> {
                    JSONObject jsonObject = new JSONObject(responseBody.string());
                    System.out.println(jsonObject);

                    List<Artist> artist = new Gson().fromJson(jsonObject.getJSONArray(DATA).toString(), new TypeToken<List<Artist>>() {
                    }.getType());

                    showLoading(false);
                    artistApiResponse.setValue(new ApiResponse<>(OnSuccess, artist));
                }, throwable -> {
                    showLoading(false);
                    artistApiResponse.setValue(ApiResponse.getErrorBody(throwable));
                }));
    }

}