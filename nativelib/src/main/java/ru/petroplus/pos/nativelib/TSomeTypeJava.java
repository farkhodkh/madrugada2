package ru.petroplus.pos.nativelib;

public class TSomeTypeJava {

    private int intValue;
    private double doubleValue;
    private char charValue;
    private boolean boolValue;
    private String strValue;

    public TSomeTypeJava(
            int intValue,
            double doubleValue,
            boolean boolValue,
            char charValue,
            String strValue) {
        this.intValue = intValue;
        this.doubleValue = doubleValue;
        this.boolValue = boolValue;
        this.charValue = charValue;
        this.strValue = strValue;
    }

    public int getIntValue() {
        return intValue;
    }

    public void setIntValue(int intValue) {
        this.intValue = intValue;
    }

    public double getDoubleValue() {
        return doubleValue;
    }

    public void setDoubleValue(double doubleValue) {
        this.doubleValue = doubleValue;
    }

    public char getCharValue() {
        return charValue;
    }

    public void setCharValue(char charValue) {
        this.charValue = charValue;
    }

    public boolean isBoolValue() {
        return boolValue;
    }

    public void setBoolValue(boolean boolValue) {
        this.boolValue = boolValue;
    }

    public String getStrValue() {
        return strValue;
    }

    public void setStrValue(String strValue) {
        this.strValue = strValue;
    }
}
