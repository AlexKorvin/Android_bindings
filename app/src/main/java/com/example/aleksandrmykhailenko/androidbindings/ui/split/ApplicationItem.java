package com.example.aleksandrmykhailenko.androidbindings.ui.split;

import android.graphics.drawable.Drawable;

import java.util.Comparator;

public class ApplicationItem {
    private String applicationName;
    private String packageName;
    private Drawable icon;
    private boolean isAllowed;

    public static Comparator<ApplicationItem> comparator = new Comparator<ApplicationItem>() {
        @Override
        public int compare(ApplicationItem item1, ApplicationItem item2) {
            return item1.getApplicationName().compareTo(item2.getApplicationName());
        }
    };

    public ApplicationItem(String applicationName, String packageName, Drawable icon) {
        this.applicationName = applicationName;
        this.icon = icon;
        this.packageName = packageName;
    }

    public String getApplicationName() {
        return applicationName;
    }

    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public Drawable getIcon() {
        return icon;
    }

    public void setIcon(Drawable icon) {
        this.icon = icon;
    }

    public boolean isAllowed() {
        return isAllowed;
    }

    public void setAllowed(boolean allowed) {
        isAllowed = allowed;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof ApplicationItem)) {
            return false;
        }
        ApplicationItem item = (ApplicationItem) obj;
        return this.packageName.equals(item.packageName);
    }
}
