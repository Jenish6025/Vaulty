package com.example.datavaulty;

import java.util.ArrayList;

/**
 * Created by Nick on 3/3/17.
 */

public class Model_images {
    String str_folder;
    ArrayList<String> al_imagepath;
    boolean is_Selected = false;
    String item;

    public String getStr_folder() {
        return str_folder;
    }

    public void setStr_folder(String str_folder) {
        this.str_folder = str_folder;
    }

    public ArrayList<String> getAl_imagepath() {
        return al_imagepath;
    }

    public void setAl_imagepath(ArrayList<String> al_imagepath) {
        this.al_imagepath = al_imagepath;
    }

    public boolean isIs_Selected() {
        return is_Selected;
    }

    public void setIs_Selected(boolean is_Selected) {
        this.is_Selected = is_Selected;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }
}

