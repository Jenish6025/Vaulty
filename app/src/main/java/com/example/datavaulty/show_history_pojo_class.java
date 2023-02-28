package com.example.datavaulty;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;


public class show_history_pojo_class {

    @SerializedName("hidehistory_data")
    @Expose
    public List<HidehistoryDatum> hidehistoryData = null;

    public class HidehistoryDatum {

        @SerializedName("deviceid")
        @Expose
        public String deviceid;
        @SerializedName("hidehistoryid")
        @Expose
        public String hidehistoryid;
        @SerializedName("totalitem")
        @Expose
        public String totalitem;
        @SerializedName("type")
        @Expose
        public String type;
        @SerializedName("hidedate")
        @Expose
        public String hidedate;

    }

}
