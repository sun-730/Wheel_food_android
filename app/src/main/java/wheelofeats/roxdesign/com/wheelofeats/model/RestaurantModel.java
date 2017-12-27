package wheelofeats.roxdesign.com.wheelofeats.model;

import android.media.Image;

/**
 * Created by admin on 26/08/2017.
 */

public class RestaurantModel {
    private String itemImgName, itemTitle1, itemTitle2;
    private Image featureImage;

    public String getItemImgName() {
        return itemImgName;
    }

    public void setItemImgName(String itemImgName) {
        this.itemImgName = itemImgName;
    }

    public String getItemTitle1() {
        return itemTitle1;
    }

    public void setItemTitle1(String itemTitle1) {
        this.itemTitle1 = itemTitle1;
    }

    public String getItemTitle2() {
        return itemTitle2;
    }

    public void setItemTitle2(String itemTitle2) {
        this.itemTitle2 = itemTitle2;
    }

    public Image getFeatureImage() {
        return featureImage;
    }

    public void setFeatureImage(Image featureImage) {
        this.featureImage = featureImage;
    }
}
