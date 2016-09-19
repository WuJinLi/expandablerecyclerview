package com.steven.android32_expandablerecyclerview.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by StevenWang on 16/6/17.
 */
public class GroupModel {
    private String title;
    private boolean isExpand;
    private List<ChildModel> childModels;

    public GroupModel(String title) {
        this.title = title;
        childModels = new ArrayList<>();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<ChildModel> getChildModels() {
        return childModels;
    }

    public void setChildModels(List<ChildModel> childModels) {
        this.childModels = childModels;
    }

    public boolean isExpand() {
        return isExpand;
    }

    public void setIsExpand(boolean isExpand) {
        this.isExpand = isExpand;
    }
}
