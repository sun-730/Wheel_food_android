package wheelofeats.roxdesign.com.wheelofeats.model;

import java.util.ArrayList;

/**
 * Created by panda on 10/12/2017.
 */

public class CustomWheelModel {
    private String customTitle;
    private String customDescription;

    public String getCustomID() {
        return customID;
    }

    public void setCustomID(String customID) {
        this.customID = customID;
    }

    private String customID;
    private ArrayList<String> customContents;

    public String getCustomTitle() {
        return customTitle;
    }

    public void setCustomTitle(String customTitle) {
        this.customTitle = customTitle;
    }

    public String getCustomDescription() {
        return customDescription;
    }

    public void setCustomDescription(String customDescription) {
        this.customDescription = customDescription;
    }

    public ArrayList<String> getCustomContents() {
        return customContents;
    }

    public void setCustomContents(ArrayList<String> customContents) {
        this.customContents = customContents;
    }
}
