package majed.eddin.shaadoowapp.data.application;

import android.app.Application;

import majed.eddin.shaadoowapp.BuildConfig;
import majed.eddin.shaadoowapp.data.consts.AppConst;

public class BaseApplication extends Application {

    private AppConst aConst;
    private static BaseApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        aConst = AppConst.getInstance();
        initAppConst();
    }


    public static synchronized BaseApplication getInstance() {
        return instance;
    }

    private void initAppConst() {
        aConst.setAppInstance(this);
        aConst.setAppBaseUrl(BuildConfig.BASE_URL);
        aConst.setMediaRootUrl("https://shaadoow.net/");
    }

}