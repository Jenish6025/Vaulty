package com.example.datavaulty;

public class privateimagemodelunhide {

    String ImagePath,Imagename;
    boolean IsSelected;

    public privateimagemodelunhide(String imagePath,String Imagename) {

        ImagePath = imagePath;
        this.Imagename=Imagename;

    }


    public String getImagePath() {
        return ImagePath;
    }


    public String getImagename() {
        return Imagename;
    }

    public boolean isSelected() {
        return IsSelected;
    }

    public void setSelected(boolean selected) {
        IsSelected = selected;
    }
}
