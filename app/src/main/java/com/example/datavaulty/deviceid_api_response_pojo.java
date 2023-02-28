package com.example.datavaulty;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class deviceid_api_response_pojo {

    @SerializedName("Response")
    @Expose
    public String response;
    @SerializedName("deviceid")
    @Expose
    public String deviceid;
    @SerializedName("device")
    @Expose
    public String device;

}
