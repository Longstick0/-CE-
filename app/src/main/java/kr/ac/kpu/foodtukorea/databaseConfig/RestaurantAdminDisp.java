package kr.ac.kpu.foodtukorea.databaseConfig;

public class RestaurantAdminDisp {

    private Long id;

    private String prcscitypoint_bsshnm; //업소명
    private String addr; //주소
    private String viltcn; //위반일자및위반내용
    private String dspscn; //처분내용
    private String dsps_dcsndt; //처분확정일자
    private String induty_cd_nm; //업종
    private String last_updt_dtm; //최종수정일
    private String laword_cd_nm; //위반법령
    private String Column11; //사업자 이름

    @Override
    public String toString() {
        return "상호명 : " + prcscitypoint_bsshnm + "\n" +
                "주소 : " + addr + "\n";
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPrcscitypoint_bsshnm() {
        return prcscitypoint_bsshnm;
    }

    public void setPrcscitypoint_bsshnm(String prcscitypoint_bsshnm) {
        this.prcscitypoint_bsshnm = prcscitypoint_bsshnm;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public String getViltcn() {
        return viltcn;
    }

    public void setViltcn(String viltcn) {
        this.viltcn = viltcn;
    }

    public String getDspscn() {
        return dspscn;
    }

    public void setDspscn(String dspscn) {
        this.dspscn = dspscn;
    }

    public String getDsps_dcsndt() {
        return dsps_dcsndt;
    }

    public void setDsps_dcsndt(String dsps_dcsndt) {
        this.dsps_dcsndt = dsps_dcsndt;
    }

    public String getInduty_cd_nm() {
        return induty_cd_nm;
    }

    public void setInduty_cd_nm(String induty_cd_nm) {
        this.induty_cd_nm = induty_cd_nm;
    }

    public String getLast_updt_dtm() {
        return last_updt_dtm;
    }

    public void setLast_updt_dtm(String last_updt_dtm) {
        this.last_updt_dtm = last_updt_dtm;
    }

    public String getLaword_cd_nm() {
        return laword_cd_nm;
    }

    public void setLaword_cd_nm(String laword_cd_nm) {
        this.laword_cd_nm = laword_cd_nm;
    }

    public String getColumn11() {
        return Column11;
    }

    public void setColumn11(String column11) {
        Column11 = column11;
    }
}
