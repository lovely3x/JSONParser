package com.lovely3x.jsonpareser.bean;

public class Index {
    private String title;

    private String des;

    private String zs;

    private String tipt;


    public Index() {
    }

    public Index(String des, String title, String zs, String tipt) {
        this.des = des;
        this.title = title;
        this.zs = zs;
        this.tipt = tipt;
    }

    public String getDes() {
        return this.des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getZs() {
        return this.zs;
    }

    public void setZs(String zs) {
        this.zs = zs;
    }

    public String getTipt() {
        return this.tipt;
    }

    public void setTipt(String tipt) {
        this.tipt = tipt;
    }

    public String toString() {
        return new StringBuilder()
                .append("Index = { ")
                .append("des").append(" = ").append(des)
                .append(',').append("title").append(" = ").append(title)
                .append(',').append("zs").append(" = ").append(zs)
                .append(',').append("tipt").append(" = ").append(tipt)
                .append('}').toString();
    }
}