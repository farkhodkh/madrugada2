package ru.petrolplus.pos.sdkapi.tlv;

public class DefaultBerTagFactory implements BerTagFactory {

    @Override
    public BerTag createTag(byte[] aBuf, int aOffset, int aLength) {
        return new BerTag(aBuf, aOffset, aLength);
    }
}
