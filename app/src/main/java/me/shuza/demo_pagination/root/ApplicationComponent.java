package me.shuza.demo_pagination.root;

import javax.inject.Singleton;

import dagger.Component;
import me.shuza.demo_pagination.ui.ContentModule;
import me.shuza.demo_pagination.ui.MainActivity;

/**
 * Created by Boka on 24-Aug-17.
 */

@Singleton
@Component(modules = {ApplicationModule.class, ContentModule.class})
public interface ApplicationComponent {

    void inject(MainActivity target);
}
