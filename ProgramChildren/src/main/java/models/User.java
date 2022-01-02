package models;

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

    public String getComputerId() {
        return computerId;
    }

    public void setComputerId(String computerId) {
        this.computerId = computerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getChildrenKey() {
        return childrenKey;
    }

    public void setChildrenKey(String childrenKey) {
        this.childrenKey = childrenKey;
    }

    public String getParentKey() {
        return parentKey;
    }

    public void setParentKey(String parentKey) {
        this.parentKey = parentKey;
    }
}
