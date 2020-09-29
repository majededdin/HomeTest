package majed.eddin.shaadoowapp.ui.view.activities;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import majed.eddin.shaadoowapp.R;
import majed.eddin.shaadoowapp.ui.base.BaseActivity;
import majed.eddin.shaadoowapp.ui.view.fragments.HomeFragment;
import majed.eddin.shaadoowapp.ui.viewModel.HomeVM;

public class ExploreActivity extends BaseActivity<HomeVM> implements BottomNavigationView.OnNavigationItemSelectedListener {

    private HomeFragment homeFragment;
    private BottomNavigationView bottom_navigation_view;


    @Override
    public Class<HomeVM> getBaseViewModel() {
        return HomeVM.class;
    }

    @Override
    protected void baseViewModelInstance(HomeVM viewModel) {
    }


    @Override
    public void onBackPressed() {
        for (Fragment fragment : getSupportFragmentManager().getFragments()) {
            if (fragment instanceof HomeFragment)
                super.onBackPressed();
            else
                loadFragment();
        }
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explore);
        viewInit();
        loadFragment();

    }


    private void loadFragment() {
        bottom_navigation_view.getChildAt(0);
        bottom_navigation_view.setSelectedItemId(R.id.nav_home);
    }


    @Override
    public void updateView() {
        for (Fragment fragment : getSupportFragmentManager().getFragments()) {
            if (fragment instanceof HomeFragment)
                ((HomeFragment) fragment).updateView();
        }
    }


    @Override
    public void viewInit() {
        bottom_navigation_view = findViewById(R.id.bottom_navigation_view);
        bottom_navigation_view.setOnNavigationItemSelectedListener(this);
        homeFragment = new HomeFragment();

    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        Fragment fragment = new Fragment();
        switch (menuItem.getItemId()) {
            case R.id.nav_explore:
                showMessage(getString(R.string.explore));
                break;

            case R.id.nav_activity:
                showMessage(getString(R.string.activity));
                break;

            case R.id.nav_profile:
                showMessage(getString(R.string.profile));
                break;

            case R.id.nav_record:
                findViewById(R.id.btn_add).performClick();
                break;

            default:
                fragment = homeFragment;
                break;
        }
        return loadFragment(fragment);
    }


    private boolean loadFragment(Fragment fragment) {
        //switching fragment
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.homeFrameLayout, fragment)
                    .commit();
            return true;
        }
        return false;
    }

}