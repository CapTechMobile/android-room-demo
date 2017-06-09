package com.captech.roomdemo.activity;

import android.arch.persistence.room.Room;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.support.v7.widget.helper.ItemTouchHelper.SimpleCallback;
import android.view.View;
import android.view.View.OnClickListener;

import com.captech.roomdemo.R;
import com.captech.roomdemo.adapter.NoteAdapter;
import com.captech.roomdemo.adapter.NoteAdapter.ActionCallback;
import com.captech.roomdemo.database.AppDatabase;
import com.captech.roomdemo.domain.CategoryNote;
import com.captech.roomdemo.domain.Note;

import java.util.List;

public class MainActivity extends AppCompatActivity implements ActionCallback, OnClickListener {

    private NoteAdapter adapter;
    private AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = Room.databaseBuilder(this, AppDatabase.class, AppDatabase.DB_NAME).build();
        findViewById(R.id.add).setOnClickListener(this);
        adapter = new NoteAdapter(this);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        ItemTouchHelper.SimpleCallback callback = new SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, ViewHolder viewHolder, ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(ViewHolder viewHolder, int direction) {
                deleteNote(adapter.getNote(viewHolder.getAdapterPosition()));
            }
        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(callback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadNotes();
    }

    @Override
    public void onEdit(CategoryNote note) {
        Intent intent = new Intent(this, EditNoteActivity.class);
        intent.putExtras(EditNoteActivity.newInstanceBundle(note.getId()));
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add:
                startActivity(new Intent(this, EditNoteActivity.class));
                break;
        }
    }

    private void loadNotes() {
        new AsyncTask<Void, Void, List<CategoryNote>>() {
            @Override
            protected List<CategoryNote> doInBackground(Void... params) {
                return db.getNoteDao().getCategoryNotes();
            }

            @Override
            protected void onPostExecute(List<CategoryNote> notes) {
                adapter.setNotes(notes);
            }
        }.execute();
    }

    private void deleteNote(Note note) {
        new AsyncTask<Note, Void, Void>() {
            @Override
            protected Void doInBackground(Note... params) {
                db.getNoteDao().deleteAll(params);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                loadNotes();
            }
        }.execute(note);
    }
}
