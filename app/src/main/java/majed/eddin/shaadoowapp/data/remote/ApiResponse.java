package majed.eddin.shaadoowapp.data.remote;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.HttpException;

import static majed.eddin.shaadoowapp.data.consts.Params.Message;
import static majed.eddin.shaadoowapp.data.remote.ApiStatus.OnConnectException;
import static majed.eddin.shaadoowapp.data.remote.ApiStatus.OnError;
import static majed.eddin.shaadoowapp.data.remote.ApiStatus.OnFailure;
import static majed.eddin.shaadoowapp.data.remote.ApiStatus.OnNotFound;
import static majed.eddin.shaadoowapp.data.remote.ApiStatus.OnTimeoutException;
import static majed.eddin.shaadoowapp.data.remote.ApiStatus.OnUnknownHost;

public class ApiResponse<T> {

    private String message;
    private ApiStatus apiStatus;
    private List<T> responseList = new ArrayList<>();

    public ApiResponse() {
    }

    public ApiResponse(ApiStatus apiStatus, String message) {
        this.apiStatus = apiStatus;
        this.message = message;
    }


    public ApiResponse(ApiStatus apiStatus) {
        this.apiStatus = apiStatus;
    }


    public ApiResponse(ApiStatus apiStatus, List<T> responseList) {
        this.apiStatus = apiStatus;
        this.responseList = responseList;
    }


    public ApiStatus getApiStatus() {
        return apiStatus;
    }


    public String getMessage() {
        return message;
    }


    public List<T> getResponseList() {
        return responseList;
    }


    public static ApiResponse getErrorBody(Throwable throwable) {
        ApiResponse<?> response = null;
        try {
            if (throwable instanceof HttpException) {
                HttpException exception = (HttpException) throwable;
                switch (exception.code()) {
                    case 404:
                        response = new ApiResponse<>(OnNotFound);
                        break;

                    case 422:
                        JSONObject jsonObject = new JSONObject(exception.response().errorBody().string());
                        System.out.println(jsonObject);

                        response = new ApiResponse<>(OnError, jsonObject.getString(Message));
                        break;

                    default:
                        String exceptionMsg = exception.response().errorBody().string();
                        Log.e("HttpExceptionMsg ", exceptionMsg);
                        response = new ApiResponse<>(OnFailure, exceptionMsg);
                        break;
                }
            } else if (throwable instanceof UnknownHostException)
                response = new ApiResponse<>(OnUnknownHost, throwable.getMessage());

            else if (throwable instanceof ConnectException)
                response = new ApiResponse<>(OnConnectException, throwable.getMessage());

            else if (throwable instanceof SocketTimeoutException)
                response = new ApiResponse<>(OnTimeoutException, throwable.getMessage());

            else {
                String throwableMsg = throwable.getMessage();
                System.out.println("throwableMsg " + throwableMsg);
                response = new ApiResponse<>(OnFailure, throwableMsg);
            }

        } catch (JSONException | IOException e) {
            e.printStackTrace();
        }
        return response;
    }

}