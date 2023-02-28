package com.example.datavaulty;

public class PrivateImageModel {

    String ImagePath,Imagename;
    boolean IsSelected;

    public PrivateImageModel(String imagePath, boolean isSelected) {

        ImagePath = imagePath;
        IsSelected = isSelected;

    }

    public String getImagePath() {
        return ImagePath;
    }

    public void setImagePath(String imagePath) {
        ImagePath = imagePath;
    }

    public boolean isSelected() {
        return IsSelected;
    }

    public void setSelected(boolean selected) {
        IsSelected = selected;
    }
}
