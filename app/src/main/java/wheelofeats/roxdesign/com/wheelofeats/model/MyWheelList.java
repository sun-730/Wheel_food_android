package wheelofeats.roxdesign.com.wheelofeats.model;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by panda on 10/12/2017.
 */

public class MyWheelList extends RealmObject {
    @PrimaryKey
    private String id;
    private String name;
    private RealmList<MyWheelObject> savedWheels;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public RealmList<MyWheelObject> getSavedWheels() {
        return savedWheels;
    }

    public void setSavedWheels(RealmList<MyWheelObject> savedWheels) {
        this.savedWheels = savedWheels;
    }
}
