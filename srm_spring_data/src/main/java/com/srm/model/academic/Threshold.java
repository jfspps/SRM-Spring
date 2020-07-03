package com.srm.model.academic;

public class Threshold {
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
