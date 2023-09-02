package com.example.ceylincovip.Modal;

public class PurposeItem {

    private String label;
    private int value;

    public PurposeItem(String label, int value) {
        this.label = label;
        this.value = value;
    }

    public String getLabel() {
        return label;
    }

    public int getValue() {
        return value;
    }

    @Override
    public String toString() {
        return label;
    }
}
