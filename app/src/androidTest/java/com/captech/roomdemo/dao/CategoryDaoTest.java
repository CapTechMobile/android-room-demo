package com.captech.roomdemo.dao;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.captech.roomdemo.database.AppDatabase;
import com.captech.roomdemo.domain.Category;

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
public class CategoryDaoTest {

    private CategoryDao categoryDao;
    private AppDatabase db;

    @Before
    public void setUp() throws Exception {
        Context context = InstrumentationRegistry.getTargetContext();
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase.class).build();
        categoryDao = db.getCategoryDao();
    }

    @After
    public void tearDown() throws Exception {
        db.close();
    }

    @Test
    public void shouldCreateDao() {
        assertNotNull(categoryDao);
    }

    @Test
    public void shouldInsertCategory() {
        Category category = new Category();
        category.setName("category name");
        long id = categoryDao.insert(category);
        List<Category> categories = categoryDao.getAll();

        assertEquals(1, categories.size());
        Category dbCategory = categories.get(0);
        assertEquals(id, dbCategory.getId());
        assertEquals(category.getName(), dbCategory.getName());
    }

    @Test
    public void shouldInsertCategoryWithId() {
        Category category = new Category();
        category.setName("category name");
        category.setId(99);
        categoryDao.insert(category);
        List<Category> categories = categoryDao.getAll();

        assertEquals(1, categories.size());
        Category dbCategory = categories.get(0);
        assertEquals(99, dbCategory.getId());
        assertEquals(category.getName(), dbCategory.getName());
    }

    @Test
    public void shouldDeleteCategory() {
        Category category = new Category();
        category.setName("category name");
        categoryDao.insert(category);
        List<Category> categories = categoryDao.getAll();

        assertEquals(1, categories.size());
        categoryDao.deleteAll(categories.get(0));
        categories = categoryDao.getAll();
        assertEquals(0, categories.size());
    }

}