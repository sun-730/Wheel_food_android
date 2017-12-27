package wheelofeats.roxdesign.com;

import android.app.Application;
import android.content.Context;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class MyApplication extends Application{

    @Override
    public void onCreate() {
        super.onCreate();
//        RealmConfiguration config = new RealmConfiguration.Builder().build();
//        Realm.setDefaultConfiguration(config);
       Realm.init(this);
//        RealmConfiguration configuration = new RealmConfiguration().Builder(this).build();
//        Realm.setDefaultConfiguration(configuration);
    }
}
