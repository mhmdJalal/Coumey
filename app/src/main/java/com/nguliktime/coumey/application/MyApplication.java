package com.nguliktime.coumey.application;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.nguliktime.coumey.helper.RealmMigration;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        Realm.init(this);
        RealmConfiguration configuration = new RealmConfiguration.Builder()
                .name("courrin.db")
                .schemaVersion(5)
                .migration(new RealmMigration())
                .build();
        Realm.setDefaultConfiguration(configuration);
    }
}
