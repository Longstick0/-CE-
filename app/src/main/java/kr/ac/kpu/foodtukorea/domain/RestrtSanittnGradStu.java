package kr.ac.kpu.foodtukorea.domain;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RestrtSanittnGradStu {

    @SerializedName("head")
    @Expose
    private List<Head> head = null;
    @SerializedName("row")
    @Expose
    private List<Row> row = null;

    public List<Head> getHead() {
        return head;
    }

    public void setHead(List<Head> head) {
        this.head = head;
    }

    public List<Row> getRow() {
        return row;
    }

    public void setRow(List<Row> row) {
        this.row = row;
    }
}
