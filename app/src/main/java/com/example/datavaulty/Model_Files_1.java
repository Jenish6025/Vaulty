package com.example.datavaulty;

import java.io.File;

public class Model_Files_1 {
    File file;
    boolean IsSelected;

    public Model_Files_1(File file, boolean isSelected) {
        this.file = file;
        IsSelected = isSelected;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public boolean isSelected() {
        return IsSelected;
    }

    public void setSelected(boolean selected) {
        IsSelected = selected;
    }
}
