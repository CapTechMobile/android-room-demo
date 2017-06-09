package com.captech.roomdemo.dao;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.captech.roomdemo.database.AppDatabase;
import com.captech.roomdemo.domain.Note;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static org.junit.Assert.*;

/**
 * @author acampbell
 */
@RunWith(AndroidJUnit4.class)
public class NoteDaoTest {

    private NoteDao noteDao;
    private CategoryDao categoryDao;
    private AppDatabase db;

    @Before
    public void setUp() throws Exception {
        Context context = InstrumentationRegistry.getTargetContext();
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase.class).build();
        noteDao = db.getNoteDao();
        categoryDao = db.getCategoryDao();
    }

    @After
    public void tearDown() throws Exception {
        db.close();
    }

    @Test
    public void shouldCreateDatabase() {
        assertNotNull(db);
    }

    @Test
    public void shouldCreateDao() {
        assertNotNull(noteDao);
        assertNotNull(categoryDao);
    }

    @Test
    public void shouldInsertNote() {
        Note note = new Note();
        note.setTitle("name1");
        note.setDescription("description1");
        noteDao.insertAll(note);
        List<Note> notes = noteDao.getAll();

        assertEquals(1, notes.size());
        Note dbNote = notes.get(0);
        assertEquals(note.getTitle(), dbNote.getTitle());
        assertEquals(note.getDescription(), dbNote.getDescription());
        assertEquals(1, dbNote.getId());
    }

    @Test
    public void shouldInsertTwoNotes() {
        Note note1 = new Note();
        note1.setTitle("name1");
        note1.setDescription("description1");

        Note note2 = new Note();
        note2.setTitle("name1");
        note2.setDescription("description2");

        noteDao.insertAll(note1, note2);
        List<Note> notes = noteDao.getAll();

        assertEquals(2, notes.size());
        Note dbNote1 = notes.get(0);
        assertEquals(note1.getTitle(), dbNote1.getTitle());
        assertEquals(note1.getDescription(), dbNote1.getDescription());
        assertEquals(1, dbNote1.getId());

        Note dbNote2 = notes.get(1);
        assertEquals(note2.getTitle(), dbNote2.getTitle());
        assertEquals(note2.getDescription(), dbNote2.getDescription());
        assertEquals(2, dbNote2.getId());
    }

    @Test
    public void shouldInsertNoteWithId() {
        Note note = new Note();
        note.setId(99);
        noteDao.insertAll(note);
        List<Note> notes = noteDao.getAll();
        assertEquals(1, notes.size());
        Note dbNote = notes.get(0);
        assertEquals(note.getId(), dbNote.getId());
    }

    @Test
    public void shouldDeleteNote() {
        Note note = new Note();
        note.setTitle("name1");
        noteDao.insertAll(note);
        List<Note> notes = noteDao.getAll();

        assertEquals(1, notes.size());
        noteDao.deleteAll(notes.get(0));
        notes = noteDao.getAll();
        assertEquals(0, notes.size());
    }

    @Test
    public void shouldUpdateNote() {
        Note note = new Note();
        note.setTitle("name1");
        noteDao.insertAll(note);
        List<Note> notes = noteDao.getAll();

        assertEquals(1, notes.size());
        Note dbNote = notes.get(0);
        assertEquals(note.getTitle(), dbNote.getTitle());

        dbNote.setTitle("name2");
        noteDao.updateAll(dbNote);
        notes = noteDao.getAll();
        assertEquals(1, notes.size());
        Note dbNote2 = notes.get(0);
        assertEquals(dbNote.getTitle(), dbNote2.getTitle());
    }

}