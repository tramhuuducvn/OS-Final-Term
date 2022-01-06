package children.models;

import java.io.Serializable;

public class User implements Serializable {
    String computerId;
    String name;
    String childrenKey;
    String parentKey;

    public User(String computerId, String name, String childrenKey, String parentKey) {
        this.computerId = computerId;
        this.name = name;
        this.childrenKey = childrenKey;
        this.parentKey = parentKey;
    }

    public String getChildrenKey() {
        return childrenKey;
    }

    public String getParentKey() {
        return parentKey;
    }

}
