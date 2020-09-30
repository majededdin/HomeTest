package majed.eddin.shaadoowapp.data.repositories;

import android.app.Application;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.util.List;

import androidx.lifecycle.MutableLiveData;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import majed.eddin.shaadoowapp.data.model.Post;
import majed.eddin.shaadoowapp.data.remote.ApiResponse;

import static majed.eddin.shaadoowapp.data.consts.Params.DATA;
import static majed.eddin.shaadoowapp.data.remote.ApiStatus.OnSuccess;


public class HomeRepository extends BaseRepository {

    private MutableLiveData<ApiResponse<Post>> postsApiResponse = new MutableLiveData<>();

    public MutableLiveData<ApiResponse<Post>> getPostsApiResponse() {
        return postsApiResponse;
    }


    public HomeRepository(Application application) {
        super(application);
    }

    public void getPosts(int page) {
        showLoading(true);
        getDisposable().add(getApiService().getPosts(page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(responseBody -> {
                    JSONObject jsonObject = new JSONObject(responseBody.string());
                    System.out.println(jsonObject);

                    List<Post> posts = new Gson().fromJson(jsonObject.getJSONArray(DATA).toString(), new TypeToken<List<Post>>() {
                    }.getType());

                    showLoading(false);
                    postsApiResponse.setValue(new ApiResponse<>(OnSuccess, posts));
                }, throwable -> {
                    showLoading(false);
                    postsApiResponse.setValue(ApiResponse.getErrorBody(throwable));
                }));
    }

}