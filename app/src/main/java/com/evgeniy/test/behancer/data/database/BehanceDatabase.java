package com.evgeniy.test.behancer.data.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.evgeniy.test.behancer.data.model.project.Cover;
import com.evgeniy.test.behancer.data.model.project.Owner;
import com.evgeniy.test.behancer.data.model.project.Project;
import com.evgeniy.test.behancer.data.model.user.Image;
import com.evgeniy.test.behancer.data.model.user.User;


@Database(entities = {Project.class, Cover.class, Owner.class, User.class, Image.class}, version = 1)
public abstract class BehanceDatabase extends RoomDatabase {

    public abstract BehanceDao getBehanceDao();
}
