package lebe.lebeprototyp02.market;

/**
 * Created by Höling on 13.11.2016.
 */

public class MarketItem {
    private String name;
    private String imgpath;
    private String ddlpath;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImgpath() {
        return imgpath;
    }

    public void setImgpath(String imgpath) {
        this.imgpath = imgpath;
    }

    public String getDdlpath() {
        return ddlpath;
    }

    public void setDdlpath(String ddlpath) {
        this.ddlpath = ddlpath;
    }

    public String getDiscription() {
        return discription;
    }

    public void setDiscription(String discription) {
        this.discription = discription;
    }

    private String discription;


}
