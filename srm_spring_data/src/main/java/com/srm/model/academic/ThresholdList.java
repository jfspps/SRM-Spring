package com.srm.model.academic;

import com.srm.model.BaseEntity;

import java.util.List;

public class ThresholdList extends BaseEntity {

    private List<Threshold> thresholdList;

    public List<Threshold> getThresholdList() {
        return thresholdList;
    }

    public void setThresholdList(List<Threshold> thresholdList) {
        this.thresholdList = thresholdList;
    }
}
