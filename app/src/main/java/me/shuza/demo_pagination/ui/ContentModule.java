package me.shuza.demo_pagination.ui;

import android.content.Context;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Boka on 24-Aug-17.
 */

@Module
public class ContentModule {

    @Provides
    public MainActivityMVP.Presenter provideMainActivityPresenter(MainActivityMVP.Model model) {
        return new MainActivityPresenter(model);
    }

    @Provides
    public MainActivityMVP.Model provideMainActivityModel(Context context) {
        return new DataModel(context);
    }

}
