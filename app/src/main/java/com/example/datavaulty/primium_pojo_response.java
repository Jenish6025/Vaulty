package com.example.datavaulty;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class primium_pojo_response {

    @SerializedName("package_data")
    @Expose
    public List<PackageDatum> packageData = null;
    public class PackageDatum {

        @SerializedName("packageid")
        @Expose
        public String packageid;
        @SerializedName("package")
        @Expose
        public String _package;
        @SerializedName("duration")
        @Expose
        public String duration;
        @SerializedName("price")
        @Expose
        public String price;
        @SerializedName("icon")
        @Expose
        public String icon;

    }
}
