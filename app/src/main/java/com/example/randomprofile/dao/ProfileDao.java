package com.example.randomprofile.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.randomprofile.entity.Profile;

import java.util.List;

@Dao
public interface ProfileDao {

    @Query("SELECT * FROM profile")
    List<Profile> getAll();

    @Query("SELECT * FROM profile WHERE id LIKE :id")
    Profile findById(String id);

    @Insert
    void insert(Profile profile);

    @Delete
    void delete(Profile profile);
}