package ru.petroplus.pos.evotorsdk.tlv;

public interface IBerTlvLogger {

    boolean isDebugEnabled();

    void debug(String aFormat, Object ...args);
}
