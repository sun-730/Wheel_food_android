package wheelofeats.roxdesign.com.wheelofeats.model;

import android.media.Image;

/**
 * Created by admin on 26/08/2017.
 */

public class FeatureModel {
    private String featureImgName, featureTitle, featureDescription, featurePrice;
    private Image featureImage;

    public String getFeatureImgName() {
        return featureImgName;
    }

    public void setFeatureImgName(String featureImgName) {
        this.featureImgName = featureImgName;
    }

    public String getFeatureTitle() {
        return featureTitle;
    }

    public void setFeatureTitle(String featureTitle) {
        this.featureTitle = featureTitle;
    }

    public String getFeatureDescription() {
        return featureDescription;
    }

    public void setFeatureDescription(String featureDescription) {
        this.featureDescription = featureDescription;
    }

    public String getFeaturePrice() {
        return featurePrice;
    }

    public void setFeaturePrice(String featurePrice) {
        this.featurePrice = featurePrice;
    }

    public Image getFeatureImage() {
        return featureImage;
    }

    public void setFeatureImage(Image featureImage) {
        this.featureImage = featureImage;
    }
}
