package lebe.lebeprototyp02;

/**
 * Created by BlackBox on 10.11.2016.
 */

public class MarketApp {
    private String image;
    private String AppName;
    private String AppInfo;

    public MarketApp(String image, String appName, String appInfo) {
        this.image = image;
        AppName = appName;
        AppInfo = appInfo;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getAppName() {
        return AppName;
    }

    public void setAppName(String appName) {
        AppName = appName;
    }

    public String getAppInfo() {
        return AppInfo;
    }

    public void setAppInfo(String appInfo) {
        AppInfo = appInfo;
    }

}
