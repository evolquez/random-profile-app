package com.example.randomprofile.dao;

import androidx.room.Dao;
import androidx.room.Query;

import com.example.randomprofile.entity.Profile;

import java.util.List;

@Dao
public interface ProfileDao {

    @Query("SELECT * FROM profile")
    List<Profile> getAll();
}
