package majed.eddin.shaadoowapp.ui.base;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import am.networkconnectivity.NetworkConnectivity;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProvider;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import majed.eddin.shaadoowapp.R;
import majed.eddin.shaadoowapp.data.remote.ApiResponse;
import majed.eddin.shaadoowapp.ui.view.fragments.LoadingFragment;
import majed.eddin.shaadoowapp.ui.viewModel.BaseViewModel;
import majed.eddin.shaadoowapp.utils.Utils;

public abstract class BaseActivity<V extends BaseViewModel> extends AppCompatActivity {

    private V viewModel;
    private RelativeLayout layout;

    private boolean secondRun = false;
    private ProgressBar progress_loading;
    private LinearLayout layout_networkStatus;
    private AppCompatTextView txt_networkStatus;

    private LoadingFragment loadingFragment = new LoadingFragment();


    protected abstract Class<V> getBaseViewModel();


    protected abstract void baseViewModelInstance(V viewModel);


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        handleNetworkResponse();
        baseViewModelInstance(initViewModel());

        super.onCreate(savedInstanceState);
    }


    public abstract void updateView();


    protected abstract void viewInit();


    @Override
    protected void onDestroy() {
        if (viewModel != null)
            viewModel.onDestroy();
        super.onDestroy();
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }


    private V initViewModel() {
        if (getBaseViewModel() != null)
            viewModel = new ViewModelProvider(this).get(getBaseViewModel());
        return viewModel;
    }


    @SuppressLint("InflateParams")
    @Override
    public void setContentView(int layoutResID) {
        layout = (RelativeLayout) getLayoutInflater().inflate(R.layout.activity_base, null);
        CoordinatorLayout coordinatorLayout = layout.findViewById(R.id.containerBase);
        baseInit();

        getLayoutInflater().inflate(layoutResID, coordinatorLayout, true);
        super.setContentView(layout);
    }


    private void baseInit() {
        progress_loading = layout.findViewById(R.id.progress_loading);
        txt_networkStatus = layout.findViewById(R.id.txt_networkStatus);
        layout_networkStatus = layout.findViewById(R.id.layout_networkStatus);
        loadingFragment.setCancelable(false);
    }


    protected void initSwipeRefresh(SwipeRefreshLayout swipeRefreshLayout) {
        // initialize onSwipeRefresh
        swipeRefreshLayout.setColorSchemeColors(ContextCompat.getColor(this, Utils.getAttrColor(this, 1)),
                ContextCompat.getColor(this, Utils.getAttrColor(this, 2)), ContextCompat.getColor(this, Utils.getAttrColor(this, 3)));

        swipeRefreshLayout.setOnRefreshListener(this::updateView);
    }


    protected void showLoadingFragment(boolean status) {
        try {
            if (!status && !loadingFragment.isDetached())
                loadingFragment.dismiss();
            else if (!loadingFragment.isAdded())
                loadingFragment.show(getSupportFragmentManager(), getClass().getName());
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }
    }


    public View getCustomView() {
        return findViewById(android.R.id.content);
    }


    protected void showLoading(LifecycleOwner owner) {
        if (viewModel != null) {
            viewModel.showLoadingResponse().observe(owner, aBoolean -> {
                if (aBoolean == null)
                    return;

                showLoadingFragment((Boolean) aBoolean);
            });
        }
    }


    public void showMessage(String message) {
        Snackbar.make(getCustomView(), message, BaseTransientBottomBar.LENGTH_SHORT).show();
    }


    private void refreshPageDetails() {
        updateView();
        for (Fragment fragment : getSupportFragmentManager().getFragments()) {
            if (fragment instanceof BaseFragment)
                ((BaseFragment<?>) fragment).updateView();
        }
    }


    public void handleApiResponse(ApiResponse apiResponse, View.OnClickListener failureListener) {
        switch (apiResponse.getApiStatus()) {
            case OnError:
                Snackbar.make(getCustomView(), apiResponse.getMessage(), Snackbar.LENGTH_SHORT).show();
                break;

            case OnFailure:
                Log.e("OnFailure ", apiResponse.getMessage());
                break;

            case OnTimeoutException:
                Snackbar.make(getCustomView(), getString(R.string.request_timeout_please_check_your_connection), Snackbar.LENGTH_INDEFINITE).setAction(getString(R.string.retry), failureListener).show();
                break;

            case OnConnectException:
            case OnUnknownHost:
                onNetworkConnectionChanged(NetworkConnectivity.NetworkStatus.OnLost);
                break;
        }
    }


    public void handleNetworkResponse() {
        new NetworkConnectivity(this).observe(this, this::onNetworkConnectionChanged);
    }


    private void onNetworkConnectionChanged(NetworkConnectivity.NetworkStatus status) {
        switch (status) {
            case OnConnected:
                if (secondRun) {
                    secondRun = false;
                    txt_networkStatus.setText(getString(R.string.connection_is_back));
                    progress_loading.setVisibility(View.GONE);
                    layout_networkStatus.setBackgroundColor(ContextCompat.getColor(this, R.color.colorNetworkConnected));

                    refreshPageDetails();
                    new Handler().postDelayed(() -> layout_networkStatus.setVisibility(View.GONE), 2000);
                }
                break;

            case OnWaiting:
                secondRun = true;
                txt_networkStatus.setText(getString(R.string.waiting_for_connection));
                progress_loading.setVisibility(View.VISIBLE);
                layout_networkStatus.setVisibility(View.VISIBLE);
                layout_networkStatus.setBackgroundColor(ContextCompat.getColor(this, R.color.colorNetworkWaiting));
                break;

            case OnLost:
                secondRun = true;
                txt_networkStatus.setText(getString(R.string.connection_is_lost));
                progress_loading.setVisibility(View.GONE);
                layout_networkStatus.setVisibility(View.VISIBLE);
                layout_networkStatus.setBackgroundColor(ContextCompat.getColor(this, R.color.colorNetworkLost));
                break;
        }
    }

}