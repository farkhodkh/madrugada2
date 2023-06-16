package ru.petroplus.pos.nativelib;

public interface NativeCallListener {
    void onNativeVoidCall();

    void onNativeStringCall(String arg);
}
