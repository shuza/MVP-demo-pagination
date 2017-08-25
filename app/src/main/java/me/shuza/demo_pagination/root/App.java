package me.shuza.demo_pagination.root;

import android.app.Application;

import me.shuza.demo_pagination.ui.ContentModule;

/**
 * Created by Boka on 24-Aug-17.
 */

public class App extends Application {
    private ApplicationComponent component;

    @Override
    public void onCreate() {
        super.onCreate();

        component = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .contentModule(new ContentModule())
                .build();
    }

    public ApplicationComponent getComponent() {
        return component;
    }
}
