package com.captech.roomdemo.domain;

/**
 * @author acampbell
 */
public class CategoryNote extends Note {

    private String categoryName;

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}
