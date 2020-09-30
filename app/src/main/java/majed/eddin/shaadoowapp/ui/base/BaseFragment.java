package majed.eddin.shaadoowapp.ui.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProvider;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import majed.eddin.shaadoowapp.R;
import majed.eddin.shaadoowapp.data.remote.ApiResponse;
import majed.eddin.shaadoowapp.ui.viewModel.BaseViewModel;
import majed.eddin.shaadoowapp.utils.Utils;

public abstract class BaseFragment<V extends BaseViewModel> extends Fragment {

    private V viewModel;
    private FrameLayout layout;
    private ProgressBar progressBar;
    private BaseActivity<?> baseActivity;
    private SwipeRefreshLayout swipeRefreshLayout;

    @LayoutRes
    protected int setLayout() {
        return 0;
    }


    protected abstract Class<V> getViewModel();


    protected abstract void viewModelInstance(V viewModel);


    public abstract void updateView();


    protected abstract void viewInit();


    public BaseFragment() {
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        viewModelInstance(initViewModel());
        super.onCreate(savedInstanceState);
    }


    private V initViewModel() {
        if (getViewModel() != null)
            viewModel = new ViewModelProvider(this).get(getViewModel());
        return viewModel;
    }


    @Override
    public void onDestroyView() {
        if (viewModel != null)
            viewModel.onDestroyView();
        super.onDestroyView();
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        layout = (FrameLayout) inflater.inflate(R.layout.fragment_base, container, false);
        swipeRefreshLayout = layout.findViewById(R.id.containerBaseFragment);
        baseInit();
        initSwipeRefresh(swipeRefreshLayout);

        getLayoutInflater().inflate(setLayout(), swipeRefreshLayout, true);
        return layout;
    }


    private void baseInit() {
        baseActivity = (BaseActivity<?>) getActivity();
        progressBar = layout.findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.GONE);
    }


    private void initSwipeRefresh(SwipeRefreshLayout swipeRefreshLayout) {
        swipeRefreshLayout.setColorSchemeColors(ContextCompat.getColor(getBaseActivity(), Utils.getAttrColor(getBaseActivity(), 1)),
                ContextCompat.getColor(getBaseActivity(), Utils.getAttrColor(getBaseActivity(), 2)), ContextCompat.getColor(getBaseActivity(), Utils.getAttrColor(getBaseActivity(), 3)));

        swipeRefreshLayout.setOnRefreshListener(this::updateView);
    }


    protected SwipeRefreshLayout getSwipeRefresh() {
        return swipeRefreshLayout;
    }

    protected void setOnSwipeRefreshStatus(boolean status) {
        if (status)
            swipeRefreshLayout.setEnabled(true);
        else
            swipeRefreshLayout.setEnabled(false);
    }


    public View getCustomView() {
        return layout;
    }


    public BaseActivity<?> getBaseActivity() {
        return baseActivity;
    }


    protected void showLoading(LifecycleOwner owner) {
        if (viewModel != null) {
            viewModel.showLoadingResponse().observe(owner, aBoolean -> {
                if (aBoolean == null)
                    return;

                showProgressBar((Boolean) aBoolean);
            });
        }
    }


    private void showProgressBar(boolean status) {
        progressBar.setVisibility(status ? View.VISIBLE : View.GONE);
    }

    public void showMessage(String message) {
        Snackbar.make(getCustomView(), message, BaseTransientBottomBar.LENGTH_SHORT).show();
    }


    public void handleApiResponse(ApiResponse<?> apiResponse, View.OnClickListener failureListener) {
        getBaseActivity().handleApiResponse(apiResponse, failureListener);
    }

}