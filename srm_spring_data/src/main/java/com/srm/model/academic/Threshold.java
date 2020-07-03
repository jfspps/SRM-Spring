package com.srm.model.academic;

import com.srm.model.BaseEntity;

public class Threshold extends BaseEntity {
    //maps a numerical score with a A*/B-/D+/MERIT/DISTINCTION etc...

    private int numerical;
    private String alphabetical;

    public int getNumerical() {
        return numerical;
    }

    public void setNumerical(int numerical) {
        this.numerical = numerical;
    }

    public String getAlphabetical() {
        return alphabetical;
    }

    public void setAlphabetical(String alphabetical) {
        this.alphabetical = alphabetical;
    }
}
