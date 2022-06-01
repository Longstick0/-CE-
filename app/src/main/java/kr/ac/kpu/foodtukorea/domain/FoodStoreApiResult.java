package kr.ac.kpu.foodtukorea.domain;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FoodStoreApiResult {
    @SerializedName("RestrtSanittnGradStus")
    @Expose
    private List<RestrtSanittnGradStu> restrtSanittnGradStus = null;

    public List<RestrtSanittnGradStu> getRestrtSanittnGradStus() {
        return restrtSanittnGradStus;
    }

    public void setRestrtSanittnGradStus(List<RestrtSanittnGradStu> restrtSanittnGradStus) {
        this.restrtSanittnGradStus = restrtSanittnGradStus;
    }
}
