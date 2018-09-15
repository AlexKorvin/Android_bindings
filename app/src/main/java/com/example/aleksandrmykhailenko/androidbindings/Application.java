package com.example.aleksandrmykhailenko.androidbindings;

public class Application extends android.app.Application {

    private static Application application;

    public Application() {
        application = this;
    }

    public static Application getContext() {
        return application;
    }
}
