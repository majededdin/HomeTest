package majed.eddin.shaadoowapp.data.remote;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

import static majed.eddin.shaadoowapp.data.consts.Params.PAGE;

public interface ApiService {

    @GET("artists/auth-less/list?limit=20")
    @Headers({"Accept: application/json", "Content-Type: application/json"})
    Observable<ResponseBody> getArtists(@Query(PAGE) int page);

    @GET("posts/recommended_for_you?limit=18")
    @Headers({"Accept: application/json", "Content-Type: application/json"})
    Observable<ResponseBody> getPosts(@Query(PAGE) int page);
}