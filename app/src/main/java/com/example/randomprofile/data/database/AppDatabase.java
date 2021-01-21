package com.example.randomprofile.data.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.randomprofile.dao.ProfileDao;
import com.example.randomprofile.entity.Profile;

@Database(entities = {Profile.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase singleInstance;

    public abstract ProfileDao profileDao();

    public static AppDatabase getInstance(Context context){
        if(singleInstance == null){
            singleInstance = Room.databaseBuilder(context, AppDatabase.class, "profiles-db").allowMainThreadQueries().build();
        }

        return singleInstance;
    }
}
