package com.captech.roomdemo.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.RoomWarnings;
import android.arch.persistence.room.Update;

import com.captech.roomdemo.domain.CategoryNote;
import com.captech.roomdemo.domain.Note;

import java.util.List;

/**
 * @author acampbell
 */
@Dao
public interface NoteDao {

    @Insert
    void insertAll(Note... notes);

    @Update
    void updateAll(Note... notes);

    @Query("SELECT * FROM note")
    List<Note> getAll();

    @SuppressWarnings(RoomWarnings.CURSOR_MISMATCH)
    @Query("SELECT note.id, note.title, note.description, category.name as categoryName " +
            "FROM note " +
            "LEFT JOIN category ON note.category_id = category.id")
    List<CategoryNote> getCategoryNotes();

    @Query("SELECT note.id, note.title, note.description, note.category_id " +
            "FROM note " +
            "LEFT JOIN category ON note.category_id = category.id " +
            "WHERE note.id = :noteId")
    CategoryNote getCategoryNote(long noteId);

    @Delete
    void deleteAll(Note... notes);
}
