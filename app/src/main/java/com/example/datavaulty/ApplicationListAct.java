package com.example.datavaulty;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import de.halfbit.pinnedsection.PinnedSectionListView;

public class ApplicationListAct extends AppCompatActivity {

    PinnedSectionListView lv;
    private Gson mGson;
    private PackageManager pm;
    private List<AppInfo> mListAppInfo = null;
    private List<String> mLockList = new ArrayList<>();
    private BrowseApplicationInfoAdapter browseAppAdapter;
    private boolean hasList;
    ImageView img_back;
    //AVLoadingIndicatorView avi;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_application_list);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //avi=findViewById(R.id.avi);
        lv=findViewById(R.id.lv);
        img_back=(ImageView)findViewById(R.id.img_back);
        LongOperation longOperation=new LongOperation();
        longOperation.execute();

        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
    private final class LongOperation extends AsyncTask<Void, Void, String> {
        private ProgressDialog dialog;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(ApplicationListAct.this);
            dialog.setMessage("Doing something, please wait.");
            dialog.show();
        }

        @Override
        protected String doInBackground(Void... params) {
            try {
                mGson = new Gson();
                pm = getPackageManager();
                mListAppInfo = new ArrayList<>();
                String str = PreferenceUtils.getString(ApplicationListAct.this, "lock_list");
                if (!TextUtils.isEmpty(str)) {
                    mLockList = mGson.fromJson(str, new TypeToken<List<String>>() {
                    }.getType());
                    if (!mLockList.isEmpty()) {
//                            lotty.setVisibility(View.GONE);
                        hasList = true;
                        mListAppInfo.clear();
                    } else {
                        mListAppInfo.clear();
                    }
                }
                queryCustomAppInfo();

                Thread.sleep(1000);
            } catch (InterruptedException e) {
            }
            return "Executed";
        }

        @Override
        protected void onPostExecute(String result) {
            if (mListAppInfo.size() != 0) {

                browseAppAdapter = new BrowseApplicationInfoAdapter(ApplicationListAct.this, mListAppInfo, new BrowseApplicationInfoAdapter.OnStatusChangedListener() {
                    @Override
                    public void onStatusChange(AppInfo appInfo) {
                        if (appInfo.isOpened()) {
                            mLockList.add(appInfo.getPkgName());
                        } else {
                            mLockList.remove(appInfo.getPkgName());
                        }
                    }
                });
                lv.setAdapter(browseAppAdapter);

            }
            dialog.dismiss();
        }
    }

    private void queryCustomAppInfo() {
        pm = this.getPackageManager();
        List<ApplicationInfo> listApplications = pm.getInstalledApplications(PackageManager.GET_UNINSTALLED_PACKAGES);
        Collections.sort(listApplications, new ApplicationInfo.DisplayNameComparator(pm));
        if (!hasList) {
            mListAppInfo.clear();
        }
        AppInfo appTitle = new AppInfo();
        appTitle.setAppLabel("User Installed");
        appTitle.setType(AppInfo.SECTION);
        mListAppInfo.add(appTitle);
        for (ApplicationInfo app : listApplications) {
            if ((app.flags & ApplicationInfo.FLAG_SYSTEM) <= 0) {
                AppInfo appInfo = getAppInfo(app);
                boolean isLocked = false;
                if (hasList) {
                    for (int i = 0; i < mLockList.size(); i++) {
                        if (TextUtils.equals(app.packageName, mLockList.get(i))) {
                            appInfo.setOpened(true);
                            isLocked = true;
                            break;
                        }
                    }
                    if (!isLocked) {
                        mListAppInfo.add(appInfo);
                    }
                } else {
                    mListAppInfo.add(appInfo);
                }
            }
        }

        querySystemAppInfo();
    }

    private void querySystemAppInfo() {
        List<ApplicationInfo> listAppcations = pm.getInstalledApplications(PackageManager.GET_UNINSTALLED_PACKAGES);
        Collections.sort(listAppcations, new ApplicationInfo.DisplayNameComparator(pm));
        AppInfo appTitle = new AppInfo();
        appTitle.setAppLabel("System Installed");
        appTitle.setType(AppInfo.SECTION);
        mListAppInfo.add(appTitle);
        for (ApplicationInfo app : listAppcations) {
            if ((app.flags & ApplicationInfo.FLAG_SYSTEM) != 0) {
                //判断以前是否有设置开启过
                AppInfo appInfo = getAppInfo(app);
                boolean isLocked = false;
                if (hasList) {
                    for (int i = 0; i < mLockList.size(); i++) {
                        if (TextUtils.equals(app.packageName, mLockList.get(i))) {
                            appInfo.setOpened(true);
                            isLocked = true;
                            break;
                        }
                    }
                    if (!isLocked) {
                        mListAppInfo.add(appInfo);
                    }
                } else {
                    mListAppInfo.add(appInfo);
                }
            }
        }

    }

    private AppInfo getAppInfo(ApplicationInfo app) {
        AppInfo appInfo = new AppInfo();
        appInfo.setAppLabel((String) app.loadLabel(pm));
        appInfo.setAppIcon(app.loadIcon(pm));
        appInfo.setPkgName(app.packageName);
        appInfo.setType(AppInfo.ITEM);
        return appInfo;
    }

    @Override
    protected void onStop() {
        String str = mGson.toJson(mLockList);
        if (!TextUtils.isEmpty(str)) {
            PreferenceUtils.putString(this, "lock_list", str);
            sendBroadcast(new Intent("action_update_unlock_list"));
        }
        super.onStop();
    }
}
