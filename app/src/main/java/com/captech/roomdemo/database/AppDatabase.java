package com.captech.roomdemo.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.captech.roomdemo.dao.CategoryDao;
import com.captech.roomdemo.dao.NoteDao;
import com.captech.roomdemo.domain.Category;
import com.captech.roomdemo.domain.Note;

/**
 * @author acampbell
 */
@Database(entities = {Note.class, Category.class}, version = 2)
public abstract class AppDatabase extends RoomDatabase {

    public static final String DB_NAME = "app_db";

    public abstract NoteDao getNoteDao();

    public abstract CategoryDao getCategoryDao();

}
