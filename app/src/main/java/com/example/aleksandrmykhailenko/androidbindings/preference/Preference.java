package com.example.aleksandrmykhailenko.androidbindings.preference;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.aleksandrmykhailenko.androidbindings.Application;

import java.util.HashSet;
import java.util.Set;

public enum Preference {
    INSTANCE;

    private static final String DISALLOWED_PACKAGES = "DISALLOWED_PACKAGES";

    private static final String DISALLOWED_APPS_PREF = "DISALLOWED_APPS_PREF";

    public Set<String> getDisallowedPackages() {
        SharedPreferences sharedPreferences = getDisallowedAppsSharedPreferences();
        return sharedPreferences.getStringSet(DISALLOWED_PACKAGES, new HashSet<String>());
    }

    public void disallowPackage(String packageName) {
        Set<String> disallowedPackages = getDisallowedPackages();
        if (packageName == null || disallowedPackages.contains(packageName)) {
            return;
        }
        //Android forbids to make changes to set that returns from sharedPrefs, so we need to create a new set

        Set<String> newDisallowedPackages = new HashSet<>(disallowedPackages);
        newDisallowedPackages.add(packageName);

        SharedPreferences sharedPreferences = getDisallowedAppsSharedPreferences();
        sharedPreferences.edit()
                .putStringSet(DISALLOWED_PACKAGES, newDisallowedPackages)
                .apply();
    }

    public void allowPackage(String packageName) {
        Set<String> disallowedPackages = getDisallowedPackages();
        if (!disallowedPackages.contains(packageName)) {
            return;
        }
        //Android forbids to make changes to set that returns from sharedPrefs, so we need to create a new set
        Set<String> newDisallowedPackages = new HashSet<>(disallowedPackages);
        newDisallowedPackages.remove(packageName);

        SharedPreferences sharedPreferences = getDisallowedAppsSharedPreferences();
        sharedPreferences.edit()
                .putStringSet(DISALLOWED_PACKAGES, newDisallowedPackages)
                .apply();
    }

    private SharedPreferences getDisallowedAppsSharedPreferences() {
        return Application.getContext().getSharedPreferences(DISALLOWED_APPS_PREF,
                Context.MODE_PRIVATE);
    }
}
