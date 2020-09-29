package majed.eddin.shaadoowapp.ui.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import majed.eddin.shaadoowapp.data.repositories.BaseRepository;


public abstract class BaseViewModel extends AndroidViewModel {

    private BaseRepository repo;

    public BaseViewModel(@NonNull Application application) {
        super(application);
        repo = new BaseRepository(application);
    }


    public abstract MutableLiveData<Boolean> showLoadingResponse();


    //------------------------------------------- Settings -----------------------------------------

    public void onDestroy() {
        repo.onDestroy();
    }

    public void onDestroyView() {
        repo.onDestroyView();
    }
}