package com.example.datavaulty;

/**
 * Created by Nick on 2019/12/1.
 * Description:
 */


import android.content.Intent;
import android.graphics.drawable.Drawable;

public class AppInfo {
    public static final int ITEM = 0;
    public static final int SECTION = 1;

    private boolean isOpened;
    private int type;
    private String appLabel;
    private Drawable appIcon;
    private Intent intent;
    private String pkgName;

    public AppInfo() {
    }

    public boolean isOpened() {
        return isOpened;
    }

    public void setOpened(boolean opened) {
        isOpened = opened;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getAppLabel() {
        return appLabel;
    }

    public void setAppLabel(String appName) {
        this.appLabel = appName;
    }

    public Drawable getAppIcon() {
        return appIcon;
    }

    public void setAppIcon(Drawable appIcon) {
        this.appIcon = appIcon;
    }

    public Intent getIntent() {
        return intent;
    }

    public void setIntent(Intent intent) {
        this.intent = intent;
    }

    public String getPkgName() {
        return pkgName;
    }

    public void setPkgName(String pkgName) {
        this.pkgName = pkgName;
    }
}