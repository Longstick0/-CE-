package kr.ac.kpu.foodtukorea.domain;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Row {
    @SerializedName("SIGUN_CD")
    @Expose
    private String sigunCd;
    @SerializedName("SIGUN_NM")
    @Expose
    private String sigunNm;
    @SerializedName("ENTRPS_NM")
    @Expose
    private String entrpsNm;
    @SerializedName("APPONT_GRAD")
    @Expose
    private String appontGrad;
    @SerializedName("APPONT_DE")
    @Expose
    private String appontDe;
    @SerializedName("APPONT_INST_DIV_NM")
    @Expose
    private String appontInstDivNm;
    @SerializedName("RM")
    @Expose
    private Object rm;
    @SerializedName("REFINE_ROADNM_ADDR")
    @Expose
    private String refineRoadnmAddr;
    @SerializedName("REFINE_LOTNO_ADDR")
    @Expose
    private String refineLotnoAddr;
    @SerializedName("REFINE_WGS84_LAT")
    @Expose
    private String refineWgs84Lat;
    @SerializedName("REFINE_WGS84_LOGT")
    @Expose
    private String refineWgs84Logt;

    public String getSigunCd() {
        return sigunCd;
    }

    public void setSigunCd(String sigunCd) {
        this.sigunCd = sigunCd;
    }

    public String getSigunNm() {
        return sigunNm;
    }

    public void setSigunNm(String sigunNm) {
        this.sigunNm = sigunNm;
    }

    public String getEntrpsNm() {
        return entrpsNm;
    }

    public void setEntrpsNm(String entrpsNm) {
        this.entrpsNm = entrpsNm;
    }

    public String getAppontGrad() {
        return appontGrad;
    }

    public void setAppontGrad(String appontGrad) {
        this.appontGrad = appontGrad;
    }

    public String getAppontDe() {
        return appontDe;
    }

    public void setAppontDe(String appontDe) {
        this.appontDe = appontDe;
    }

    public String getAppontInstDivNm() {
        return appontInstDivNm;
    }

    public void setAppontInstDivNm(String appontInstDivNm) {
        this.appontInstDivNm = appontInstDivNm;
    }

    public Object getRm() {
        return rm;
    }

    public void setRm(Object rm) {
        this.rm = rm;
    }

    public String getRefineRoadnmAddr() {
        return refineRoadnmAddr;
    }

    public void setRefineRoadnmAddr(String refineRoadnmAddr) {
        this.refineRoadnmAddr = refineRoadnmAddr;
    }

    public String getRefineLotnoAddr() {
        return refineLotnoAddr;
    }

    public void setRefineLotnoAddr(String refineLotnoAddr) {
        this.refineLotnoAddr = refineLotnoAddr;
    }

    public String getRefineWgs84Lat() {
        return refineWgs84Lat;
    }

    public void setRefineWgs84Lat(String refineWgs84Lat) {
        this.refineWgs84Lat = refineWgs84Lat;
    }

    public String getRefineWgs84Logt() {
        return refineWgs84Logt;
    }

    public void setRefineWgs84Logt(String refineWgs84Logt) {
        this.refineWgs84Logt = refineWgs84Logt;
    }
}
