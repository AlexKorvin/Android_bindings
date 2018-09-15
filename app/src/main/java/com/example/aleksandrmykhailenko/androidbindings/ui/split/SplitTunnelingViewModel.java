package com.example.aleksandrmykhailenko.androidbindings.ui.split;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableList;
import android.os.AsyncTask;

import com.example.aleksandrmykhailenko.androidbindings.preference.Preference;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class SplitTunnelingViewModel {

    public final ObservableBoolean dataLoading = new ObservableBoolean();
    public final ObservableList<ApplicationItem> apps = new ObservableArrayList<>();
    public final ObservableList<String> disallowedApps = new ObservableArrayList<String>();
    public final OnApplicationItemSelectionChangedListener selectionChangedListener = new OnApplicationItemSelectionChangedListener() {
        @Override
        public void onApplicationItemSelectionChanged(ApplicationItem applicationItem, boolean isSelected) {
            if (isSelected) {
                allowPackage(applicationItem.getPackageName());
            } else {
                disallowPackage(applicationItem.getPackageName());
            }
        }
    };

    SplitTunnelingViewModel() {
        disallowedApps.clear();
        disallowedApps.addAll(getDisallowedPackages());
    }

    void getApplicationsList(PackageManager packageManager) {
        new InflateApplicationInfoAsyncTask(packageManager).execute();
    }

    private void allowPackage(String packageName) {
        Preference.INSTANCE.allowPackage(packageName);
    }

    private void disallowPackage(String packageName) {
        Preference.INSTANCE.disallowPackage(packageName);
    }

    private Set<String> getDisallowedPackages() {
        return Preference.INSTANCE.getDisallowedPackages();
    }

    private class InflateApplicationInfoAsyncTask extends AsyncTask<Void, Void, List<ApplicationItem>> {

        private List<ApplicationInfo> applicationInfoList;
        private PackageManager packageManager;

        InflateApplicationInfoAsyncTask(PackageManager packageManager) {
            this.packageManager = packageManager;
            this.applicationInfoList = packageManager.getInstalledApplications(PackageManager.GET_META_DATA);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dataLoading.set(true);
        }

        @Override
        protected List<ApplicationItem> doInBackground(Void... voids) {
            List<ApplicationItem> items = new LinkedList<>();
            for (ApplicationInfo info : applicationInfoList) {
                try {
                    if (null != packageManager.getLaunchIntentForPackage(info.packageName)) {
                        items.add(new ApplicationItem(info.loadLabel(packageManager).toString(), info.packageName,
                                info.loadIcon(packageManager)));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return items;
        }

        @Override
        protected void onPostExecute(List<ApplicationItem> applicationItems) {
            apps.clear();
            apps.addAll(applicationItems);
            dataLoading.set(false);
        }
    }
}