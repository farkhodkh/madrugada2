package ru.petrolplus.pos.evotorsdk.tlv;

public interface BerTagFactory {
	BerTag createTag(byte[] aBuf, int aOffset, int aLength);
}
