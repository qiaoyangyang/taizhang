package com.meiling.common.base;

public class WheelItem implements IWheel {

    String label;

    public WheelItem(String label) {
        this.label = label;
    }

    @Override
    public String getShowText() {
        return label;
    }
}
