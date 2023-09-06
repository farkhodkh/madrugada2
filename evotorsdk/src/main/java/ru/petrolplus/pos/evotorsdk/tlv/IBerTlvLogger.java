package ru.petrolplus.pos.evotorsdk.tlv;

public interface IBerTlvLogger {

    boolean isDebugEnabled();

    void debug(String aFormat, Object ...args);
}
