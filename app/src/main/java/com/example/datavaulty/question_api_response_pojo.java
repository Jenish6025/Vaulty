package com.example.datavaulty;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class question_api_response_pojo {

    @SerializedName("sequrityquestion_data")
    @Expose
    public List<SequrityquestionDatum>
            sequrityquestionData = null;

    public class SequrityquestionDatum {

        @SerializedName("questionid")
        @Expose
        public String questionid;
        @SerializedName("question")
        @Expose
        public String question;

    }
}