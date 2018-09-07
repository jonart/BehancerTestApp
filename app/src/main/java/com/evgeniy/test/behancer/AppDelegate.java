package com.evgeniy.test.behancer;

import android.app.Application;
import android.arch.persistence.room.Room;

import com.evgeniy.test.behancer.data.database.BehanceDatabase;
import com.evgeniy.test.behancer.data.Storage;

public class AppDelegate extends Application {

    private Storage mStorage;

    @Override
    public void onCreate() {
        super.onCreate();

        final BehanceDatabase database = Room.databaseBuilder(this, BehanceDatabase.class, "behance_database")
                .fallbackToDestructiveMigration()
                .build();

        mStorage = new Storage(database.getBehanceDao());
    }

    public Storage getStorage() {
        return mStorage;
    }
}
