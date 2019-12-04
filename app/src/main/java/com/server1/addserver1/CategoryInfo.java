package com.server1.addserver1;

public class CategoryInfo {
    String category_name;
    String category_info;

    public CategoryInfo(String category_name, String category_info) {
        this.category_name = category_name;
        this.category_info = category_info;
    }

    public String getCategory_info() {
        return category_info;
    }

    public String getCategory_name() {
        return category_name;
    }
}
