package com.air.safety.monitor;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CurrentValue {
    int pmValue, vocValue, co2Value, coValue;
    Long timestamp;

    public CurrentValue() {
    }

    public CurrentValue(int pmValue,int vocValue,int co2Value,int coValue) {
        this.pmValue = pmValue;
        this.vocValue = vocValue;
        this.co2Value = co2Value;
        this.coValue = coValue;

        this.timestamp = System.currentTimeMillis();
    }

    public int getPmValue() {
        return pmValue;
    }

    public int getVocValue() {
        return vocValue;
    }

    public int getCo2Value() {
        return co2Value;
    }

    public int getCoValue() {
        return coValue;
    }

    public long gettimestamp() { return  timestamp; }

}
