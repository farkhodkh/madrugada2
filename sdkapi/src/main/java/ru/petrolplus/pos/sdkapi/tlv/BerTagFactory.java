package ru.petrolplus.pos.sdkapi.tlv;

public interface BerTagFactory {
	BerTag createTag(byte[] aBuf, int aOffset, int aLength);
}
