package majed.eddin.shaadoowapp.data.consts;

import android.app.Application;

public class AppConst {

    private Application appInstance;
    public String appBaseUrl;
    private String mediaRootUrl;

    private static AppConst instance = new AppConst();

    public static AppConst getInstance() {
        return instance;
    }

    public static void setInstance(AppConst instance) {
        AppConst.instance = instance;
    }


    //----------------------------------------Getters & Setters-------------------------------------

    public Application getAppInstance() {
        return appInstance;
    }

    public void setAppInstance(Application appInstance) {
        this.appInstance = appInstance;
    }

    public String getAppBaseUrl() {
        return this.appBaseUrl;
    }

    public void setAppBaseUrl(String var1) {
        this.appBaseUrl = var1;
    }

    public void setMediaRootUrl(String mediaRootUrl) {
        this.mediaRootUrl = mediaRootUrl;
    }

    public String getMediaRootUrl() {
        return mediaRootUrl;
    }
}