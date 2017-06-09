package com.captech.roomdemo.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.captech.roomdemo.domain.Category;

import java.util.List;

/**
 * @author acampbell
 */
@Dao
public interface CategoryDao {

    @Insert
    long insert(Category category);

    @Query("SELECT * FROM category")
    List<Category> getAll();

    @Delete
    void deleteAll(Category... categories);
}
